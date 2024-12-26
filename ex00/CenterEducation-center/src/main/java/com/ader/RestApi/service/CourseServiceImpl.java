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
        // Lesson lesson1 = lessonRepository.findById(lessonDto.getLessonId()).orElseThrow(() -> new BadRequestException("lesson not found"));
        System.out.println("lessonDto: " + lessonDto+ " >>>\n");
        Lesson lesson = lessonRepository.saveDto(lessonDto);
        if (course.getLessons() == null) {
            course.setLessons(new ArrayList<>());
        }
        course.getLessons().add(lesson);
        return lesson;
    }

    @Override
    public List<Lesson> getLessonsByCourseId(Long course_id) {
        return lessonRepository.findByCourseId(course_id);
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
    public void deleteLessonByCourse(Long lesson_id, Long course_id) {
        Course course = courseRepository.findById(course_id).orElseThrow(() -> new RuntimeException("Lesson not found"));
        lessonRepository.delete(lesson_id);
    }

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

    @Override
    public List<User> getStudentsByCourseId(Long course_id) {
        return courseRepository.getStudentsByCourseId(course_id);
    }

    @Override
    public void deleteStudentFromCourse(Long student_id, Long course_id) {
        courseRepository.deleteStudentFromCourse(student_id, course_id);
    }

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

    @Override   
    public List<User> getTeachersByCourseId(Long course_id) {
        return courseRepository.getTeachersByCourseId(course_id);
    }

    @Override
    public void deleteTeacherFromCourse(Long teacher_id, Long course_id) {
        courseRepository.deleteTeacherFromCourse(teacher_id, course_id);
    }
}
