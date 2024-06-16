package com.dorpeled.pterodactylcommandsmod.util

import com.dorpeled.pterodactylcommandsmod.models.ServerScheduleList
import com.dorpeled.pterodactylcommandsmod.models.ServerScheduleWrapper
import com.dorpeled.pterodactylcommandsmod.network.RestClient
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import org.apache.http.HttpStatus
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture

object ScheduleUtils {
    private var serverScheduleList: ServerScheduleList? = null
    private val objectMapper = ObjectMapper().apply {
        registerModule(JavaTimeModule())
    }

    fun fetchScheduleData(url: String): HttpResponse<String?>? {
        return try {
            RestClient.sendGetRequest(url)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun parseScheduleResponse(response: HttpResponse<String?>): ServerScheduleList? {
        return when (response.statusCode()) {
            HttpStatus.SC_OK -> response.body()?.let {
                objectMapper.readValue<ServerScheduleList>(it).also { serverScheduleList ->
                    this.serverScheduleList = serverScheduleList
                }
            }

            else -> null

        }
    }

    fun buildSuggestions(
        schedule: ServerScheduleList?,
        builder: SuggestionsBuilder?
    ): CompletableFuture<Suggestions?>? {
        schedule?.data
            ?.filterNot { it.hasPowerAction() }
            ?.forEach { serverSchedule ->
                val scheduleName: String = serverSchedule.attributes.name
                builder?.suggest(scheduleName.replace(" ", "_"))
            }
        return CompletableFuture.completedFuture(builder?.build())
    }

    fun getScheduleIdByName(scheduleName: String): String? {
        return serverScheduleList?.data
            ?.find { it.attributes.name.equals(scheduleName, ignoreCase = true) }
            ?.attributes
            ?.id
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
        return serverScheduleList?.data
            ?.find { it.attributes.id.toString() == scheduleId }
            ?.hasPowerAction() ?: false
    }
}
