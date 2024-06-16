package com.dorpeled.pterodactylcommandsmod.commands

import com.dorpeled.pterodactylcommandsmod.config.PterodactylCommandsConfig
import com.dorpeled.pterodactylcommandsmod.util.ConfigKey
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import com.mojang.logging.LogUtils
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraftforge.common.ForgeConfigSpec
import org.slf4j.Logger
import java.util.concurrent.CompletableFuture

object ConfigCommands {
    private val LOGGER: Logger? = LogUtils.getLogger()
    fun register(dispatcher: CommandDispatcher<CommandSourceStack?>?) {
        dispatcher?.register(
            Commands.literal("pterodactyl").then(
                Commands.literal("set_config").then(
                    Commands.argument("key", StringArgumentType.word())
                        .suggests { _, builder -> suggestConfigKeys(builder) }
                        .then(
                            Commands.argument("value", StringArgumentType.greedyString())
                                .executes { context ->
                                    val key: String = StringArgumentType.getString(context, "key")
                                    val value: String = StringArgumentType.getString(context, "value")
                                    setConfig(context.source, key, value)
                                })
                )
            )
        )
    }

    private fun suggestConfigKeys(builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        ConfigKey.entries.forEach { entry -> builder.suggest(entry.toString()) }
        return builder.buildFuture()
    }

    private fun setConfig(source: CommandSourceStack, key: String, value: String): Int {
        val configKey = ConfigKey.entries.find { it.toString() == key }
        if (configKey == null) {
            LOGGER?.error("Invalid config key: $key")
            source.sendFailure(Component.literal("Invalid config key: $key"))
            return 0
        }

        return when (configKey) {
            ConfigKey.BASE_URL -> validateAndSetConfig(
                source,
                key,
                value,
                PterodactylCommandsConfig::validateBaseUrl,
                PterodactylCommandsConfig.BASE_URL
            )

            ConfigKey.API_KEY -> validateAndSetConfig(
                source,
                key,
                value,
                PterodactylCommandsConfig::validateApiKey,
                PterodactylCommandsConfig.API_KEY
            )

            ConfigKey.SERVER_ID -> validateAndSetConfig(
                source,
                key,
                value,
                PterodactylCommandsConfig::validateServerId,
                PterodactylCommandsConfig.SERVER_ID
            )
        }
    }

    private fun validateAndSetConfig(
        source: CommandSourceStack,
        key: String,
        value: String,
        validator: (String?) -> Boolean,
        config: ForgeConfigSpec.ConfigValue<String>
    ): Int {
        if (!validator(value)) {
            LOGGER?.error("$key is not valid.")
            source.sendFailure(Component.literal("$key is not valid."))
            return 0
        }

        config.set(value)
        source.sendSuccess({ Component.literal("Config value set: $key = $value") }, true)

        if (PterodactylCommandsConfig.validate()) {
            source.sendSuccess({ Component.literal("All configurations are set correctly.") }, true)
        }

        return 1
    }
}