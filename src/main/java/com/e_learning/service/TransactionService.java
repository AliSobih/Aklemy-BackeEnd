package com.e_learning.service;

import com.e_learning.dao.TransactionDao;
import com.e_learning.entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends BaseServiceImp<Transaction> {
    @Autowired
    private TransactionDao transactionDao;
    @Override
    public JpaRepository<Transaction, Long> Repository() {
        return transactionDao;
    }
}
