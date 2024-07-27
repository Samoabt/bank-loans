package com.fioneer.homework.converter;

import com.fioneer.homework.dto.LoanRequestDTO;
import com.fioneer.homework.entity.LoanRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class LoanRequestToLoanRequestDtoConverter {

    private final LoanRequestStepToLoanRequestStepDto loanRequestStepToLoanRequestStepDto;


    public LoanRequestDTO convert(LoanRequest loanRequest) {

        var loanRequestDto = loanRequest.getLoanRequestSteps()
                .stream()
                .map(loanRequestStepToLoanRequestStepDto::convert)
                .toList();

        return LoanRequestDTO.builder()
                .id(loanRequest.getId())
                .firstName(loanRequest.getFirstName())
                .lastName(loanRequest.getLastName())
                .loanAmount(loanRequest.getLoanAmount())
                .status(loanRequest.getStatus())
                .loanTypeId(loanRequest.getLoanType().getId())
                .loanRequestSteps(loanRequestDto)
                .build();

    }

}
