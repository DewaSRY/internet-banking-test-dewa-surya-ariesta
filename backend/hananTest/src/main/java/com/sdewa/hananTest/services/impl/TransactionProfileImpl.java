package com.sdewa.hananTest.services.impl;

import com.sdewa.hananTest.dtos.response.TransactionProfileResponse;
import com.sdewa.hananTest.entity.User;
import com.sdewa.hananTest.enums.TransactionEnum;
import com.sdewa.hananTest.repository.TransactionRepository;
import com.sdewa.hananTest.services.TransactionProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionProfileImpl implements TransactionProfileService {

    private final TransactionRepository transactionRepository;

    @Override
    @Transactional(readOnly = true)
    public TransactionProfileResponse getProfileTransaction(User user) {
        BigDecimal deposit = transactionRepository.sumAmountByUserIdAndTransactionType(
                user.getId(),
                TransactionEnum.DEPOSIT
        );

        BigDecimal withdraw = transactionRepository.sumAmountByUserIdAndTransactionType(
                user.getId(),
                TransactionEnum.WITHDRAW
        );

        BigDecimal transfer = transactionRepository.sumAmountByUserIdAndTransactionType(
                user.getId(),
                TransactionEnum.TRANSFER
        );

        BigDecimal received = transactionRepository.sumReceivedAmountByUserId(
                user.getId(),
                TransactionEnum.TRANSFER
        );

        BigDecimal total = deposit
                .add(received)
                .subtract(withdraw)
                .subtract(transfer);

        return TransactionProfileResponse.builder()
                .total(total)
                .widthDraw(withdraw)
                .transfer(transfer)
                .received(received)
                .deposit(deposit)
                .build();
    }
}
