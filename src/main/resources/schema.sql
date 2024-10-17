-- Create schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS spring;

-- Switch to schema

-- Create user table
CREATE TABLE IF NOT EXISTS spring.users (
    userId BIGSERIAL PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    login VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role INT
);

CREATE TABLE IF NOT EXISTS spring.courses (
    courseId SERIAL PRIMARY KEY,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Create lesson table
CREATE TABLE IF NOT EXISTS spring.lessons (
    lessonId SERIAL PRIMARY KEY,
    startTime TIME NOT NULL,
    endTime TIME NOT NULL,
    dayOfWeek VARCHAR(50) NOT NULL,
    teacherId BIGINT NOT NULL,
    courseId BIGINT NOT NULL,
    FOREIGN KEY (courseId) REFERENCES spring.courses(courseId),
    FOREIGN KEY (teacherId) REFERENCES spring.users(userId)
);

-- Existing tables (users, courses, lessons) remain the same

-- Create junction table for course-student relationship
CREATE TABLE IF NOT EXISTS spring.course_students (
    courseId BIGINT NOT NULL,
    studentId BIGINT NOT NULL,
    PRIMARY KEY (courseId, studentId),
    FOREIGN KEY (courseId) REFERENCES spring.courses(courseId),
    FOREIGN KEY (studentId) REFERENCES spring.users(userId)
);

-- Create junction table for course-teacher relationship
CREATE TABLE IF NOT EXISTS spring.course_teachers (
    courseId BIGINT NOT NULL,
    teacherId BIGINT NOT NULL,
    PRIMARY KEY (courseId, teacherId),
    FOREIGN KEY (courseId) REFERENCES spring.courses(courseId),
    FOREIGN KEY (teacherId) REFERENCES spring.users(userId)
);

