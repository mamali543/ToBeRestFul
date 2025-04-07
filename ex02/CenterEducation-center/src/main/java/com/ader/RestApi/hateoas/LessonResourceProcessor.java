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
import com.ader.RestApi.controller.LessonController;
import com.ader.RestApi.controller.UserController;
import com.ader.RestApi.pojo.Lesson;

@Component
public class LessonResourceProcessor implements RepresentationModelProcessor<EntityModel<Lesson>> {

    @Override
    public EntityModel<Lesson> process(EntityModel<Lesson> model) {
        Lesson lesson = model.getContent();
        
        if (lesson != null) {
            // Check if user is an administrator
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = authentication != null && 
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMINISTRATOR"));
            
            if (isAdmin) {
                // Add update link
                model.add(linkTo(methodOn(LessonController.class)
                    .updateLesson(lesson.getLessonId(), null))
                    .withRel("update"));
                
                // Add delete link
                model.add(linkTo(methodOn(LessonController.class)
                    .deleteLesson(lesson.getLessonId()))
                    .withRel("delete"));
            }
            
            model.add(linkTo(UserController.class).slash(lesson.getTeacher().getUserId()).withRel("teacher"));
            model.add(linkTo(CourseController.class).slash(lesson.getCourse().getCourseId()).withRel("course"));
        }
        
        return model;
    }
}
