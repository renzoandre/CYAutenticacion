package com.bootcamp.usecase.user;

import com.bootcamp.model.user.User;
import com.bootcamp.model.user.gateways.UserRepository;
import com.bootcamp.usecase.user.exception.UserExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

@Log
@RequiredArgsConstructor
public class UserUseCase {
    private final UserRepository userRepository;

    public Mono<User> saveUser(User user) {
        log.info("Use Case saveUser");
        return userRepository.findUserByEmail(user.getEmail())
                .flatMap(existingUser ->
                        Mono.<User>error(new UserExistException("El email ya está registrado"))
                )
                .switchIfEmpty(Mono.defer(() -> userRepository.saveUser(user)));
    }

}
