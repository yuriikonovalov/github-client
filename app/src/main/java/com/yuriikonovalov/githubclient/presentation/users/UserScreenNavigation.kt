package com.yuriikonovalov.githubclient.presentation.users

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val USERS_ROUTE: String = "users"

fun NavGraphBuilder.usersRoute(
    onUserClick: (login: String) -> Unit
) {
    composable(route = USERS_ROUTE) {
        UsersScreen(onUserClick = onUserClick)
    }
}