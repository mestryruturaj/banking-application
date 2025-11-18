package io.enscryptingbytes.banking_application.service;

import io.enscryptingbytes.banking_application.dto.BranchDto;
import io.enscryptingbytes.banking_application.entity.Branch;
import io.enscryptingbytes.banking_application.exception.BankBranchException;
import io.enscryptingbytes.banking_application.repository.BranchRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

import static io.enscryptingbytes.banking_application.message.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;

    public BranchDto createBranch(BranchDto branchDto) throws BankBranchException {
        if (branchRepository.existsById(branchDto.getIfsc())) {
            throw new BankBranchException(BRANCH_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }

        Branch branch = Branch.builder()
                .ifsc(branchDto.getIfsc())
                .name(branchDto.getName())
                .address(branchDto.getAddress())
                .pinCode(branchDto.getPinCode())
                .bankName(branchDto.getBankName())
                .build();
        branchRepository.save(branch);
        return branchDto;
    }

    public BranchDto getBranch(String ifsc) throws BankBranchException {
        Branch existingBranch = branchRepository.findById(ifsc).orElseThrow(() -> new BankBranchException(BRANCH_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND));
        return convertBranchToBranchDto(existingBranch);
    }

    public BranchDto updateBranch(String ifsc, BranchDto branchDto) throws BankBranchException {
        Branch existingBranch = branchRepository.findById(ifsc).orElseThrow(() -> new BankBranchException(BRANCH_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND));

        if (branchDto.getAddress() != null) {
            existingBranch.setAddress(branchDto.getAddress());
        }
        if (branchDto.getName() != null) {
            existingBranch.setName(branchDto.getName());
        }
        if (branchDto.getPinCode() != null) {
            existingBranch.setPinCode(branchDto.getPinCode());
        }
        if (branchDto.getBankName() != null) {
            existingBranch.setBankName(branchDto.getBankName());
        }

        branchRepository.save(existingBranch);
        return convertBranchToBranchDto(existingBranch);
    }

    public BranchDto deleteBranch(String ifsc) throws BankBranchException {
        Branch existingBranch = branchRepository.findById(ifsc).orElseThrow(() -> new BankBranchException(BRANCH_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND));
        branchRepository.delete(existingBranch);
        return convertBranchToBranchDto(existingBranch);
    }

    public Branch findBranchByIfsc(String ifsc) throws BankBranchException {
        if (StringUtils.isBlank(ifsc) || !Pattern.matches("[A-Z]{4}0[0-9A-Z]{6}", ifsc)) {
            throw new BankBranchException(INVALID_INPUT, HttpStatus.BAD_REQUEST);
        }
        return branchRepository.findById(ifsc).orElseThrow(() -> new BankBranchException(BRANCH_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND));
    }

    public static BranchDto convertBranchToBranchDto(Branch branch) {
        return BranchDto.builder()
                .ifsc(branch.getIfsc())
                .name(branch.getName())
                .address(branch.getAddress())
                .pinCode(branch.getPinCode())
                .bankName(branch.getBankName())
                .build();
    }

    private static Branch convertBranchDtoToBranch(BranchDto branchDto) {
        return Branch.builder()
                .ifsc(branchDto.getIfsc())
                .name(branchDto.getName())
                .address(branchDto.getAddress())
                .pinCode(branchDto.getPinCode())
                .bankName(branchDto.getBankName())
                .build();
    }
}
