package com.group.BuzzNet.User;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void save(User user) {

        //check for existing user with id
        User existingUser = userRepository.findById(user.getUserId())
                .orElse(null);
        if (existingUser != null) {
            throw new IllegalStateException("User with id: " + user.getUserId() + " already exists.");
        }

        //sanitize email
        user.setEmail(user.getEmail().toLowerCase());

        //unique email check
        existingUser = userRepository.findByEmail(user.getEmail())
                .orElse(null);
        if (existingUser != null) {
            throw new IllegalStateException("User with email: " + user.getEmail() + " already exists.");
        }

        //unique username check
        existingUser = userRepository.findByUsername(user.getUsername())
                .orElse(null);
        if (existingUser != null) {
            throw new IllegalStateException("User with username: " + user.getUsername() + " already exists.");
        }

        //valid email check
        user.validEmail();

        //valid password check
        user.validPassword();

        //valid birthdate check
        user.validBirthDate();

        userRepository.save(user);
    }

    public void save(List<User> users) {
        users.forEach(this::save);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::adapter)
                .collect(Collectors.toList());
    }

    public UserDto findById(long userId) {
        return userRepository.findById(userId)
                .map(this::adapter)
                .orElseThrow(() -> new IllegalStateException("No users found with id: " + userId));
    }

    public UserDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::adapter)
                .orElseThrow(() -> new IllegalStateException("No users found with username: " + username));
    }

    public UserDto findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::adapter)
                .orElseThrow(() -> new IllegalStateException("No users found with email: " + email));
    }

    public void delete(long userId) {
        userRepository.deleteById(userId);
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }

    @Transactional
    public void update(long userId, String firstName, String lastName, String username, String email, String password, LocalDate birthDate) {

        //check user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("No users found with id: " + userId));

        //update firstname check
        if (firstName != null && firstName.length() > 0 && !user.getFirstName().equals(firstName)) {
            user.setFirstName(firstName);
        }

        //update lastname check
        if (lastName != null && lastName.length() > 0 && !user.getLastName().equals(lastName)) {
            user.setLastName(lastName);
        }

        //update username check
        if (username != null && username.length() > 0 && !user.getUsername().equals(username)) {
            User takenUsername = userRepository.findByUsername(username)
                    .orElse(null);
            if (takenUsername != null) {
                throw new IllegalStateException(username + " is already in use");
            }
            user.setUsername(username);
        }

        //update email check
        if (email != null && email.length() > 0 && !user.getEmail().equals(email) && user.validEmail()) {
            User takenEmail = userRepository.findByEmail(email)
                    .orElse(null);
            if (takenEmail != null) {
                throw new IllegalStateException(email + " is already in use");
            }
            user.setEmail(email);
        }

        //update password check
        if (password != null && password.length() > 0 && !user.getPassword().equals(password) && user.validPassword()) {
            user.setPassword(password);
        }

        //update birthdate check
        if (birthDate != null && !user.getBirthDate().equals(birthDate) && user.validBirthDate() && !user.getBirthDate().isEqual(birthDate)) {
            user.setBirthDate(birthDate);
        }

    }

    private UserDto adapter(User user) {
        return new UserDto(user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.getBirthDate());
    }

}
