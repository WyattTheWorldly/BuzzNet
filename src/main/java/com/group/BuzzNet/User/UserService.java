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

    public void save(UserModel user) {

        //check for null values
        if (!user.noNullFields()){
            throw new IllegalStateException("One or more fields were left as null. Save aborted");
        }

        //check firstName size
        if (user.getFirstName().length() > UserConstants.NAME_LENGTH){
            throw new IllegalStateException("First name is too large, expecting "+UserConstants.NAME_LENGTH+" characters or less.");
        }

        //check lastName size
        if (user.getLastName().length() > UserConstants.NAME_LENGTH){
            throw new IllegalStateException("Last name is too large, expecting "+UserConstants.NAME_LENGTH+" characters or less.");
        }

        //check username size
        if (user.getUsername().length() > UserConstants.USERNAME_LENGTH){
            throw new IllegalStateException("Username is too large, expecting "+UserConstants.USERNAME_LENGTH+" characters or less");
        }

        //check email size
        if (user.getEmail().length() > UserConstants.EMAIL_LENGTH){
            throw new IllegalStateException("Email is too large, expecting "+UserConstants.EMAIL_LENGTH+" characters or less.");
        }

        //check password size
        if (user.getPassword().length() > UserConstants.PASSWORD_LENGTH){
            throw new IllegalStateException("Password is too large, expecting "+UserConstants.PASSWORD_LENGTH+" characters or less.");
        }

        //valid email check
        user.validEmail();

        //valid password check
        user.validPassword();

        //valid birthdate check
        user.validBirthDate();


        //check for existing user with id
        //avoids an update on a user
        if (userRepository.findById(user.getId()).isPresent()){
            throw new IllegalStateException("User with id: " + user.getId() + " already exists.");
        }

        //sanitize email
        user.setEmail(user.getEmail().toLowerCase());

        //unique email check
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalStateException("User with email: " + user.getEmail() + " already exists.");
        }

        //unique username check
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalStateException("User with username: " + user.getUsername() + " already exists.");
        }

        userRepository.save(user);
    }

    public void save(List<UserModel> users) {
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

    public List<UserDto> findByPartialName(String partialName){
        return userRepository.findByPartialName(partialName)
                .stream()
                .map(this::adapter)
                .collect(Collectors.toList());
    }

    public List<UserDto> findByPartialUsername(String username){
        return userRepository.findByPartialUserame(username)
                .stream()
                .map(this::adapter)
                .collect(Collectors.toList());
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

        //check that user exists
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("No users found with id: " + userId));

        //update firstname logic
        if (firstName != null){
            if (firstName.length() == 0){
                throw new IllegalStateException("First name must be at least one character long.");
            }

            if (user.getFirstName().equals(firstName)){
                throw new IllegalStateException("The name provided for an update should be different than current name.");
            }

            if (firstName.length() > UserConstants.NAME_LENGTH){
                throw new IllegalStateException("First name must be "+UserConstants.NAME_LENGTH+" characters or shorter.");
            }
            user.setFirstName(firstName);
        }

        //update lastname logic
        if (lastName != null){
            if (lastName.length() == 0){
                throw new IllegalStateException("Last name must be at least one character long.");
            }

            if (user.getLastName().equals(lastName)){
                throw new IllegalStateException("The name provided for an update should be different than current name.");
            }

            if (lastName.length() > UserConstants.NAME_LENGTH){
                throw new IllegalStateException("Last name must be "+UserConstants.NAME_LENGTH+" characters or shorter.");
            }
            user.setLastName(lastName);
        }

        //update username logic
        if (username != null){
            if (username.length() == 0){
                throw new IllegalStateException("Username must be at least one character long.");
            }

            if (user.getUsername().equals(username)){
                throw new IllegalStateException("Username provided for an update should be different than current username.");
            }

            if (username.length() > UserConstants.USERNAME_LENGTH){
                throw new IllegalStateException("Username must be "+UserConstants.USERNAME_LENGTH+" characters or shorter.");
            }

            //unique username check
            if(userRepository.findByUsername(username).isPresent()){
                throw new IllegalStateException("User with username: " + username + " already exists.");
            }
            user.setUsername(username);
        }

        //update email logic
        if (email != null){
            //email pattern check
            if (!UserConstants.EMAIL_PATTERN.matcher(email).matches()){
                throw new IllegalStateException("Incorrect email format.");
            }

            email = email.toLowerCase();
            if (user.getEmail().equals(email)){
                throw new IllegalStateException("Email provided for an update should be different than current email.");
            }

            if (email.length() > UserConstants.EMAIL_LENGTH){
                throw new IllegalStateException("Email must be "+UserConstants.EMAIL_LENGTH+" characters or shorter.");
            }

            //unique email check
            if(userRepository.findByEmail(email).isPresent()){
                throw new IllegalStateException("User with email: " + email + " already exists.");
            }
            user.setEmail(email);
        }

        //update password logic
        if (password != null){
            if (!UserConstants.PASSWORD_PATTERN.matcher(password).matches()){
                throw new IllegalStateException("""
                    
                    Password must be:
                    At least 8 characters long.
                    Contain at least one uppercase letter.
                    Contain at least one lower case letter.
                    Contain at least one digit.
                    Contain at least one special character.""");
            }

            if (user.getPassword().equals(password)){
                throw new IllegalStateException("Password provided for an update should be different than current password.");
            }

            if (password.length() > UserConstants.PASSWORD_LENGTH){
                throw new IllegalStateException("Password must be "+UserConstants.PASSWORD_LENGTH+" characters or shorter.");
            }
            user.setPassword(password);
        }

        //update birthdate check
        if (birthDate != null){
            if (user.getBirthDate().equals(birthDate)){
                throw new IllegalStateException("Birthdate provided for an update should be different than current birthdate");
            }

            LocalDate limitDate = LocalDate.now().minusYears(16);
            if (birthDate.isAfter(limitDate)){
                throw new IllegalStateException("User must be above the age of 16.");
            }
            user.setBirthDate(birthDate);
        }

    }

    private UserDto adapter(UserModel user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.getBirthDate());
    }

}
