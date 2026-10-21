package com.runly.run.location

import com.runly.core.domain.location.Location

import com.runly.core.domain.location.LocationWithAltitude

fun android.location.Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location = Location(
            latitude = latitude,
            longitude = longitude
        ),
        altitude = altitude
    )
}