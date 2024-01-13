package com.vehicule.api.services;

import com.vehicule.api.entity.User;
import com.vehicule.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(String email,String nom,String password){
        User user = new User();
        user.setEmail(email);
        user.setNom(nom);
        user.setPassword(password);
        user = userRepository.save(user);
        return user;
    }
}
