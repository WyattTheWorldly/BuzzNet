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
    public void deleteAll() {
        userService.deleteAll();
    }

    @Test
    public void save(){
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
    public void delete() {
        long userId = 2;
        userService.delete(userId);
    }

    @Test
    public void saveMany(){
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
    public void findAll() {
        List<UserDto> users = userService.findAll();
        users.forEach(System.out::println);

    }

    @Test
    public void findByUserId() {
        long userId = 1;
        UserDto user = userService.findById(userId);
        assertNotNull(user);
        System.out.println(user.toString());
    }

    @Test
    public void findByUsername() {
        String username = "username1";
        UserDto user = userService.findByUsername(username);
        assertNotNull(user);
        System.out.println(user.toString());
    }

    @Test
    public void findByEmail() {
        String email = "legitemail1@gmail.com";
        UserDto user = userService.findByEmail(email);
        assertNotNull(user);
        System.out.println(user.toString());
    }

    @Test
    public void findByPartialName(){
        String partialName = "test";
        List<UserDto> users = userService.findByPartialName(partialName);
        users.forEach(System.out::println);
    }

    @Test
    public void findByPartialUsername(){
        String username = "wyatt";
        List<UserDto> users = userService.findByPartialUsername(username);
        users.forEach(System.out::println);
    }

    @Test
    public void update() {
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