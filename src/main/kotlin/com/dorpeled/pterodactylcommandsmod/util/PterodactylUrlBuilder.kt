package com.dorpeled.pterodactylcommandsmod.util

import com.dorpeled.pterodactylcommandsmod.config.PterodactylCommandsConfig

class PterodactylUrlBuilder private constructor() {
    private val baseUrl: String = PterodactylCommandsConfig.BASE_URL.get() ?: throw IllegalStateException("Base URL not configured")
    private var endpoint: String = "/api/client/servers"
    private val params: MutableList<String> = mutableListOf()

    private fun param(param: String): PterodactylUrlBuilder = apply { params.add(param) }

    private fun build(): String = StringBuilder(baseUrl).apply {
        append(endpoint)
        params.forEach { append("/").append(it) }
        params.clear()
    }.toString()

    fun getServerUrl(): String = param(PterodactylCommandsConfig.SERVER_ID.get()).build()

    fun getScheduleListUrl(): String = param(PterodactylCommandsConfig.SERVER_ID.get()).param("schedules").build()

    fun getScheduleExecuteUrl(scheduleId: String): String = param(PterodactylCommandsConfig.SERVER_ID.get()).param("schedules").param(scheduleId).param("execute").build()

    companion object {
        val instance: PterodactylUrlBuilder by lazy { PterodactylUrlBuilder() }
    }
}
