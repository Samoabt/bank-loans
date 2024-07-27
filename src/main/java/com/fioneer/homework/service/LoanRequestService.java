package com.fioneer.homework.service;

import com.fioneer.homework.converter.LoanRequestToLoanRequestDtoConverter;
import com.fioneer.homework.dto.LoanRequestDTO;
import com.fioneer.homework.dto.LoanRequestStepDTO;
import com.fioneer.homework.entity.LoanRequest;
import com.fioneer.homework.entity.LoanRequestStep;
import com.fioneer.homework.entity.LoanType;
import com.fioneer.homework.exception.NotFoundException;
import com.fioneer.homework.exception.StepException;
import com.fioneer.homework.repository.LoanRequestRepository;
import com.fioneer.homework.repository.LoanRequestStepRepository;
import com.fioneer.homework.repository.LoanTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LoanRequestService {
    private final LoanRequestRepository loanRequestRepository;
    private final LoanRequestStepRepository loanRequestStepRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final LoanRequestToLoanRequestDtoConverter loanRequestToLoanRequestDtoConverter;

    @Transactional
    public LoanRequest createLoanRequest(LoanRequestDTO loanRequestDTO) {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setFirstName(loanRequestDTO.getFirstName());
        loanRequest.setLastName(loanRequestDTO.getLastName());
        loanRequest.setLoanAmount(loanRequestDTO.getLoanAmount());
        loanRequest.setStatus("processing");

        LoanType loanType = loanTypeRepository.findById(loanRequestDTO.getLoanTypeId())
                .orElseThrow(() -> new RuntimeException("LoanType not found"));

        loanRequest.setLoanType(loanType);

        List<LoanRequestStep> loanRequestSteps = loanType.getProcedureSteps().stream().map(procedureStep -> {
            LoanRequestStep step = new LoanRequestStep();
            step.setName(procedureStep.getName());
            step.setOrderNumber(procedureStep.getOrderNumber());
            step.setExpectedDurationDays(procedureStep.getExpectedDurationInDays());
            step.setStatus("pending");
            step.setLoanRequest(loanRequest);
            return step;
        }).toList();

        loanRequest.setLoanRequestSteps(loanRequestSteps);

        return loanRequestRepository.save(loanRequest);
    }

    public List<LoanRequest> getAllLoanRequests() {
        return loanRequestRepository.findAll();
    }

    public LoanRequestDTO getLoanRequestById(Long id) {
        final var loanRequest = loanRequestRepository.findById(id);
        return loanRequest.map(loanRequestToLoanRequestDtoConverter::convert)
                .orElseThrow(() -> new NotFoundException("Loan request not found by id:" + id));
    }

    public List<LoanRequest> getLoanRequestsByStatus(String status) {
        return loanRequestRepository.findByStatus(status);
    }

    @Transactional
    public LoanRequest updateLoanRequestStep(Long loanRequestId, Long stepId, LoanRequestStepDTO stepDTO) {
        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new NotFoundException("Loan request not found by id: " + loanRequestId));

        LoanRequestStep step = loanRequestStepRepository.findById(stepId)
                .orElseThrow(() -> new NotFoundException("Loan request step not found by id: " + stepId));

        if (!step.getStatus().equals("pending")) {
            throw new StepException("Step already completed");
        }

        if (step.getOrderNumber() > 1) {
            LoanRequestStep previousStep = loanRequestStepRepository.findByLoanRequestAndOrderNumber(loanRequest, step.getOrderNumber() - 1);
            if (!previousStep.getStatus().equals("successful")) {
                throw new StepException("Previous steps must be successful");
            }
        }

        step.setActualDurationDays(stepDTO.getActualDurationDays());
        step.setStatus(stepDTO.getStatus());

        loanRequestStepRepository.save(step);

        if (stepDTO.getStatus().equals("failed")) {
            loanRequest.setStatus("rejected");
        } else if (loanRequest.getLoanRequestSteps().stream().allMatch(s -> s.getStatus().equals("successful"))) {
            loanRequest.setStatus("approved");
        }

        return loanRequestRepository.save(loanRequest);
    }

    @Transactional
    public void deleteLoanRequest(Long id) {
        loanRequestRepository.deleteById(id);
    }
}
