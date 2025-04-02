package com.ader.RestApi.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ader.RestApi.controller.CourseController;
import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.enums.CourseState;

@Component
public class CourseResourceProcessor implements RepresentationModelProcessor<EntityModel<Course>> {

    @Override
    public EntityModel<Course> process(EntityModel<Course> model) {
        // Retrieve the Course object from the EntityModel
        Course course = model.getContent();
        
        // Check if the course object is not null
        if (course != null) {
            // Add a link to the related lessons resource
            model.add(linkTo(methodOn(CourseController.class)
                .getLessonsByCourseId(course.getCourseId()))
                .withRel("lessons"));
                
            // Add a link to the related students resource
            model.add(linkTo(methodOn(CourseController.class)
                .getStudentsByCourseId(course.getCourseId()))
                .withRel("students"));
                
            // Add a link to the related teachers resource
            model.add(linkTo(methodOn(CourseController.class)
                .getTeachersByCourseId(course.getCourseId()))
                .withRel("teachers"));
            
            // Check if the current user is an administrator
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = authentication != null && 
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMINISTRATOR"));
            
            // If the user is an administrator, add administrative links
            if (isAdmin) {
                // Add a link to update the course
                model.add(linkTo(methodOn(CourseController.class)
                    .updateCourse(course.getCourseId(), course))
                    .withRel("update"));
                
                // Add a link to delete the course
                model.add(linkTo(methodOn(CourseController.class)
                    .deleteCourse(course.getCourseId()))
                    .withRel("delete"));
                
                // Add a link to manage course components
                // Add a link to add a lesson to the course
                model.add(linkTo(methodOn(CourseController.class)
                    .addLessonToCourse(course.getCourseId(), null))
                    .withRel("add-lesson"));
                
                // Add a link to add a student to the course
                model.add(linkTo(methodOn(CourseController.class)
                    .addStudentToCourse(course.getCourseId(), null))
                    .withRel("add-student"));
                
                // Add a link to add a teacher to the course
                model.add(linkTo(methodOn(CourseController.class)
                    .addTeacherToCourse(course.getCourseId(), null))
                    .withRel("add-teacher"));
                
                if (course.getCourseState() == CourseState.Draft) {
                    model.add(linkTo(methodOn(CourseController.class)
                        .publishCourse(course.getCourseId()))
                        .withRel("publish"));
                }
            }
        }
        
        // Return the modified EntityModel with added links
        return model;
    }
}
