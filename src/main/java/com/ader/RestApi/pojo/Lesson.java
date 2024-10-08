package com.ader.RestApi.pojo;

import java.time.LocalTime;

//import com.ader.RestApi.pojo.User;

public class Lesson {
    private LocalTime startTime;
    private LocalTime endTime;
    private String dayOfWeek;
    private User teacher;

    public Lesson() {
    }

    public Lesson(LocalTime startTime, LocalTime endTime, String dayOfWeek, User teacher) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.teacher = teacher;
    }

    // Getters and setters
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
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}
