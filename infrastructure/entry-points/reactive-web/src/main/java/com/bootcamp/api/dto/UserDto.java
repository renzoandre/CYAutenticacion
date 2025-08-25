package com.bootcamp.api.dto;

import java.time.LocalDate;

public record UserDto (
        String name,
        String lastName1,
        String lastName2,
        LocalDate birthDate,
        String address,
        String phone,
        String email,
        Double baseSalary) {
}