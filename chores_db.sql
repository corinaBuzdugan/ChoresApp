CREATE TABLE parents (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE children (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    parent_id INT NOT NULL REFERENCES parents(id)
);
CREATE TABLE chores (
    id SERIAL PRIMARY KEY,
    chore_name VARCHAR(255) NOT NULL UNIQUE,
    points INT NOT NULL,
    description TEXT
);
CREATE TABLE daily_scores (
    id SERIAL PRIMARY KEY,
    child_id INT NOT NULL REFERENCES children(id),
    chore_id INT NOT NULL REFERENCES chores(id),
    date_completed DATE NOT NULL
);
CREATE TABLE weekly_reports (
    id SERIAL PRIMARY KEY,
    child_id INT NOT NULL REFERENCES children(id),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_score INT NOT NULL
);
