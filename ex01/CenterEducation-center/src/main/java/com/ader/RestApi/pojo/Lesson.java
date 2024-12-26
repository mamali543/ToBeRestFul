package com.ader.RestApi.pojo;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "lessonid", "starttime", "endTime", "dayOfWeek", "teacher" })
public class Lesson {
    private Long lessonid;
    private LocalTime starttime;
    private LocalTime endTime;
    private String dayOfWeek;
    private User teacher;

    public Lesson() {
    }

    public Lesson(Long lessonid, LocalTime starttime, LocalTime endTime, String dayOfWeek, User teacher) {
        this.lessonid = lessonid;
        this.starttime = starttime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.teacher = teacher;
    }

    // Getters and setters
    public Long getlessonid() {
        return lessonid;
    }

    public void setlessonid(Long lessonid) {
        this.lessonid = lessonid;
    }

    public LocalTime getstarttime() {
        return starttime;
    }

    public void setstarttime(LocalTime starttime) {
        this.starttime = starttime;
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
                "lessonid=" + lessonid +
                ", starttime=" + starttime +
                ", endTime=" + endTime +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", teacher=" + teacher.toString() +
                '}';
    }
}
