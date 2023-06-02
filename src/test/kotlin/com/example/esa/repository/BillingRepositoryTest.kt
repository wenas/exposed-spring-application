package com.example.esa.repository

import com.example.esa.dsl.GameInfo
import com.example.esa.dsl.GameNameVariants
import org.jetbrains.exposed.sql.orWhere
import org.jetbrains.exposed.sql.select
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
import java.time.LocalDate
import java.time.YearMonth

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
internal class BillingRepositoryTest {
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
    lateinit var billingRepository: BillingRepository

    @Test
    @Transactional
    fun 月の課金額をゲームごとに取得する() {

        val marchMap = billingRepository.月の課金額をゲームごとに取得する(1, YearMonth.of(2023, 3))
        assertEquals(2, marchMap.size)
        assertEquals(3650, marchMap.get("JJUGクエスト"))
        assertEquals(26480, marchMap.get("JavaRPG"))
    }

    @Test
    @Transactional
    fun 月の課金額リスト() {

        val list = billingRepository.月の課金リストを取得(1, YearMonth.of(2023, 4))

        assertEquals(4, list.size)
        assertEquals(LocalDate.of(2023, 4, 14), list[0].purchaseDate)
        assertEquals("JJUGクエスト", list[0].gameName)
        assertEquals("Java特訓パック", list[0].purchasedItem)
        assertEquals(2000, list[0].amount)

        assertEquals(LocalDate.of(2023, 4, 13), list[1].purchaseDate)
        assertEquals("JJUGクエスト", list[1].gameName)
        assertEquals("Spring強化パック", list[1].purchasedItem)
        assertEquals(1000, list[1].amount)

        assertEquals(LocalDate.of(2023, 4, 12), list[2].purchaseDate)
        assertEquals("JavaRPG", list[2].gameName)
        assertEquals("経験値セット", list[2].purchasedItem)
        assertEquals(2000, list[2].amount)

        assertEquals(LocalDate.of(2023, 4, 11), list[3].purchaseDate)
        assertEquals("JavaRPG", list[3].gameName)
        assertEquals("NPE回避装備", list[3].purchasedItem)
        assertEquals(480, list[3].amount)

    }





    @Test
    @Transactional
    fun ゲームIDを指定して課金額を取得する() {

        val gameAmount = billingRepository.ゲーム名を指定して課金額を取得する(1, 1)

        assertEquals("JJUGクエスト", gameAmount!!.first)
        assertEquals(19850, gameAmount!!.second)


    }


}