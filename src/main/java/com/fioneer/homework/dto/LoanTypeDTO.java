package com.fioneer.homework.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanTypeDTO {

    private Long id;
    private String name;
    private List<ProcedureStepDTO> procedureSteps;

}
