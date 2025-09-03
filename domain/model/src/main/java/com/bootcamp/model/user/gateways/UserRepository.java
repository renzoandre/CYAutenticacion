package com.bootcamp.model.user.gateways;

import com.bootcamp.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> findUserByEmail(String email);
    Mono<User> saveUser(User user);
}
