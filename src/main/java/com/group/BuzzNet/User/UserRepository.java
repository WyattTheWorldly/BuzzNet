package com.group.BuzzNet.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<UserModel> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<UserModel> findByEmail(String username);
}
