package com.sdewa.hananTest.dtos.response;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionProfileResponse {
    private BigDecimal total;
    private BigDecimal widthDraw;
    private BigDecimal transfer;
    private BigDecimal received;
    private BigDecimal deposit;
}
