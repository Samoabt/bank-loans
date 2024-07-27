package com.fioneer.homework.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcedureStepDTO {
    private Long id;
    private String name;
    private int orderNumber;
    private int durationDays;
}
