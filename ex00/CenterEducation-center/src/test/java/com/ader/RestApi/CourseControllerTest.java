package com.ader.RestApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        // Setup mock responses
        Course course = new Course();
        course.setCourseId(1L);
        course.setName("Test Course");

        when(courseService.getCourseById(1L)).thenReturn(java.util.Optional.of(course));
        when(courseService.createCourse(any(Course.class))).thenReturn(course);
        when(courseService.updateCourse(any(Course.class))).thenReturn(course);
        doNothing().when(courseService).deleteCourse(1L);
    }

    @Test
    public void testGetCourse() throws Exception {
        mockMvc.perform(get("/courses/1"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.courseId").value(1))
               .andExpect(jsonPath("$.name").value("Test Course"));
    }

    @Test
    public void testCreateCourse() throws Exception {
        Course newCourse = new Course();
        newCourse.setName("New Course");

        mockMvc.perform(post("/courses")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(newCourse)))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.courseId").value(1))
               .andExpect(jsonPath("$.name").value("Test Course"));
    }

    @Test
    public void testUpdateCourse() throws Exception {
        Course updatedCourse = new Course();
        updatedCourse.setName("Updated Course");

        mockMvc.perform(put("/courses/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(updatedCourse)))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.courseId").value(1))
               .andExpect(jsonPath("$.name").value("Test Course"));
    }

    @Test
    public void testDeleteCourse() throws Exception {
        mockMvc.perform(delete("/courses/1"))
               .andDo(print())
               .andExpect(status().isNoContent());
    }
}