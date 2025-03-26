package com.ader.RestApi.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalTime;

@Entity
@Table(name = "lessons", schema = "spring")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "lessonId", "startTime", "endTime", "dayOfWeek", "teacher", "course" })
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "day_of_week", nullable = false)
    private String dayOfWeek;

    @ManyToOne(fetch = FetchType.LAZY) // Defines many-to-one relationship with User entity, using lazy loading for
                                       // performance
    @JoinColumn(name = "teacher_id", nullable = false) // Specifies the foreign key column in the lessons table
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" }) // Prevents serialization of Hibernate proxy
                                                                     // properties to avoid circular references
    private User teacher; // Reference to the teacher (User) associated with this lesson

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @JsonIgnore
    private Course course;
}