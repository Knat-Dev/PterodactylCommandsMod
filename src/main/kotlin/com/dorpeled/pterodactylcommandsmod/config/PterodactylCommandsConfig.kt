package com.dorpeled.pterodactylcommandsmod.config

import com.dorpeled.pterodactylcommandsmod.network.RestClient
import com.dorpeled.pterodactylcommandsmod.util.PterodactylUrlBuilder
import com.mojang.logging.LogUtils
import net.minecraftforge.common.ForgeConfigSpec
import org.slf4j.Logger
import java.net.MalformedURLException
import java.net.URL
import java.net.http.HttpResponse
import java.util.*

object PterodactylCommandsConfig {
    private val LOGGER: Logger? = LogUtils.getLogger()
    private val BUILDER: ForgeConfigSpec.Builder = ForgeConfigSpec.Builder()
    val config: ForgeConfigSpec
    val BASE_URL: ForgeConfigSpec.ConfigValue<String>
    val API_KEY: ForgeConfigSpec.ConfigValue<String>
    val SERVER_ID: ForgeConfigSpec.ConfigValue<String>

    init {
        BUILDER.push("General")
        BASE_URL = BUILDER.comment("Pterodactyl panel base url").define("baseUrl", "")
        API_KEY = BUILDER.comment("API Key for the Backup Command").define("apiKey", "")
        SERVER_ID = BUILDER.comment("The Pterodactyl server id").define("serverId", "")
        BUILDER.pop()
        config = BUILDER.build()
    }

    fun validate(): Boolean {
        var isValid =
            validateBaseUrl(BASE_URL.get()) && validateApiKey(API_KEY.get()) && validateServerId(SERVER_ID.get())
        if (isValid) {
            val url: String = PterodactylUrlBuilder.instance.getServerUrl()
            LOGGER?.info("Validating configuration with URL: $url")
            isValid = try {
                val response: HttpResponse<String?>? = RestClient.sendGetRequest(url)
                response?.statusCode() == 200
            } catch (e: Exception) {
                LOGGER?.error("Validation of some configuration values failed. Please check your configuration file.")
                false
            }
        }
        return isValid
    }

    fun validateBaseUrl(baseUrl: String?): Boolean {
        if (baseUrl != null) {
            if (baseUrl.isEmpty()) {
                LOGGER?.error("Base URL is missing. Please check your configuration file.")
                return false
            }
        }

        try {
            URL(baseUrl)
        } catch (e: MalformedURLException) {
            LOGGER?.error("Base URL is not a valid URL. Please check your configuration file.")
            return false
        }
        return true
    }

    fun validateApiKey(apiKey: String?): Boolean {
        if (apiKey != null) {
            if (apiKey.isEmpty()) {
                LOGGER?.error("API key is missing. Please check your configuration file.")
                return false
            }
        }
        val regex = Regex("^ptlc_[a-zA-Z0-9]{43}$")
        if (apiKey != null) {
            if (!apiKey.matches(regex)) {
                LOGGER?.error("API key is not valid. Please check your configuration file.")
                return false
            }
        }
        return true
    }

    fun validateServerId(serverId: String?): Boolean {
        if (serverId != null) {
            if (serverId.isEmpty()) {
                LOGGER?.error("Server ID is missing. Please check your configuration file.")
                return false
            }
        }
        try {
            UUID.fromString(serverId)
        } catch (e: IllegalArgumentException) {
            LOGGER?.error("Server ID is not a valid UUID. Please check your configuration file.")
            return false
        }
        return true
    }
}
