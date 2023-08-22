package com.yuriikonovalov.githubclient.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Divider(modifier: Modifier = Modifier) {
    androidx.compose.material3.Divider(
        modifier = modifier.fillMaxWidth(),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
    )
}