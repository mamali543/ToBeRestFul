-- Insert sample users
INSERT INTO
    spring.users (
        firstName,
        lastName,
        login,
        password,
        role
    )
VALUES (
        'John',
        'Doe',
        'jdoe',
        'password123',
        1
    ),
    (
        'Jane',
        'Smith',
        'jsmith',
        'password456',
        2
    );

-- Insert sample courses
INSERT INTO
    spring.courses (
        startDate,
        endDate,
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
        starttime,
        endTime,
        dayOfWeek,
        teacherId,
        courseId
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
    spring.course_students (courseId, studentId)
VALUES (1, 2),
    (2, 1);

-- Insert sample course_teachers
INSERT INTO
    spring.course_teachers (courseId, teacherId)
VALUES (1, 1),
    (2, 2);