-- ユーザーテーブル
CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(255)             NOT NULL UNIQUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_active
(
    user_id    INTEGER PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
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
