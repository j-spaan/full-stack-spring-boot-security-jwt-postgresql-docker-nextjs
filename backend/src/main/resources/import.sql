-- FOR TESTING PURPOSES ONLY
INSERT INTO roles(id, name) VALUES('1','ROLE_USER');
INSERT INTO roles(id, name) VALUES('2','ROLE_MODERATOR');
INSERT INTO roles(id, name) VALUES('3','ROLE_ADMIN');

INSERT INTO users(id, first_name, last_name, email, username, password) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48340', 'userFirst', 'userLast', 'user@mail.com', 'user', '$argon2id$v=19$m=16384,t=2,p=1$rAV5qOZ88GDboAhP0AYo1w$JeOR/LWYSFA1D+X0vUpcM76MwoTbwfHRDT4Dc5XyLkk');
INSERT INTO users(id, first_name, last_name, email, username, password) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48341', 'managerFirst', 'managerLast', 'manager@mail.com', 'manager', '$argon2id$v=19$m=16384,t=2,p=1$rAV5qOZ88GDboAhP0AYo1w$JeOR/LWYSFA1D+X0vUpcM76MwoTbwfHRDT4Dc5XyLkk');
INSERT INTO users(id, first_name, last_name, email, username, password) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48342', 'adminFirst', 'adminLast', 'admin@mail.com', 'admin', '$argon2id$v=19$m=16384,t=2,p=1$rAV5qOZ88GDboAhP0AYo1w$JeOR/LWYSFA1D+X0vUpcM76MwoTbwfHRDT4Dc5XyLkk');

INSERT INTO user_roles(user_id, role_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48340', '1');
INSERT INTO user_roles(user_id, role_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48341', '1');
INSERT INTO user_roles(user_id, role_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48341', '2');
INSERT INTO user_roles(user_id, role_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48342', '1');
INSERT INTO user_roles(user_id, role_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48342', '2');
INSERT INTO user_roles(user_id, role_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48342', '3');