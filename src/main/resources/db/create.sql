SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS weed (
  id int PRIMARY KEY auto_increment,
  description VARCHAR,
  categoryId int,
  completed BOOLEAN

);
CREATE TABLE IF NOT EXISTS store (
id int PRIMARY KEY auto_increment,
name VARCHAR
);