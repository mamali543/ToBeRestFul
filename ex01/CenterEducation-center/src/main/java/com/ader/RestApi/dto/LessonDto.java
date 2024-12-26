package com.ader.RestApi.dto;

public class LessonDto {
    private Long lessonid;
    private String starttime;
    private String endTime;
    private String dayOfWeek;
    private Long teacherId;
    private Long courseId;

    public LessonDto() {
    }

    public LessonDto(Long lessonid, String starttime, String endTime, String dayOfWeek, Long teacherId, Long courseId) {
        this.lessonid = lessonid;
        this.starttime = starttime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.teacherId = teacherId;
        this.courseId = courseId;
    }

    public Long getlessonid() {
        return lessonid;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getstarttime() {
        return starttime;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;

    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setlessonid(Long lessonid) {
        this.lessonid = lessonid;
    }

    public void setstarttime(String starttime) {
        this.starttime = starttime;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "LessonDto [lessonid=" + lessonid + ", starttime=" + starttime + ", endTime=" + endTime + ", dayOfWeek="
                + dayOfWeek + ", teacherId=" + teacherId + ", courseId=" + courseId + "]";
    }
}
