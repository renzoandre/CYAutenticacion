package com.bootcamp.usecase.user;

import com.bootcamp.model.user.User;
import com.bootcamp.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {
    private final UserRepository userRepository;

    public Flux<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public Mono<User> saveUser(User user) {
        return userRepository.saveUser(user);
    }

    public Mono<User> updateUser(User user) {
        return userRepository.updateUser(user);
    }
}
