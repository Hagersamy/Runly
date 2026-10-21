package com.runly.run.domain

import com.runly.core.domain.location.LocationWithAltitude
import kotlinx.coroutines.flow.Flow


interface LocationObserver {
    fun observeLocation(interval: Long): Flow<LocationWithAltitude> // return height from specific point sea
    // خط العرض
    // Longitude  خط الطول

}