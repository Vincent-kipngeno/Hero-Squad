CREATE DATABASE hero_squad;
\c hero_squad;
CREATE TABLE heroes (id SERIAL PRIMARY KEY, name VARCHAR, specialPower VARCHAR, weakness VARCHAR, age INTEGER, squadId INTEGER);
CREATE TABLE squads (id SERIAL PRIMARY KEY, maximumSize INTEGER, name VARCHAR, causeToFight VARCHAR);
CREATE DATABASE hero_squad_test WITH TEMPLATE hero_squad;