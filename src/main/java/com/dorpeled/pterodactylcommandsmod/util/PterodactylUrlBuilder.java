package com.dorpeled.pterodactylcommandsmod.util;

import java.util.ArrayList;
import java.util.List;

public class PterodactylUrlBuilder {
    private static PterodactylUrlBuilder instance;
    private String baseUrl;
    private String endpoint;
    private List<String> params;

    private PterodactylUrlBuilder() {
        params = new ArrayList<>();
    }

    public static PterodactylUrlBuilder getInstance() {
        if (instance == null) {
            instance = new PterodactylUrlBuilder();
        }
        return instance;
    }

    public PterodactylUrlBuilder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public PterodactylUrlBuilder endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public PterodactylUrlBuilder param(String param) {
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

        return urlBuilder.toString();
    }
}