package com.knat.pterodactylcommandsmod;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.slf4j.Logger;

import static org.apache.logging.log4j.core.util.Assert.isNonEmpty;

@Mod.EventBusSubscriber(modid = PterodactylCommandsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec config;

    public static final ForgeConfigSpec.ConfigValue<String> BASE_URL;
    public static final ForgeConfigSpec.ConfigValue<String> API_KEY;
    public static final ForgeConfigSpec.ConfigValue<String> SERVER_ID;
    private static final Logger LOGGER = LogUtils.getLogger();

    static {
        BUILDER.push("General");
        BASE_URL = BUILDER.comment("Pterodactyl panel base url")
                .define("baseUrl", "");
        API_KEY = BUILDER.comment("API Key for the Backup Command")
                .define("apiKey", "");
        SERVER_ID = BUILDER.comment("The Pterodactyl server id")
                .define("serverId","");
        BUILDER.pop();

        config = BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading event) {
        LOGGER.info("Loaded config file {}", event.getConfig().getFileName());
    }

    public static boolean isConfigValid() {
        return isNonEmpty(BASE_URL.get()) && isNonEmpty(API_KEY.get()) && isNonEmpty(SERVER_ID.get());
    }
}
