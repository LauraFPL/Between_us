CREATE DATABASE IF NOT EXISTS Between_Us;
USE Between_Us;

DROP TABLE IF EXISTS gameLogs CASCADE;
DROP TABLE IF EXISTS npc_game CASCADE;
DROP TABLE IF EXISTS npc CASCADE;
DROP TABLE IF EXISTS game CASCADE;
DROP TABLE IF EXISTS player CASCADE;

-- CREACIÓ DE LES TAULES---------------------------------------------------------------------------------------------------------------------------

CREATE TABLE player (
  `name` varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`name`) 

);


CREATE TABLE npc (
  color varchar(255),
  PRIMARY KEY (color)
);


CREATE TABLE game (
  num_game INTEGER NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  player_color varchar(255) DEFAULT NULL,
  posY_player int DEFAULT NULL,
  posX_player int DEFAULT NULL,
  alive_player boolean default 1,
  map INTEGER DEFAULT NULL,
  player varchar(255) NOT NULL,
  started BOOLEAN DEFAULT NULL,
  finished BOOLEAN DEFAULT NULL,
  winner boolean DEFAULT NULL,
  instant INTEGER DEFAULT 0,
  creation_date datetime DEFAULT NULL,
  start_game_date datetime DEFAULT NULL,
  finish_game_date datetime DEFAULT NULL,
  PRIMARY KEY (num_game),
  KEY player_idx (player),
  CONSTRAINT `name` FOREIGN KEY (player) REFERENCES player (`name`)
);


CREATE TABLE npc_game (
  num_game INTEGER NOT NULL,
  game_name VARCHAR(255) NOT NULL,
  player_name VARCHAR(255) NOT NULL,
  NPC_id varchar(255) NOT NULL,
  posX int DEFAULT NULL,
  posY int DEFAULT NULL,
  role varchar(255) DEFAULT NULL,
  alive boolean DEFAULT 1,
  suspicionRole varchar(255) DEFAULT 'UNKNOWN',
  PRIMARY KEY (num_game,NPC_id),
  KEY NPC_id_idx (NPC_id),
  CONSTRAINT num_game FOREIGN KEY (num_game) REFERENCES game (num_game),
  CONSTRAINT player_name FOREIGN KEY (player_name) REFERENCES player (`name`),
  CONSTRAINT NPC_id FOREIGN KEY (NPC_id) REFERENCES npc (color)
); 


CREATE TABLE gameLogs (
  num_game INTEGER NOT NULL,
  game_name VARCHAR (255) NOT NULL,
  player_name VARCHAR(255) NOT NULL,
  gameLogs_id INTEGER NOT NULL,
  NPC_id varchar(255) NOT NULL,
  room_name varchar(255) NOT NULL,
  instant int NOT NULL,
  CONSTRAINT gameLogs_PK PRIMARY KEY (num_game,gameLogs_id),
  CONSTRAINT num_games FOREIGN KEY (num_game) REFERENCES game (num_game),
  CONSTRAINT playerName FOREIGN KEY (player_name) REFERENCES player (name),
  CONSTRAINT NPCId FOREIGN KEY (NPC_id) REFERENCES npc (color)
); 


-- Players de prova-----------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO player (name, email, password) VALUES ('test', 'test@test.com', '123');
INSERT INTO Player VALUES ('Hello', 'aasd@sdf.com', '123');
INSERT INTO Player VALUES ('Laura', 'aasd@sdf.com', '123');
INSERT INTO Player VALUES ('Guillem', 'aasd@sdf.com', '123');
INSERT INTO Player VALUES ('Anna', 'aasd@sdf.com', '123');
INSERT INTO Player VALUES ('Marta', 'aasd@sdf.com', '123');
INSERT INTO Player VALUES ('Albert', 'aasd@sdf.com', '123');


-- INSERCIONS PRÈVIES NECESSARIES----------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO npc (color) VALUES ('red'), ('blue'), ('green'), ('pink'), ('orange'), ('yellow'),
                                     ('black'), ('purple'), ('white'), ('cyan'), ('dark_green'), ('brown');
                                     

-- SELECTS NECESSARIS--------------------------------------------------------------------------------------------------------------------------------------------------------
/*

SELECT * FROM game;
SELECT * FROM npc;
SELECT * FROM npc_game;
SELECT * FROM player;
SELECT * FROM gameLogs;

*/

