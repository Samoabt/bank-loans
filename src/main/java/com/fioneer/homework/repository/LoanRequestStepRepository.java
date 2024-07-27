package com.fioneer.homework.repository;


import com.fioneer.homework.entity.LoanRequest;
import com.fioneer.homework.entity.LoanRequestStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRequestStepRepository extends JpaRepository<LoanRequestStep, Long> {
    LoanRequestStep findByLoanRequestAndOrderNumber(LoanRequest loanRequest, int orderNumber);
}
