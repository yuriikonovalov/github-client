package com.yuriikonovalov.githubclient.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.yuriikonovalov.githubclient.presentation.repositories.navigateToRepositoriesRoute
import com.yuriikonovalov.githubclient.presentation.repositories.repositoriesRoute
import com.yuriikonovalov.githubclient.presentation.users.usersRoute

@Composable
fun GitHubClientNavHost(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        usersRoute(onUserClick = navController::navigateToRepositoriesRoute)
        repositoriesRoute(onBackClick = navController::navigateUp)
    }
}