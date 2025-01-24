package com.lotteryresult.lotteryresult.service;

import com.lotteryresult.lotteryresult.model.LotteryResult;
import com.lotteryresult.lotteryresult.repo.LotteryResultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryResultService {

    @Autowired
    private LotteryResultRepo lotteryResultRepo;

    public void storeLotteryResult(LotteryResult lotteryResult) {
        lotteryResultRepo.save(lotteryResult);
    }
}
