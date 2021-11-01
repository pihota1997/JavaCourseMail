CREATE TABLE teachers
(
    id           SERIAL  NOT NULL,
    surname      VARCHAR NOT NULL,
    name         VARCHAR NOT NULL,
    phone_number VARCHAR,
    email        VARCHAR NOT NULL,
    CONSTRAINT teachers_pk PRIMARY KEY (id)
);

CREATE TABLE courses
(
    id   SERIAL  NOT NULL,
    name VARCHAR NOT NULL,
    CONSTRAINT courses_pk PRIMARY KEY (id)
);

CREATE TABLE groups
(
    id        SERIAL NOT NULL,
    number    INT    NOT NULL,
    course_id INT    NOT NULL REFERENCES courses (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT groups_pk PRIMARY KEY (id),
    CONSTRAINT groups_uc UNIQUE (number)
);

CREATE TABLE groups_teachers
(
    teacher_id INT NOT NULL REFERENCES teachers (id) ON UPDATE CASCADE ON DELETE CASCADE,
    group_id   INT NOT NULL REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT courses_teachers_pk PRIMARY KEY (teacher_id, group_id)
);

CREATE TABLE schedule
(
    id          SERIAL NOT NULL,
    data        DATE   NOT NULL,
    description VARCHAR,
    group_id    INT    NOT NULL REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT schedule_pk PRIMARY KEY (id)
);

CREATE TABLE students
(
    id           SERIAL  NOT NULL,
    surname      VARCHAR NOT NULL,
    name         VARCHAR NOT NULL,
    phone_number VARCHAR,
    email        VARCHAR NOT NULL,
    group_id     INT     NOT NULL REFERENCES groups (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT students_pk PRIMARY KEY (id)
);