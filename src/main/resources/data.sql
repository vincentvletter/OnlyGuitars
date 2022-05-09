
INSERT INTO profiles (id, username, password, enabled) VALUES (1002 ,'admin', '$2a$10$Z2cPoT34PCz9zqYmw3.Dt.UwcRoGMJYZMovkkUyyxkDASwnX0I8bq', 1);
INSERT INTO profiles (id, username, password, enabled) VALUES (1003 ,'user', '$2a$10$Z2cPoT34PCz9zqYmw3.Dt.UwcRoGMJYZMovkkUyyxkDASwnX0I8bq', 1);

INSERT INTO authorities (authority, username, profile_id) VALUES ('ADMIN', 'admin', 1002);
INSERT INTO authorities (authority, username, profile_id) VALUES ('USER', 'user', 1003);