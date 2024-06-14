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
    private var schedule: Schedule? = null
    fun register(dispatcher: CommandDispatcher<CommandSourceStack?>?) {
        dispatcher?.register(
            Commands.literal("pterodactyl")
                .then(Commands.literal("execute_schedule")
                    .then(Commands.argument("scheduleName", StringArgumentType.word())
                        .suggests { context, builder -> getSuggestions(builder) }
                        .executes(ExecuteScheduleCommand::executeSchedule)
                    )
                )
        )
    }

    private fun getSuggestions(builder: SuggestionsBuilder?): CompletableFuture<Suggestions?>? {
        val url: String? = PterodactylUrlBuilder.getInstance()?.getScheduleListUrl()

        try {
            val response: HttpResponse<String?>? = RestClient.sendGetRequest(url)
            if (response?.statusCode() == HttpStatus.SC_OK) {
                val mapper = ObjectMapper()
                schedule = mapper.readValue(response.body(), Schedule::class.java)
                val numberOfSchedules: Int = schedule?.getData()?.size ?: 0
                if (numberOfSchedules == 0) {
                    return CompletableFuture.completedFuture(builder?.build())
                }
                for (data in schedule?.getData()!!) {
                    builder?.suggest(data?.getAttributes()?.getName()?.replace(" ", "_"))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return CompletableFuture.completedFuture(builder?.build())
    }

    private fun executeSchedule(context: CommandContext<CommandSourceStack?>?): Int {
        val scheduleName: String = StringArgumentType.getString(context, "scheduleName")
        var scheduleId: String? = ""
        for (data in schedule?.getData()!!) {
            if (data?.getAttributes()?.getName().equals(scheduleName.replace("_", " "))) {
                scheduleId = data?.getAttributes()?.getId().toString()
                break
            }
        }
        if (scheduleId.equals("")) {
            context?.source?.sendFailure(Component.literal("Schedule not found"))
            return 0
        }
        val url: String? = PterodactylUrlBuilder.getInstance()?.getScheduleExecuteUrl(scheduleId)
        return try {
            val response: HttpResponse<String?>? = RestClient.sendPostRequest(url, "\"{}\"")
            if (response?.statusCode() == HttpStatus.SC_ACCEPTED) {
                context?.getSource()?.sendSuccess({ Component.literal("Schedule executed successfully") }, false)
                1
            } else {
                context?.getSource()?.sendFailure(Component.literal("Failed to execute schedule"))
                0
            }
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
}