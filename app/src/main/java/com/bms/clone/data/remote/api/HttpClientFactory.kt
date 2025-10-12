package com.bms.clone.data.remote.api

import com.bms.clone.utils.ApiConstants
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun  create(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                })
            }

            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }

            defaultRequest {
                url(ApiConstants.BASE_URL)
                header("Authorization", "Bearer ${ApiConstants.ACCESS_TOKEN}")
                header("accept", "application/json")
            }


            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 10000L
                socketTimeoutMillis = 15000L
            }
        }
    }
}