package com.yuriikonovalov.githubclient.data.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yuriikonovalov.githubclient.data.database.entities.UserEntity


@JsonClass(generateAdapter = true)
data class UserApiResponse(
    @Json(name = "avatar_url")
    val avatarUrl: String,
    val id: Int,
    val login: String
)

fun List<UserApiResponse>.toDatabaseEntities() = map { api ->
    UserEntity(
        uid = api.id,
        login = api.login,
        avatarUrl = api.avatarUrl
    )
}