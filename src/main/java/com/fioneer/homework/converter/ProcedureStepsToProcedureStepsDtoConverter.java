package com.fioneer.homework.converter;

import com.fioneer.homework.dto.ProcedureStepDTO;
import com.fioneer.homework.entity.ProcedureStep;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcedureStepsToProcedureStepsDtoConverter {

    public ProcedureStepDTO convert(ProcedureStep procedureStep) {
        return ProcedureStepDTO.builder()
                .id(procedureStep.getId())
                .name(procedureStep.getName())
                .durationDays(procedureStep.getExpectedDurationInDays())
                .orderNumber(procedureStep.getOrderNumber())
                .build();
    }
}