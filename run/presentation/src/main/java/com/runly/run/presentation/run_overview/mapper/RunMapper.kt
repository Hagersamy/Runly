package com.runly.run.presentation.run_overview.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.runly.core.domain.run.Run
import com.runly.core.presentation.ui.formatted
import com.runly.core.presentation.ui.toFormattedKm
import com.runly.core.presentation.ui.toFormattedKmh
import com.runly.core.presentation.ui.toFormattedMeters
import com.runly.core.presentation.ui.toFormattedPace
import com.runly.run.presentation.run_overview.model.RunUi
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun Run.toRunUi(): RunUi {
    val dateTimeInLocalTime = dateTimeUtc
        .withZoneSameInstant(ZoneId.systemDefault())
    val formattedDateTime = DateTimeFormatter
        .ofPattern("MMM dd, yyyy - hh:mma")
        .format(dateTimeInLocalTime)

    val distanceKm = distanceMeters / 1000.0

    return RunUi(
        id = id!!,
        duration = duration.formatted(),
        dateTime = formattedDateTime,
        distance = distanceKm.toFormattedKm(),
        avgSpeed = avgSpeedKmh.toFormattedKmh(),
        maxSpeed = maxSpeedKmh.toFormattedKmh(),
        pace = duration.toFormattedPace(distanceKm),
        totalElevation = totalElevationMeters.toFormattedMeters(),
        mapPictureUrl = mapPictureUrl
    )
}