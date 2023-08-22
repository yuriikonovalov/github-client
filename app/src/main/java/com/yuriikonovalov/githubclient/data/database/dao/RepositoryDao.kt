package com.yuriikonovalov.githubclient.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.yuriikonovalov.githubclient.data.database.entities.RepositoryEntity

@Dao
interface RepositoryDao {
    @Upsert
    suspend fun insertAll(entities: List<RepositoryEntity>)

    @Query(
        """
        SELECT *
        FROM repositories
        WHERE user_login = :login
    """
    )
    fun getRepositoriesByUserLogin(login: String): PagingSource<Int, RepositoryEntity>
}