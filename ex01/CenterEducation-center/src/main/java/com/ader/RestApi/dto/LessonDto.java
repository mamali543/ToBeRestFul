package com.ader.RestApi.dto;

public class LessonDto {
    private Long lessonId;
    private String startTime;
    private String endTime;
    private String dayOfWeek;
    private Long teacherId;
    private Long courseId;

    public LessonDto() {
    }

    public LessonDto(Long lessonId, String startTime, String endTime, String dayOfWeek, Long teacherId, Long courseId) {
        this.lessonId = lessonId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.teacherId = teacherId;
        this.courseId = courseId;
    }

    public Long getLessonId() {
        return lessonId;
    }
    
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getEndTime() {
        return endTime;
    }
    public String getStartTime() {
        return startTime;
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

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
        return "LessonDto [lessonId=" + lessonId + ", startTime=" + startTime + ", endTime=" + endTime + ", dayOfWeek="
                + dayOfWeek + ", teacherId=" + teacherId + ", courseId=" + courseId + "]";
    }
}
