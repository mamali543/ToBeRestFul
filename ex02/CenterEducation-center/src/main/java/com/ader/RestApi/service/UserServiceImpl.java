package com.ader.RestApi.service;

import com.ader.RestApi.exception.BadRequestException;
import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.pojo.User;
import com.ader.RestApi.repositories.LessonRepository;
import com.ader.RestApi.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LessonRepository lessonRepository;

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

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