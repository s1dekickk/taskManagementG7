DROP TABLE IF EXISTS assignment;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS user;


-- 2. CREATES (Dependency Order)
-- Create the parent tables (Task and User) first
-- NOTE: We are assuming your Task entity exists and maps to the 'task' table.

-- Create User Table (Parent)
CREATE TABLE user (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Create Task Table (Parent)
CREATE TABLE task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT, -- Assuming your Task primary key is named 'id'
    title VARCHAR(255) NOT NULL,
    description TEXT,
    -- Add other Task columns here (e.g., due_date, status)
    status VARCHAR(50)
);

-- Create Assignment Table (Child) last, with Foreign Keys
CREATE TABLE assignment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    assigned_date DATE, -- LocalDate typically maps to DATE

    -- Foreign Key for Task
    task_id BIGINT NOT NULL,
    FOREIGN KEY (task_id) REFERENCES task(id), -- References the Task table's ID

    -- Foreign Key for User
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id) -- References the User table's user_id
);