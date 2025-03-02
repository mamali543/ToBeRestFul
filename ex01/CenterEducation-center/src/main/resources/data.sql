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
        'John',
        'Doe',
        'jdoe',
        '$2a$10$YourBCryptEncodedPasswordHere',  -- Use BCrypt encoded password
        0
    ),
    (
        'Jane',
        'Smith',
        'jsmith',
        '$2a$10$YourBCryptEncodedPasswordHere',  -- Use BCrypt encoded password
        1
    );

-- Insert sample courses
INSERT INTO
    spring.courses (
        start_date,
        end_date,
        name,
        description
    )
VALUES (
        '2024-01-01',
        '2024-01-05',
        'Course 1',
        'Description 1'
    ),
    (
        '2024-01-06',
        '2024-01-10',
        'Course 2',
        'Description 2'
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
        1,
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
VALUES (1, 2),
    (2, 1);

-- Insert sample course_teachers
INSERT INTO
    spring.course_teachers (course_id, teacher_id)
VALUES (1, 1),
    (2, 2);