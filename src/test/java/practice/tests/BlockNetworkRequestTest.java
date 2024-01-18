package practice.tests;

import com.google.common.collect.ImmutableList;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.network.Network;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.annotation.concurrent.Immutable;
import java.util.Optional;

/*
 *@className-BlockNetworkRequestTest
 * @objective- The objective of test is to block the network request eg. ,jpg,css files for faster execution
 */
public class BlockNetworkRequestTest {

    ChromeDriver driver;
    @BeforeClass
    public void driverSetup() {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            }

    @Test
    public void networkFaildReq() throws InterruptedException {
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        //enable network
        devTools.send(Network.enable(Optional.empty(),Optional.empty(),Optional.empty()));
        //block network req pass url to block in list which is immutable
        devTools.send(Network.setBlockedURLs(ImmutableList.of("*.jpg","*.css")));
        long startTime =System.currentTimeMillis();
        driver.get("https://rahulshettyacademy.com/angularAppdemo");
        driver.findElement(By.cssSelector(".btn.btn-lg.btn-success")).click();
        driver.findElement(By.linkText("Selenium")).click();
        driver.findElement(By.cssSelector(".add-to-cart")).click();
        //to see how web page looks without css and images files
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.cssSelector("div p.product-description")).getText());
        long endTime = System.currentTimeMillis();
        //time taken to run test
        System.out.println(endTime-startTime);

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
