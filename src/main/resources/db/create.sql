SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS weed (
  id int PRIMARY KEY auto_increment,
  weedName VARCHAR,
  description VARCHAR,
  strain VARCHAR,
  origin VARCHAR,
  storeId int

);

CREATE TABLE IF NOT EXISTS store (
id int PRIMARY KEY auto_increment,
storeName VARCHAR
);