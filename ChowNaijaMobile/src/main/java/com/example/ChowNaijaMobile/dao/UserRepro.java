package com.example.ChowNaijaMobile.dao;

import com.example.ChowNaijaMobile.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface UserRepro extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findFirstByEmail(@Param("email") String email);
    User findByIsAccountFlagedTrueAndFlagDateBefore(LocalDateTime date);
}
