package com.yuriikonovalov.githubclient.domain

import androidx.paging.PagingData
import com.yuriikonovalov.githubclient.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val users: Flow<PagingData<User>>
}