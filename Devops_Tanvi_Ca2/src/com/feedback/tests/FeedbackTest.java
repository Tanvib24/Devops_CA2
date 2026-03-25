package com.feedback.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class FeedbackTest {

    static WebDriver driver;
    static WebDriverWait wait;

    static int passed = 0;
    static int failed = 0;

    static String formPath = "file:///C:/Users/Tanvibandebuche/Downloads/devops_ca2/feedback_form.html";

    public static void main(String[] args) throws Exception {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().window().maximize();

        test1_FormOpens();
        test2_ValidSubmission();
        test3_EmptyFields();
        test4_InvalidEmail();
        test5_InvalidMobile();
        test6_Dropdown();
        test7_Reset();

        // FINAL SUMMARY
        System.out.println("\n===== TEST SUMMARY =====");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total: " + (passed + failed));

        if (failed == 0) {
            System.out.println("ALL TESTS PASSED ✅");
        } else {
            System.out.println("SOME TESTS FAILED ❌");
        }

        driver.quit();
    }

    static void markResult(String testName, boolean status) {
        if (status) {
            System.out.println(testName + " → PASSED");
            passed++;
        } else {
            System.out.println(testName + " → FAILED");
            failed++;
        }
    }

    static void openForm() throws Exception {
        driver.get(formPath);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("studentName")));
        Thread.sleep(500);
    }

    static void fillForm(String name, String email, String mobile,
                         String dept, String gender, String feedback) throws Exception {

        driver.findElement(By.id("studentName")).clear();
        driver.findElement(By.id("studentName")).sendKeys(name);

        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys(email);

        driver.findElement(By.id("mobile")).clear();
        driver.findElement(By.id("mobile")).sendKeys(mobile);

        if (dept != null)
            new Select(driver.findElement(By.id("department")))
                    .selectByValue(dept);

        if (gender != null) {
            List<WebElement> radios = driver.findElements(By.name("gender"));
            for (WebElement r : radios) {
                if (r.getAttribute("value").equals(gender))
                    r.click();
            }
        }

        driver.findElement(By.id("feedback")).clear();
        driver.findElement(By.id("feedback")).sendKeys(feedback);
    }

    static void submit() throws Exception {
        driver.findElement(By.id("submitBtn")).click();
        Thread.sleep(500);
    }

    // TEST CASES

    static void test1_FormOpens() throws Exception {
        openForm();
        boolean status = driver.findElement(By.id("studentName")).isDisplayed();
        markResult("Test1 Form Opens", status);
    }

    static void test2_ValidSubmission() throws Exception {
        openForm();
        fillForm("John", "john@test.com", "9876543210",
                "cse", "male",
                "This is a valid feedback with more than ten words");
        submit();

        boolean status = driver.getPageSource().toLowerCase().contains("success");
        markResult("Test2 Valid Submission", status);
    }

    static void test3_EmptyFields() throws Exception {
        openForm();
        submit();

        boolean status = driver.getPageSource().toLowerCase().contains("required");
        markResult("Test3 Empty Fields", status);
    }

    static void test4_InvalidEmail() throws Exception {
        openForm();
        fillForm("Jane", "invalidemail", "9876543210",
                "it", "female",
                "This is valid feedback with enough words");
        submit();

        boolean status = driver.getPageSource().toLowerCase().contains("email");
        markResult("Test4 Invalid Email", status);
    }

    static void test5_InvalidMobile() throws Exception {
        openForm();
        fillForm("Jane", "jane@test.com", "12345abc",
                "it", "female",
                "This is valid feedback with enough words");
        submit();

        boolean status = driver.getPageSource().toLowerCase().contains("mobile");
        markResult("Test5 Invalid Mobile", status);
    }

    static void test6_Dropdown() throws Exception {
        openForm();

        Select dropdown = new Select(driver.findElement(By.id("department")));
        List<WebElement> options = dropdown.getOptions();

        boolean status = options.size() > 1;
        markResult("Test6 Dropdown", status);
    }

    static void test7_Reset() throws Exception {
        openForm();

        fillForm("Test", "test@test.com", "9876543210",
                "cse", "male", "Some feedback");

        driver.findElement(By.id("resetBtn")).click();

        String name = driver.findElement(By.id("studentName")).getAttribute("value");

        boolean status = name.isEmpty();
        markResult("Test7 Reset", status);
    }
}
