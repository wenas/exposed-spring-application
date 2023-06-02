package com.example.esa.repository

import com.example.esa.dsl.BillingHistory
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.sum
import org.springframework.stereotype.Repository

@Repository
class BillingAnalyzeRepository {


    fun ユーザーの月毎の課金額(userId: Int) {

        // 日付をYYYY-MMにフォーマットして、group byで集計する
        val yearMothColumn = DateFormat(BillingHistory.purchaseDate, "YYYY-MM").alias("paymentMonth")

        BillingHistory

            .slice(yearMothColumn, BillingHistory.amount.sum())
            .select {
                BillingHistory.userId eq userId
            }

            // 月毎に集計
            .groupBy(yearMothColumn)
            .orderBy(yearMothColumn)
            .forEach {
                println(it[yearMothColumn] + ":" + it[BillingHistory.amount.sum()])
            }

    }


}