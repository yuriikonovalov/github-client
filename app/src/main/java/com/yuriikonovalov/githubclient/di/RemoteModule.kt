package com.yuriikonovalov.githubclient.di

import com.squareup.moshi.Moshi
import com.yuriikonovalov.githubclient.data.remote.GitHubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteModule {

    companion object {
        @Provides
        @Singleton
        fun provideGitHubService(): GitHubService {
            val moshi = Moshi.Builder()
                .build()

            return Retrofit.Builder()
                .baseUrl(GitHubService.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(GitHubService::class.java)
        }
    }
}