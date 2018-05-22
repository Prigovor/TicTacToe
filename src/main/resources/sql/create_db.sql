/*Create schema*/
CREATE SCHEMA IF NOT EXISTS `tic_tac_toe_db` DEFAULT CHARACTER SET utf8;

/*Create table*/
CREATE TABLE IF NOT EXISTS `game` (
  `game_id` BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `gameTitle` VARCHAR(100),
  `status` VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS `move` (
  `move_id` BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `game_id` BIGINT             NOT NULL,
  `number`  INTEGER            NOT NULL,
  `x`       INTEGER            NOT NULL,
  `y`       INTEGER            NOT NULL,
  FOREIGN KEY (game_id) REFERENCES game (game_id)
);
