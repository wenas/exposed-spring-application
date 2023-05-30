package com.example.esa.repository

import com.example.esa.dsl.BillingHistory
import com.example.esa.dsl.GameInfo
import com.example.esa.dsl.GameNameVariants
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


    val gameNameColumn = GameInfo
        .slice(GameInfo.id, GameInfo.gameName)
        .select {
            GameInfo.id eqSubQuery
                    GameNameVariants
                        .slice(GameNameVariants.gameInfoId)
                        .select { GameNameVariants.nameVariant eq "JAVA_RPG" }
        }.orWhere {
            GameInfo.gameName eq "JAVA_RPG"
        }


//    fun gameNameQuery(gameName: String) = Expression.build {
//        GameInfo
//            .slice(GameInfo.gameName)
//            .select {
//                GameInfo.id eqSubQuery
//                        GameNameVariants
//                            .slice(GameNameVariants.gameInfoId)
//                            .select { GameNameVariants.nameVariant eq gameName }
//            }.orWhere {
//                GameInfo.gameName eq gameName
//            }
//    }

    val sumColum = BillingHistory.amount.sum().alias("amount")
    fun 月の課金額をゲームごとに取得する(userId: Int, yearMonth: YearMonth): Map<String, Int> {



//        BillingHistoryのgameNameは表記揺れがあるため使えない設定

        return BillingHistory
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

    fun ゲーム名を指定して下金額を取得する(userId: Int, gameInfoId: Int) {

        BillingHistory

            .slice(
                GameInfo.gameName, sumColum
            )
            .select {
                BillingHistory.userId eq userId
            }.andWhere {
                BillingHistory.gameName eqSubQuery GameInfo.slice(GameInfo.gameName).select { GameInfo.id eq gameInfoId }
            }.andWhere {
                BillingHistory.gameName inSubQuery GameNameVariants.slice(GameNameVariants.nameVariant).select { GameNameVariants.gameInfoId eq gameInfoId }
            }


    }

}