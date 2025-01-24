package com.lotteryresult.lotteryresult.repo;

import com.lotteryresult.lotteryresult.model.LotteryResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotteryResultRepo extends JpaRepository<LotteryResult, Integer> { }
