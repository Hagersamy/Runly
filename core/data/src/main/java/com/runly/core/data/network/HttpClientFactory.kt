package com.runly.core.data.network

import com.runly.core.data.BuildConfig
import com.runly.core.data.network.auth.AccessTokenRequest
import com.runly.core.data.network.auth.AccessTokenResponse
import com.runly.core.domain.auth.AuthInfo
import com.runly.core.domain.auth.SessionStorage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import timber.log.Timber
import com.runly.core.domain.utils.Result


class HttpClientFactory(
    private val sessionStorage: SessionStorage

) {
    fun build(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d(message)
                    }
                }
                // Full logging exposes passwords and tokens, so only do it in debug builds
                level = if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                header("x-api-key", BuildConfig.API_KEY)
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val info = sessionStorage.get()
                        BearerTokens(
                            accessToken = info?.accessToken ?: "",
                            refreshToken = info?.refreshToken ?: ""
                        )
                    }
                    refreshTokens {
                        val info = sessionStorage.get()
                        val response = safeCall<AccessTokenResponse> {
                            client.post {
                                url(constructRoute("/accessToken"))
                                setBody(
                                    AccessTokenRequest(
                                        refreshToken = info?.refreshToken ?: "",
                                        userId = info?.userId ?: ""
                                    )
                                )
                                // Prevents a 401 on this request from triggering another refresh
                                markAsRefreshTokenRequest()
                            }
                        }
                        if (response is Result.Success) {
                            val newAuthInfo = AuthInfo(
                                accessToken = response.data.accessToken,
                                refreshToken = info?.refreshToken ?: "",
                                userId = info?.userId ?: ""
                            )
                            sessionStorage.set(newAuthInfo)

                            BearerTokens(
                                accessToken = newAuthInfo.accessToken,
                                refreshToken = newAuthInfo.refreshToken
                            )
                        } else {
                            // Refresh token is no longer valid, so the session is over
                            sessionStorage.set(null)
                            null
                        }
                    }
                }
            }
        }
    }
}
