package com.ader.RestApi.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.exception.BadRequestException;
import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.pojo.Lesson;
import com.ader.RestApi.pojo.Role;
import com.ader.RestApi.pojo.User;
import com.ader.RestApi.repositories.CourseRepository;
import com.ader.RestApi.repositories.LessonRepository;
import com.ader.RestApi.repositories.UserRepository;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, LessonRepository lessonRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<Course> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

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
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course updateCourse(Course course) {
        if (!courseRepository.existsById(course.getCourseId())) {
            throw new BadRequestException("Course not found with id: " + course.getCourseId());
        }
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new BadRequestException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

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

    @Override
    public Lesson updateLessonByCourse(Long lessonId, LessonDto lessonDto) {
        Course course = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(() -> new BadRequestException("Course not found"));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new BadRequestException("Lesson not found"));

        if (!lesson.getCourse().getCourseId().equals(course.getCourseId())) {
            throw new BadRequestException("Lesson does not belong to this course");
        }

        lesson.setStartTime(lessonDto.getStartTime());
        lesson.setEndTime(lessonDto.getEndTime());
        lesson.setDayOfWeek(lessonDto.getDayOfWeek());

        if (lessonDto.getTeacherId() != null) {
            User teacher = userRepository.findById(lessonDto.getTeacherId())
                    .orElseThrow(() -> new BadRequestException("Teacher not found"));
            if (teacher.getRole() != Role.TEACHER) {
                throw new BadRequestException("User is not a teacher");
            }
            lesson.setTeacher(teacher);
        }

        return lessonRepository.save(lesson);
    }

    @Override
    public void deleteLessonByCourse(Long lessonId, Long courseId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new BadRequestException("Lesson not found"));

        if (!lesson.getCourse().getCourseId().equals(courseId)) {
            throw new BadRequestException("Lesson does not belong to this course");
        }

        lessonRepository.delete(lesson);
    }

    @Override
    public User addStudentToCourse(Long studentId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException("Course not found"));

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new BadRequestException("Student not found"));

        if (student.getRole() != Role.STUDENT) {
            throw new BadRequestException("User is not a student");
        }

        course.getStudents().add(student);
        courseRepository.save(course);
        return student;
    }

    @Override
    public List<User> getStudentsByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException("Course not found"));
        return course.getStudents();
    }

    @Override
    public void deleteStudentFromCourse(Long studentId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException("Course not found"));

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new BadRequestException("Student not found"));

        course.getStudents().remove(student);
        courseRepository.save(course);
    }

    @Override
    public User addTeacherToCourse(Long teacherId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException("Course not found"));

        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new BadRequestException("Teacher not found"));

        if (teacher.getRole() != Role.TEACHER) {
            throw new BadRequestException("User is not a teacher");
        }

        course.getTeachers().add(teacher);
        courseRepository.save(course);
        return teacher;
    }

    @Override
    public List<User> getTeachersByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException("Course not found"));
        return course.getTeachers();
    }

    @Override
    public void deleteTeacherFromCourse(Long teacherId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException("Course not found"));

        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new BadRequestException("Teacher not found"));

        course.getTeachers().remove(teacher);
        courseRepository.save(course);
    }
}
