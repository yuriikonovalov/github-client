package com.yuriikonovalov.githubclient.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.yuriikonovalov.githubclient.data.database.dao.RepositoryDao
import com.yuriikonovalov.githubclient.data.paging.RepositoryRemoteMediator
import com.yuriikonovalov.githubclient.data.remote.GitHubService
import com.yuriikonovalov.githubclient.domain.RepositoryRepository
import com.yuriikonovalov.githubclient.domain.entities.Repository
import com.yuriikonovalov.githubclient.domain.entities.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RepositoryRepositoryImpl @Inject constructor(
    private val gitHubService: GitHubService,
    private val dao: RepositoryDao
) : RepositoryRepository {
    override fun getRepositories(login: User.Login): Flow<PagingData<Repository>> {
        val remoteMediator = RepositoryRemoteMediator(
            service = gitHubService,
            dao = dao,
            login = login.value
        )
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { dao.getRepositoriesByUserLogin(login.value) }
        )
            .flow
            .map { pagingData ->
                pagingData.map { entity ->
                    entity.toDomain()
                }
            }
    }


    companion object {
        const val PAGE_SIZE = 10
    }
}