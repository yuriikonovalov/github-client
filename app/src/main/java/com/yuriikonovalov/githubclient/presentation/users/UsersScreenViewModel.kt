package com.yuriikonovalov.githubclient.presentation.users

import androidx.lifecycle.ViewModel
import com.yuriikonovalov.githubclient.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersScreenViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {
    val state = userRepository.users
}