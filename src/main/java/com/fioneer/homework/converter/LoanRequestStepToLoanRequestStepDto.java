package com.fioneer.homework.converter;


import com.fioneer.homework.dto.LoanRequestStepDTO;
import com.fioneer.homework.entity.LoanRequestStep;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoanRequestStepToLoanRequestStepDto {

    public LoanRequestStepDTO convert(LoanRequestStep loanRequestStep) {
        return LoanRequestStepDTO.builder()
                .id(loanRequestStep.getId())
                .name(loanRequestStep.getName())
                .orderNumber(loanRequestStep.getOrderNumber())
                .expectedDurationDays(loanRequestStep.getExpectedDurationDays())
                .actualDurationDays(loanRequestStep.getActualDurationDays())
                .status(loanRequestStep.getStatus())
                .build();
    }
}
