package com.bootcamp.usecase.user;

import com.bootcamp.model.user.User;
import com.bootcamp.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log
@RequiredArgsConstructor
public class UserUseCase {
    private final UserRepository userRepository;

    public Flux<User> findAllUsers() {
        log.info("Use Case findAllUsers");
        return userRepository.findAllUsers();
    }

    public Mono<User> saveUser(User user) {
        log.info("Use Case saveUser");
        return userRepository.saveUser(user);
    }

    public Mono<User> updateUser(User user) {
        log.info("Use Case updateUser");
        return userRepository.updateUser(user);
    }
}
