package com.ader.RestApi.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ader.RestApi.dto.LessonDto;
import com.ader.RestApi.exception.BadRequestException;
import com.ader.RestApi.pojo.Lesson;
import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.pojo.User;
import com.ader.RestApi.repositories.LessonRepository;
import com.ader.RestApi.repositories.CourseRepository;
import com.ader.RestApi.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public Lesson createLesson(LessonDto lessonDto) {
        // Validate times
        if (lessonDto.getStartTime().isAfter(lessonDto.getEndTime())) {
            throw new BadRequestException("Start time must be before end time");
        }

        User teacher = userRepository.findById(lessonDto.getTeacherId())
                .orElseThrow(() -> new BadRequestException("Teacher not found"));

        Course course = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(() -> new BadRequestException("Course not found"));

        // If teacher isn't associated with course, add them
        if (!course.getTeachers().contains(teacher)) {
            course.getTeachers().add(teacher);
            teacher.getTaughtCourses().add(course);
        }

        // Check time conflicts
        checkTimeConflicts(course, null, lessonDto.getDayOfWeek(), lessonDto.getStartTime(), lessonDto.getEndTime());

        Lesson lesson = new Lesson();
        lesson.setStartTime(lessonDto.getStartTime());
        lesson.setEndTime(lessonDto.getEndTime());
        lesson.setDayOfWeek(lessonDto.getDayOfWeek());
        lesson.setTeacher(teacher);
        lesson.setCourse(course);

        // Add lesson to course's lessons collection
        course.getLessons().add(lesson);

        return lessonRepository.save(lesson);
    }

    @Override
    @Transactional
    public Lesson updateLesson(LessonDto lessonDto, Long lessonId) {
        // Validate times
        if (lessonDto.getStartTime().isAfter(lessonDto.getEndTime())) {
            throw new BadRequestException("Start time must be before end time");
        }

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new BadRequestException("Lesson not found"));

        User teacher = userRepository.findById(lessonDto.getTeacherId())
                .orElseThrow(() -> new BadRequestException("Teacher not found"));

        Course newCourse = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(() -> new BadRequestException("Course not found"));

        // Log current and new course IDs
        System.out.println("Current Course ID: " + lesson.getCourse().getCourseId());
        System.out.println("New Course ID: " + newCourse.getCourseId());

        // If teacher isn't associated with the new course, add them
        if (!newCourse.getTeachers().contains(teacher)) {
            newCourse.getTeachers().add(teacher);
            teacher.getTaughtCourses().add(newCourse);
        }

        // Check time conflicts
        checkTimeConflicts(newCourse, lessonId, lessonDto.getDayOfWeek(), lessonDto.getStartTime(),
                lessonDto.getEndTime());

        // Change course if necessary
        if (!lesson.getCourse().getCourseId().equals(newCourse.getCourseId())) {
            System.out.println("Changing course for lesson: " + lesson.getLessonId());

            // Store the old course reference
            Course oldCourse = lesson.getCourse();

            // Set the new course
            lesson.setCourse(newCourse); // Set the new course

            // Add to the new course's lessons
            newCourse.getLessons().add(lesson); // Add to the new course's lessons

            // Remove from the old course's lessons
            // oldCourse.getLessons().remove(lesson); // Remove from the old course's lessons
        }

        // Update lesson details
        lesson.setStartTime(lessonDto.getStartTime());
        lesson.setEndTime(lessonDto.getEndTime());
        lesson.setDayOfWeek(lessonDto.getDayOfWeek());
        lesson.setTeacher(teacher);

        return lessonRepository.save(lesson);
    }

    @Override
    public void deleteLesson(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Lesson not found"));

        // Remove lesson from course's collection
        lesson.getCourse().getLessons().remove(lesson);

        lessonRepository.deleteById(id);
    }

    @Override
    public List<Lesson> getLessonsByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new BadRequestException("Course not found with id: " + courseId);
        }
        return lessonRepository.findByCourse_CourseId(courseId);
    }

    @Override
    public List<Lesson> getLessonsByTeacherId(Long teacherId) {
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new BadRequestException("Teacher not found"));
        return teacher.getLessons();
    }

    // This method checks for time conflicts in a course
    private void checkTimeConflicts(Course course, Long lessonId, String dayOfWeek, java.time.LocalTime startTime,
            java.time.LocalTime endTime) {
        // Filter the lessons in the course
        boolean hasConflict = course.getLessons().stream()
                // Exclude the current lesson if it's being updated
                .filter(existingLesson -> lessonId == null ||
                        !existingLesson.getLessonId().equals(lessonId))
                // Check if there's a lesson with the same day of week and overlapping time
                .anyMatch(existingLesson -> existingLesson.getDayOfWeek().equals(dayOfWeek) &&
                        (startTime.isBefore(existingLesson.getEndTime())
                                && endTime.isAfter(existingLesson.getStartTime())));

        // If there's a conflict, throw a BadRequestException
        if (hasConflict) {
            throw new BadRequestException("Time conflict with existing lesson in the course");
        }
    }
}
