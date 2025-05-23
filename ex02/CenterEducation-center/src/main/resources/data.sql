-- Create schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS spring;

-- Insert sample users with BCrypt encoded passwords
INSERT INTO
    spring.users (
        first_name,
        last_name,
        login,
        password,  -- Passwords must be BCrypt encoded
        role
    )
VALUES (
        'John', -- 123456
        'Doe',
        'jdoe',
        '$2y$10$A1XWYkZT6Fa6JF2a0SLFmuXLKYOo/9qqpwoHbqf9pgtWcI8MW52pS',  -- Use BCrypt encoded password
        0
    ),
    (
        'Jane', -- janejane
        'Smith',
        'jsmith',
        '$2y$10$cGsNxvVXuB7BC8Yu4Va7Qup5CS7qiYiyw3Wl.lDPJwSL9uo40S37W',  -- Use BCrypt encoded password
        1
    ),
    (
        'mosan',
        'mosan',
        'mosani',
        '$2a$10$YourBCryptEncodedPasswordHere',  -- Use BCrypt encoded password
        2
    )
    ;

-- Insert sample courses
INSERT INTO
    spring.courses (
        start_date,
        end_date,
        name,
        description,
        course_state
    )
VALUES (
        '2024-01-01',
        '2024-01-05',
        'Course 1',
        'Description 1',
        'Draft'
    ),
    (
        '2024-01-06',
        '2024-01-10',
        'Course 2',
        'Description 2',
        'Draft'
    );
-- Insert sample lessons
INSERT INTO
    spring.lessons (
        start_time,
        end_time,
        day_of_week,
        teacher_id,
        course_id
    )
VALUES (
        '09:00:00',
        '10:30:00',
        'Monday',
        2,
        1
    ),
    (
        '11:00:00',
        '12:30:00',
        'Tuesday',
        2,
        2
    );

-- Insert sample course_students
INSERT INTO
    spring.course_students (course_id, student_id)
VALUES (1, 3),
    (2, 3);

-- Insert sample course_teachers
INSERT INTO
    spring.course_teachers (course_id, teacher_id)
VALUES (1, 2),
    (2, 2);