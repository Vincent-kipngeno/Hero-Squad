CREATE DATABASE hero_squad;
\c hero_squad;
CREATE TABLE hero (id SERIAL PRIMARY KEY, name VARCHAR, special_power VARCHAR, weakness VARCHAR, age INTEGER, squadId INTEGER);
CREATE TABLE squad (id SERIAL PRIMARY KEY, maximum_size INTEGER, name VARCHAR, cause VARCHAR, age INTEGER);
CREATE DATABASE hero_squad_test WITH TEMPLATE hero_squad;