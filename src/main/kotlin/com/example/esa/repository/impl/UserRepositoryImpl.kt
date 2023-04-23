package com.example.esa.repository.impl

import com.example.esa.dsl.UserActive
import com.example.esa.dsl.Users
import com.example.esa.entity.User
import com.example.esa.repository.UserRepository
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Component

@Component
class UserRepositoryImpl : UserRepository {
    override fun findUserById(userId: Int): User? {
        return Users
            .join(UserActive, JoinType.INNER, additionalConstraint = { Users.id eq UserActive.id })
            .select { Users.id eq userId }
            .map { User(it[Users.id].value, it[Users.username]) }
            .getOrNull(0)
    }
}