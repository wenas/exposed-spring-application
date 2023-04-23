-- ゲームと開発会社
CREATE TABLE game_info
(
    id           SERIAL PRIMARY KEY,
    game_name    VARCHAR NOT NULL,
    company_name VARCHAR NOT NULL
);

-- 表記揺れの補正テーブル
CREATE TABLE game_name_variants
(
    id           SERIAL PRIMARY KEY,
    game_info_id INTEGER NOT NULL,
    name_variant VARCHAR NOT NULL,
    FOREIGN KEY (game_info_id) REFERENCES game_info (id) ON DELETE CASCADE
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

