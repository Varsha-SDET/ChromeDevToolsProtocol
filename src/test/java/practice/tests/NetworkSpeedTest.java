package practice.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.Connection;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.network.Network;
import org.openqa.selenium.devtools.v119.network.model.ConnectionType;
import org.openqa.selenium.mobile.NetworkConnection;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;


/*
 *@className-NetworkSpeedTest
 * @objective- The objective of test is to slower network
 */
public class NetworkSpeedTest {
    ChromeDriver driver;
    @BeforeClass
    public void driverSetup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void networkFaildReq() {
        //log file
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        //enable network
        devTools.send(Network.enable(Optional.empty(),Optional.empty(),Optional.empty()));
        long startTime = System.currentTimeMillis();
        devTools.send(Network.emulateNetworkConditions(false,3000
                ,2000,10000, Optional.of(ConnectionType.ETHERNET)));
        driver.get("https://rahulshettyacademy.com/angularAppdemo");
        driver.findElement(By.cssSelector("button[routerlink*='library']")).click();
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);

    }


    @Test
    public void loadingReqFailedError() {
        //log file
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        //enable network
        devTools.send(Network.enable(Optional.empty(),Optional.empty(),Optional.empty()));
        long startTime = System.currentTimeMillis();
        //make it offline and check error with help of listener
        devTools.send(Network.emulateNetworkConditions(true,3000
                ,2000,10000, Optional.of(ConnectionType.ETHERNET)));

        devTools.addListener(Network.loadingFailed(),loadingFailed -> {
            //catch why req failed
            System.out.println(loadingFailed.getErrorText());
            System.out.println(loadingFailed.getTimestamp());
        });
        driver.get("http://google.com");
        driver.findElement(By.name("q")).sendKeys("amazon", Keys.ENTER);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
