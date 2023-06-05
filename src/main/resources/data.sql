CREATE TABLE IF NOT EXISTS positions
(
    id            SERIAL PRIMARY KEY,
    position_name CHARACTER VARYING(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS employees
(
    id          SERIAL PRIMARY KEY,
    first_name  CHARACTER VARYING(30) NOT NULL,
    last_name   CHARACTER VARYING(30) NOT NULL,
    email       CHARACTER VARYING(30) NOT NULL,
    age         INTEGER               NOT NULL,
    id_position INTEGER,
    FOREIGN KEY (id_position)
        REFERENCES positions (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS projects
(
    id           SERIAL PRIMARY KEY,
    project_name CHARACTER VARYING(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS employees_to_projects
(
    id          SERIAL PRIMARY KEY,
    id_employee INTEGER NOT NULL,
    id_project  INTEGER NOT NULL,
    FOREIGN KEY (id_employee)
        REFERENCES employees (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_project)
        REFERENCES projects (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

INSERT INTO positions (position_name)
VALUES ('Director'),
       ('Backand Developer'),
       ('Frontend Developer'),
       ('Devops Engineer'),
       ('HR manager');

INSERT INTO employees (first_name, last_name, email, age, id_position)
VALUES ('Андрей', 'Кожевников', 'koshev@mail.ru', 33, 2),
       ('Михаил', 'Горбач', 'gorbach@mail.ru', 35, 1),
       ('Людмила', 'Гуляева', 'gulaeva@mail.ru', 40, 3),
       ('Владимир', 'Мономах', 'monomah@mail.ru', 25, 5),
       ('Екатерина', 'Каренина', 'carenina@mail.ru', 20, 4),
       ('Александр', 'Лобов', 'lobov@mail.ru', 23, 1),
       ('Кирилл', 'Корин', 'corin@mail.ru', 27, 2);

INSERT INTO projects (project_name)
VALUES ('ChatProject'),
       ('CloudProject'),
       ('MarketProject');

INSERT INTO employees_to_projects (id_employee, id_project)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 3),
       (5, 2),
       (1, 3),
       (6, 2),
       (7, 3);