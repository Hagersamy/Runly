package com.runly.run.domain

import com.runly.core.domain.location.LocationTimestamp
import kotlin.time.Duration

data class RunData(
    val distanceMeters: Int = 0,
    val pace: Duration = Duration.ZERO, // this is the time i need to run ( total type / distance)
    val locations: List<List<LocationTimestamp>> = emptyList())
