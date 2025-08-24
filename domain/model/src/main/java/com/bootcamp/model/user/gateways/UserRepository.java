package com.bootcamp.model.user.gateways;

import com.bootcamp.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Flux<User> findAllUsers();
    Mono<User> findUserById(String id);
    Mono<User> saveUser(User user);
    Mono<User> updateUser(User user);
    Mono<Void> deleteUserById(String id);
}
