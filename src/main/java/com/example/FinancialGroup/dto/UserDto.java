package com.example.FinancialGroup.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDto {
    private String name;

    private String email;

    private String password;

    private String city;

    private Long salary;
}
