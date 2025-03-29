package com.example.antockassignment.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

@Component
public class CSVDownload {

    private static final String DOWNLOAD_DIR = System.getProperty("user.home") + "/Downloads";
    private static final String CSV_EXTENSION = ".csv";
    private static final String URL = "https://www.ftc.go.kr/www/selectBizCommOpenList.do?key=255";

    public String download(String city, String district) throws IOException, InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized", "--headless", "--disable-gpu", "--no-sandbox");
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(URL);
            Thread.sleep(3000);

            WebElement cityDropdown = driver.findElement(By.id("searchInst1"));
            new Select(cityDropdown).selectByVisibleText(city);
            Thread.sleep(2000);

            WebElement districtDropdown = driver.findElement(By.id("searchInst2"));
            new Select(districtDropdown).selectByVisibleText(district);
            Thread.sleep(2000);

            WebElement csvDownloadButton = driver.findElement(By.cssSelector("a.btn.md.primary.ico-down[onclick*='fn_commExcel()']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", csvDownloadButton);

            Thread.sleep(3000);

            File folder = new File(DOWNLOAD_DIR);
            File[] files = folder.listFiles((dir, name) -> name.endsWith(CSV_EXTENSION));

            if (files != null && files.length > 0) {
                File csvFile = files[0];
                return new String(Files.readAllBytes(csvFile.toPath()), Charset.forName("CP949"));
            } else {
                return "CSV 파일을 찾을 수 없습니다.";
            }
        } finally {
            driver.quit();
        }
    }
}
