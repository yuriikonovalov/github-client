package com.yuriikonovalov.githubclient.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yuriikonovalov.githubclient.data.database.dao.RepositoryDao
import com.yuriikonovalov.githubclient.data.database.dao.UserDao
import com.yuriikonovalov.githubclient.data.database.entities.DateConverter
import com.yuriikonovalov.githubclient.data.database.entities.RepositoryEntity
import com.yuriikonovalov.githubclient.data.database.entities.UserEntity

@Database(
    entities = [UserEntity::class, RepositoryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class GitHubClientDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun repositoryDao(): RepositoryDao

    companion object {
        const val NAME = "github_client_database"
    }
}