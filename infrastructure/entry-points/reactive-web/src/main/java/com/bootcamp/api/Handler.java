package com.bootcamp.api;

import com.bootcamp.api.dto.CreateUserDto;
import com.bootcamp.api.dto.UpdateUserDto;
import com.bootcamp.api.dto.UserDto;
import com.bootcamp.api.mapper.UserDtoMapper;
import com.bootcamp.model.user.User;
import com.bootcamp.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final UserUseCase userUseCase;
    private final UserDtoMapper userDtoMapper;

    public Mono<ServerResponse> findAllUsers(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(userUseCase.findAllUsers(), User.class);
    }

    public Mono<ServerResponse> saveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateUserDto.class)
                .map(userDtoMapper::toModel)
                .flatMap(userUseCase::saveUser)
                .flatMap(savedUser -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedUser));
    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UpdateUserDto.class)
                .map(userDtoMapper::toModel)
                .flatMap(userUseCase::updateUser)
                .flatMap(updatedUser -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedUser));
    }

}
