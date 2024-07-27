package com.fioneer.homework.controller;

import com.fioneer.homework.dto.LoanRequestDTO;
import com.fioneer.homework.dto.LoanRequestStepDTO;
import com.fioneer.homework.entity.LoanRequest;
import com.fioneer.homework.service.LoanRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-requests")
public class LoanRequestController {
    @Autowired
    private LoanRequestService loanRequestService;

    @PostMapping
    public ResponseEntity<LoanRequest> createLoanRequest(@RequestBody LoanRequestDTO loanRequestDTO) {
        LoanRequest createdLoanRequest = loanRequestService.createLoanRequest(loanRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLoanRequest);
    }

    @PutMapping("/{loanRequestId}/steps/{stepId}")
    public ResponseEntity<LoanRequest> updateLoanRequestStep(@PathVariable Long loanRequestId, @PathVariable Long stepId, @RequestBody LoanRequestStepDTO stepDTO) {
        LoanRequest updatedLoanRequest = loanRequestService.updateLoanRequestStep(loanRequestId, stepId, stepDTO);
        return ResponseEntity.ok(updatedLoanRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanRequestDTO> getLoanRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(loanRequestService.getLoanRequestById(id));
    }

    @GetMapping
    public ResponseEntity<List<LoanRequest>> getAllLoanRequests() {
        return ResponseEntity.ok(loanRequestService.getAllLoanRequests());
    }

    @GetMapping("/search-by-status")
    public ResponseEntity<List<LoanRequest>> getLoanRequestsByStatus(@RequestParam String status) {
        List<LoanRequest> loanRequests = loanRequestService.getLoanRequestsByStatus(status);
        return ResponseEntity.ok(loanRequests);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanRequest(@PathVariable Long id) {
        loanRequestService.deleteLoanRequest(id);
        return ResponseEntity.noContent().build();
    }
}

