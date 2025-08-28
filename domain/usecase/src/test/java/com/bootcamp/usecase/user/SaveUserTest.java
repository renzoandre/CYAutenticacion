package com.bootcamp.usecase.user;

import com.bootcamp.model.user.User;
import com.bootcamp.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SaveUserTest {

    private UserRepository userRepository;
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userUseCase = new UserUseCase(userRepository);
    }

    @Test
    void registryUser_conUsuarioValidoYRolExistente_guardaUsuario() {
        String birthDateText = "2010-02-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        User user = User.builder()
                //.id(UUID.fromString("e6424d99-9bf9-4dea-b554-cffddabe78d0"))
                .name("Jose")
                .lastName1("Dias")
                .lastName2("Tenorio")
                .birthDate(LocalDate.parse(birthDateText, formatter))
                .address("Direccion de felipe")
                .phone("111222333")
                .email("jose@email.com")
                .baseSalary(10000.00)
                .active(false)
                .build();

        when(userRepository.findUserByEmail(user.getEmail()))
                .thenReturn(Mono.empty());

        when(userRepository.saveUser(any()))
                .thenAnswer(invocation -> {
                    User u = invocation.getArgument(0);
                    return Mono.just(u);
                });

        // Act & Assert
        StepVerifier.create(userUseCase.saveUser(user))
                .expectNextMatches(u -> u.getName().equals("Jose"))
                .verifyComplete();

        // Verificar que el usuario se guardó
        /*
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).saveUser(captor.capture());
        User userSaved = captor.getValue();
        assertEquals("Jose", userSaved.getName());
        */
    }

}
