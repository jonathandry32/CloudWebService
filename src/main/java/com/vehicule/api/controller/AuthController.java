package com.vehicule.api.controller;

import com.vehicule.api.auth.JwtUtil;
import com.vehicule.api.entity.User;
import com.vehicule.api.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

   @PostMapping("/auth/login")
    public String login(String mail, String password)  {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mail, password));
            User user = userRepository.findByEmail(mail);
            String token = jwtUtil.createToken(user);
            return token;

        }catch (Exception e){
            return e.getMessage();
        }
    }

}
