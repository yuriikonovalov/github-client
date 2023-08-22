package com.yuriikonovalov.githubclient.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.yuriikonovalov.githubclient.data.database.entities.UserEntity

@Dao
interface UserDao {
    @Upsert
    suspend fun insertAll(entities: List<UserEntity>)

    @Query(
        """
        SELECT * FROM users
    """
    )
    fun getUsers(): PagingSource<Int, UserEntity>
}