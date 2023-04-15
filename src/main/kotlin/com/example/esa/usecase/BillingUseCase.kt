package com.example.esa.usecase

import com.example.esa.dsl.BillingHistory
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BillingUseCase {

    @Transactional
    fun 月の課金額をゲームごとに取得する() {
        BillingHistory.selectAll().forEach {

            print("ゲーム名： ${it[BillingHistory.gameName]} , 金額: ${it[BillingHistory.amount]}")
        }
    }
}