package com.fioneer.homework.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequestStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int orderNumber;
    private int expectedDurationDays;
    private int actualDurationDays;
    private String status;

    @ManyToOne
    @JoinColumn(name = "loan_request_id")
    private LoanRequest loanRequest;
}

