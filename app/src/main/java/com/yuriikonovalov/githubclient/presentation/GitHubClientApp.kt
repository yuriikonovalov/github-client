package com.yuriikonovalov.githubclient.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun GitHuClientApp(
    modifier: Modifier,
    startDestination: String
) {
    val navController = rememberNavController()

    GitHubClientNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    )
}