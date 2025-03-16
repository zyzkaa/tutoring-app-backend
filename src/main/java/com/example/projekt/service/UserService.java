package com.example.projekt.service;

import com.example.projekt.model.RegularUser;
import com.example.projekt.repository.RegularUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RegularUserRepository regularUserRepository;

    public RegularUser createUser(RegularUser user) {
        return regularUserRepository.save(user);
    }
}
