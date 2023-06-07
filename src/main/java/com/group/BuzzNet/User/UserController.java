package com.group.BuzzNet.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/save")
    public void save(@RequestBody User user){
        userService.save(user);
    }

    @PostMapping("/saveAll")
    public void saveAll(@RequestBody List<User> users){
        userService.save(users);
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable(name = "userId") long userId){
        return ResponseEntity.ok(userService.findById(userId));
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> findByEmail(@PathVariable(name = "email") String email){
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> findByUsername(@PathVariable(name = "username") String username){
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @PutMapping("/update/{id}")
    public void update(
            @PathVariable(name = "id") long userId,
            @RequestParam(required = false, name = "firstName") String firstName,
            @RequestParam(required = false, name = "lastName") String lastName,
            @RequestParam(required = false, name = "username") String username,
            @RequestParam(required = false, name = "email") String email,
            @RequestParam(required = false, name = "password") String password,
            @RequestParam(required = false, name = "birthDate") LocalDate birthDate
            ){
        userService.update(userId, firstName, lastName, username, email, password, birthDate);
    }
}
