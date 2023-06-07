package com.group.BuzzNet.User;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDto {

    private long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private LocalDate birthDate;

}
