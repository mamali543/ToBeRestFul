package com.ader.RestApi.pojo;

import java.time.LocalDate;
import java.util.List;

public class Course {
    private Long courseId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String name;
    private List<User> teachers; // List of teachers (User)
    private List<User> students; // List of students (User)
    private String description;
    private List<Lesson> lessons; // List of lessons

    public Course() {

    }

    public Course(Long courseId, LocalDate startDate, LocalDate endDate, String name, List<User> teachers, List<User> students, String description, List<Lesson> lessons) {
        this.courseId = courseId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.teachers = teachers;
        this.students = students;
        this.description = description;
        this.lessons = lessons;
    }

    // Getters and setters
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getTeachers() {
        return teachers;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }   

    public void setTeachers(List<User> teachers) {
        this.teachers = teachers;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public String toString() {
        return "Course{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", name='" + name + '\'' +
                ", teachers=" + teachers +
                ", students=" + students +
                ", description='" + description + '\'' +
                ", lessons=" + lessons +
                '}';
    }
}
