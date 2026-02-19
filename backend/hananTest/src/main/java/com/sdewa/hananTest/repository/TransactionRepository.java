package com.sdewa.hananTest.repository;

import com.sdewa.hananTest.entity.Transaction;
import com.sdewa.hananTest.enums.TransactionEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    @Query("SELECT t FROM Transaction t WHERE t.accountFromId = :userId OR t.accountToId = :userId ORDER BY t.createdAt DESC")
    Page<Transaction> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.accountFromId = :userId AND t.transactionType = :transactionType")
    BigDecimal sumAmountByUserIdAndTransactionType(@Param("userId") Long userId, @Param("transactionType") TransactionEnum transactionType);
    
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.accountToId = :userId AND t.transactionType = :transactionType")
    BigDecimal sumReceivedAmountByUserId(@Param("userId") Long userId, @Param("transactionType") TransactionEnum transactionType);
}
