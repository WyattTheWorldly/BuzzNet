package com.group.BuzzNet.User;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Entity(name = "User")
@Table(name = "user",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_email_constraint", columnNames = "email"),
                @UniqueConstraint(name = "unique_username_constraint", columnNames = "username")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "username",
            length = 11,
            nullable = false)
    private String username;

    @Column(name = "password",
            length = 21,
            nullable = false)
    private String password;

    @Column(name = "first_name",
            length = 21,
            nullable = false)
    private String firstName;

    @Column(name = "last_name",
            length = 21,
            nullable = false)
    private String lastName;

    @Column(name = "email",
            length = 21,
            nullable = false)
    private String email;

    @Column(name = "birth_date",
            nullable = false)
    private LocalDate birthDate;

    public boolean validEmail(){
        final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        if (!EMAIL_PATTERN.matcher(this.email).matches()){
            throw new IllegalStateException("Incorrect email format.");
        }
        return true;
    }

    public boolean validPassword(){
        final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        if (!PASSWORD_PATTERN.matcher(this.password).matches()){
            throw new IllegalStateException("""
                    Password must be:
                    At least 8 characters long.
                    Contain at least one uppercase letter.
                    Contain at least one lower case letter.
                    Contain at least one digit.
                    Contain at least one special character.""");
        }

        return !PASSWORD_PATTERN.matcher(this.password).matches();
    }

    public boolean validBirthDate(){
        LocalDate limitDate = LocalDate.now().minusYears(16);
        if (this.birthDate.isAfter(limitDate)){
            throw new IllegalStateException("User must be above the age of 16");
        }
        return this.birthDate.isBefore(limitDate);
    }

    public boolean checkForNullValues(){

        if (this.getFirstName() == null) return false;
        if (this.getLastName() == null) return false;
        if (this.getUsername() == null) return false;
        if (this.getPassword() == null) return false;
        if (this.getEmail() == null) return false;
        return this.getBirthDate() != null;
    }

}
