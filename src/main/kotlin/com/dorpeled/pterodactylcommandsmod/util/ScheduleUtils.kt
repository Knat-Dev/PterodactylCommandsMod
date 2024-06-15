package com.dorpeled.pterodactylcommandsmod.util

import com.dorpeled.pterodactylcommandsmod.models.Schedule
import com.dorpeled.pterodactylcommandsmod.network.RestClient
import com.fasterxml.jackson.databind.ObjectMapper
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import org.apache.http.HttpStatus
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture

object ScheduleUtils {
    private var schedule: Schedule? = null

    fun fetchScheduleData(url: String): HttpResponse<String?>? {
        return try {
            RestClient.sendGetRequest(url)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun parseScheduleResponse(response: HttpResponse<String?>): Schedule? {
        return when (response.statusCode()) {
            HttpStatus.SC_OK -> ObjectMapper().readValue(response.body(), Schedule::class.java)
                .also { schedule -> this.schedule = schedule }

            else -> null
        }
    }

    fun buildSuggestions(schedule: Schedule?, builder: SuggestionsBuilder?): CompletableFuture<Suggestions?>? {
        schedule?.getData()
            ?.filterNot { it?.hasPowerAction() ?: false }
            ?.forEach { serverSchedule ->
                val scheduleName: String? = serverSchedule?.getAttributes()?.getName()
                builder?.suggest(scheduleName?.replace(" ", "_"))
            }
        return CompletableFuture.completedFuture(builder?.build())
    }

    fun getScheduleIdByName(scheduleName: String): String? {
        return schedule?.getData()
            ?.find { it?.getAttributes()?.getName().equals(scheduleName, ignoreCase = true) }
            ?.getAttributes()
            ?.getId()
            ?.toString()
    }

    fun executeScheduleCommand(url: String): HttpResponse<String?>? {
        return try {
            RestClient.sendPostRequest(url, "\"{}\"")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun handleSuccess(context: CommandContext<CommandSourceStack>, scheduleName: String): Int {
        context.source?.sendSuccess({ Component.literal("Schedule '$scheduleName' executed successfully") }, false)
        return 1
    }

    fun handleFailure(context: CommandContext<CommandSourceStack>, message: String): Int {
        context.source?.sendFailure(Component.literal(message))
        return 0
    }

    fun scheduleHasPowerAction(scheduleId: String): Boolean {
        return schedule?.getData()
            ?.find { it?.getAttributes()?.getId().toString() == scheduleId }
            ?.hasPowerAction() ?: false
    }
}