package com.ader.RestApi.pojo;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "lessonId", "startTime", "endTime", "dayOfWeek", "teacher" })
public class Lesson {
    private Long lessonId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String dayOfWeek;
    private User teacher;

    public Lesson() {
    }

    public Lesson(Long lessonId, LocalTime startTime, LocalTime endTime, String dayOfWeek, User teacher) {
        this.lessonId = lessonId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.teacher = teacher;
    }

    // Getters and setters
    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public User getTeacher() {
        return teacher;
    }

        public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId=" + lessonId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", teacher=" + teacher.toString() +
                '}';
    }
}
