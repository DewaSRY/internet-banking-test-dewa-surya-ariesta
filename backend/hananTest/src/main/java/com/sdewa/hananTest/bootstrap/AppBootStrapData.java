package com.sdewa.hananTest.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sdewa.hananTest.dtos.request.CreateTransaction;
import com.sdewa.hananTest.dtos.request.SignupRequest;
import com.sdewa.hananTest.enums.TransactionEnum;
import com.sdewa.hananTest.repository.UserRepository;
import com.sdewa.hananTest.services.AuthService;
import com.sdewa.hananTest.services.TransactionCRUDService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppBootStrapData implements CommandLineRunner {

        private final AuthService authService;
        private final TransactionCRUDService transactionCRUDService;
        private final UserRepository userRepository;

        @Override
        public void run(String... args) throws Exception {
                var userOneResponse = authService.signup(SignupRequest.builder()
                                .email("user1@example.com")
                                .password("password123")
                                .username("user 1")
                                .build());

                var userTwoResponse = authService.signup(SignupRequest.builder()
                                .email("user2@example.com")
                                .password("password123")
                                .username("user 2")
                                .build());

                var userOne = userOneResponse.getUserProfileDto();
                var userTow = userTwoResponse.getUserProfileDto();

                var userOneModelMock = userRepository.findByEmail(userOne.getEmail())
                                .orElseThrow(() -> new RuntimeException("user email not found"));

                var userTwoModelMock = userRepository.findByEmail(userTow.getEmail())
                                .orElseThrow(() -> new RuntimeException("user email not found"));

                transactionCRUDService.createTransaction(
                                userOneModelMock,
                                CreateTransaction.builder()
                                                .amount(new BigDecimal("1000000"))
                                                .transactionEnum(TransactionEnum.DEPOSIT)
                                                .build());

                transactionCRUDService.createTransaction(
                                userTwoModelMock,
                                CreateTransaction.builder()
                                                .amount(new BigDecimal("1500000"))
                                                .transactionEnum(TransactionEnum.DEPOSIT)
                                                .build());

                transactionCRUDService.createTransaction(
                                userOneModelMock,
                                CreateTransaction.builder()
                                                .amount(new BigDecimal("10000"))
                                                .transactionEnum(TransactionEnum.TRANSFER)
                                                .email(userTwoModelMock.getEmail())
                                                .build());

                transactionCRUDService.createTransaction(
                                userTwoModelMock,
                                CreateTransaction.builder()
                                                .amount(new BigDecimal("30000"))
                                                .transactionEnum(TransactionEnum.TRANSFER)
                                                .email(userOneModelMock.getEmail())
                                                .build());

                transactionCRUDService.createTransaction(
                                userTwoModelMock,
                                CreateTransaction.builder()
                                                .amount(new BigDecimal("30000"))
                                                .transactionEnum(TransactionEnum.WITHDRAW)
                                                .build());
        }

}
