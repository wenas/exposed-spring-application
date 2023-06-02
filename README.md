
## これは何？

JJUG CCCでExposedを紹介用に作ったサンプルコード。

## 前提

以下の環境で動作検証しています

* Java 17以上
* Docker(TestContainersでDBを動かしているため)

serviceとかcontrollerはExposedと関係ないので割愛・・・。

## 実行方法

com.example.esa.repositoryにあるテストクラスを実行してください。

TestcontainersでPostgresのコンテナが起動し、Repositoryのクエリを実行します。
実行したSQLはログに出力されます

```
13:55:11.145 [Test worker] DEBUG Exposed - SELECT to_char(billing_history.purchase_date, 'YYYY-MM') paymentMonth, SUM(billing_history.amount) FROM billing_history WHERE billing_history.user_id = 1 GROUP BY paymentMonth ORDER BY paymentMonth ASC
```

## ストーリー

あえて変なテーブル設計をしてみました。

1. 単純な記録を目的に、billing_history(課金履歴)とusers（ユーザー）のテーブルを作る。この時はゲーム名はユーザーがテキストで登録
2. 集計する要件ができたため、ゲーム名を管理するテーブル(game_info)を追加
3. 1の時は自由に入力可能だったため表記揺れが発生 （Java、JAVAのようなもの）
4. 表記揺れを解決するため、game_name_variantsテーブルを追加してgame_infoと入力された名前の紐付けを解決

4のところを無理やりExposedのAPIだけできるあたりがセールスポイントかなぁと・・・。

## ソースの解説

### テーブルオブジェクト

com.example.esa.dslにあるファイル。
各テーブル（src/main/resources/db/migrationにあるDDL参照）に対応しています。


### クエリ

selectのみですが、ORMじゃちょっとむずかしそうな

* sumでの集計
* 交差テーブルがないテーブル間のJoin
* SQLの関数を隠蔽しているgroupConcatによる1:Nの解決

### DDL

以下のファイルにあります。
Flywayを使用しているので、起動時にテーブルが作成されます。

V1__create_table.sql
V2__create_another_table.sql

また、テストでは以下のファイルにあるSQLをテストデータとして使用しています。

V100__sample_data.sql




