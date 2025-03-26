package com.ader.RestApi.service;

import com.ader.RestApi.exception.BadRequestException;
import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.pojo.User;
import com.ader.RestApi.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        // Remove user from all courses where they are a student
        for (Course course : user.getEnrolledCourses()) {
            course.getStudents().remove(user);
        }

        // Remove user from all courses where they are a teacher
        for (Course course : user.getTaughtCourses()) {
            course.getTeachers().remove(user);
        }

        // No need for explicit lesson deletion
        // @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE)
        // will handle this automatically

        userRepository.delete(user);
    }
}
