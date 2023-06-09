package com.group.BuzzNet.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<UserModel> findByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<UserModel> findByEmail(@Param(("email")) String email);

    @Query("SELECT u FROM User u WHERE u.firstName LIKE %:search% OR u.lastName LIKE %:search%")
    List<UserModel> findByPartialName(@Param("search") String search);

    @Query("SELECT u FROM User u WHERE u.username LIKE %:search%")
    List<UserModel> findByPartialUserame(@Param("search") String search);

    //TODO make more search queries test and implement. Do one for a limit on searches.
}
