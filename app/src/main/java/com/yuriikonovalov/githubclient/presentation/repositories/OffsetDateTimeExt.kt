package com.yuriikonovalov.githubclient.presentation.repositories

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import com.yuriikonovalov.githubclient.R
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@Composable
fun OffsetDateTime.toUiString(): String {
    val dateOfLastUpdate = this.toLocalDate().atStartOfDay()
    val today = LocalDate.now().atStartOfDay()

    val days = ChronoUnit.DAYS.between(dateOfLastUpdate, today).toInt()

    return pluralStringResource(R.plurals.last_update_date, days, days)
}