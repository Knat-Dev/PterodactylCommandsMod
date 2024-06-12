package com.dorpeled.pterodactylcommandsmod.util;

import com.dorpeled.pterodactylcommandsmod.config.PterodactylCommandsConfig;

import java.util.ArrayList;
import java.util.List;

public class PterodactylUrlBuilder {
    private static PterodactylUrlBuilder instance;
    private String baseUrl;
    private String endpoint;
    private List<String> params;

    private PterodactylUrlBuilder() {
        this.baseUrl = PterodactylCommandsConfig.BASE_URL.get();
        params = new ArrayList<>();
    }

    public static PterodactylUrlBuilder getInstance() {
        if (instance == null) {
            instance = new PterodactylUrlBuilder();
        }
        return instance;
    }

    public PterodactylUrlBuilder endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public PterodactylUrlBuilder    param(String param) {
        this.params.add(param);
        return this;
    }

    public String build() {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        urlBuilder.append(endpoint);

        for (String param : params) {
            urlBuilder.append("/");
            urlBuilder.append(param);
        }

        params.clear();
        endpoint = null;

        return urlBuilder.toString();
    }

    public String getServerUrl() {
        return this.endpoint("/api/client/servers")
                .param(PterodactylCommandsConfig.SERVER_ID.get())
                .build();
    }

    public String getScheduleListUrl() {
        return this.endpoint("/api/client/servers")
                .param(PterodactylCommandsConfig.SERVER_ID.get())
                .param("schedules")
                .build();
    }

    public String getScheduleExecuteUrl(String scheduleId) {
        return this.endpoint("/api/client/servers")
                .param(PterodactylCommandsConfig.SERVER_ID.get())
                .param("schedules")
                .param(scheduleId)
                .param("execute")
                .build();
    }
}