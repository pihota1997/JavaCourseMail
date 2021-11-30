INSERT INTO users(name, password)
VALUES ('manager', 'manager'),
       ('guest', 'guest');

INSERT INTO roles(role)
VALUES ('manager'),
       ('guest');

INSERT INTO user_roles(user_id, role_id)
VALUES (1, 1),
       (2, 2);