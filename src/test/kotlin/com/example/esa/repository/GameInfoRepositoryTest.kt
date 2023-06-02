package com.example.esa.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GameInfoRepositoryTest {

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
    lateinit var gameInfoRepository: GameInfoRepository

    @Test
    @Transactional
    fun ゲームの一覧が取得できること() {

        val gameList = gameInfoRepository.登録済みのゲーム名を取得()

        assertEquals(2, gameList.size)

        assertEquals("JJUGクエスト", gameList[0].gameName)
        assertEquals("ぺち", gameList[0].companyName)
        assertEquals(0, gameList[0].nameVariants.size)

        assertEquals("JavaRPG", gameList[1].gameName)
        assertEquals("ぺち", gameList[1].companyName)
        assertEquals(2, gameList[1].nameVariants.size)


    }


}