DELETE FROM elo_score
WHERE elo_score > 800;
DELETE FROM elo_score
WHERE elo_score > 800;
SELECT setval('elo_score_id_seq', 97, true);
DELETE FROM elo_score
WHERE id > 97;