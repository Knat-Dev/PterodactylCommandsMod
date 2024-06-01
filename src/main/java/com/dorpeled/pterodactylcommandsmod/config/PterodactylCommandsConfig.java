package com.dorpeled.pterodactylcommandsmod.config;

import com.dorpeled.pterodactylcommandsmod.network.RestClient;
import com.dorpeled.pterodactylcommandsmod.util.PterodactylUrlBuilder;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.ForgeConfigSpec;
import org.slf4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpResponse;
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
        BASE_URL = BUILDER.comment("Pterodactyl panel base url").define("baseUrl", "");
        API_KEY = BUILDER.comment("API Key for the Backup Command").define("apiKey", "");
        SERVER_ID = BUILDER.comment("The Pterodactyl server id").define("serverId", "");
        BUILDER.pop();

        config = BUILDER.build();
    }

    public static boolean validate() {
        boolean isValid = validateBaseUrl(BASE_URL.get()) &&
                validateApiKey(API_KEY.get()) &&
                validateServerId(SERVER_ID.get());

        if (isValid) {
            String url = PterodactylUrlBuilder.getInstance()
                    .baseUrl(BASE_URL.get())
                    .endpoint("/api/client/servers/")
                    .param(SERVER_ID.get())
                    .build();

            try {
                HttpResponse<String> response = RestClient.sendGetRequest(url);

                isValid = response.statusCode() == 200;
            } catch (Exception e) {
                LOGGER.error("Validation of some configuration values failed. Please check your configuration file.");
                isValid = false;
            }
        }
        return isValid;
    }

    public static boolean validateBaseUrl(String baseUrl) {
        if (baseUrl.isEmpty()) {
            LOGGER.error("Base URL is missing. Please check your configuration file.");
            return false;
        }

        try {
            new URL(baseUrl);
        } catch (MalformedURLException e) {
            LOGGER.error("Base URL is not a valid URL. Please check your configuration file.");
            return false;
        }

        return true;
    }

    public static boolean validateApiKey(String apiKey) {
        if (apiKey.isEmpty()) {
            LOGGER.error("API key is missing. Please check your configuration file.");
            return false;
        }

        String regex = "^ptlc_[a-zA-Z0-9]{43}$";
        if (!apiKey.matches(regex)) {
            LOGGER.error("API key is not valid. Please check your configuration file.");
            return false;
        }

        return true;
    }

    public static boolean validateServerId(String serverId) {
        if (serverId.isEmpty()) {
            LOGGER.error("Server ID is missing. Please check your configuration file.");
            return false;
        }

        try {
            UUID uuid = UUID.fromString(serverId);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Server ID is not a valid UUID. Please check your configuration file.");
            return false;
        }

        return true;
    }
}
