package com.example.projekt.service;

import com.example.projekt.model.Teacher;
import com.example.projekt.model.User;
import com.example.projekt.repository.TeacherRepository;
import com.example.projekt.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

@RequiredArgsConstructor
@Component
public class MyUserService extends OidcUserService {
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final HttpSession session;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        System.out.println("print 1");
        User user = userRepository.findByEmail(oidcUser.getEmail()).orElse(null);
        System.out.println("print 2");
        if (user != null) {
            System.out.println("print 3");
            user.setAttributes(oidcUser.getAttributes());
            return user;
        }

        String role = (String) session.getAttribute("register_role");

        if(role == null) {
            throw new RuntimeException("role not specified");
        }

        if(role.equals("user")) {
            var newUser = new User(oidcUser);
            userRepository.save(newUser);
            return loadUser(userRequest);
        }

        if(role.equals("teacher")) {
            var newTeacher = new Teacher(oidcUser);
            teacherRepository.save(newTeacher);
            return loadUser(userRequest);
        }

        return null;
    }
}
