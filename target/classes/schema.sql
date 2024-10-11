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

-- Create lesson table
CREATE TABLE IF NOT EXISTS spring.lessons (
    lessonId SERIAL PRIMARY KEY,
    startTime TIME NOT NULL,
    endTime TIME NOT NULL,
    dayOfWeek VARCHAR(50) NOT NULL,
    teacherId BIGINT NOT NULL,
    FOREIGN KEY (teacherId) REFERENCES spring.users(userId)
);

CREATE TABLE IF NOT EXISTS spring.courses (
    courseId SERIAL PRIMARY KEY,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

