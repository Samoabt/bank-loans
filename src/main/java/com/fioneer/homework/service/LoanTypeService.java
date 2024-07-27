package com.fioneer.homework.service;

import com.fioneer.homework.converter.LoanTypeToLoanTypeDtoConverter;
import com.fioneer.homework.dto.LoanTypeDTO;
import com.fioneer.homework.dto.ProcedureStepDTO;
import com.fioneer.homework.entity.LoanType;
import com.fioneer.homework.entity.ProcedureStep;
import com.fioneer.homework.exception.NotFoundException;
import com.fioneer.homework.repository.LoanTypeRepository;
import com.fioneer.homework.repository.ProcedureStepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanTypeService {
    private final LoanTypeRepository loanTypeRepository;
    private final ProcedureStepRepository procedureStepRepository;
    private final LoanTypeToLoanTypeDtoConverter loanTypeToLoanTypeDtoConverter;

    @Transactional
    public LoanType createLoanType(LoanTypeDTO loanTypeDTO) {
        LoanType loanType = new LoanType();
        loanType.setName(loanTypeDTO.getName());

        List<ProcedureStep> procedureSteps = loanTypeDTO.getProcedureSteps().stream().map(stepDTO -> {
            ProcedureStep step = new ProcedureStep();
            step.setName(stepDTO.getName());

            step.setOrderNumber(stepDTO.getOrderNumber());
            step.setExpectedDurationInDays(stepDTO.getDurationDays());
            step.setLoanType(loanType);
            return step;
        }).toList();

        loanType.setProcedureSteps(procedureSteps);

        return loanTypeRepository.save(loanType);
    }

    public List<LoanTypeDTO> getAllLoanTypes() {
        return loanTypeRepository.findAll()
                .stream()
                .map(loanTypeToLoanTypeDtoConverter::convert)
                .toList();
    }

    public LoanTypeDTO getLoanTypeByName(String name) {
        return loanTypeRepository.findByName(name)
                .map(loanTypeToLoanTypeDtoConverter::convert)
                .orElseThrow(() -> new NotFoundException("Loan type " + name + " not found"));
    }

    public LoanTypeDTO getLoanTypeById(Long id) {
        final var loanType = loanTypeRepository.findById(id);
        return loanType.map(loanTypeToLoanTypeDtoConverter::convert)
                .orElseThrow(() -> new NotFoundException("Loan type not found by id: " + id));
    }

    @Transactional
    public LoanType updateLoanType(Long id, LoanTypeDTO loanTypeDTO) {
        LoanType loanType = loanTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("LoanType not found"));

        loanType.setName(loanTypeDTO.getName());

        List<Long> incomingStepIds = loanTypeDTO.getProcedureSteps().stream()
                .map(ProcedureStepDTO::getId)
                .filter(Objects::nonNull)
                .toList();

        loanType.getProcedureSteps().removeIf(step -> !incomingStepIds.contains(step.getId()));

        List<ProcedureStep> updatedSteps = loanTypeDTO.getProcedureSteps().stream().map(stepDTO -> {
            ProcedureStep step = stepDTO.getId() != null
                    ? procedureStepRepository.findById(stepDTO.getId())
                    .orElseThrow(() -> new NotFoundException("Procedure step not found"))
                    : new ProcedureStep();
            step.setName(stepDTO.getName());
            step.setOrderNumber(stepDTO.getOrderNumber());
            step.setExpectedDurationInDays(stepDTO.getDurationDays());
            step.setLoanType(loanType);
            return step;
        }).collect(Collectors.toList());

        loanType.setProcedureSteps(updatedSteps);

        return loanTypeRepository.save(loanType);
    }

    @Transactional
    public void deleteLoanType(Long id) {
        loanTypeRepository.deleteById(id);
    }

}
