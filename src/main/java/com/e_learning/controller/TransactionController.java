package com.e_learning.controller;

import com.e_learning.dto.TransactionDTO;
import com.e_learning.entities.Transaction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@CrossOrigin
public class TransactionController extends BaseController<Transaction, TransactionDTO> {

}
