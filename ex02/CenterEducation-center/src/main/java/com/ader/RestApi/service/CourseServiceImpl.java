package com.ader.RestApi.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.enums.CourseState;
import com.ader.RestApi.enums.Role;
import com.ader.RestApi.exception.BadRequestException;
// import com.ader.RestApi.exception.ResourceNotFoundException;
import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.pojo.Lesson;
import com.ader.RestApi.pojo.User;
import com.ader.RestApi.repositories.CourseRepository;
import com.ader.RestApi.repositories.LessonRepository;
import com.ader.RestApi.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    /*
     * --------------------------------- Managing Courses * ---------------------------------*/

    @Override
    public Course createCourse(Course course) {
        // 1. Save teachers
        if (course.getTeachers() != null) {
            List<User> savedTeachers = course.getTeachers().stream()
                    .map(teacher -> userRepository.findByLogin(teacher.getLogin())
                            .orElseGet(() -> userRepository.save(teacher)))
                    .collect(Collectors.toList());
            course.setTeachers(savedTeachers);
        }

        // 2. Save students
        if (course.getStudents() != null) {
            List<User> savedStudents = course.getStudents().stream()
                    .map(student -> userRepository.findByLogin(student.getLogin())
                            .orElseGet(() -> userRepository.save(student)))
                    .collect(Collectors.toList());
            course.setStudents(savedStudents);
        }

        // 3. Save the course first (without lessons)
        List<Lesson> tempLessons = course.getLessons();
        course.setLessons(new ArrayList<>());
        Course savedCourse = courseRepository.save(course);

        // 4. Save lessons with references to the saved course
        if (tempLessons != null) {
            List<Lesson> savedLessons = tempLessons.stream()
                    .map(lesson -> {
                        // Find or save the teacher for this lesson
                        User teacher = userRepository.findByLogin(lesson.getTeacher().getLogin())
                                .orElseGet(() -> userRepository.save(lesson.getTeacher()));

                        // Set the saved course and teacher
                        lesson.setCourse(savedCourse);
                        lesson.setTeacher(teacher);

                        return lessonRepository.save(lesson);
                    })
                    .collect(Collectors.toList());

            // Update course with saved lessons
            savedCourse.setLessons(savedLessons);
        }

        return savedCourse;
    }

    @Override
    public Course updateCourse(Course course) {
        Course existingCourse = courseRepository.findById(course.getCourseId())
                .orElseThrow(() -> new BadRequestException("Course not found"));

        // Update basic fields
        existingCourse.setStartDate(course.getStartDate());
        existingCourse.setEndDate(course.getEndDate());
        existingCourse.setName(course.getName());
        existingCourse.setDescription(course.getDescription());

        // Update teachers
        if (course.getTeachers() != null) {
            existingCourse.setTeachers(course.getTeachers());
        }

        // Update students
        if (course.getStudents() != null) {
            existingCourse.setStudents(course.getStudents());
        }

        // Update lessons
        if (course.getLessons() != null) {
            // Clear existing lessons
            existingCourse.getLessons().clear();

            // Add new lessons with proper course reference
            course.getLessons().forEach(lesson -> {
                lesson.setCourse(existingCourse); // Set the course reference
                existingCourse.getLessons().add(lesson);
            });
        }

        return courseRepository.save(existingCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new BadRequestException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

    /*
     * --------------------------------- Managing course Lessons * ---------------------------------*/
    @Override
    public Lesson addLessonToCourse(LessonDto lessonDto) {
        Course course = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(() -> new BadRequestException("Course not found"));

        User teacher = userRepository.findById(lessonDto.getTeacherId())
                .orElseThrow(() -> new BadRequestException("Teacher not found"));

        if (teacher.getRole() != Role.TEACHER) {
            throw new BadRequestException("User is not a teacher");
        }

        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lesson.setTeacher(teacher);
        lesson.setStartTime(lessonDto.getStartTime());
        lesson.setEndTime(lessonDto.getEndTime());
        lesson.setDayOfWeek(lessonDto.getDayOfWeek());

        return lessonRepository.save(lesson);
    }

    @Override
    public List<Lesson> getLessonsByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new BadRequestException("Course not found with id: " + courseId);
        }
        return lessonRepository.findByCourse_CourseId(courseId);
    }

    // This method updates a lesson by course
    @Override
    public Lesson updateLessonByCourse(Long lessonId, LessonDto lessonDto) {
        // Fetch the new course
        Course newCourse = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(() -> new BadRequestException("Course not found"));

        // Fetch the lesson to be updated
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new BadRequestException("Lesson not found"));

        // If the lesson is changing to a different course
        if (!lesson.getCourse().getCourseId().equals(newCourse.getCourseId())) {
            // Remove the lesson from the old course
            lesson.getCourse().getLessons().remove(lesson);
            // Add the lesson to the new course
            lesson.setCourse(newCourse);
            newCourse.getLessons().add(lesson);
        }

        // Update the lesson details
        lesson.setStartTime(lessonDto.getStartTime());
        lesson.setEndTime(lessonDto.getEndTime());
        lesson.setDayOfWeek(lessonDto.getDayOfWeek());

        // If a new teacher is specified
        if (lessonDto.getTeacherId() != null) {
            // Fetch the teacher
            User teacher = userRepository.findById(lessonDto.getTeacherId())
                    .orElseThrow(() -> new BadRequestException("Teacher not found"));
            // If the user is not a teacher, throw an exception
            if (teacher.getRole() != Role.TEACHER) {
                throw new BadRequestException("User is not a teacher");
            }

            // If the teacher is not associated with the new course, add them
            if (!newCourse.getTeachers().contains(teacher)) {
                newCourse.getTeachers().add(teacher);
                teacher.getTaughtCourses().add(newCourse);
            }

            // Set the teacher for the lesson
            lesson.setTeacher(teacher);
        }

        // Save and return the updated lesson
        return lessonRepository.save(lesson);
    }

    @Override
    public void deleteLessonByCourse(Long lessonId, Long courseId) {
        // Fetch the lesson to be deleted
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new BadRequestException("Lesson not found"));

        // Check if the lesson belongs to the specified course
        if (!lesson.getCourse().getCourseId().equals(courseId)) {
            throw new BadRequestException("Lesson does not belong to this course");
        }

        // Remove the lesson from the database
        lessonRepository.delete(lesson);
    }

    /*
     * --------------------------------- Managing course Students * ---------------------------------*/
    @Override
    public User addStudentToCourse(Long studentId, Long courseId) {
        // Fetch the course to which the student will be added
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException("Course not found"));

        // Fetch the student to be added
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new BadRequestException("Student not found"));

        // Check if the user is a student
        if (student.getRole() != Role.STUDENT) {
            throw new BadRequestException("User is not a student");
        }

        // Add the student to the course
        course.getStudents().add(student);
        // Update the other side of the relationship
        student.getEnrolledCourses().add(course);
        
        courseRepository.save(course);
        return student;
    }

    @Override
    public List<User> getStudentsByCourseId(Long courseId) {
        // Fetch the course to get its students
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException("Course not found"));
        return course.getStudents();
    }

    @Override
    public void deleteStudentFromCourse(Long studentId, Long courseId) {
        // Fetch the course from which the student will be removed
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException("Course not found"));

        // Fetch the student to be removed
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new BadRequestException("Student not found"));

        // Remove the student from the course
        course.getStudents().remove(student);
        // Update the other side of the relationship
        student.getEnrolledCourses().remove(course);
        
        courseRepository.save(course);
    }

    /*
     * --------------------------------- Managing course Teachers * ---------------------------------*/
    @Override
    public User addTeacherToCourse(Long teacherId, Long courseId) {
        // Fetch the course to which the teacher will be added
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException("Course not found"));

        // Fetch the teacher to be added
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new BadRequestException("Teacher not found"));

        // Check if the user is a teacher
        if (teacher.getRole() != Role.TEACHER) {
            throw new BadRequestException("User is not a teacher");
        }

        // Add the teacher to the course
        course.getTeachers().add(teacher);
        // Update the other side of the relationship
        teacher.getTaughtCourses().add(course);
        
        courseRepository.save(course);
        return teacher;
    }

    @Override
    public List<User> getTeachersByCourseId(Long courseId) {
        // Fetch the course to get its teachers
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException("Course not found"));
        return course.getTeachers();
    }

    @Override
    public void deleteTeacherFromCourse(Long teacherId, Long courseId) {
        // Fetch the course from which the teacher will be removed
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException("Course not found"));

        // Fetch the teacher to be removed
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new BadRequestException("Teacher not found"));

        // Remove the teacher from the course
        course.getTeachers().remove(teacher);
        // Update the other side of the relationship
        teacher.getTaughtCourses().remove(course);
        
        courseRepository.save(course);
    }

    @Override
    public List<Course> getCoursesByStudentId(Long studentId) {
        User student = userRepository.findById(studentId)
            .orElseThrow(() -> new BadRequestException("Student not found"));
        return student.getEnrolledCourses();
    }

    @Override
    public List<Course> getCoursesByTeacherId(Long teacherId) {
        User teacher = userRepository.findById(teacherId)
            .orElseThrow(() -> new BadRequestException("Teacher not found"));
        return teacher.getTaughtCourses();
    }

    @Override
    public Course publishCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException("Course not found"));

        // Check if the course is in draft state
        if (course.getCourseState() != CourseState.Draft) {
            throw new BadRequestException("Only draft courses can be published");
        }

        // Change the state to PUBLISHED
        course.setCourseState(CourseState.Published);
        
        // Save the updated course
        return courseRepository.save(course);
    }
}
