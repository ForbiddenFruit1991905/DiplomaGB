CREATE TABLE mydbfordiploma.roles (
     id VARCHAR(40) PRIMARY KEY,
     role_name VARCHAR(50),
     user_id VARCHAR(40)
 );

 CREATE TABLE mydbfordiploma.planner (
     id VARCHAR(40) PRIMARY KEY,
     title VARCHAR(50) NOT NULL,
     description VARCHAR(500),
     owner_id VARCHAR(40) NOT NULL
 );

 CREATE TABLE mydbfordiploma.users (
     id VARCHAR(40) PRIMARY KEY,
     first_name VARCHAR(50) NOT NULL,
     last_name VARCHAR(255) NOT NULL,
     email VARCHAR(250) UNIQUE NOT NULL,
     password VARCHAR(255) NOT NULL,
     role_id VARCHAR(40) NOT NULL,
     planner_id VARCHAR(40),
     FOREIGN KEY (role_id) REFERENCES mydbfordiploma.roles(id),
     FOREIGN KEY (planner_id) REFERENCES mydbfordiploma.planner(id)
 );

 CREATE TABLE mydbfordiploma.notes (
     id VARCHAR(40) PRIMARY KEY,
     header VARCHAR(255) NOT NULL,
     text TEXT NOT NULL,
     status VARCHAR(50),
     created_date TIMESTAMP NOT NULL,
     planner_id VARCHAR(40),
     user_id VARCHAR(40),
     FOREIGN KEY (planner_id) REFERENCES mydbfordiploma.planner(id),
     FOREIGN KEY (user_id) REFERENCES mydbfordiploma.users(id)
 );


ALTER TABLE mydbfordiploma.notes DROP FOREIGN KEY notes_ibfk_2;
ALTER TABLE mydbfordiploma.planner DROP FOREIGN KEY FKbdjddbtoor8hd7dasbk2u85es;
ALTER TABLE mydbfordiploma.users MODIFY COLUMN id VARCHAR(255) NOT NULL;
ALTER TABLE mydbfordiploma.planner ADD CONSTRAINT FKbdjddbtoor8hd7dasbk2u85es FOREIGN KEY (id) REFERENCES mydbfordiploma.users(id);
ALTER TABLE mydbfordiploma.notes ADD CONSTRAINT notes_ibfk_2 FOREIGN KEY (user_id) REFERENCES mydbfordiploma.users(id);

ALTER TABLE mydbfordiploma.notes DROP FOREIGN KEY notes_ibfk_2;
ALTER TABLE mydbfordiploma.notes MODIFY COLUMN user_id VARCHAR(255);

ALTER TABLE mydbfordiploma.notes ADD CONSTRAINT notes_ibfk_2 FOREIGN KEY (user_id) REFERENCES mydbfordiploma.users(id);





