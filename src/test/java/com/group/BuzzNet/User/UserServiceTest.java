package com.group.BuzzNet.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void deleteAll() {
        userService.deleteAll();
    }

    @Test
    void save(){
        UserModel user = UserModel.builder()
                .firstName("Test")
                .lastName("User")
                .password("$trongPassword34")
                .email("fake@gmail.com")
                .birthDate(LocalDate.of(2000, 5, 25))
                .username("username")
                .build();

        userService.save(user);
    }

    @Test
    void delete() {
        long userId = 2;
        userService.delete(userId);
    }

    @Test
    void saveMany(){
        List<UserModel> users = new ArrayList<>();

        for (int i = 0; i < 10; i ++){
            users.add(UserModel.builder()
                    .firstName("Test")
                    .lastName("User")
                    .password("$trongPassword34")
                    .email("legitEmail"+i+"@gmail.com")
                    .birthDate(LocalDate.of(2000, 1, 1))
                    .username("username"+i)
                    .build());
        }
        userService.save(users);
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
        String username = "username1";
        UserDto user = userService.findByUsername(username);
        assertNotNull(user);
        System.out.println(user.toString());
    }

    @Test
    void findByEmail() {
        String email = "legitemail1@gmail.com";
        UserDto user = userService.findByEmail(email);
        assertNotNull(user);
        System.out.println(user.toString());
    }

    @Test
    void update() {
        long userId = 1;
        String firstName = "Ethan";
        String lastName = "Clyde";
        String username = "AllClydeAside";
        String email = "legit@gmail.com";
        String password = "!Password78";
        LocalDate birthDate = LocalDate.of(2002, 5, 25);

        userService.update(userId, null, null, null, null, null, null);
    }

}