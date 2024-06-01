package com.dorpeled.pterodactylcommandsmod.config;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.ForgeConfigSpec;
import org.slf4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

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

    public static void validate() {
        validateBaseUrl(BASE_URL.get());
        validateApiKey(API_KEY.get());
        validateServerId(SERVER_ID.get());
    }

    private static void validateBaseUrl(String baseUrl) {
        if (baseUrl.isEmpty()) {
            LOGGER.error("Base URL is missing. Please check your configuration file.");
            throw new IllegalStateException("Base URL is missing. Please check your configuration file.");
        }

        try {
            new URL(baseUrl);
        } catch (MalformedURLException e) {
            LOGGER.error("Base URL is not a valid URL. Please check your configuration file.");
            throw new IllegalStateException("Base URL is not a valid URL. Please check your configuration file.", e);
        }
    }

    private static void validateApiKey(String apiKey) {
        if (apiKey.isEmpty()) {
            LOGGER.error("API key is missing. Please check your configuration file.");
            throw new IllegalStateException("API key is missing. Please check your configuration file.");
        }

        String regex = "^ptlc_[a-zA-Z0-9]{40}$";
        if (!apiKey.matches(regex)) {
            LOGGER.error("API key is not valid. Please check your configuration file.");
            throw new IllegalStateException("API key is not valid. Please check your configuration file.");
        }
    }

    private static void validateServerId(String serverId) {
        if (serverId.isEmpty()) {
            LOGGER.error("Server ID is missing. Please check your configuration file.");
            throw new IllegalStateException("Server ID is missing. Please check your configuration file.");
        }

        try {
            Integer.parseInt(serverId);
        } catch (NumberFormatException e) {
            LOGGER.error("Server ID is not a valid integer. Please check your configuration file.");
            throw new IllegalStateException("Server ID is not a valid integer. Please check your configuration file.", e);
        }
    }
}
