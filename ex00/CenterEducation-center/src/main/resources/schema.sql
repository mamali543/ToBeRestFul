-- Create schema if it doesn't exist
DROP SCHEMA IF EXISTS spring CASCADE;
CREATE SCHEMA IF NOT EXISTS spring;

-- Switch to schema
-- Create user table
CREATE TABLE IF NOT EXISTS spring.users (
    user_id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    login VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role INT
);

CREATE TABLE IF NOT EXISTS spring.courses (
    course_id SERIAL PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Create lesson table
CREATE TABLE IF NOT EXISTS spring.lessons (
    lesson_id SERIAL PRIMARY KEY,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    day_of_week VARCHAR(50) NOT NULL,
    teacher_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES spring.courses(course_id),
    FOREIGN KEY (teacher_id) REFERENCES spring.users(user_id)
);

-- Existing tables (users, courses, lessons) remain the same

-- Create junction table for course-student relationship
CREATE TABLE IF NOT EXISTS spring.course_students (
    course_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    PRIMARY KEY (course_id, student_id),
    FOREIGN KEY (course_id) REFERENCES spring.courses(course_id),
    FOREIGN KEY (student_id) REFERENCES spring.users(user_id)
);

-- Create junction table for course-teacher relationship
CREATE TABLE IF NOT EXISTS spring.course_teachers (
    course_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    PRIMARY KEY (course_id, teacher_id),
    FOREIGN KEY (course_id) REFERENCES spring.courses(course_id),
    FOREIGN KEY (teacher_id) REFERENCES spring.users(user_id)
);

