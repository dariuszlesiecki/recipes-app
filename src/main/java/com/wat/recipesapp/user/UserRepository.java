package com.wat.recipesapp.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    User findById(long id);
    Boolean existsByEmail(String email);
    User findByEmail(String email);
}
