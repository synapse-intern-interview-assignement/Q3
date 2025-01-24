package com.lotteryresult.lotteryresult.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "lottery_results_v1_manoj")
public class LotteryResult {
    @Id
    @GeneratedValue
    private int lotteryId;
    private String lotteryNameAndDateText;
    private int firstNumber;
    private int secondNumber;
    private int thirdNumber;
    private int fourthNumber;
    private String prizeAmount;
}
