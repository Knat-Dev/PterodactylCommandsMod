package com.dorpeled.pterodactylcommandsmod.commands

import com.dorpeled.pterodactylcommandsmod.util.ScheduleUtils
import com.dorpeled.pterodactylcommandsmod.util.PterodactylUrlBuilder
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import org.apache.http.HttpStatus
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
            ?.let { response -> ScheduleUtils.parseScheduleResponse(response) }
            ?.let { schedule -> ScheduleUtils.buildSuggestions(schedule, builder) }
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