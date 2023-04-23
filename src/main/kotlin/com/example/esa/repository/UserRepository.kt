package com.example.esa.repository

import com.example.esa.entity.User
import org.springframework.stereotype.Repository

@Repository
interface UserRepository {

    fun findUserById(userId:Int): User?

}