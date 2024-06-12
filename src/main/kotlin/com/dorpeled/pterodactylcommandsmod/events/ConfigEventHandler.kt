package com.dorpeled.pterodactylcommandsmod.events

import com.dorpeled.pterodactylcommandsmod.config.PterodactylCommandsConfig
import com.mojang.logging.LogUtils
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.event.config.ModConfigEvent
import org.slf4j.Logger


class ConfigEventHandler {
    @SubscribeEvent
    fun onConfigLoad(configEvent: ModConfigEvent.Loading?) {
        LOGGER?.info("Configuration has been loaded for: {}", configEvent?.config?.fileName)
        logConfigValues()
    }

    @SubscribeEvent
    fun onConfigReload(configEvent: ModConfigEvent.Reloading?) {
        if (configEvent != null) {
            LOGGER?.info("Configuration has been reloaded for: {}", configEvent.config?.fileName)
        }
        logConfigValues()
    }

    private fun logConfigValues() {
        LOGGER?.info("API_KEY: {}", PterodactylCommandsConfig.API_KEY.get())
        LOGGER?.info("BASE_URL: {}", PterodactylCommandsConfig.BASE_URL.get())
        LOGGER?.info("SERVER_ID: {}", PterodactylCommandsConfig.SERVER_ID.get())
    }

    companion object {
        private val LOGGER: Logger? = LogUtils.getLogger()
    }
}
