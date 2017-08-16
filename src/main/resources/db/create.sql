SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS weed (
  id int PRIMARY KEY auto_increment,
  weedname VARCHAR,
  description VARCHAR,
  strain VARCHAR,
  origin VARCHAR,
  storeid int

);

CREATE TABLE IF NOT EXISTS store (
id int PRIMARY KEY auto_increment,
storename VARCHAR
);