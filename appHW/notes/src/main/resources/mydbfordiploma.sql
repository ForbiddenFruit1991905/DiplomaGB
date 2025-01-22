

 CREATE TABLE mydbfordiploma.roles (
     id CHAR(36) PRIMARY KEY,
     role_name VARCHAR(50),
     user_id CHAR(36)
 );

 CREATE TABLE mydbfordiploma.planner (
     id CHAR(36) PRIMARY KEY,
     title VARCHAR(50) NOT NULL,
     description VARCHAR(500),
     owner_id CHAR(36) NOT NULL
 );

 CREATE TABLE mydbfordiploma.users (
     id CHAR(16) PRIMARY KEY,
     first_name VARCHAR(50) NOT NULL,
     last_name VARCHAR(255) NOT NULL,
     email VARCHAR(250) UNIQUE NOT NULL,
     password VARCHAR(255) NOT NULL,
     role_id CHAR(36) NOT NULL,
     planner_id CHAR(36),
     FOREIGN KEY (role_id) REFERENCES mydbfordiploma.roles(id),
     FOREIGN KEY (planner_id) REFERENCES mydbfordiploma.planner(id)
 );

 CREATE TABLE mydbfordiploma.notes (
     id CHAR(36) PRIMARY KEY,
     header VARCHAR(255) NOT NULL,
     text TEXT NOT NULL,
     status VARCHAR(50),
     created_date TIMESTAMP NOT NULL,
     planner_id CHAR(36),
     user_id CHAR(36),
     FOREIGN KEY (planner_id) REFERENCES mydbfordiploma.planner(id),
     FOREIGN KEY (user_id) REFERENCES mydbfordiploma.users(id)
 );



--   ALTER TABLE mydbfordiploma.users CHANGE planner_id users_ibfk_2 BINARY(16) NOT NULL;
   

