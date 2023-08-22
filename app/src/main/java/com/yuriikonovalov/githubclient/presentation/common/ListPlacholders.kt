@file:Suppress("FunctionName")

package com.yuriikonovalov.githubclient.presentation.common

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yuriikonovalov.githubclient.R

fun LazyListScope.LoadingNextPageIndicator(
    modifier: Modifier,
    visible: Boolean,
    @StringRes textResId: Int
) {
    if (visible)
        item {
            LoadingDataIndicator(
                modifier = modifier.padding(vertical = 16.dp),
                text = stringResource(id = textResId)
            )
        }
}

fun LazyListScope.LoadingNextPageError(
    modifier: Modifier,
    visible: Boolean,
    message: String?,
    onRetryClick: () -> Unit
) {
    if (visible)
        item {
            LoadingDataError(
                modifier = modifier.padding(vertical = 16.dp),
                text = stringResource(
                    R.string.loading_data_error,
                    message ?: stringResource(R.string.something_went_wrong)
                ),
                onRetry = onRetryClick
            )
        }
}

fun LazyListScope.LoadingInitialDataIndicator(visible: Boolean) {
    if (visible)
        item {
            LoadingDataIndicator(
                modifier = Modifier.fillParentMaxSize(),
                text = stringResource(R.string.users_screen_loading_data)
            )
        }
}

fun LazyListScope.LoadingInitialDataError(
    visible: Boolean,
    message: String?,
    onRetryClick: () -> Unit
) {
    if (visible)
        item {
            LoadingDataError(
                modifier = Modifier.fillParentMaxSize(),
                text = stringResource(
                    R.string.loading_data_error,
                    message ?: stringResource(R.string.something_went_wrong)
                ),
                onRetry = onRetryClick
            )
        }
}


fun LazyListScope.RefreshingDataIndicator(
    modifier: Modifier,
    visible: Boolean,
) {
    item {
        AnimatedVisibility(
            modifier = modifier,
            visible = visible,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                Text(
                    text = stringResource(R.string.refreshing_data),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun LoadingDataError(
    modifier: Modifier,
    text: String,
    onRetry: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            modifier = Modifier.padding(vertical = 16.dp),
            onClick = onRetry
        ) {
            Text(text = stringResource(R.string.try_again))
        }
    }
}

@Composable
private fun LoadingDataIndicator(
    modifier: Modifier,
    text: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}