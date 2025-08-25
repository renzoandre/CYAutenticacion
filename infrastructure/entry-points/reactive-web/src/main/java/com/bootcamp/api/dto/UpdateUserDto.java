package com.bootcamp.api.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UpdateUserDto (
        @NotNull(message = "Debe ingresar el identificado")
        @NotBlank(message = "Debe ingresar el identificado")
        String id,
        @NotNull(message = "Debe ingresar el nombre")
        @NotBlank(message = "Debe ingresar el nombre")
        String name,
        @NotNull(message = "Debe ingresar el primer apellido")
        @NotBlank(message = "Debe ingresar el primer apellido")
        String lastName1,
        @NotNull(message = "Debe ingresar el segundo apellido")
        @NotBlank(message = "Debe ingresar el segundo apellido")
        String lastName2,
        LocalDate birthDate,
        String address,
        String phone,
        @NotNull(message = "Debe ingresar el correo electrónico")
        @NotBlank(message = "Debe ingresar el correo electrónico")
        @Email(message = "El correo electrónico no es válido")
        String email,
        @NotNull(message = "Debe ingresar el salario base")
        @NotBlank(message = "Debe ingresar el salario base")
        @Min(value = 0, message = "El salario base ser mayor a 0")
        @Max(value = 15000000, message = "El salario base debe ser menor a 15000000")
        Double baseSalary
) {}
