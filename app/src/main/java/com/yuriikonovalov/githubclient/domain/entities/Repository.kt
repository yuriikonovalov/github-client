package com.yuriikonovalov.githubclient.domain.entities

import java.time.OffsetDateTime

data class Repository(
    val id: Id,
    val name: Name,
    val description: Description,
    val language: Language,
    val updatedAt: OffsetDateTime,
    val stars: Stars,
    val forks: Forks
) {
    @JvmInline
    value class Id(val value: Int)

    @JvmInline
    value class Name(val value: String)

    @JvmInline
    value class Description(val value: String?)

    @JvmInline
    value class Language(val value: String?)

    @JvmInline
    value class Stars(val count: Int) {
        init {
            check(count >= 0)
        }
    }

    @JvmInline
    value class Forks(val count: Int) {
        init {
            check(count >= 0)
        }
    }
}
