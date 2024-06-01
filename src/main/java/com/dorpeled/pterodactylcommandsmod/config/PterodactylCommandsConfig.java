package com.dorpeled.pterodactylcommandsmod.config;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.ForgeConfigSpec;
import org.slf4j.Logger;

public class PterodactylCommandsConfig {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec config;
    public static final ForgeConfigSpec.ConfigValue<String> BASE_URL;
    public static final ForgeConfigSpec.ConfigValue<String> API_KEY;
    public static final ForgeConfigSpec.ConfigValue<String> SERVER_ID;

    static {
        BUILDER.push("General");
        BASE_URL = BUILDER.comment("Pterodactyl panel base url")
                .define("baseUrl", "");
        API_KEY = BUILDER.comment("API Key for the Backup Command")
                .define("apiKey", "");
        SERVER_ID = BUILDER.comment("The Pterodactyl server id")
                .define("serverId", "");
        BUILDER.pop();

        config = BUILDER.build();
    }
}
