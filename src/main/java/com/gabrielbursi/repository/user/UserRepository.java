package com.gabrielbursi.repository.user;

import com.gabrielbursi.domain.user.User;

public interface UserRepository {
    public User findById(String id);
}
