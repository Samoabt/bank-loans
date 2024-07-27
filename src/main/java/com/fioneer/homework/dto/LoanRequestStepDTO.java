package com.fioneer.homework.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequestStepDTO {
    private Long id;
    private String name;
    private int orderNumber;
    private int expectedDurationDays;
    private int actualDurationDays;
    private String status;
}
