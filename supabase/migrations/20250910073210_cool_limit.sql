-- サンプルメンバーデータ
INSERT INTO members (name, email, phone) VALUES 
('田中太郎', 'tanaka@example.com', '090-1234-5678'),
('佐藤花子', 'sato@example.com', '090-2345-6789'),
('鈴木一郎', 'suzuki@example.com', '090-3456-7890'),
('高橋美咲', 'takahashi@example.com', '090-4567-8901'),
('山田健太', 'yamada@example.com', '090-5678-9012');

-- サンプルイベントデータ
INSERT INTO events (title, description, start_date_time, end_date_time, location, max_participants, created_at) VALUES 
('新年会', '2024年の新年会を開催します。みなさんでお疲れ様でした！', '2024-01-15 18:00:00', '2024-01-15 21:00:00', '居酒屋「さくら」', 20, NOW()),
('春のハイキング', '桜の季節に山登りを楽しみましょう。初心者も大歓迎です。', '2024-04-07 09:00:00', '2024-04-07 16:00:00', '高尾山', 15, NOW()),
('BBQ大会', '夏の恒例BBQ大会です。食材は準備しますので手ぶらでOK！', '2024-07-20 11:00:00', '2024-07-20 17:00:00', '多摩川河川敷', 25, NOW()),
('忘年会', '今年も一年お疲れ様でした。楽しい忘年会にしましょう！', '2024-12-15 18:30:00', '2024-12-15 22:00:00', 'ホテル「グランド」', 30, NOW());

-- サンプル参加データ
INSERT INTO participations (event_id, member_id, status, comment, created_at, updated_at) VALUES 
(1, 1, 'ATTENDING', '楽しみにしています！', NOW(), NOW()),
(1, 2, 'ATTENDING', 'よろしくお願いします', NOW(), NOW()),
(1, 3, 'NOT_ATTENDING', '残念ですが都合がつきません', NOW(), NOW()),
(2, 1, 'MAYBE', '天気次第で参加します', NOW(), NOW()),
(2, 2, 'ATTENDING', 'ハイキング大好きです！', NOW(), NOW()),
(2, 4, 'ATTENDING', '初参加ですがよろしくお願いします', NOW(), NOW()),
(3, 1, 'ATTENDING', 'BBQ楽しみです', NOW(), NOW()),
(3, 3, 'ATTENDING', '肉を持参します', NOW(), NOW()),
(3, 5, 'ATTENDING', 'ビール係やります', NOW(), NOW());