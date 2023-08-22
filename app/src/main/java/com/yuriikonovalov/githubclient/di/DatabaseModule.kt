package com.yuriikonovalov.githubclient.di

import android.content.Context
import androidx.room.Room
import com.yuriikonovalov.githubclient.data.database.GitHubClientDatabase
import com.yuriikonovalov.githubclient.data.database.dao.RepositoryDao
import com.yuriikonovalov.githubclient.data.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideGitHubClientDatabase(@ApplicationContext context: Context): GitHubClientDatabase {
        return Room.databaseBuilder(
            context,
            GitHubClientDatabase::class.java,
            GitHubClientDatabase.NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: GitHubClientDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideRepositoryDao(
        database: GitHubClientDatabase
    ): RepositoryDao = database.repositoryDao()
}