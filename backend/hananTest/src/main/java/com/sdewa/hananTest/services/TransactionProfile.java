package com.sdewa.hananTest.services;

import com.sdewa.hananTest.dtos.response.TransactionProfileResponse;
import com.sdewa.hananTest.entity.User;

public interface TransactionProfile {
    TransactionProfileResponse getProfileTransaction(User user);
}

