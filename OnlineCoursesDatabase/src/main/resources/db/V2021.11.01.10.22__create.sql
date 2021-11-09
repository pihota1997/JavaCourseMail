CREATE TABLE teachers
(
    id           SERIAL  NOT NULL,
    surname      VARCHAR NOT NULL,
    name         VARCHAR NOT NULL,
    phone_number VARCHAR NOT NUlL,
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
    id        SERIAL  NOT NULL,
    number    VARCHAR NOT NULL,
    course_id INT     NOT NULL REFERENCES courses (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT groups_pk PRIMARY KEY (id),
    CONSTRAINT groups_uc UNIQUE (number)
);

CREATE TABLE lessons
(
    id          SERIAL  NOT NULL,
    name        VARCHAR NOT NULL,
    description VARCHAR,
    CONSTRAINT lessons_pk PRIMARY KEY (id)
);

CREATE TABLE schedule
(
    id              SERIAL    NOT NULL,
    lesson_starting TIMESTAMP NOT NULL,
    lesson_ending   TIMESTAMP NOT NULL,
    lesson_id       INT       NOT NULL REFERENCES lessons (id) ON UPDATE CASCADE ON DELETE CASCADE,
    group_id        INT       NOT NULL REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE,
    teacher_id      INT       REFERENCES teachers (id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT schedule_pk PRIMARY KEY (id)
);

CREATE TABLE students
(
    id           SERIAL  NOT NULL,
    surname      VARCHAR NOT NULL,
    name         VARCHAR NOT NULL,
    phone_number VARCHAR NOT NULL,
    email        VARCHAR NOT NULL,
    group_id     INT     REFERENCES groups (id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT students_pk PRIMARY KEY (id)
);