package com.runly.auth.data

import com.runly.auth.data.models.LoginRequest
import com.runly.auth.data.models.LoginResponse
import com.runly.auth.data.models.RegisterRequest
import com.runly.auth.domain.AuthRepository
import com.runly.core.data.network.post
import com.runly.core.domain.auth.AuthInfo
import com.runly.core.domain.auth.SessionStorage
import com.runly.core.domain.utils.DataError
import io.ktor.client.HttpClient
import com.runly.core.domain.utils.EmptyDataResult
import com.runly.core.domain.utils.Result
import com.runly.core.domain.utils.asEmptyDataResult

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage

): AuthRepository {
    override suspend fun register(email: String, password: String): EmptyDataResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
    }

    override suspend fun login(
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> {
        val result= httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )
        if(result is Result.Success){
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )

        }
        return result.asEmptyDataResult()
    }
}