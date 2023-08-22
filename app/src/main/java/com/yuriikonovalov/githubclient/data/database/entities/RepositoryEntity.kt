package com.yuriikonovalov.githubclient.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.yuriikonovalov.githubclient.domain.entities.Repository
import java.time.OffsetDateTime

@Entity(
    tableName = "repositories",
    indices = [Index("user_login")],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["login"],
            childColumns = ["user_login"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RepositoryEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val language: String?,
    val description: String?,
    @ColumnInfo(name = "updated_at")
    val updatedAt: OffsetDateTime,
    @ColumnInfo(name = "stargazers_count")
    val stargazersCount: Int,
    @ColumnInfo(name = "forks_count")
    val forksCount: Int,
    @ColumnInfo(name = "user_login")
    val userLogin: String
) {
    fun toDomain() = Repository(
        id = Repository.Id(id),
        name = Repository.Name(name),
        language = Repository.Language(language),
        description = Repository.Description(description),
        updatedAt = updatedAt,
        stars = Repository.Stars(stargazersCount),
        forks = Repository.Forks(forksCount)
    )
}
