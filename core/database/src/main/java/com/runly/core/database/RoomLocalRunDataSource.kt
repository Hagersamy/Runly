package com.runly.core.database

import android.database.sqlite.SQLiteFullException
import android.os.Build
import androidx.annotation.RequiresApi
import com.runly.core.database.dao.RunDao
import com.runly.core.database.mappers.toRun
import com.runly.core.database.mappers.toRunEntity
import com.runly.core.domain.run.LocalRunDataSource
import com.runly.core.domain.run.Run
import com.runly.core.domain.run.RunId
import com.runly.core.domain.utils.DataError
import com.runly.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalRunDataSource(private val runDao: RunDao) : LocalRunDataSource {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getRuns(): Flow<List<Run>> {
        return runDao.getRuns().map { runEntities ->
            runEntities.map { it.toRun() }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun upsertRun(run: Run): Result<RunId, DataError.Local> {
        return try {
            val entity = run.toRunEntity()
            runDao.upsertRun(entity)
            Result.Success(entity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun upsertRuns(runs: List<Run>): Result<List<RunId>, DataError.Local> {
        return try {
            val entities = runs.map { it.toRunEntity() }
            runDao.upsertRuns(entities)
            Result.Success(entities.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteRun(id: String) {
        runDao.deleteRun(id)
    }

    override suspend fun deleteAllRuns() {
        runDao.deleteAllRuns()
    }
}