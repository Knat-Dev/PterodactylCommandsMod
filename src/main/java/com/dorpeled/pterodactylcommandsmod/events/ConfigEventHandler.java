package com.dorpeled.pterodactylcommandsmod.events;

import com.dorpeled.pterodactylcommandsmod.PterodactylCommandsMod;
import com.dorpeled.pterodactylcommandsmod.config.PterodactylCommandsConfig;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.slf4j.Logger;


public class ConfigEventHandler {

    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public void onConfigLoad(ModConfigEvent.Loading configEvent) {
        LOGGER.info("Configuration has been loaded for: {}", configEvent.getConfig().getFileName());
        logConfigValues();
    }

    @SubscribeEvent
    public void onConfigReload(ModConfigEvent.Reloading configEvent) {
        LOGGER.info("Configuration has been reloaded for: {}", configEvent.getConfig().getFileName());
        logConfigValues();
    }

    private void logConfigValues() {
        LOGGER.info("API_KEY: {}", PterodactylCommandsConfig.API_KEY.get());
        LOGGER.info("BASE_URL: {}", PterodactylCommandsConfig.BASE_URL.get());
        LOGGER.info("SERVER_ID: {}", PterodactylCommandsConfig.SERVER_ID.get());
    }
}
