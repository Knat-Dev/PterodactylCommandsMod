package com.dorpeled.pterodactylcommandsmod.util

import com.dorpeled.pterodactylcommandsmod.config.PterodactylCommandsConfig


class PterodactylUrlBuilder private constructor() {
    private val baseUrl: String? = PterodactylCommandsConfig.BASE_URL.get()
    private var endpoint: String? = "\"/api/client/servers\""
    private val params: ArrayList<String?>?

    init {
        params = ArrayList()
    }

    private fun param(param: String?): PterodactylUrlBuilder {
        params?.add(param)
        return this
    }

    private fun build(): String {
        val urlBuilder = StringBuilder(baseUrl)
        urlBuilder.append(endpoint)
        if (params != null) {
            for (param in params) {
                urlBuilder.append("/")
                urlBuilder.append(param)
            }
        }
        params?.clear()
        endpoint = null
        return urlBuilder.toString()
    }

    fun getServerUrl(): String {
        return param(PterodactylCommandsConfig.SERVER_ID.get())
            .build()
    }

    fun getScheduleListUrl(): String {
        return param(PterodactylCommandsConfig.SERVER_ID.get())
            .param("schedules")
            .build()
    }

    fun getScheduleExecuteUrl(scheduleId: String?): String {
        return param(PterodactylCommandsConfig.SERVER_ID.get())
            .param("schedules")
            .param(scheduleId)
            .param("execute")
            .build()
    }

    companion object {
        private var instance: PterodactylUrlBuilder? = null
        fun getInstance(): PterodactylUrlBuilder? {
            if (instance == null) {
                instance = PterodactylUrlBuilder()
            }
            return instance
        }
    }
}