CREATE TABLE IF NOT EXISTS positions
(
    id serial NOT NULL PRIMARY KEY,
    name character varying(30) NOT NULL UNIQUE
    );

alter table positions
    owner to postgres;

CREATE TABLE IF NOT EXISTS employees
(
    id serial NOT NULL PRIMARY KEY,
    first_name character varying(30) NOT NULL,
    last_name character varying(30) NOT NULL,
    position_id integer,
    CONSTRAINT fk_emp_pos FOREIGN KEY (position_id)
    REFERENCES positions (id) MATCH SIMPLE
    ON UPDATE CASCADE
    ON DELETE SET NULL
    );

alter table employees
    owner to postgres;

CREATE TABLE IF NOT EXISTS projects
(
    id serial NOT NULL PRIMARY KEY,
    name character varying(30) NOT NULL UNIQUE
    );

alter table projects
    owner to postgres;

CREATE TABLE IF NOT EXISTS employees_2_projects
(
    employee_id integer NOT NULL,
    project_id integer NOT NULL,
    CONSTRAINT pk_emp_proj PRIMARY KEY (employee_id, project_id),
    CONSTRAINT fk_emp_emp_proj FOREIGN KEY (employee_id)
    REFERENCES employees (id) MATCH SIMPLE
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    CONSTRAINT fk_proj_emp_proj FOREIGN KEY (project_id)
    REFERENCES projects (id) MATCH SIMPLE
    ON UPDATE CASCADE
    ON DELETE CASCADE
    );

alter table employees_2_projects
    owner to postgres;