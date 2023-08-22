package com.yuriikonovalov.githubclient.domain

import androidx.paging.PagingData
import com.yuriikonovalov.githubclient.domain.entities.Repository
import com.yuriikonovalov.githubclient.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface RepositoryRepository {
    fun getRepositories(login: User.Login): Flow<PagingData<Repository>>
}