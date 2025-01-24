package com.lotteryresult.lotteryresult.controller;

import com.lotteryresult.lotteryresult.model.LotteryResult;
import com.lotteryresult.lotteryresult.service.LotteryResultService;
import com.lotteryresult.lotteryresult.service.LotteryScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class LotteryResultController {
    @Autowired
    private LotteryResultService lotteryResultService;

    @Autowired
    private LotteryScraperService lotteryScraperService;

    @GetMapping("/store-lottery-result")
    public String storeLotteryResult() {
        LotteryResult lotteryResult = lotteryScraperService.scrapeLotteryResults();
        lotteryResultService.storeLotteryResult(lotteryResult);
        return "LotteryResult stored successfully";
    }
}
