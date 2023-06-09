package com.group.BuzzNet.User;

import com.group.BuzzNet.Post.PostModel;
import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {
        "posts"
})

@Entity(name = "User")
@Table(name = "user",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_email_constraint", columnNames = "email"),
                @UniqueConstraint(name = "unique_username_constraint", columnNames = "username")
        }
)
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "username",
            length = UserConstants.USERNAME_LENGTH,
            nullable = false)
    private String username;

    @Column(name = "password",
            length = UserConstants.PASSWORD_LENGTH,
            nullable = false)
    private String password;

    @Column(name = "first_name",
            length = UserConstants.NAME_LENGTH,
            nullable = false)
    private String firstName;

    @Column(name = "last_name",
            length = UserConstants.NAME_LENGTH,
            nullable = false)
    private String lastName;

    @Column(name = "email",
            length = UserConstants.EMAIL_LENGTH,
            nullable = false)
    private String email;

    @Column(name = "birth_date",
            nullable = false)
    private LocalDate birthDate;

    //Relationship to PostModel.user
    @OneToMany(targetEntity = PostModel.class, cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<PostModel> posts;

    public boolean validEmail(){
        if (!UserConstants.EMAIL_PATTERN.matcher(this.email).matches()){
            throw new IllegalStateException("Incorrect email format.");
        }
        return !UserConstants.EMAIL_PATTERN.matcher(this.email).matches();
    }

    public boolean validPassword(){
        if (!UserConstants.PASSWORD_PATTERN.matcher(this.password).matches()){
            throw new IllegalStateException("Password must be at least 8 characters long, contain at least one uppercase letter, " +
                    "contain at least one lower case letter, contain at least one digit, contain at least one special character.");
        }

        return !UserConstants.PASSWORD_PATTERN.matcher(this.password).matches();
    }

    public boolean validBirthDate(){
        LocalDate limitDate = LocalDate.now().minusYears(16);
        if (this.birthDate.isAfter(limitDate)){
            throw new IllegalStateException("User must be above the age of 16.");
        }
        return this.birthDate.isBefore(limitDate);
    }

    public boolean noNullFields(){

        return (this.getBirthDate() != null) && (this.getEmail() != null) &&
                (this.getPassword() != null) && (this.getUsername() != null) &&
                (this.getLastName() != null) && (this.getFirstName() != null);
    }

}
