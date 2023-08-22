package com.yuriikonovalov.githubclient.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.yuriikonovalov.githubclient.domain.entities.User

@Entity(
    tableName = "users",
    indices = [Index(value = ["login"], unique = true)]
)
data class UserEntity(
    @PrimaryKey
    val uid: Int,
    val login: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String
) {
    fun toDomain() = User(
        id = User.Id(uid),
        login = User.Login(login),
        avatar = User.Avatar(avatarUrl)
    )
}