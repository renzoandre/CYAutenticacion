package com.bootcamp.api.dto;

import java.time.LocalDate;

public record UserResponseDto (
        String id,
        String name,
        String lastName1,
        String lastName2,
        LocalDate birthDate,
        String address,
        String phone,
        String email,
        Double baseSalary,
        boolean active
) {}