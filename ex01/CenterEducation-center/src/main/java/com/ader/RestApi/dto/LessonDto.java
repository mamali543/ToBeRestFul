package com.ader.RestApi.dto;

import java.time.LocalTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
