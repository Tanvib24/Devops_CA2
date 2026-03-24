package com.feedback.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class FeedbackTest {

    static WebDriver driver;
    static WebDriverWait wait;

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

        driver.quit();
    }

    static void openForm() throws Exception {
        driver.get(formPath);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("studentName")));
        Thread.sleep(800);
    }

    static void fillForm(String name, String email, String mobile,
                         String dept, String gender, String feedback) throws Exception {

        driver.findElement(By.id("studentName")).clear();
        driver.findElement(By.id("studentName")).sendKeys(name);

        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys(email);

        driver.findElement(By.id("mobile")).clear();
        driver.findElement(By.id("mobile")).sendKeys(mobile);

        if(dept != null)
            new Select(driver.findElement(By.id("department")))
                .selectByValue(dept);

        if(gender != null) {
            List<WebElement> radios = driver.findElements(By.name("gender"));
            for(WebElement r : radios) {
                if(r.getAttribute("value").equals(gender))
                    r.click();
            }
        }

        driver.findElement(By.id("feedback")).clear();
        driver.findElement(By.id("feedback")).sendKeys(feedback);
    }

    static void submit() throws Exception {
        driver.findElement(By.id("submitBtn")).click();
        Thread.sleep(800);
    }

    static void test1_FormOpens() throws Exception {
        openForm();
        System.out.println("Form Opened");
    }

    static void test2_ValidSubmission() throws Exception {
        openForm();
        fillForm("John", "john@test.com", "9876543210",
                "cse", "male",
                "This is a valid feedback with more than ten words");
        submit();
    }

    static void test3_EmptyFields() throws Exception {
        openForm();
        submit();
    }

    static void test4_InvalidEmail() throws Exception {
        openForm();
        fillForm("Jane", "invalidemail", "9876543210",
                "it", "female",
                "This is valid feedback with enough words");
        submit();
    }

    static void test5_InvalidMobile() throws Exception {
        openForm();
        fillForm("Jane", "jane@test.com", "12345abc",
                "it", "female",
                "This is valid feedback with enough words");
        submit();
    }

    static void test6_Dropdown() throws Exception {
        openForm();
        submit();
    }

    static void test7_Reset() throws Exception {
        openForm();
        driver.findElement(By.id("resetBtn")).click();
    }
}