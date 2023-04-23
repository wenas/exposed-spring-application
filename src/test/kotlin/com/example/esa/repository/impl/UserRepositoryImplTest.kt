package com.example.esa.repository.impl

import com.example.esa.repository.BillingRepository
import com.example.esa.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.YearMonth

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
internal class UserRepositoryImplTest {
    companion object {
        @Container
        val postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:13.3").apply {
            withDatabaseName("test_db")
            withUsername("test_user")
            withPassword("test_password")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword)
        }
    }

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    @Transactional
    fun ユーザーの取得() {

        assertEquals("せち", userRepository.findUserById(1)!!.username)
        assertEquals("ぷち", userRepository.findUserById(2)!!.username)
    }

    @Test
    @Transactional
    fun ユーザーがいない場合() {

        assertNull(userRepository.findUserById(-1))
    }


}