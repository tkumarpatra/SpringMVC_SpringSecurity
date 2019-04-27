package com.fsd.cts.service;

import com.fsd.cts.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
