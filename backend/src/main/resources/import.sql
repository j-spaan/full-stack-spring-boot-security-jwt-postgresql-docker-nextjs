-- FOR TESTING PURPOSES ONLY
INSERT INTO permissions(id, name) VALUES('1','user:read');
INSERT INTO permissions(id, name) VALUES('2','user:create');
INSERT INTO permissions(id, name) VALUES('3','user:update');
INSERT INTO permissions(id, name) VALUES('4','user:delete');
INSERT INTO permissions(id, name) VALUES('5','moderator:read');
INSERT INTO permissions(id, name) VALUES('6','moderator:create');
INSERT INTO permissions(id, name) VALUES('7','moderator:update');
INSERT INTO permissions(id, name) VALUES('8','moderator:delete');
INSERT INTO permissions(id, name) VALUES('9','admin:read');
INSERT INTO permissions(id, name) VALUES('10','admin:create');
INSERT INTO permissions(id, name) VALUES('11','admin:update');
INSERT INTO permissions(id, name) VALUES('12','admin:delete');

INSERT INTO roles(id, name) VALUES('1','ROLE_USER');
INSERT INTO roles(id, name) VALUES('2','ROLE_MODERATOR');
INSERT INTO roles(id, name) VALUES('3','ROLE_ADMIN');


INSERT INTO users(id, first_name, last_name, email, username, password) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48340', 'userFirst', 'userLast', 'user@mail.com', 'user', '$argon2id$v=19$m=16384,t=2,p=1$rAV5qOZ88GDboAhP0AYo1w$JeOR/LWYSFA1D+X0vUpcM76MwoTbwfHRDT4Dc5XyLkk');
INSERT INTO users(id, first_name, last_name, email, username, password) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48341', 'managerFirst', 'managerLast', 'manager@mail.com', 'manager', '$argon2id$v=19$m=16384,t=2,p=1$rAV5qOZ88GDboAhP0AYo1w$JeOR/LWYSFA1D+X0vUpcM76MwoTbwfHRDT4Dc5XyLkk');
INSERT INTO users(id, first_name, last_name, email, username, password) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48342', 'adminFirst', 'adminLast', 'admin@mail.com', 'admin', '$argon2id$v=19$m=16384,t=2,p=1$rAV5qOZ88GDboAhP0AYo1w$JeOR/LWYSFA1D+X0vUpcM76MwoTbwfHRDT4Dc5XyLkk');

INSERT INTO roles_permissions(role_id, permission_id) VALUES ('1', '1');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('1', '2');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('1', '3');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('1', '4');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('2', '5');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('2', '6');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('2', '7');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('2', '8');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('3', '9');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('3', '10');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('3', '11');
INSERT INTO roles_permissions(role_id, permission_id) VALUES ('3', '12');

INSERT INTO users_roles(user_id, role_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48340', '1');
INSERT INTO users_roles(user_id, role_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48341', '1');
INSERT INTO users_roles(user_id, role_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48341', '2');
INSERT INTO users_roles(user_id, role_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48342', '1');
INSERT INTO users_roles(user_id, role_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48342', '2');
INSERT INTO users_roles(user_id, role_id) VALUES ('21b14b90-861f-4883-b457-ddf8d5f48342', '3');