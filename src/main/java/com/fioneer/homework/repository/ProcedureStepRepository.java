package com.fioneer.homework.repository;

import com.fioneer.homework.entity.ProcedureStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedureStepRepository extends JpaRepository<ProcedureStep, Long> {
}
