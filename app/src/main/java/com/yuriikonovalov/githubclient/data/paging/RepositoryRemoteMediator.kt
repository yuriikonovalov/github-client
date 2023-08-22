package com.yuriikonovalov.githubclient.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.yuriikonovalov.githubclient.data.database.dao.RepositoryDao
import com.yuriikonovalov.githubclient.data.database.entities.RepositoryEntity
import com.yuriikonovalov.githubclient.data.remote.GitHubService
import com.yuriikonovalov.githubclient.data.remote.model.toDatabaseEntities
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RepositoryRemoteMediator(
    private val service: GitHubService,
    private val dao: RepositoryDao,
    private val login: String
) : RemoteMediator<Int, RepositoryEntity>() {

    override suspend fun initialize(): InitializeAction {
        // There's no requirement for checking data cache validity so we force an initial refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepositoryEntity>
    ): MediatorResult {
        return try {
            // The provided GitHub API doesn't support paging so all the data will be loaded at once
            // when refresh is required.
            if (loadType == LoadType.REFRESH) {
                val newRepositories = service.getUserRepositories(login).toDatabaseEntities()
                dao.insertAll(newRepositories)
            }

            MediatorResult.Success(endOfPaginationReached = true)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }
}