package com.yuriikonovalov.githubclient.presentation.repositories

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val REPOSITORIES_ROUTE_ARGUMENT = "login"
private const val REPOSITORIES_ROUTE = "repositories/{$REPOSITORIES_ROUTE_ARGUMENT}"
private fun composeRoute(login: String) = "repositories/$login"

fun NavGraphBuilder.repositoriesRoute(onBackClick: () -> Unit) {
    composable(
        route = REPOSITORIES_ROUTE,
        arguments = listOf(
            navArgument(name = REPOSITORIES_ROUTE_ARGUMENT) {
                type = NavType.StringType
            }
        )
    ) { navBackStackEntry ->
        val login = checkNotNull(
            navBackStackEntry.arguments?.getString(REPOSITORIES_ROUTE_ARGUMENT)
        )
        RepositoriesScreen(
            onBackClick = onBackClick,
            login = login
        )
    }
}

fun NavHostController.navigateToRepositoriesRoute(userLogin: String) {
    navigate(route = composeRoute(userLogin))
}