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

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonRepository lessonRepository;

    public CourseServiceImpl(CourseRepository courseRepository, LessonRepository lessonRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Course> getAllCourses(int page, int size) {
        return courseRepository.findAll(page, size);
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course updateCourse(Course course) {
        return courseRepository.update(course);
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.delete(id);
    }

    @Override
    public Lesson addLessonToCourse(LessonDto lessonDto)
    {
        Course course = courseRepository.findById(lessonDto.getCourseId()).orElseThrow(() -> new BadRequestException("course not found"));
        Lesson lesson1 = lessonRepository.findById(lessonDto.getLessonId()).orElseThrow(() -> new BadRequestException("lesson not found"));
        Lesson lesson = lessonRepository.saveDto(lessonDto);
        if (course.getLessons() == null) {
            course.setLessons(new ArrayList<>());
        }
        course.getLessons().add(lesson);
        return lesson;
    }

    @Override
    public List<Lesson> getLessonsByCourseId(Long courseId) {
        return lessonRepository.findByCourseId(courseId);
    }

    @Override
    public Lesson updateLessonByCourse(Long lessonId, LessonDto lessonDto) {
        //verify if there's a course with this id!
        Course course = courseRepository.findById(lessonDto.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found"));
         // Find the lesson
        Lesson lessonToUpdate = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new RuntimeException("Lesson not found with id: " + lessonId));
        // Update only non-null fields
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
        //update the lesson
        return lessonRepository.updateDto(lessonDto, lessonId);
    }

    @Override
    public void deleteLessonByCourse(Long lessonId, Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Lesson not found"));
        lessonRepository.delete(lessonId);
    }

    @Override
    public User addStudentToCourse(Long studentId, Long courseId) {
        User student = userRepository.findById(studentId).orElseThrow(() -> new BadRequestException("Student not found"));
        if (student.getRole() != Role.STUDENT) {
            throw new BadRequestException("User is not a student");
        }
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new BadRequestException("Course not found"));
        courseRepository.addStudentToCourse(studentId, courseId);
        return student;
    }

    @Override
    public List<User> getStudentsByCourseId(Long courseId) {
        return courseRepository.getStudentsByCourseId(courseId);
    }

    @Override
    public void deleteStudentFromCourse(Long studentId, Long courseId) {
        courseRepository.deleteStudentFromCourse(studentId, courseId);
    }

    @Override
    public User addTeacherToCourse(Long teacherId, Long courseId) {
        User teacher = userRepository.findById(teacherId).orElseThrow(() -> new BadRequestException("Teacher not found"));
        if (teacher.getRole() != Role.TEACHER) {
            throw new BadRequestException("User is not a teacher");
        }
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new BadRequestException("Course not found"));
        courseRepository.addTeacherToCourse(teacherId, courseId);
        return teacher;
    }

    @Override   
    public List<User> getTeachersByCourseId(Long courseId) {
        return courseRepository.getTeachersByCourseId(courseId);
    }

    @Override
    public void deleteTeacherFromCourse(Long teacherId, Long courseId) {
        courseRepository.deleteTeacherFromCourse(teacherId, courseId);
    }
}
