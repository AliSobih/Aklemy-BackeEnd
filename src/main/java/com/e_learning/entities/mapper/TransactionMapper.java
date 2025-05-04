package com.e_learning.entities.mapper;

import com.e_learning.dto.TransactionDTO;
import com.e_learning.entities.Transaction;
import com.e_learning.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.toCollection;

@Component
public class TransactionMapper implements Mapper<Transaction, TransactionDTO> {
    @Override
    public TransactionDTO toDTO(Transaction entity) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(entity.getId());
        transactionDTO.setTransactionDate(entity.getTransactionDate());
        transactionDTO.setAmount(entity.getAmount());
        transactionDTO.setDiscount(entity.getDiscount());
        transactionDTO.setPaymentMethod(entity.getPaymentMethod());
        return transactionDTO;
    }

    @Override
    public Transaction toEntity(TransactionDTO dto) {
        Transaction transaction = new Transaction();
        transaction.setId(dto.getId());
        transaction.setAmount(dto.getAmount());
        transaction.setTransactionDate(dto.getTransactionDate());
        transaction.setDiscount(dto.getDiscount());
        transaction.setPaymentMethod(dto.getPaymentMethod());
        return transaction;
    }

    @Override
    public ArrayList<TransactionDTO> toDTOs(Collection<Transaction> transactions) {
        return transactions.stream().map(this::toDTO).collect(toCollection(ArrayList<TransactionDTO>::new));
    }

    @Override
    public ArrayList<Transaction> toEntities(Collection<TransactionDTO> transactionDTOS) {
        return transactionDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList<Transaction>::new));
    }

    @Override
    public PageResult<TransactionDTO> toDataPage(PageResult<Transaction> entities) {
        return new PageResult<>(
                toDTOs(entities.getData()),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
