package com.wat.recipesapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public void processOAuthPostLogin(String email) {

        if (!repo.existsByEmail(email)) {
            User newUser = new User();
            newUser.setEmail(email);
            repo.save(newUser);
        }

    }
}
