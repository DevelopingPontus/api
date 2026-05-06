package com.example.api.demo.feature.loan.v1;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.common.wrapper.GenericWrapperResponse;
import com.example.api.demo.feature.loan.LoanFacade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/loans")
@Tag(name = "Loans", description = "Operations related to loans")
public class LoanControllerV1 {

    protected final LoanFacade loanFacade;
    protected final String version;

    protected LoanControllerV1(LoanFacade loanFacade) {
        this.loanFacade = loanFacade;
        this.version = "v1";
    }

    @Operation(summary = "Get all loans", description = "Retrieve a list of all loans")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of loans")
    @GetMapping

    public ResponseEntity<GenericWrapperResponse<LoanResponseV1>> getAll() {
        List<LoanResponseV1> entities = loanFacade.getAll();
        GenericWrapperResponse<LoanResponseV1> wrapperResponse = new GenericWrapperResponse<>(entities, version);
        return ResponseEntity.ok(wrapperResponse);
    }

    @Operation(summary = "Get loan by it's ID", description = "Retrieve loan")
    @Parameter(name = "loanId", required = true, description = "ID of the loan to retrieve")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the loan")
    @ApiResponse(responseCode = "404", description = "Loan not found")
    @GetMapping("{loanId}")
    public ResponseEntity<GenericWrapperResponse<LoanResponseV1>> getById(@PathVariable Long loanId) {
        List<LoanResponseV1> singleList = List.of(loanFacade.getById(loanId));
        GenericWrapperResponse<LoanResponseV1> wrapperResponse = new GenericWrapperResponse<>(singleList, version);
        return ResponseEntity.ok(wrapperResponse);
    }

    @Operation(summary = "Delete loan by it's ID", description = "Delete a loan")
    @Parameter(name = "loanId", required = true, description = "ID of the loan to delete")
    @ApiResponse(responseCode = "204", description = "Loan deleted successfully")
    @DeleteMapping("{loanId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long loanId) {
        loanFacade.deleteById(loanId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Make loan with Book Id", description = "Create a new loan")
    @Parameter(name = "bookId", required = true, description = "ID of the book to loan")
    @ApiResponse(responseCode = "201", description = "Entity created successfully")
    @PostMapping
    public ResponseEntity<GenericWrapperResponse<LoanResponseV1>> save(@RequestBody Long bookId) {
        List<LoanResponseV1> savedDto = List.of(loanFacade.save(bookId));
        GenericWrapperResponse<LoanResponseV1> wrapperResponse = new GenericWrapperResponse<>(savedDto, version);
        return ResponseEntity.status(201).body(wrapperResponse);
    }

    @Operation(summary = "Return book by Book Id", description = "Uses Book Id to return loan if book is on loan")
    @Parameter(name = "bookId", required = true, description = "ID of the book to return")
    @ApiResponse(responseCode = "200", description = "Entity updated successfully")
    @PutMapping("{bookId}")
    public ResponseEntity<GenericWrapperResponse<LoanResponseV1>> update(@PathVariable Long bookId) {
        List<LoanResponseV1> updatedDtos = List.of(loanFacade.update(bookId));
        GenericWrapperResponse<LoanResponseV1> wrapperResponse = new GenericWrapperResponse<>(updatedDtos, version);
        return ResponseEntity.ok(wrapperResponse);
    }

}
