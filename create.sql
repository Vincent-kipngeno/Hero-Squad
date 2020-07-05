CREATE DATABASE hero_squad;
\c hero_squad;
CREATE TABLE hero (id SERIAL PRIMARY KEY, name VARCHAR, specialpower VARCHAR, weakness VARCHAR, age INTEGER, squadId INTEGER);
CREATE TABLE squad (id SERIAL PRIMARY KEY, name VARCHAR, cause VARCHAR);
CREATE DATABASE hero_squad_test WITH TEMPLATE hero_squad;