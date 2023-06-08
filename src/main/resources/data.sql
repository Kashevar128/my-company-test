

INSERT INTO positions (position_name)
VALUES ('Director'),
       ('Backand Developer'),
       ('Frontend Developer'),
       ('Devops Engineer'),
       ('HR manager');

INSERT INTO employees (first_name, last_name, email, age)
VALUES ('Андрей', 'Кожевников', 'koshev@mail.ru', 33),
       ('Михаил', 'Горбач', 'gorbach@mail.ru', 35),
       ('Людмила', 'Гуляева', 'gulaeva@mail.ru', 40),
       ('Владимир', 'Мономах', 'monomah@mail.ru', 25),
       ('Екатерина', 'Каренина', 'carenina@mail.ru', 20),
       ('Александр', 'Лобов', 'lobov@mail.ru', 23),
       ('Кирилл', 'Корин', 'corin@mail.ru', 27);

INSERT INTO projects (project_name)
VALUES ('ChatProject'),
       ('CloudProject'),
       ('MarketProject');

INSERT INTO projects_to_employees (id_employee, id_project)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 3),
       (5, 2),
       (1, 3),
       (6, 2),
       (7, 3);

INSERT INTO positions_to_employees (id_employee, id_position)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 1),
       (7, 2)