package com.example.esa.repository

import com.example.esa.dsl.BillingHistory
import com.example.esa.entity.Billing
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import java.time.YearMonth

@Repository
class BillingAnalyzeRepository {


    fun ユーザーごとの課金額の平均(): List<Billing> {
//        return BillingHistory
//            .select {
//                BillingHistory.userId eq userId
//            }
//            .andWhere {
//                BillingHistory.purchaseDate.between(yearMonth.atDay(1), yearMonth.atEndOfMonth())
//            }
//            .orderBy(BillingHistory.purchaseDate, SortOrder.DESC)
//            .map {
//                Billing(
//                    purchaseDate = it[BillingHistory.purchaseDate],
//                    gameName = it[BillingHistory.gameName],
//                    purchasedItem = it[BillingHistory.purchasedItem],
//                    amount = it[BillingHistory.amount]
//                )
//            }
        return TODO()

    }


}