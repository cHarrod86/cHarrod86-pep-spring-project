package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer>{

    /**
     * Custom JPQL query to find a specific account by the passed username
     * @param username
     * @return
     */
    @Query("SELECT a FROM Account a WHERE username = :username")
    Optional<Account> findByUsername(@Param("username")String username);
}


