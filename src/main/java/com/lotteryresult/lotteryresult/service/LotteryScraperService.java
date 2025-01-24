package com.lotteryresult.lotteryresult.service;

import com.lotteryresult.lotteryresult.model.LotteryResult;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class LotteryScraperService {

    private static final int ITEM_POSITION = 7; // 7th lottery result
    private static final int SCROLL_ATTEMPTS = 5;
    private static final int SCROLL_PIXELS = 1000;
    private static final int SCROLL_WAIT_TIME_MS = 2000;
    // Make sure to add the location of the chromedriver before run this application
    private static final String CHROME_DRIVER_PATH = "C:\\Users\\manoj\\Desktop\\chromedriver-win64\\chromedriver.exe";
    private static final String LOTTERY_URL = "https://www.dlb.lk/";

    public LotteryResult scrapeLotteryResults() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(LOTTERY_URL);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            scrollPage(driver);

            List<WebElement> resultBoxes = driver.findElements(By.cssSelector(".col-md-12.no_padding.slideanim.slide"));

            if (resultBoxes.size() >= ITEM_POSITION) {
                WebElement seventhResultBox = resultBoxes.get(ITEM_POSITION - 1);

                String lotteryNameAndDateText = seventhResultBox.findElement(By.cssSelector(".lottery_n_d")).getText();
                StringBuilder numbers = extractLotteryNumbers(seventhResultBox);
                String prizeAmount = seventhResultBox.findElement(By.cssSelector(".next_jkpt")).getText();

                String[] splitNumbers = numbers.toString().trim().split(" ");

                // Create and populate the LotteryResult object
                LotteryResult lotteryResult = new LotteryResult();
                lotteryResult.setLotteryNameAndDateText(lotteryNameAndDateText);

                if (splitNumbers.length >= 4) { // Assuming there are at least 4 numbers
                    lotteryResult.setFirstNumber(Integer.parseInt(splitNumbers[0]));
                    lotteryResult.setSecondNumber(Integer.parseInt(splitNumbers[1]));
                    lotteryResult.setThirdNumber(Integer.parseInt(splitNumbers[2]));
                    lotteryResult.setFourthNumber(Integer.parseInt(splitNumbers[3]));
                }

                lotteryResult.setPrizeAmount(prizeAmount);

                return lotteryResult;
            } else {
                System.out.println("The requested lottery result is not available.");
                return null;
            }
        } finally {
            driver.quit();
        }
    }

    private static void scrollPage(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        for (int i = 0; i < SCROLL_ATTEMPTS; i++) {
            jsExecutor.executeScript("window.scrollBy(0, " + SCROLL_PIXELS + ");");
            try {
                Thread.sleep(SCROLL_WAIT_TIME_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static StringBuilder extractLotteryNumbers(WebElement resultBox) {
        List<WebElement> numberElements = resultBox.findElements(By.cssSelector(".number_shanida.number_circle"));
        StringBuilder numbers = new StringBuilder();
        for (WebElement numberElement : numberElements) {
            numbers.append(numberElement.getText()).append(" ");
        }
        return numbers;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledScrape() {
        scrapeLotteryResults();
    }
}
