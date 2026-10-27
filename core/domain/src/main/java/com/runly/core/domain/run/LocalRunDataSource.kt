package com.runly.core.domain.run

import com.runly.core.domain.utils.DataError
import com.runly.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow


typealias RunId = String

interface LocalRunDataSource {
    fun getRuns(): Flow<List<Run>>
    suspend fun upsertRun(run: Run): Result<RunId, DataError.Local>
    suspend fun upsertRuns(runs: List<Run>): Result<List<RunId>, DataError.Local>
    suspend fun deleteRun(id: String)
    suspend fun deleteAllRuns()
}