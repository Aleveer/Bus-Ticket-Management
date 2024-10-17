package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Account;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);

    Optional<Account> findByPasswordResetToken(String passwordResetToken);

    // Optional<User> findByRole(String role);
}
