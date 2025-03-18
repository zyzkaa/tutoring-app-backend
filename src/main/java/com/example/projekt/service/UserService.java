package com.example.projekt.service;

import com.example.projekt.model.RegularUser;
import com.example.projekt.model.UserEntity;
import com.example.projekt.repository.RegularUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final RegularUserRepository regularUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(RegularUserRepository regularUserRepository) {
        this.regularUserRepository = regularUserRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    private void encodePassword(UserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }

    public RegularUser createUser(RegularUser user) {
        encodePassword(user);
        return regularUserRepository.save(user);
    }

    public List<RegularUser> getAllUsers() {
        return regularUserRepository.findAll();
    }
}
