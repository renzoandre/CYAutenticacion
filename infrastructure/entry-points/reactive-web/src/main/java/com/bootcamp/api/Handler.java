package com.bootcamp.api;

import com.bootcamp.api.dto.CreateUserDto;
import com.bootcamp.api.exception.ValidationDtoException;
import com.bootcamp.api.helper.BuildApiResponseHelper;
import com.bootcamp.api.mapper.UserDtoMapper;
import com.bootcamp.api.validator.RequestValidator;
import com.bootcamp.model.user.User;
import com.bootcamp.usecase.user.UserUseCase;
import com.bootcamp.usecase.user.exception.UserExistException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Recibe un objeto CreateUserDto para registrarlo",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente"),
                    @ApiResponse(responseCode = "400", description = "Error al validar datos requeridos",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)))
            }
    )
    public Mono<ServerResponse> saveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateUserDto.class)
                .flatMap(requestValidator::validate)
                .map(userDtoMapper::toModel)
                .flatMap(userUseCase::saveUser)
                .map(userDtoMapper::toResponse)
                .flatMap(BuildApiResponseHelper::buildSuccess)
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

}
