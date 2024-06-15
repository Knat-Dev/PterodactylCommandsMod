package com.dorpeled.pterodactylcommandsmod.network

import com.dorpeled.pterodactylcommandsmod.config.PterodactylCommandsConfig
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

object RestClient {
    private var httpClient: HttpClient? = null
    private fun getHttpClient(): HttpClient? {
        if (httpClient == null) {
            httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build()
        }
        return httpClient
    }

    @Throws(Exception::class)
    fun sendGetRequest(url: String?): HttpResponse<String?>? {
        val request: HttpRequest = HttpRequest.newBuilder()
            .GET()
            .uri(url?.let { URI.create(it) })
            .setHeader("User-Agent", "Java 11 HttpClient Bot")
            .header("Content-Type", "application/json")
            .setHeader("Authorization", "Bearer " + PterodactylCommandsConfig.API_KEY.get())
            .build()
        return getHttpClient()?.send(request, HttpResponse.BodyHandlers.ofString())
    }

    @Throws(Exception::class)
    fun sendPostRequest(url: String?, body: String?): HttpResponse<String?>? {
        val request: HttpRequest = HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .uri(url?.let { URI.create(it) })
            .setHeader("User-Agent", "Java 11 HttpClient Bot")
            .setHeader("Authorization", "Bearer " + PterodactylCommandsConfig.API_KEY.get())
            .header("Content-Type", "application/json")
            .build()
        return httpClient?.send(request, HttpResponse.BodyHandlers.ofString())
    }

    @Throws(Exception::class)
    fun sendPutRequest(url: String?, body: String?): HttpResponse<String?>? {
        val request: HttpRequest = HttpRequest.newBuilder()
            .PUT(HttpRequest.BodyPublishers.ofString(body))
            .uri(url?.let { URI.create(it) })
            .setHeader("User-Agent", "Java 11 HttpClient Bot")
            .setHeader("Authorization", "Bearer " + PterodactylCommandsConfig.API_KEY.get())
            .header("Content-Type", "application/json")
            .build()
        return httpClient?.send(request, HttpResponse.BodyHandlers.ofString())
    }

    @Throws(Exception::class)
    fun sendDeleteRequest(url: String?): HttpResponse<String?>? {
        val request: HttpRequest = HttpRequest.newBuilder()
            .DELETE()
            .uri(url?.let { URI.create(it) })
            .setHeader("User-Agent", "Java 11 HttpClient Bot")
            .setHeader("Authorization", "Bearer " + PterodactylCommandsConfig.API_KEY.get())
            .header("Content-Type", "application/json")
            .build()
        return httpClient?.send(request, HttpResponse.BodyHandlers.ofString())
    }

    @Throws(Exception::class)
    fun sendPatchRequest(url: String?, body: String?): HttpResponse<String?>? {
        val request: HttpRequest = HttpRequest.newBuilder()
            .method("PATCH", HttpRequest.BodyPublishers.ofString(body))
            .uri(url?.let { URI.create(it) })
            .setHeader("User-Agent", "Java 11 HttpClient Bot")
            .setHeader("Authorization", "Bearer " + PterodactylCommandsConfig.API_KEY.get())
            .header("Content-Type", "application/json")
            .build()
        return httpClient?.send(request, HttpResponse.BodyHandlers.ofString())
    }
}