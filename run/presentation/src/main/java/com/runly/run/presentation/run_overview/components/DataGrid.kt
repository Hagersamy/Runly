package com.runly.run.presentation.run_overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.runly.run.presentation.run_overview.model.RunUi
import com.runly.run.presentation.R
import com.runly.run.presentation.run_overview.model.RunDataUi
import kotlin.math.max

@Composable
fun DataGrid(
    run: RunUi,
    modifier: Modifier = Modifier
) {
    val runDataUiList = listOf(
        RunDataUi(
            name = stringResource(id = R.string.distance),
            value = run.distance
        ),
        RunDataUi(
            name = stringResource(id = R.string.pace),
            value = run.pace
        ),
        RunDataUi(
            name = stringResource(id = R.string.avg_speed),
            value = run.avgSpeed
        ),
        RunDataUi(
            name = stringResource(id = R.string.max_speed),
            value = run.maxSpeed
        ),
        RunDataUi(
            name = stringResource(id = R.string.total_elevation),
            value = run.totalElevation
        ),
    )
    var maxWidth by remember {
        mutableIntStateOf(0)
    }
    val maxWidthDp = with(LocalDensity.current) { maxWidth.toDp() }
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        runDataUiList.forEach { run ->
            DataGridCell(
                runData = run,
                modifier = Modifier
                    .defaultMinSize(minWidth = maxWidthDp)
                    .onSizeChanged {
                        maxWidth = max(maxWidth, it.width)
                    }
            )
        }
    }
}