package com.bootcamp.api;

import com.bootcamp.api.config.UserPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    private final UserPaths userPaths;
    private final Handler userHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET(userPaths.getUsers()), userHandler::findAllUsers)
                .andRoute(POST(userPaths.getUser()), userHandler::saveUser)
                .andRoute(PATCH(userPaths.getUser()), userHandler::updateUser);
    }

}
