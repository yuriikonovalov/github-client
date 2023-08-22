package com.yuriikonovalov.githubclient.presentation.repositories

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.yuriikonovalov.githubclient.domain.RepositoryRepository
import com.yuriikonovalov.githubclient.domain.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepositoriesScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: RepositoryRepository
) : ViewModel() {
    private val argument: String = checkNotNull(savedStateHandle[REPOSITORIES_ROUTE_ARGUMENT])
    private val login = User.Login(argument)
    val repositories = repository.getRepositories(login)
}