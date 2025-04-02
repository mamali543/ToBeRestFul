package com.ader.RestApi.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ader.RestApi.controller.LessonController;
import com.ader.RestApi.pojo.Lesson;

@Component
public class LessonResourceProcessor implements RepresentationModelProcessor<EntityModel<Lesson>> {

    @Override
    public EntityModel<Lesson> process(EntityModel<Lesson> model) {
        Lesson lesson = model.getContent();
        
        if (lesson != null) {
            // Add self link
            model.add(linkTo(methodOn(LessonController.class)
                .getLessonsByCourseId(lesson.getCourse().getCourseId()))
                .withRel("course-lessons"));
                
            model.add(linkTo(methodOn(LessonController.class)
                .getLessonsByTeacherId(lesson.getTeacher().getUserId()))
                .withRel("teacher-lessons"));
            
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
        }
        
        return model;
    }
}
