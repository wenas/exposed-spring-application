package com.example.esa.repository

import com.example.esa.dsl.BillingHistory
import com.example.esa.dto.Billing
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import java.time.YearMonth

@Repository
class BillingRepository {


    fun 月の課金リストを取得(userId: Int, yearMonth: YearMonth): List<Billing> {
        return BillingHistory
            .select {
                BillingHistory.userId eq userId
            }
            .andWhere {
                BillingHistory.purchaseDate.between(yearMonth.atDay(1), yearMonth.atEndOfMonth())
            }
            .orderBy(BillingHistory.purchaseDate, SortOrder.DESC)
            .map {
                Billing(
                    purchaseDate = it[BillingHistory.purchaseDate],
                    gameName = it[BillingHistory.gameName],
                    purchasedItem = it[BillingHistory.purchasedItem],
                    amount = it[BillingHistory.amount]
                )
            }

    }


    fun 月の課金額をゲームごとに取得する(userId: Int, yearMonth: YearMonth): Map<String, Int> {

        val sumColum = BillingHistory.amount.sum().alias("amount")

        return BillingHistory
            .slice(BillingHistory.gameName, sumColum)
            .select {
                BillingHistory.userId eq userId
            }
            .andWhere {
                BillingHistory.purchaseDate.between(yearMonth.atDay(1), yearMonth.atEndOfMonth())
            }
            .groupBy(BillingHistory.gameName)
            .associate {
                it[BillingHistory.gameName] to it[sumColum]!!
            }
    }
}