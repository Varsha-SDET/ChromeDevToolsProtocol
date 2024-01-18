package practice.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.network.Network;
import org.openqa.selenium.devtools.v119.network.model.Request;
import org.openqa.selenium.devtools.v119.network.model.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;
/*
 *@className-NetworkLogActivityTest
 * @objective- The objective of test is to log network activity.
 */
public class NetworkLogActivityTest {
    ChromeDriver driver;
    @BeforeClass
    public void driverSetup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void networkLogActivity() {
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        //enables network traffic listening
        devTools.send(Network.enable(Optional.empty(),Optional.empty(),Optional.empty()));
       //request send event
        devTools.addListener(Network.requestWillBeSent(),request ->{
            Request req =request.getRequest();
         //   System.out.println(req.getUrl());
        });
        //event triggered when got response
        devTools.addListener(Network.responseReceived(),response ->{
            Response res = response.getResponse();

//            System.out.println(res.getUrl());
 //           System.out.println(res.getStatus());

          //  log response only when( any api call )response is failed
            if(res.getStatus().toString().startsWith("4")){
                System.out.println(res.getUrl() +"is failing with status code : " + res.getStatus());
            }
        });
        driver.get("https://rahulshettyacademy.com/angularAppdemo");
        driver.findElement(By.cssSelector("button[routerlink*='library']")).click();
        devTools.disconnectSession();

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
