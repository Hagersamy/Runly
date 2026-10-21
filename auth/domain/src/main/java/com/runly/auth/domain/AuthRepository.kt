package com.runly.auth.domain

import com.runly.core.domain.utils.DataError
import com.runly.core.domain.utils.EmptyDataResult

interface AuthRepository {
    suspend fun register(email: String, password: String): EmptyDataResult<DataError.Network>
    suspend fun login(email: String, password: String): EmptyDataResult<DataError.Network>
}