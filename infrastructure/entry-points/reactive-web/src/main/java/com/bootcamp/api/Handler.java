package com.bootcamp.api;

import com.bootcamp.api.dto.CreateUserDto;
import com.bootcamp.api.dto.UpdateUserDto;
import com.bootcamp.api.exception.ValidationDtoException;
import com.bootcamp.api.helper.BuildApiResponseHelper;
import com.bootcamp.api.mapper.UserDtoMapper;
import com.bootcamp.api.validator.RequestValidator;
import com.bootcamp.model.user.User;
import com.bootcamp.usecase.user.UserUseCase;
import com.bootcamp.usecase.user.exception.UserExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final UserUseCase userUseCase;
    private final UserDtoMapper userDtoMapper;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> findAllUsers(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(userUseCase.findAllUsers(), User.class);
    }

    public Mono<ServerResponse> saveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateUserDto.class)
                .flatMap(requestValidator::validate)                        // Validación genérica
                .map(userDtoMapper::toModel)                                // Mapear a dominio
                .flatMap(userUseCase::saveUser)                             // Guardar
                .map(userDtoMapper::toResponse)                             // Mapear a Dto
                .flatMap(BuildApiResponseHelper::buildSuccess)              // Respuesta OK
                .onErrorResume(
                        ValidationDtoException.class, ex -> BuildApiResponseHelper.buildError(ex, HttpStatus.BAD_REQUEST)
                )
                .onErrorResume(
                        UserExistException.class, ex -> BuildApiResponseHelper.buildError(ex, HttpStatus.CONFLICT)
                )
                .onErrorResume(
                        RuntimeException.class, ex -> BuildApiResponseHelper.buildError(ex, HttpStatus.INTERNAL_SERVER_ERROR)
                );
    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        serverRequest.bodyToMono(UpdateUserDto.class)
                .flatMap(requestValidator::validate)                        // Validación genérica
                .map(userDtoMapper::toModel)                                // Mapear a dominio
                .flatMap(userUseCase::updateUser)                           // Actualizar
                .map(userDtoMapper::toResponse)                             // Mapear a Dto
                .flatMap(BuildApiResponseHelper::buildSuccess)              // Respuesta OK
                .onErrorResume(
                        ValidationDtoException.class, ex -> BuildApiResponseHelper.buildError(ex, HttpStatus.BAD_REQUEST)
                )
                .onErrorResume(
                        UserExistException.class, ex -> BuildApiResponseHelper.buildError(ex, HttpStatus.CONFLICT)
                )
                .onErrorResume(
                        RuntimeException.class, ex -> BuildApiResponseHelper.buildError(ex, HttpStatus.INTERNAL_SERVER_ERROR)
                );
        return null;
    }

}
