package io.enscryptingbytes.banking_application.controller;

import io.enscryptingbytes.banking_application.dto.BranchDto;
import io.enscryptingbytes.banking_application.dto.response.GenericResponse;
import io.enscryptingbytes.banking_application.exception.BankBranchException;
import io.enscryptingbytes.banking_application.service.BranchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static io.enscryptingbytes.banking_application.message.ResponseMessage.*;

@RestController
@RequestMapping(value = "/branch", produces = "application/json")
@RequiredArgsConstructor
@Validated
@Slf4j
public class BranchController {
    private final BranchService branchService;

    @PostMapping(value = "", consumes = "application/json")
    public GenericResponse<BranchDto> createBranch(@Valid @RequestBody BranchDto branchDto) throws BankBranchException {
        BranchDto createdBranch = branchService.createBranch(branchDto);
        return GenericResponse.<BranchDto>builder()
                .message(BRANCH_CREATION_PASSED)
                .httpStatus(HttpStatus.CREATED)
                .response(createdBranch)
                .build();
    }

    @GetMapping(value = "/{ifsc}")
    public GenericResponse<BranchDto> getBranch(@Pattern(regexp = "[A-Z]{4}0[0-9A-Z]{6}", message = "IFSC must be an 11-character code starting with four letters, followed by a zero, and ending with six letters or numbers (e.g., HDFC0001980).") @PathVariable String ifsc) throws BankBranchException {
        BranchDto branch = branchService.getBranch(ifsc);
        return GenericResponse.<BranchDto>builder()
                .message(BRANCH_FOUND)
                .httpStatus(HttpStatus.OK)
                .response(branch)
                .build();
    }


    @PatchMapping(value = "/{ifsc}", consumes = "application/json")
    public GenericResponse<BranchDto> updateBranch(@Pattern(regexp = "[A-Z]{4}0[0-9A-Z]{6}", message = "IFSC must be an 11-character code starting with four letters, followed by a zero, and ending with six letters or numbers (e.g., HDFC0001980).") @PathVariable String ifsc, @Valid @RequestBody BranchDto branchDto) throws BankBranchException {
        BranchDto branch = branchService.updateBranch(ifsc, branchDto);
        return GenericResponse.<BranchDto>builder()
                .message(BRANCH_UPDATED)
                .httpStatus(HttpStatus.OK)
                .response(branch)
                .build();

    }

    @DeleteMapping(value = "/{ifsc}")
    public GenericResponse<BranchDto> deleteBranch(@Pattern(regexp = "[A-Z]{4}0[0-9A-Z]{6}", message = "IFSC must be an 11-character code starting with four letters, followed by a zero, and ending with six letters or numbers (e.g., HDFC0001980).") @PathVariable String ifsc) throws BankBranchException {
        BranchDto branch = branchService.deleteBranch(ifsc);
        return GenericResponse.<BranchDto>builder()
                .message(BRANCH_DELETED)
                .httpStatus(HttpStatus.OK)
                .response(branch)
                .build();

    }
}
