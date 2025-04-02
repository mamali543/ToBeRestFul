package com.ader.RestApi.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {
    private Long lessonId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String dayOfWeek;
    private Long teacherId;
    private Long courseId;
}
