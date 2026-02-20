package com.sdewa.hananTest.services.impl;

import com.sdewa.hananTest.dtos.common.PaginateMetaData;
import com.sdewa.hananTest.dtos.common.TransactionHistoryRecord;
import com.sdewa.hananTest.dtos.common.UserProfileDto;
import com.sdewa.hananTest.dtos.filters.CommonFilters;
import com.sdewa.hananTest.dtos.request.CreateTransaction;
import com.sdewa.hananTest.dtos.response.CommonResponse;
import com.sdewa.hananTest.entity.Transaction;
import com.sdewa.hananTest.entity.User;
import com.sdewa.hananTest.enums.TransactionEnum;
import com.sdewa.hananTest.exception.CustomBadRequestException;
import com.sdewa.hananTest.repository.TransactionRepository;
import com.sdewa.hananTest.repository.UserRepository;
import com.sdewa.hananTest.services.TransactionCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionCRUDImpl implements TransactionCRUDService {

        private final TransactionRepository transactionRepository;
        private final UserRepository userRepository;

        @Override
        @Transactional(readOnly = true)
        public CommonResponse<TransactionHistoryRecord> getTransactionRecord(User user, CommonFilters commonFilters) {
                Pageable pageable = PageRequest.of(
                                commonFilters.getPage() - 1,
                                commonFilters.getLimit());

                Page<Transaction> transactionPage = transactionRepository.findByUserIdOrderByCreatedAtDesc(
                                user.getId(),
                                                pageable);

                List<TransactionHistoryRecord> records = transactionPage.getContent().stream()
                                .map(transaction -> {
                                        User userFrom = userRepository.findById(transaction.getAccountFromId())
                                                        .orElse(null);

                                        User userTo = null;
                                        if (transaction.getAccountToId() != null) {
                                                userTo = userRepository.findById(transaction.getAccountToId())
                                                                .orElse(null);
                                        }

                                        TransactionEnum transactionType = transaction.getTransactionType();
                                        if (transactionType.equals(TransactionEnum.TRANSFER)
                                                        && userTo.getEmail().equals(user.getEmail())) {
                                                transactionType = TransactionEnum.RECEIVE;
                                        }

                                        return TransactionHistoryRecord.builder()
                                                        .transactionEnum(transactionType)
                                                        .amount(transaction.getAmount())
                                                        .userFrom(userFrom != null ? UserProfileDto.builder()
                                                                        .username(userFrom.getName())
                                                                        .email(userFrom.getEmail())
                                                                        .build() : null)
                                                        .userTo(userTo != null ? UserProfileDto.builder()
                                                                        .username(userTo.getName())
                                                                        .email(userTo.getEmail())
                                                                        .build() : null)
                                                        .createdAt(transaction.getCreatedAt())
                                                        .build();
                                })
                                .collect(Collectors.toList());

                PaginateMetaData metaData = PaginateMetaData.builder()
                                .total((int) transactionPage.getTotalElements())
                                .page(commonFilters.getPage())
                                .limit(commonFilters.getLimit())
                                .build();

                return CommonResponse.<TransactionHistoryRecord>builder()
                                .message("Transaction records retrieved successfully")
                                .data(records)
                                .metaData(metaData)
                                .build();
        }

        @Override
        @Transactional
        @SuppressWarnings("rawtypes")
        public CommonResponse createTransaction(User user, CreateTransaction createTransaction) {
                if (createTransaction.getTransactionEnum() == null) {
                        throw new CustomBadRequestException("Transaction type is required",
                                        HttpStatus.BAD_REQUEST.value());
                }

                if (createTransaction.getAmount() == null || createTransaction.getAmount().signum() <= 0) {
                        throw new CustomBadRequestException("Amount must be greater than zero",
                                        HttpStatus.UNPROCESSABLE_CONTENT.value());
                }

                Transaction transaction;
                switch (createTransaction.getTransactionEnum()) {
                        case DEPOSIT:
                                transaction = Transaction.builder()
                                                .accountFromId(user.getId())
                                                .accountToId(null)
                                                .transactionType(TransactionEnum.DEPOSIT)
                                                .amount(createTransaction.getAmount())
                                                .build();
                                break;

                        case WITHDRAW:
                                BigDecimal currentBalance = transactionRepository.getCurrentBalance(user.getId());

                                if (currentBalance.subtract(createTransaction.getAmount())
                                                .compareTo(BigDecimal.ZERO) < 0) {
                                        throw new CustomBadRequestException("Insufficient balance",
                                                        HttpStatus.CONFLICT.value());
                                }
                                transaction = Transaction.builder()
                                                .accountFromId(user.getId())
                                                .accountToId(null)
                                                .transactionType(TransactionEnum.WITHDRAW)
                                                .amount(createTransaction.getAmount())
                                                .build();
                                break;

                        case TRANSFER:
                                if (createTransaction.getEmail() == null || createTransaction.getEmail().isEmpty()) {
                                        throw new CustomBadRequestException("Recipient email is required for transfer",
                                                        HttpStatus.BAD_REQUEST.value());
                                }

                                if (user.getEmail().equals(createTransaction.getEmail())) {
                                        throw new CustomBadRequestException("Cannot sending to the same account", 0);
                                }
                                User recipientUser = userRepository.findByEmail(createTransaction.getEmail())
                                                .orElseThrow(() -> new CustomBadRequestException(
                                                                "Recipient not found with email: "
                                                                                + createTransaction.getEmail(),
                                                                HttpStatus.NOT_FOUND.value()));

                                transaction = Transaction.builder()
                                                .accountFromId(user.getId())
                                                .accountToId(recipientUser.getId())
                                                .transactionType(TransactionEnum.TRANSFER)
                                                .amount(createTransaction.getAmount())
                                                .build();
                                break;
                        default:
                                throw new CustomBadRequestException("Invalid transaction type",
                                                HttpStatus.BAD_REQUEST.value());
                }
                transactionRepository.save(transaction);
                return CommonResponse.builder()
                                .message("Transaction created successfully")
                                .data(List.of())
                                .build();
        }
}
