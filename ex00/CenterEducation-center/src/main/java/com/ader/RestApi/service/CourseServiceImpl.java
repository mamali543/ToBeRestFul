package com.ader.RestApi.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.exception.BadRequestException;
import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.pojo.Lesson;
import com.ader.RestApi.pojo.Role;
import com.ader.RestApi.pojo.User;
import com.ader.RestApi.repositories.CourseRepository;
import com.ader.RestApi.repositories.LessonRepository;
import com.ader.RestApi.repositories.UserRepository;

/**
 * Service implementation class for managing courses and related operations
 */
@Service
public class CourseServiceImpl implements CourseService {

    /** Repository for course operations */
    @Autowired
    private CourseRepository courseRepository;

    /** Repository for user operations */
    @Autowired
    private UserRepository userRepository;

    /** Repository for lesson operations */
    @Autowired
    private LessonRepository lessonRepository;

    /**
     * Constructor for CourseServiceImpl
     * @param courseRepository Repository for course operations
     * @param lessonRepository Repository for lesson operations  
     * @param userRepository Repository for user operations
     */
    public CourseServiceImpl(CourseRepository courseRepository, LessonRepository lessonRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all courses with pagination
     * @param page Page number
     * @param size Number of items per page
     * @return List of courses
     */
    @Override
    public List<Course> getAllCourses(int page, int size) {
        return courseRepository.findAll(page, size);
    }

    /**
     * Creates a new course
     * @param course Course to create
     * @return Created course
     */
    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    /**
     * Retrieves a course by its ID
     * @param id Course ID
     * @return Optional containing the course if found
     */
    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    /**
     * Updates an existing course
     * @param course Course with updated information
     * @return Updated course
     */
    @Override
    public Course updateCourse(Course course) {
        return courseRepository.update(course);
    }

    /**
     * Saves a course (creates new or updates existing)
     * @param course Course to save
     * @return Saved course
     */
    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    /**
     * Deletes a course by its ID
     * @param id Course ID to delete
     */
    @Override
    public void deleteCourse(Long id) {
        courseRepository.delete(id);
    }

    /**
     * Adds a lesson to a course
     * @param lessonDto Lesson data transfer object
     * @return Created lesson
     */
    @Override
    public Lesson addLessonToCourse(LessonDto lessonDto)
    {
        Course course = courseRepository.findById(lessonDto.getCourseId()).orElseThrow(() -> new BadRequestException("course not found"));
        System.out.println("lessonDto: " + lessonDto+ " >>>\n");
        Lesson lesson = lessonRepository.saveDto(lessonDto);
        if (course.getLessons() == null) {
            course.setLessons(new ArrayList<>());
        }
        course.getLessons().add(lesson);
        return lesson;
    }

    /**
     * Retrieves all lessons for a course
     * @param course_id Course ID
     * @return List of lessons
     */
    @Override
    public List<Lesson> getLessonsByCourseId(Long course_id) {
        return lessonRepository.findByCourseId(course_id);
    }

    /**
     * Updates a lesson within a course
     * @param lessonId Lesson ID to update
     * @param lessonDto Updated lesson data
     * @return Updated lesson
     */
    @Override
    public Lesson updateLessonByCourse(Long lessonId, LessonDto lessonDto) {
        Course course = courseRepository.findById(lessonDto.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found"));
        Lesson lessonToUpdate = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new RuntimeException("Lesson not found with id: " + lessonId));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (lessonDto.getStartTime() == null) {
            lessonDto.setStartTime(lessonToUpdate.getStartTime().format(formatter));   
        }
        if (lessonDto.getEndTime() == null) {
            lessonDto.setEndTime(lessonToUpdate.getEndTime().format(formatter));
        }
        if (lessonDto.getDayOfWeek() == null) {
            lessonDto.setDayOfWeek(lessonToUpdate.getDayOfWeek());
        }
        if (lessonDto.getTeacherId() == null) {
            lessonDto.setTeacherId(lessonToUpdate.getTeacher().getId());
        }
        return lessonRepository.updateDto(lessonDto, lessonId);
    }

    /**
     * Deletes a lesson from a course
     * @param lesson_id Lesson ID to delete
     * @param course_id Course ID
     */
    @Override
    public void deleteLessonByCourse(Long lesson_id, Long course_id) {
        Course course = courseRepository.findById(course_id).orElseThrow(() -> new RuntimeException("Lesson not found"));
        lessonRepository.delete(lesson_id);
    }

    /**
     * Adds a student to a course
     * @param student_id Student ID to add
     * @param course_id Course ID
     * @return Added student
     */
    @Override
    public User addStudentToCourse(Long student_id, Long course_id) {
        User student = userRepository.findById(student_id).orElseThrow(() -> new BadRequestException("Student not found"));
        if (student.getRole() != Role.STUDENT) {
            throw new BadRequestException("User is not a student");
        }
        Course course = courseRepository.findById(course_id).orElseThrow(() -> new BadRequestException("Course not found"));
        courseRepository.addStudentToCourse(student_id, course_id);
        return student;
    }

    /**
     * Retrieves all students enrolled in a course
     * @param course_id Course ID
     * @return List of students
     */
    @Override
    public List<User> getStudentsByCourseId(Long course_id) {
        return courseRepository.getStudentsByCourseId(course_id);
    }

    /**
     * Removes a student from a course
     * @param student_id Student ID to remove
     * @param course_id Course ID
     */
    @Override
    public void deleteStudentFromCourse(Long student_id, Long course_id) {
        courseRepository.deleteStudentFromCourse(student_id, course_id);
    }

    /**
     * Adds a teacher to a course
     * @param teacher_id Teacher ID to add
     * @param course_id Course ID
     * @return Added teacher
     */
    @Override
    public User addTeacherToCourse(Long teacher_id, Long course_id) {
        User teacher = userRepository.findById(teacher_id).orElseThrow(() -> new BadRequestException("Teacher not found"));
        if (teacher.getRole() != Role.TEACHER) {
            throw new BadRequestException("User is not a teacher");
        }
        Course course = courseRepository.findById(course_id).orElseThrow(() -> new BadRequestException("Course not found"));
        courseRepository.addTeacherToCourse(teacher_id, course_id);
        return teacher;
    }

    /**
     * Retrieves all teachers assigned to a course
     * @param course_id Course ID
     * @return List of teachers
     */
    @Override   
    public List<User> getTeachersByCourseId(Long course_id) {
        return courseRepository.getTeachersByCourseId(course_id);
    }

    /**
     * Removes a teacher from a course
     * @param teacher_id Teacher ID to remove
     * @param course_id Course ID
     */
    @Override
    public void deleteTeacherFromCourse(Long teacher_id, Long course_id) {
        courseRepository.deleteTeacherFromCourse(teacher_id, course_id);
    }
}
