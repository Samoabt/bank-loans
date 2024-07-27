package com.fioneer.homework.converter;

import com.fioneer.homework.dto.LoanTypeDTO;
import com.fioneer.homework.entity.LoanType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class LoanTypeToLoanTypeDtoConverter {
    private final ProcedureStepsToProcedureStepsDtoConverter procedureStepsToProcedureStepsDtoConverter;

    public LoanTypeDTO convert(LoanType loanType) {
        var procedureStepsDtos = loanType.getProcedureSteps()
                .stream()
                .map(procedureStepsToProcedureStepsDtoConverter::convert)
                .toList();

        return LoanTypeDTO.builder()
                .id(loanType.getId())
                .name(loanType.getName())
                .procedureSteps(procedureStepsDtos)
                .build();
    }
}