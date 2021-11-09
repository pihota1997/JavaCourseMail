CREATE TABLE academic_performance
(
    id             SERIAL  NOT NULL,
    lesson_id    INT     NOT NULL REFERENCES lessons (id) ON UPDATE CASCADE ON DELETE CASCADE,
    student_id     INT     NOT NULL REFERENCES students (id) ON UPDATE CASCADE ON DELETE CASCADE,
    attendance     BOOLEAN NOT NULL,
    homework_grade INT     NOT NULL,
    CONSTRAINT academic_performance_pk PRIMARY KEY (id)
);