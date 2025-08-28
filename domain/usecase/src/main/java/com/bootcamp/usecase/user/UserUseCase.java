package com.bootcamp.usecase.user;

import com.bootcamp.model.user.User;
import com.bootcamp.model.user.gateways.UserRepository;
import com.bootcamp.usecase.user.exception.UserExistException;
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
        return userRepository.findUserByEmail(user.getEmail())
                .flatMap(existingUser ->
                        Mono.<User>error(new UserExistException("El email ya está registrado"))
                )
                .switchIfEmpty(Mono.defer(() -> userRepository.saveUser(user)));
    }

    public Mono<User> updateUser(User user) {
        log.info("Use Case updateUser");
        return userRepository.findUserByEmail(user.getEmail())
                .flatMap(existingUser ->
                        Mono.<User>error(new UserExistException("El email ya está registrado"))
                )
                .switchIfEmpty(Mono.defer(() -> userRepository.updateUser(user)));
    }
}
