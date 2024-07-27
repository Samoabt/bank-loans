package com.fioneer.homework.controller;

import com.fioneer.homework.dto.LoanTypeDTO;
import com.fioneer.homework.entity.LoanType;
import com.fioneer.homework.service.LoanTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-types")
public class LoanTypeController {
    @Autowired
    private LoanTypeService loanTypeService;

    @PostMapping
    public ResponseEntity<LoanType> createLoanType(@RequestBody LoanTypeDTO loanTypeDTO) {
        LoanType createdLoanType = loanTypeService.createLoanType(loanTypeDTO);
        return ResponseEntity.ok(createdLoanType);
    }

    @GetMapping
    public ResponseEntity<List<LoanTypeDTO>> getAllLoanTypes() {
        return ResponseEntity.ok(loanTypeService.getAllLoanTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanTypeDTO> getLoanTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(loanTypeService.getLoanTypeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanType> updateLoanType(@PathVariable Long id, @RequestBody LoanTypeDTO loanTypeDTO) {
        LoanType updatedLoanType = loanTypeService.updateLoanType(id, loanTypeDTO);
        return ResponseEntity.ok(updatedLoanType);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<LoanTypeDTO> getLoanTypeByName(@PathVariable String name) {
        return ResponseEntity.ok(loanTypeService.getLoanTypeByName(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLoanType(@PathVariable Long id) {
        loanTypeService.deleteLoanType(id);
        return ResponseEntity.ok("Loan type deleteded!");
    }
}

