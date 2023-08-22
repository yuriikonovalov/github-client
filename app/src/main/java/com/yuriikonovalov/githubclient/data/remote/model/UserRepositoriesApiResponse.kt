package com.yuriikonovalov.githubclient.data.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yuriikonovalov.githubclient.data.database.entities.RepositoryEntity
import java.time.OffsetDateTime


@JsonClass(generateAdapter = true)
data class UserRepositoryApiResponse(
    val description: String?,
    @Json(name = "forks_count")
    val forksCount: Int,
    val id: Int,
    val language: String?,
    val name: String,
    @Json(name = "stargazers_count")
    val stargazersCount: Int,
    @Json(name = "updated_at")
    val updatedAt: String,
    val owner: Owner
) {
    @JsonClass(generateAdapter = true)
    data class Owner(val login: String)
}

fun List<UserRepositoryApiResponse>.toDatabaseEntities() = map { api ->
    RepositoryEntity(
        id = api.id,
        name = api.name,
        description = api.description,
        language = api.language,
        updatedAt = OffsetDateTime.parse(api.updatedAt),
        stargazersCount = api.stargazersCount,
        forksCount = api.forksCount,
        userLogin = api.owner.login
    )
}