package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Account;
import com.myproject.busticket.models.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Optional<Feedback> findByAccount(Account account);

    void deleteByAccount(Account account);

    // More methods if needed
}
