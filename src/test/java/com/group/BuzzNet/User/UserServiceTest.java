package com.group.BuzzNet.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void save(){
        User user = User.builder()
                .firstName("Test")
                .lastName("User2")
                .password("$trongPassword34")
                .email("fake@gmail.com")
                .birthDate(LocalDate.of(2001, 5, 25))
                .username("testUser2")
                .build();

        userService.save(user);
    }

    @Test
    void findAll() {
        List<UserDto> users = userService.findAll();
        users.forEach(System.out::println);
    }

    @Test
    void findByUserId() {
        long userId = 1;
        UserDto user = userService.findById(userId);
        assertNotNull(user);
        System.out.println(user.toString());
    }

    @Test
    void findByUsername() {
        String username = "testUser";
        UserDto user = userService.findByUsername(username);
        assertNotNull(user);
        System.out.println(user.toString());
    }

    @Test
    void findByEmail() {
        String email = "fake@gmail.com";
        UserDto user = userService.findByEmail(email);
        assertNotNull(user);
        System.out.println(user.toString());
    }

    @Test
    void delete() {
        long userId = 1;
        userService.delete(1);
    }

    @Test
    void deleteAll() {
        userService.deleteAll();
    }

    @Test
    void update() {
        long userId = 2;
        String firstName = "Wyatt";
        String lastName = "Stohr";
        String username = "QuietWyatt";
        String email = "realemail@gmail.com";
        String password = "$trongPassword43";
        LocalDate birthDate = LocalDate.of(2000, 5, 25);

        userService.update(userId, firstName, lastName, username, email, password, birthDate);
    }

}