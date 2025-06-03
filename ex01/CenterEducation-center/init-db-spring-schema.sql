--This script is executed when the PostgreSQL container initializes the database.
    CREATE SCHEMA IF NOT EXISTS spring;
    
-- This allows 'amalireda' to create tables and other objects within this schema.
    ALTER SCHEMA spring OWNER TO amalireda;