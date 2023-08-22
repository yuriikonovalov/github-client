package com.yuriikonovalov.githubclient.di

import com.yuriikonovalov.githubclient.data.RepositoryRepositoryImpl
import com.yuriikonovalov.githubclient.data.UserRepositoryImpl
import com.yuriikonovalov.githubclient.domain.RepositoryRepository
import com.yuriikonovalov.githubclient.domain.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindsRepositoryRepository(impl: RepositoryRepositoryImpl): RepositoryRepository
}