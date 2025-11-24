package com.kalechoweb.backend.service;

import com.kalechoweb.backend.model.User;
import com.kalechoweb.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.lang.NonNull;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(@NonNull User user) {
        // In a real app, password should be hashed
        return userRepository.save(user);
    }

    public Optional<User> login(@NonNull String email, @NonNull String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }
}
