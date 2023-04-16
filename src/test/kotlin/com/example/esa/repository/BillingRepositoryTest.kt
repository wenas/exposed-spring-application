package com.example.esa.repository

import com.example.esa.dto.Billing
import org.junit.jupiter.api.Assertions
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
        assertEquals(41480, marchMap.get("ブルーアーカイブ"))
        assertEquals(6250, marchMap.get("アークナイツ"))
    }

    @Test
    @Transactional
    fun 月の課金額リスト() {

        val list = billingRepository.月の課金リストを取得(1, YearMonth.of(2023, 4))

        assertEquals(5, list.size)
        assertEquals(LocalDate.of(2023,4,14), list[0].purchaseDate)
        assertEquals("アークナイツ", list[0].gameName)
        assertEquals("汎用特訓パック", list[0].purchasedItem)
        assertEquals(2000, list[0].amount)

    }



}