package com.example.api.demo.feature.loan;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findLastByBookIdOptional(Long bookId);

}
