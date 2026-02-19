package com.sdewa.hananTest.dtos.common;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sdewa.hananTest.enums.TransactionEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryRecord {
    private TransactionEnum transactionEnum;
    private BigDecimal amount;
    private UserProfileDto userFrom;
    private UserProfileDto userTO;
    private LocalDateTime createdAt;
}
