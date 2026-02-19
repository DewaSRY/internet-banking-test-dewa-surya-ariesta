package com.sdewa.hananTest.controllers.transactions;

import com.sdewa.hananTest.dtos.common.TransactionHistoryRecord;
import com.sdewa.hananTest.dtos.filters.CommonFilters;
import com.sdewa.hananTest.dtos.request.CreateTransaction;
import com.sdewa.hananTest.dtos.response.TransactionProfileResponse;
import com.sdewa.hananTest.dtos.response.CommonResponse;
import com.sdewa.hananTest.entity.User;
import com.sdewa.hananTest.services.TransactionCRUDService;
import com.sdewa.hananTest.services.TransactionProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionCRUDService transactionCRUDService;
    private final TransactionProfileService transactionProfileService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/")
    public ResponseEntity<CommonResponse> createTransaction(
            @Valid @RequestBody CreateTransaction createPayload,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(transactionCRUDService.createTransaction(user, createPayload));
    }

    @GetMapping("/profile")
    public ResponseEntity<TransactionProfileResponse> getUserProfile(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(transactionProfileService.getProfileTransaction(user));
    }

    @GetMapping("/history")
    public ResponseEntity<CommonResponse<TransactionHistoryRecord>> getUserTransactionHistory(
            CommonFilters filters,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(transactionCRUDService.getTransactionRecord(user, filters));
    }

}
