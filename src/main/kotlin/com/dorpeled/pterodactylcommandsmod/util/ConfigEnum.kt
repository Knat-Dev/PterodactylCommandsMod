package com.dorpeled.pterodactylcommandsmod.util

enum class ConfigKey(private val key: String) {
    BASE_URL("baseUrl"),
    API_KEY("apiKey"),
    SERVER_ID("serverId");

    override fun toString(): String = key

}