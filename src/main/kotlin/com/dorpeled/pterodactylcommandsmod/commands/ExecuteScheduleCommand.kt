package com.dorpeled.pterodactylcommandsmod.commands

import com.dorpeled.pterodactylcommandsmod.models.Schedule
import com.dorpeled.pterodactylcommandsmod.network.RestClient
import com.dorpeled.pterodactylcommandsmod.util.PterodactylUrlBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import org.apache.http.HttpStatus
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture


object ExecuteScheduleCommand {
    fun register(dispatcher: CommandDispatcher<CommandSourceStack?>?) {
        dispatcher?.register(
            Commands.literal("pterodactyl").apply {
                then(
                    Commands.literal("execute_schedule").apply {
                        then(
                            Commands.argument("scheduleName", StringArgumentType.word())
                                .suggests { _, builder -> getSuggestions(builder) }
                                .executes(ExecuteScheduleCommand::executeSchedule)
                        )
                    }
                )
            }
        )
    }

    private fun getSuggestions(builder: SuggestionsBuilder?): CompletableFuture<Suggestions?>? {
        val url = PterodactylUrlBuilder.instance.getScheduleListUrl()
        return ScheduleUtils.fetchScheduleData(url)
            ?.let { ScheduleUtils.parseScheduleResponse(it) }
            ?.let { ScheduleUtils.buildSuggestions(it, builder) }
            ?: CompletableFuture.completedFuture(builder?.build())
    }

    private fun executeSchedule(context: CommandContext<CommandSourceStack?>?): Int {
        val scheduleName = StringArgumentType.getString(context, "scheduleName").replace("_", " ")
        val scheduleId = ScheduleUtils.getScheduleIdByName(scheduleName)
            ?: return ScheduleUtils.handleFailure(context, "Schedule '$scheduleName' not found")

        if (ScheduleUtils.scheduleHasPowerAction(scheduleId)) {
            return ScheduleUtils.handleFailure(context, "Execution of schedules with power actions is not allowed")
        }

        val url = PterodactylUrlBuilder.instance.getScheduleExecuteUrl(scheduleId)
        val response = ScheduleUtils.executeScheduleCommand(url)

        return when (response?.statusCode()) {
            HttpStatus.SC_ACCEPTED -> ScheduleUtils.handleSuccess(context, scheduleName)
            else -> ScheduleUtils.handleFailure(context, "Failed to execute schedule")
        }
    }
}


private fun Schedule.ServerSchedule.hasPowerAction(): Boolean {
    return this.getAttributes()?.getRelationships()?.getTasks()?.getData()
        ?.any { it?.getAttributes()?.getAction() == "power" } == true
}

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

    fun parseScheduleResponse(response: HttpResponse<String?>?): Schedule? {
        return if (response == null) null else
            when (response.statusCode()) {
                HttpStatus.SC_OK -> ObjectMapper().readValue(response.body(), Schedule::class.java)
                    .also { schedule = it }

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

    fun handleSuccess(context: CommandContext<CommandSourceStack?>?, scheduleName: String): Int {
        context?.source?.sendSuccess({ Component.literal("Schedule '$scheduleName' executed successfully") }, false)
        return 1
    }

    fun handleFailure(context: CommandContext<CommandSourceStack?>?, message: String): Int {
        context?.source?.sendFailure(Component.literal(message))
        return 0
    }

    fun scheduleHasPowerAction(scheduleId: String): Boolean {
        return schedule?.getData()
            ?.find { it?.getAttributes()?.getId().toString() == scheduleId }
            ?.hasPowerAction() ?: false
    }
}
