INSERT INTO user (user_id, name, email, role) VALUES (1, 'Alice Johnson', 'alice@work.com','ADMIN');
INSERT INTO user (user_id, name, email, role) VALUES (2, 'Bob Williams', 'bob123@gmail.com','USER');
INSERT INTO user (user_id, name, email, role) VALUES (3, 'Charles Darwin', 'charlesD@gmailcom','USER');
INSERT INTO user (user_id, name, email, role) VALUES (4, 'Don Diego', 'DDigeo@gmail.com','USER');
INSERT INTO user (user_id, name, email, role) VALUES (5, 'Ellie Willson', 'Ellie1999@work.com','MANAGER');
INSERT INTO user (user_id, name, email, role) VALUES (6, 'Flanders Ned', 'NedFlan@gmail.com','USER');
INSERT INTO user (user_id, name, email, role) VALUES (7, 'George Theore', 'GeogreT22@gmailcom','USER');
INSERT INTO user (user_id, name, email, role) VALUES (8, 'Hillary Manders', 'hhhillsMandy@gmail.com','USER');
ALTER TABLE user AUTO_INCREMENT = 3;


INSERT INTO task (id, title, description, status) VALUES (101, 'Review Code', 'Check pull request #5', 'OPEN');
INSERT INTO task (id, title, description, status) VALUES (102, 'Fix Bug', 'Database connection fails', 'PENDING');
ALTER TABLE task AUTO_INCREMENT = 103;

INSERT INTO assignment (id, assigned_date, task_id, user_id)
VALUES (201, '2025-10-20', 101, 1);
INSERT INTO assignment (id, assigned_date, task_id, user_id)
VALUES (202, '2025-10-20', 102, 2);
ALTER TABLE assignment AUTO_INCREMENT = 203;