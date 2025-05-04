package com.e_learning.service;

import com.e_learning.dao.AnswerDao;
import com.e_learning.entities.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerService extends BaseServiceImp<Answer>{
    @Autowired
    private AnswerDao  answerDao;

    @Override
    public JpaRepository<Answer,Long> Repository() {
        return answerDao;
    }
}
