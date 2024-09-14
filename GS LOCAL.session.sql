DELETE FROM elo_score
WHERE elo_score > 800;
DELETE FROM elo_score
WHERE elo_score > 800;
SELECT setval('elo_score_id_seq', 97, true);
DELETE FROM elo_score
WHERE id > 97;
INSERT INTO elo_score (id, game_id, timestamp, elo_score)
VALUES (
    id :integer,
    game_id :integer,
    'timestamp:timestamp without time zone',
    elo_score :integer
  );
SELECT game_id,
  INTEGER,
  ?
FROM elo_score \ d elo_score

ALTER TABLE elo_score
ADD CONSTRAINT unique_game_timestamp UNIQUE (game_id, timestamp);

ALTER TABLE elo_score
DROP CONSTRAINT unique_game_timestamp;

ALTER TABLE elo_score
ADD CONSTRAINT unique_timestamp UNIQUE ("timestamp");

TRUNCATE TABLE elo_score;

ALTER TABLE elo_score
DROP CONSTRAINT unique_timestamp;