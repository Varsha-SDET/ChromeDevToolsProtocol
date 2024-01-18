package practice.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v118.emulation.Emulation;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
/*
 *@className- MobileEmulatorTest
 * @objective- The objective of class is to CDP function- modileEmulator practice
 */
public class MobileEmulatorTest {
    //using selenium custom command (recommanded)
    ChromeDriver driver;
    @BeforeClass
    public void driverSetup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void mobileEmulator() throws InterruptedException {

        DevTools devTools =driver.getDevTools();
        devTools.createSession();
        //send commands to CDP =>invoke cdp methods and get access to dev tools
        //pass dimentions for mobile emulations 4 mendatory rest optional
        devTools.send(Emulation.setDeviceMetricsOverride(600,1000,50,true,
                Optional.empty(),Optional.empty(),Optional.empty(),
                Optional.empty(),Optional.empty(),Optional.empty(),
                Optional.empty(),Optional.empty(),Optional.empty()));

        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        //open menu and click library
        driver.findElement(By.cssSelector(".navbar-toggler")).click();
        Thread.sleep(3000);
        driver.findElement(By.linkText("Library")).click();
    }

    //perform mobile emulation with another type ->directly calling CDP
    @Test
    public void practiceCDPCommands() throws InterruptedException {
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        //pass all values as param after creating hashmap (for Emulation.setDeviceMetricsOverride selenium method)
        Map deviceMetricsMap =new HashMap();
        deviceMetricsMap.put("width",600);
        deviceMetricsMap.put("height",1000);
        deviceMetricsMap.put("deviceScaleFactor",50);
        deviceMetricsMap.put("mobile",true);
        driver.executeCdpCommand("Emulation.setDeviceMetricsOverride",deviceMetricsMap);

        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        //open menu and click library
        driver.findElement(By.cssSelector(".navbar-toggler")).click();
        Thread.sleep(3000);
        driver.findElement(By.linkText("Library")).click();
        devTools.disconnectSession();

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
