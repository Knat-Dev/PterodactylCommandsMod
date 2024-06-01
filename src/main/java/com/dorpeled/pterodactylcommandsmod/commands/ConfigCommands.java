package com.dorpeled.pterodactylcommandsmod.commands;

import com.dorpeled.pterodactylcommandsmod.config.PterodactylCommandsConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

public class ConfigCommands {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("pterodactyl").then(Commands.literal("setconfig").then(Commands.argument("key", StringArgumentType.word()).suggests(ConfigCommands::suggestConfigKeys).then(Commands.argument("value", StringArgumentType.greedyString()).executes(context -> {
            String key = StringArgumentType.getString(context, "key");
            String value = StringArgumentType.getString(context, "value");
            return setConfig(context.getSource(), key, value);
        })))));
    }

    private static CompletableFuture<Suggestions> suggestConfigKeys(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        builder.suggest("baseUrl");
        builder.suggest("apiKey");
        builder.suggest("serverId");
        return builder.buildFuture();
    }

    private static int setConfig(CommandSourceStack source, String key, String value) {
        switch (key) {
            case "baseUrl" -> {
                if (!PterodactylCommandsConfig.validateBaseUrl(value) || value.isEmpty()) {
                    LOGGER.error("Invalid URL: " + value);
                    source.sendFailure(Component.literal("Invalid URL: " + value));
                    return 0;
                }
                PterodactylCommandsConfig.BASE_URL.set(value);
            }
            case "apiKey" -> {
                if (!PterodactylCommandsConfig.validateApiKey(value) || value.isEmpty()) {
                    LOGGER.error("API key is not valid.");
                    source.sendFailure(Component.literal("API key is not valid."));
                    return 0;
                }
                PterodactylCommandsConfig.API_KEY.set(value);
            } case "serverId" -> {
                if (!PterodactylCommandsConfig.validateServerId(value) || value.isEmpty()) {
                    LOGGER.error("Server ID is not valid.");
                    source.sendFailure(Component.literal("Server ID is not valid."));
                    return 0;
                }
                PterodactylCommandsConfig.SERVER_ID.set(value);
            }
            default -> {
                LOGGER.error("Invalid config key: " + key);
                return 0;
            }
        }

        source.sendSuccess(() -> Component.literal("Config value set: " + key + " = " + value), true);

        if (PterodactylCommandsConfig.validate()) {
            source.sendSuccess(() -> Component.literal("All configurations are set correctly."), true);
        }

        return 1;
    }


}