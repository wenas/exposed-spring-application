-- ユーザーテーブル
CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(255)             NOT NULL UNIQUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 課金履歴
CREATE TABLE billing_history
(
    id             SERIAL PRIMARY KEY,
    user_id        INTEGER NOT NULL,
    purchase_date  DATE    NOT NULL,
    game_name      VARCHAR NOT NULL,
    purchased_item VARCHAR NOT NULL,
    amount         INTEGER NOT NULL
);

CREATE INDEX billing_history_game_name_idx ON billing_history (game_name);
CREATE INDEX billing_history_purchase_date_idx ON billing_history (purchase_date);

-- ゲームと開発会社
CREATE TABLE game_info
(
    id           SERIAL PRIMARY KEY,
    game_name    VARCHAR NOT NULL,
    company_name VARCHAR NOT NULL
);

-- ゲームのイベント
CREATE TABLE game_events
(
    id               SERIAL PRIMARY KEY,
    game_info_id     INTEGER                  NOT NULL,
    event_name       VARCHAR                  NOT NULL,
    event_start_date TIMESTAMP WITH TIME ZONE NOT NULL,
    event_end_date   TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (game_info_id) REFERENCES game_info (id)
);

-- イベントのピックアップキャラ
CREATE TABLE event_characters
(
    id              SERIAL PRIMARY KEY,
    game_event_id   INTEGER NOT NULL,
    character_name  VARCHAR NOT NULL,
    character_class VARCHAR NOT NULL,
    ability         VARCHAR NOT NULL,
    FOREIGN KEY (game_event_id) REFERENCES game_events (id)
);


-- キャラクターを引けたかどうか
CREATE TABLE character_drawn
(
    id                 SERIAL PRIMARY KEY,
    user_id            INTEGER                  NOT NULL,
    event_character_id INTEGER                  NOT NULL,
    drawn_date         TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (event_character_id) REFERENCES event_characters (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

