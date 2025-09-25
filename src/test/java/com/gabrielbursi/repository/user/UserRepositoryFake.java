package com.gabrielbursi.repository.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.gabrielbursi.domain.user.User;

public class UserRepositoryFake implements UserRepository {

    private final Map<String, User> users = new HashMap<>();

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public void update(User user) {
        users.put(user.getId().getValue(), user);
    }

    @Override
    public void save(User user) {
        users.put(user.getId().getValue(), user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().getValue().equals(email))
                .findFirst();
    }

    public void clear() {
        users.clear();
    }
}
