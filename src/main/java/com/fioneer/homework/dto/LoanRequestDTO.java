package com.fioneer.homework.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequestDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private double loanAmount;
    private String status;
    private Long loanTypeId;
    private List<LoanRequestStepDTO> loanRequestSteps;
}
