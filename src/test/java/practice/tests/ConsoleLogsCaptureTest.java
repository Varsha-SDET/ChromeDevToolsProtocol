package practice.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/*
 *@classname - ConsoleLogsCaptureTest
 *@objective- jss error logs print or log file
 * (not related to network but code thats why can capture it and also using devtools section capturing)
 */
public class ConsoleLogsCaptureTest {
    ChromeDriver driver;
    @BeforeClass
    public void driverSetup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void ConsoleLogsCapture(){
        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        driver.findElement(By.linkText("Browse Products")).click();
        driver.findElement(By.partialLinkText("Selenium")).click();
        driver.findElement(By.cssSelector(".add-to-cart")).click();
        driver.findElement(By.linkText("Cart")).click();
        driver.findElement(By.id("exampleInputEmail1")).clear();
        driver.findElement(By.id("exampleInputEmail1")).sendKeys("2");
        //log  entries object (use this logic in listener block to get logs where its failed listener -> on test failure )
        LogEntries entry =driver.manage().logs().get(LogType.BROWSER);
        List<LogEntry> logs=entry.getAll(); //return all logs (getall method)
        for (LogEntry e: logs){
            System.out.println(e.getMessage()); //iterating and printing logs
            //use log4j to print logs
        }
    }

//    @AfterClass
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}
