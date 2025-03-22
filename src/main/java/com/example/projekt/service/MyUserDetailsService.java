package com.example.projekt.service;

import com.example.projekt.model.Teacher;
import com.example.projekt.model.User;
import com.example.projekt.repository.TeacherRepository;
import com.example.projekt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;

    private UserDetails createUserDetails(User user, String role) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(role)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return createUserDetails(user.get(), "USER");
        }

        Optional<Teacher> teacher = teacherRepository.findByUsername(username);
        if (teacher.isPresent()) {
            return createUserDetails(teacher.get(), "TEACHER");
        }

        System.out.println("user not found");
        throw new UsernameNotFoundException(username);
    }
}
