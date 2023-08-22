package com.yuriikonovalov.githubclient.domain.entities

data class User(
    val id: Id,
    val login: Login,
    val avatar: Avatar
) {
    @JvmInline
    value class Id(val value: Int)

    @JvmInline
    value class Login(val value: String) {
        init {
            check(value.isNotBlank())
        }
    }

    @JvmInline
    value class Avatar(val url: String)
}