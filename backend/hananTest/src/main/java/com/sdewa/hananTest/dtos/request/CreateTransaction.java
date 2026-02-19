package com.sdewa.hananTest.dtos.request;


import java.math.BigDecimal;

import com.sdewa.hananTest.enums.TransactionEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransaction {

    private String email;
    private TransactionEnum transactionEnum;
    private BigDecimal amount;

}
