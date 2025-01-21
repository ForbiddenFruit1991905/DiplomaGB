

CREATE TABLE mydbfordiploma.roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50),
    user_id VARCHAR(50)
    -- FOREIGN KEY (user_id) REFERENCES mydbfordiploma.users(id)
);

CREATE TABLE mydbfordiploma.planner (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    owner_id BIGINT NOT NULL
    -- FOREIGN KEY (owner_id) REFERENCES mydbfordiploma.users(id)
);

CREATE TABLE mydbfordiploma.users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(250) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id BIGINT NOT NULL,
    planner_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES mydbfordiploma.roles(id),
    FOREIGN KEY (planner_id) REFERENCES mydbfordiploma.planner(id)
);

CREATE TABLE mydbfordiploma.notes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    header VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    status VARCHAR(50),
    created_date TIMESTAMP NOT NULL,
    planner_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (planner_id) REFERENCES mydbfordiploma.planner(id),
    FOREIGN KEY (user_id) REFERENCES mydbfordiploma.users(id)
);
 

