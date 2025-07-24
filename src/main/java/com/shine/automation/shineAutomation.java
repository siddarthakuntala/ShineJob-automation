package com.shine.automation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class shineAutomation {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().window().maximize();

        try {
            // 1. Login
            driver.get("https://www.shine.com/");
            System.out.println("✅ Opened Shine.com");

            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Login']")));
            loginBtn.click();

            WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_email_login")));
            WebElement password = driver.findElement(By.id("id_password"));
            email.sendKeys("siddarth0045@gmail.com");
            password.sendKeys("Siddartha@45");

            WebElement submit = driver.findElement(By.xpath("//button[contains(@class,'w-100 mt-30')]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
            System.out.println("✅ Submitted Login");

            // ✅ Wait for post-login dashboard or known element
            wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'dashboardContainer')]")),
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'myshine/profile')]"))
            ));
            takeScreenshot(driver, "1_logged_in.png");

            // 2. Job search
            driver.get("https://www.shine.com/job-search/");
            System.out.println("✅ Navigated to Job Search page");

            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Job title, skills']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBox);

            WebElement jobInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_q")));
            WebElement locInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_loc")));
            WebElement expInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_exp")));

            jobInput.clear();
            jobInput.sendKeys("Software Testing");

            locInput.clear();
            locInput.sendKeys("Hyderabad");

            expInput.clear();
            expInput.sendKeys("2");

            System.out.println("✅ Filled search form");
            takeScreenshot(driver, "2_filled_search_fields.png");

            WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Search jobs')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBtn);
            System.out.println("✅ Clicked search button");

            // 3. Close skill modal if it appears
            try {
                WebElement skillModalClose = wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button.modalNova_modalClose__sxVHP")));
                skillModalClose.click();
                Thread.sleep(1000);
                System.out.println("✅ Closed skill modal");
            } catch (Exception e) {
                System.out.println("⚠️ Skill modal not present");
            }

            // 4. Find and click the 2nd job listing
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("a[target='_self']")));
            List<WebElement> jobLinks = driver.findElements(By.cssSelector("a[target='_self']"));

            if (jobLinks.size() >= 2) {
                WebElement secondJob = jobLinks.get(1);
                String jobTitle = secondJob.getText().trim();
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", secondJob);
                Thread.sleep(1000);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", secondJob);
                System.out.println("✅ Clicked 2nd job listing: " + jobTitle);
            } else {
                System.out.println("❌ Less than 2 job listings found.");
                return;
            }

            // 5. Switch to new tab
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
            }

            // 6. Apply to the job
            try {
                WebElement applyBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(@id,'id_apply') and contains(text(),'Apply')]")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", applyBtn);
                Thread.sleep(1000);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", applyBtn);
                System.out.println("✅ Applied to job");
                takeScreenshot(driver, "3_applied_job.png");
            } catch (TimeoutException e) {
                System.out.println("❌ Apply button not found or not clickable.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            takeScreenshot(driver, "error_generic.png");
            e.printStackTrace();
        } finally {
            driver.quit();
            System.out.println("✅ Browser closed");
        }
    }

    public static void takeScreenshot(WebDriver driver, String filename) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            Files.createDirectories(Paths.get("screenshots"));
            Files.copy(src.toPath(), Paths.get("screenshots", filename), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("📸 Screenshot saved: " + filename);
        } catch (IOException e) {
            System.out.println("❌ Failed to capture screenshot: " + filename);
        }
    }
}
