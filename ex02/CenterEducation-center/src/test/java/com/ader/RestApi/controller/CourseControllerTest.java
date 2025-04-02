// package com.ader.RestApi.controller;

// import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
// import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
// import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.restdocs.RestDocumentationContextProvider;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.web.context.WebApplicationContext;

// import com.ader.RestApi.security.JwtService;

// @SpringBootTest
// @AutoConfigureMockMvc
// public class CourseControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private WebApplicationContext webApplicationContext;

//     @Autowired
//     private UserDetailsService userDetailsService;

//     @Autowired
//     private JwtService jwtService;

//     @Autowired
//     private RestDocumentationContextProvider restDocumentation;

//     @BeforeEach
//     public void setUp() {
//         this.mockMvc = MockMvcBuilders
//                 .webAppContextSetup(webApplicationContext)
//                 .apply(documentationConfiguration(this.restDocumentation))
//                 .build();
//     }

//     @Test
//     public void publishCourse() throws Exception {
//         Long courseId = 1L; // Replace with a valid course ID
        
//         // Load the user from the database
//         UserDetails userDetails = userDetailsService.loadUserByUsername("jdoe"); // Use the login of the user with ADMINISTRATOR role
        
//         // Generate a JWT token for the user
//         String token = jwtService.generateToken(userDetails);

//         mockMvc.perform(put("/courses/{courseId}/publish", courseId)
//                 .header("Authorization", "Bearer " + token))
//             .andExpect(status().isOk())
//             .andDo(document("publish-course", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
//     }
// }