package com.example.esa.repository

import com.example.esa.dsl.BillingHistory
import com.example.esa.dsl.GameInfo
import com.example.esa.dsl.GameNameVariants
import com.example.esa.dsl.UserActive
import com.example.esa.entity.Billing
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


    val sumColum = BillingHistory.amount.sum().alias("amount")
    fun 月の課金額をゲームごとに取得する(userId: Int, yearMonth: YearMonth): Map<String, Int> {


        return BillingHistory
            // アクティブユーザーであることをチェックするためのJOIN
            .join(UserActive, JoinType.INNER, additionalConstraint = {UserActive.id eq BillingHistory.userId})
            .join(GameInfo, JoinType.LEFT, additionalConstraint = {
                (GameInfo.gameName eq BillingHistory.gameName).or(GameInfo.id eqSubQuery
                        GameNameVariants
                            .slice(GameNameVariants.gameInfoId)
                            .select { GameNameVariants.nameVariant eq BillingHistory.gameName })
            })
            .slice(
                GameInfo.gameName, sumColum
            )
            .select {
                BillingHistory.userId eq userId
            }
            .andWhere {
                BillingHistory.purchaseDate.between(yearMonth.atDay(1), yearMonth.atEndOfMonth())
            }
            .groupBy(GameInfo.gameName)
            .associate {
                it[GameInfo.gameName] to it[sumColum]!!
            }
    }

    fun ゲーム名を指定して課金額を取得する(userId: Int, gameInfoId: Int): Pair<String, Int>? {

        return BillingHistory
            // アクティブユーザーであることをチェックするためのJOIN
            .join(UserActive, JoinType.INNER, additionalConstraint = {UserActive.id eq BillingHistory.userId})
            // BillingHistoryに登録されているゲーム名から、GameInfoのレコードを特定する
            // GameInfo.gameNameが一致するか、名前の表記揺れを管理するGameNameVariants.nameVariantに一致するレコードからGameInfoを取得
            .join(GameInfo, JoinType.LEFT, additionalConstraint = {
                (GameInfo.gameName eq BillingHistory.gameName).or(GameInfo.id eqSubQuery
                        GameNameVariants
                            .slice(GameNameVariants.gameInfoId)
                            .select { GameNameVariants.nameVariant eq BillingHistory.gameName })
            })
            .slice(
                GameInfo.id, GameInfo.gameName, sumColum
            )
            .select {
                BillingHistory.userId eq userId
            }
            .having {
                GameInfo.id eq gameInfoId
            }
            .groupBy(GameInfo.id)
            .map{
                it[GameInfo.gameName] to it[sumColum]!!
            }.getOrNull(0)


    }

}