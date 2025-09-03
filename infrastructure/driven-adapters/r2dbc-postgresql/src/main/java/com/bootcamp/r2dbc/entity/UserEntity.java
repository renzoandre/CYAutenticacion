package com.bootcamp.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {
    @Id
    @Column("id")
    private UUID id;
    private String name;
    @Column("last_name_1")
    private String lastName1;
    @Column("last_name_2")
    private String lastName2;
    @Column("birth_date")
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String email;
    @Column("base_salary")
    private Double baseSalary;
    private boolean active;
}
