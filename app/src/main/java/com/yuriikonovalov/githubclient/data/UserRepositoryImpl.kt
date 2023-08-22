package com.yuriikonovalov.githubclient.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.yuriikonovalov.githubclient.data.database.dao.UserDao
import com.yuriikonovalov.githubclient.data.paging.UserRemoteMediator
import com.yuriikonovalov.githubclient.data.remote.GitHubService
import com.yuriikonovalov.githubclient.domain.UserRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val gitHubService: GitHubService
) : UserRepository {
    private val remoteMediator = UserRemoteMediator(
        dao = userDao,
        gitHubService = gitHubService
    )

    override val users = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        remoteMediator = remoteMediator,
        pagingSourceFactory = { userDao.getUsers() }
    )
        .flow
        .map { pagingData ->
            pagingData.map { entity ->
                entity.toDomain()
            }
        }

    companion object {
        private const val PAGE_SIZE = 20
    }
}