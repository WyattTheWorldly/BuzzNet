package com.group.BuzzNet.Post;

import com.group.BuzzNet.User.UserModel;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@Builder

@Entity(name = "Post")
@Table(name = "post")
public class PostModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id",
            nullable = false)
    private long id;

    @Column(name = "date_posted",
            nullable = false)
    private LocalDate date;

    @Column(name = "post_caption",
            length = 500)
    private String caption;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, targetEntity = UserModel.class)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "user_id",
            nullable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "post_fk_user")
    )
    private UserModel user;
}
