package com.example.esa.repository

import com.example.esa.dsl.GameInfo
import com.example.esa.dsl.GameNameVariants
import com.example.esa.entity.Game
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.groupConcat
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Repository

@Repository
class GameInfoRepository {


    fun 登録済みのゲーム名を取得(): List<Game> {

        // JOINしたとき行が増えるのを避けるため、groupConcatで配列に集約する
        // groupConcatはPostgresには存在しないため、STRING_AGGが使われる
        // 生成されるSQLは STRING_AGG(game_name_variants.name_variant, ',')
        val nameVariants = GameNameVariants.nameVariant.groupConcat(separator = ",").alias("variants")

        return GameInfo
            .join(
                GameNameVariants,
                JoinType.LEFT,
                additionalConstraint = { GameInfo.id eq GameNameVariants.gameInfoId })
            .slice(
                GameInfo.id,
                GameInfo.gameName,
                GameInfo.companyName,
                // GameNameVariantsのnameVariant（1:n）を1列に集約
                nameVariants
            )
            .selectAll()
            .groupBy(GameInfo.id)
            .orderBy(GameInfo.id)
            .map {
                Game(
                    id = it[GameInfo.id].value,
                    gameName = it[GameInfo.gameName],
                    companyName = it[GameInfo.companyName],
                    // left joinのため、nullableになる・・・
                    nameVariants = it[nameVariants]?.split(",") ?: emptyList()
                )
            }

    }

}