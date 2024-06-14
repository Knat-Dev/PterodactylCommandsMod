package com.dorpeled.pterodactylcommandsmod.commands

import com.dorpeled.pterodactylcommandsmod.config.PterodactylCommandsConfig
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import com.mojang.logging.LogUtils
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import org.slf4j.Logger
import java.util.concurrent.CompletableFuture

object ConfigCommands {
    private val LOGGER: Logger? = LogUtils.getLogger()
    fun register(dispatcher: CommandDispatcher<CommandSourceStack?>?) {
        dispatcher?.register(
            Commands.literal("pterodactyl").then(
                Commands.literal("setconfig").then(
                    Commands.argument("key", StringArgumentType.word())
                        .suggests(ConfigCommands::suggestConfigKeys)
                        .then(Commands.argument("value", StringArgumentType.greedyString()).executes { context ->
                            val key: String = StringArgumentType.getString(context, "key")
                            val value: String = StringArgumentType.getString(context, "value")
                            setConfig(context.source, key, value)
                        })
                )
            )
        )
    }

    private fun suggestConfigKeys(
        context: CommandContext<CommandSourceStack?>?,
        builder: SuggestionsBuilder?
    ): CompletableFuture<Suggestions?>? {
        builder?.suggest("baseUrl")
        builder?.suggest("apiKey")
        builder?.suggest("serverId")
        return builder?.buildFuture()
    }

    private fun setConfig(source: CommandSourceStack?, key: String?, value: String): Int {
        when (key) {
            "baseUrl" -> {

                PterodactylCommandsConfig.BASE_URL.set(value)
            }

            "apiKey" -> {
                    if (!PterodactylCommandsConfig.validateApiKey(value) || value.isEmpty()) {
                        LOGGER?.error("API key is not valid.")
                        source?.sendFailure(Component.literal("API key is not valid."))
                        return 0
                    }
                PterodactylCommandsConfig.API_KEY.set(value)
            }

            "serverId" -> {
                    if (!PterodactylCommandsConfig.validateServerId(value) || value.isEmpty()) {
                        LOGGER?.error("Server ID is not valid.")
                        source?.sendFailure(Component.literal("Server ID is not valid."))
                        return 0
                    }
                PterodactylCommandsConfig.SERVER_ID.set(value)
            }

            else -> {
                LOGGER?.error("Invalid config key: $key")
                return 0
            }
        }
        source?.sendSuccess({ Component.literal("Config value set: $key = $value") }, true)
        if (PterodactylCommandsConfig.validate()) {
            source?.sendSuccess({ Component.literal("All configurations are set correctly.") }, true)
        }
        return 1
    }
}