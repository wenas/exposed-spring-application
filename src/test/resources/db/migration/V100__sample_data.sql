INSERT INTO users (username)
VALUES ('せち');

INSERT INTO game_info (game_name, company_name)
VALUES ('アークナイツ', 'Yoster'),
       ('ブルーアーカイブ', 'Yoster');

INSERT INTO game_events (game_info_id, event_name, event_start_date, event_end_date)
VALUES (1, 'Event 1', '2023-04-01 00:00:00', '2023-04-10 23:59:59'),
       (2, 'Event 2', '2023-04-15 00:00:00', '2023-04-25 23:59:59');


INSERT INTO event_characters (game_event_id, character_name, character_class, ability)
VALUES (1, 'あああ', 'Warrior', 'Strength'),
       (1, 'Character 2', 'Mage', 'Intelligence'),
       (2, 'Character 3', 'Archer', 'Agility');

INSERT INTO character_drawn (user_id, event_character_id, drawn_date)
VALUES (1, 1, '2023-04-05 12:30:00'),
       (1, 1, '2023-04-07 10:15:00');

INSERT INTO billing_history(user_id, purchase_date, game_name, purchased_item, amount)
VALUES (1, '2023-04-13', 'アークナイツ', '汎用特訓パック', 2000),
       (1, '2023-04-13', 'アークナイツ', '汎用強化パック', 1000),
       (1, '2023-04-13', 'ブルーアーカイブ', 'レポートパッケージ', 2000),
       (1, '2023-04-13', 'ブルーアーカイブ', '2weeksスタミナパッケージ', 480),
       (1, '2023-04-13', 'ブルーアーカイブ', 'マンスリーパッケージ', 1000);
