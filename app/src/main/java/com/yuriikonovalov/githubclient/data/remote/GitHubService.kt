package com.yuriikonovalov.githubclient.data.remote

import com.yuriikonovalov.githubclient.data.remote.model.UserRepositoryApiResponse
import com.yuriikonovalov.githubclient.data.remote.model.UserApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("/users")
    suspend fun getUsers(): List<UserApiResponse>

    @GET("/users/{login}/repos")
    suspend fun getUserRepositories(@Path(value = "login") login: String): List<UserRepositoryApiResponse>

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}