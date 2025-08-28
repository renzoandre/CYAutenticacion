package com.bootcamp.api;

import com.bootcamp.api.config.UserPathsConfig;
import lombok.RequiredArgsConstructor;
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
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET(userPathsConfig.getUsers()), userHandler::findAllUsers)
                .andRoute(POST(userPathsConfig.getUser()), userHandler::saveUser)
                .andRoute(PATCH(userPathsConfig.getUser()), userHandler::updateUser);
    }

}
