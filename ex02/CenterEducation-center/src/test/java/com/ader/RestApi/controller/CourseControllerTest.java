package com.ader.RestApi.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ader.RestApi.enums.CourseState;
import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest //Loads the full application context for integration testing.
@AutoConfigureMockMvc //Configures MockMvc for testing HTTP requests without a real server
@ExtendWith(RestDocumentationExtension.class) //Enables Spring REST Docs to generate snippets.
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CourseService courseService;

    @BeforeEach //Initializes MockMvc with REST Docs and Spring Security configurations
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMINISTRATOR"}) //Simulates an admin user to bypass security checks.
    public void publishCourse() throws Exception {
        Long courseId = 1L;
        Course course = new Course();
        course.setCourseId(courseId);
        course.setName("Test Course");
        course.setDescription("A test course");
        course.setCourseState(CourseState.Published);

        when(courseService.publishCourse(courseId)).thenReturn(course);

        this.mockMvc.perform(post("/courses/{courseId}/publish", courseId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //to generate documentation snippets.
                .andDo(document("publish-course",
                        pathParameters(
                                parameterWithName("courseId").description("The ID of the course to publish")
                        ),
                        responseFields(
                                fieldWithPath("courseId").description("The ID of the course"),
                                fieldWithPath("name").description("The name of the course"),
                                fieldWithPath("description").description("The description of the course"),
                                fieldWithPath("courseState").description("The state of the course (e.g., Published)"),
                                fieldWithPath("startDate").description("The start date of the course").optional(),
                                fieldWithPath("endDate").description("The end date of the course").optional(),
                                fieldWithPath("_links.update.href").description("Link to update the course (admin only)").optional(),
                                fieldWithPath("_links.delete.href").description("Link to delete the course (admin only)").optional(),
                                fieldWithPath("_links.add-lesson.href").description("Link to add a lesson to the course (admin only)").optional(),
                                fieldWithPath("_links.add-student.href").description("Link to add a student to the course (admin only)").optional(),
                                fieldWithPath("_links.add-student.templated").description("Indicates if the add-student link is templated").optional(),
                                fieldWithPath("_links.add-teacher.href").description("Link to add a teacher to the course (admin only)").optional(),
                                fieldWithPath("_links.add-teacher.templated").description("Indicates if the add-teacher link is templated").optional()
                        )
                ));
    }
}