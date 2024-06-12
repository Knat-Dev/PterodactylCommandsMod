package com.dorpeled.pterodactylcommandsmod.commands;

import com.dorpeled.pterodactylcommandsmod.models.Schedule;
import com.dorpeled.pterodactylcommandsmod.network.RestClient;
import com.dorpeled.pterodactylcommandsmod.util.PterodactylUrlBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.apache.http.HttpStatus;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class ExecuteScheduleCommand {
    static Schedule schedule;
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("pterodactyl")
                .then(Commands.literal("execute_schedule")
                        .then(Commands.argument("scheduleName", StringArgumentType.word())
                                .suggests((context, builder) -> getSuggestions(builder))
                                .executes(ExecuteScheduleCommand::executeSchedule))));
    }

    private static CompletableFuture<Suggestions> getSuggestions(SuggestionsBuilder builder) {
        String url = PterodactylUrlBuilder.getInstance().getScheduleListUrl();

        try {
            HttpResponse<String> response = RestClient.sendGetRequest(url);

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                schedule = mapper.readValue(response.body(), Schedule.class);

                int numberOfSchedules = schedule.getData().size();
                if (numberOfSchedules == 0) {
                    return CompletableFuture.completedFuture(builder.build());
                }

                for (Schedule.ServerSchedule data : schedule.getData()) {
                    builder.suggest(data.getAttributes().getName().replaceAll("\\s+","_"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(builder.build());
    }

    private static int executeSchedule(CommandContext<CommandSourceStack> context) {
        String scheduleName = StringArgumentType.getString(context, "scheduleName");
        String scheduleId = "";

        for (Schedule.ServerSchedule data : schedule.getData()) {
            if (data.getAttributes().getName().equals(scheduleName.replaceAll("_"," "))) {
                scheduleId =  String.valueOf(data.getAttributes().getId());
                break;
            }
        }

        if (scheduleId.equals("")) {
            context.getSource().sendFailure(Component.literal("Schedule not found"));
            return 0;
        }

        String url = PterodactylUrlBuilder.getInstance().getScheduleExecuteUrl(scheduleId);

        try {
            HttpResponse<String> response = RestClient.sendPostRequest(url, "\"{}\"");

            if (response.statusCode() == HttpStatus.SC_ACCEPTED) {
                context.getSource().sendSuccess(() -> Component.literal("Schedule executed successfully"), false);
                return 1;
            } else {
                context.getSource().sendFailure(Component.literal("Failed to execute schedule"));
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}