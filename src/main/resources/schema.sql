-- Create schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS spring;

-- Switch to schema

-- Create user table
CREATE TABLE IF NOT EXISTS spring.users (
    userId SERIAL PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    login VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
    );
