package com.bootcamp.api;

import com.bootcamp.api.config.UserPathsConfig;
import com.bootcamp.api.dto.CreateUserDto;
import com.bootcamp.api.helper.ApiResponseHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    private final UserPathsConfig userPathsConfig;
    private final Handler userHandler;

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/usuario",
                    beanClass = Handler.class,
                    beanMethod = "saveUser",
                    operation = @Operation(
                            operationId = "saveUser",
                            summary = "Registrar nuevo usuario",
                            description = "Recibe un objeto CreateUserDto para guardarlo",
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "Datos del usuario a registrar",
                                    content = @Content(schema = @Schema(implementation = CreateUserDto.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = ApiResponseHelper.class))),
                                    @ApiResponse(responseCode = "400", description = "Error al validar datos requeridos",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = ApiResponseHelper.class))),
                                    @ApiResponse(responseCode = "500", description = "Error interno",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = ApiResponseHelper.class)))
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction() {

        return route(POST(userPathsConfig.getUser()), userHandler::saveUser)
                .andRoute(GET(userPathsConfig.getUsers()), userHandler::findAllUsers)
                .andRoute(PATCH(userPathsConfig.getUser()), userHandler::updateUser);
    }

}
