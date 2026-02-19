package com.sdewa.hananTest.services;

import com.sdewa.hananTest.dtos.common.TransactionHistoryRecord;
import com.sdewa.hananTest.dtos.filters.CommonFilters;
import com.sdewa.hananTest.dtos.request.CreateTransaction;
import com.sdewa.hananTest.dtos.response.CommonResponse;
import com.sdewa.hananTest.entity.User;

public interface TransactionCRUDService {

    CommonResponse<TransactionHistoryRecord> getTransactionRecord( User user,CommonFilters commonFilters);

    @SuppressWarnings("rawtypes")
    CommonResponse createTransaction(User user,CreateTransaction createTransaction);
    
    
}  
