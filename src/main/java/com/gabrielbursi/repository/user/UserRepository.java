package com.gabrielbursi.repository.user;

import java.util.Optional;

import com.gabrielbursi.domain.user.User;

public interface UserRepository {
    public Optional<User> findById(String id);
    public void update(User user);
    public void save(User user);
    public Optional<User> findByEmail(String email);
}
