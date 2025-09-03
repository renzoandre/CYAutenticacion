package com.bootcamp.api;

import com.bootcamp.api.config.UserPathsConfig;
import com.bootcamp.api.mapper.UserDtoMapper;
import com.bootcamp.api.validator.RequestValidator;
import com.bootcamp.model.user.User;
import com.bootcamp.usecase.user.UserUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@ContextConfiguration(classes = {RouterRest.class, Handler.class, UserDtoMapper.class})
//@EnableConfigurationProperties(UserPathsConfig.class)
//@WebFluxTest

@WebFluxTest(controllers = RouterRest.class)
@ContextConfiguration(classes = {
        RouterRest.class,
        Handler.class,
        RouterRestTest.TestConfig.class,
        RequestValidator.class
})
@EnableConfigurationProperties(UserPathsConfig.class)
@TestPropertySource(properties = {
        "routes.paths.users=/api/v1/users",
        "routes.paths.user=/api/v1/user"
})
class RouterRestTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        UserDtoMapper userDtoMapper() {
            return Mappers.getMapper(UserDtoMapper.class);
        }
    }

    @Autowired
    private WebTestClient webTestClient;
    @MockitoBean
    private UserUseCase userUseCase;
    @Autowired
    private UserPathsConfig userPathsConfig;
    @Autowired
    private UserDtoMapper userDtoMapper;

    private UserDtoMapper mapper = Mappers.getMapper(UserDtoMapper.class);

    private final String usersUrl = "/api/v1/users";
    private final String userUrl = "/api/v1/user";
    String birthDateText = "2010-02-01";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final User user1 = User.builder()
            .id(UUID.fromString("6491deb6-c9f0-4a30-b942-367e29b23b86"))
            .name("Renzo")
            .lastName1("Condo")
            .lastName2("Miranda")
            .birthDate(LocalDate.parse(birthDateText, formatter))
            .address("Direccion de felipe")
            .phone("111222333")
            .email("renzo@email.com")
            .baseSalary(10000.00)
            .active(false)
            .build();

    private final User user2 = User.builder()
            //.id(UUID.fromString("e6424d99-9bf9-4dea-b554-cffddabe78d0"))
            .name("Juan")
            .lastName1("Dias")
            .lastName2("Tenorio")
            .birthDate(LocalDate.parse(birthDateText, formatter))
            .address("Direccion de felipe")
            .phone("111222333")
            .email("Juan@email.com")
            .baseSalary(10000.00)
            .active(false)
            .build();

    @Test
    void shouldLoadTaskPathProperties() {
        assertEquals("/api/v1/users", userPathsConfig.getUsers());
        assertEquals("/api/v1/user", userPathsConfig.getUser());
    }

    /*
    @Test
    void shouldGetAllUsers() {
        when(userUseCase.findAllUsers()).thenReturn(Flux.just(user1, user2));
        webTestClient.get()
                .uri(usersUrl)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(2)
                .value(users -> {
                    Assertions.assertThat(users).isNotEmpty();
                    Assertions.assertThat(users.get(0).getId()).isEqualTo(UUID.fromString("6491deb6-c9f0-4a30-b942-367e29b23b86"));
                });
    }
    */

    @Test
    void shouldPostSaveuser() {
        when(userUseCase.saveUser(any())).thenReturn(Mono.just(user2));
        webTestClient.post()
                .uri(userUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user2)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(saved -> Assertions.assertThat(saved.getName()).isEqualTo(user2.getName()));
    }

}
