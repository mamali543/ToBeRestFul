package com.ader.RestApi.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ader.RestApi.controller.CourseController;
import com.ader.RestApi.controller.LessonController;
import com.ader.RestApi.controller.UserController;
import com.ader.RestApi.enums.Role;
import com.ader.RestApi.pojo.User;

@Component
public class UserResourceProcessor implements RepresentationModelProcessor<EntityModel<User>> {

    @Override
    public EntityModel<User> process(EntityModel<User> model) {
        User user = model.getContent();
        
        if (user != null) {
            // Clear existing links to prevent unwanted associations
            // model.removeLinks();

            // Add role-specific links
            if (user.getRole() == Role.STUDENT && user.getEnrolledCourses() != null && !user.getEnrolledCourses().isEmpty()) {
                model.add(linkTo(methodOn(CourseController.class)
                    .getCoursesByStudentId(user.getUserId()))
                    .withRel("enrolledCourses"));
            } 
            
            if (user.getRole() == Role.TEACHER) {
                if (user.getTaughtCourses() != null && !user.getTaughtCourses().isEmpty())
                    model.add(linkTo(methodOn(CourseController.class)
                        .getTaughtCourses(user.getUserId()))
                        .withRel("taughtCourses"));

            if (user.getLessons() != null && !user.getLessons().isEmpty())            
                model.add(linkTo(methodOn(LessonController.class)
                    .getLessonsByTeacherId(user.getUserId()))
                    .withRel("taughtLessons"));
            }

            // Add admin-specific links
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = authentication != null && 
                authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ADMINISTRATOR"));
            
            if (isAdmin) {
                model.add(linkTo(UserController.class).slash("users").slash(user.getUserId()).withRel("update"));
                model.add(linkTo(methodOn(UserController.class)
                    .deleteUser(user.getUserId()))
                    .withRel("delete"));
            }
        }
        
        return model;
    }
}