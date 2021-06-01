package com.wat.recipesapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository repo;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public boolean existsByEmail(String email){
        return repo.existsByEmail(email);
    }

    public User findByEmail(String email){
        return repo.findByEmail(email);
    }


    public void register(UserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        repo.save(user);
    }

    public void processOAuthPostLogin(String email) {
        if (!repo.existsByEmail(email)) {
            User newUser = new User();
            newUser.setEmail(email);
            repo.save(newUser);
        }
        else {
            System.out.println(email+"-user already exists");
        }
    }




}
