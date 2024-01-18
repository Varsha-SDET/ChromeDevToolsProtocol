package practice.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.fetch.Fetch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

/*
 *@className-NetworkLogActivityTest
 * @objective- The objective of test is to to fetch the domain .
 */
public class NetworkMockingTest {
    ChromeDriver driver;
    @BeforeClass
    public void driverSetup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void networkMocking() throws InterruptedException {
        DevTools devTools =  driver.getDevTools();
        devTools.createSession();
        //enable domain req triggered and grab the req
        devTools.send(Fetch.enable(Optional.empty(),Optional.empty()));
        //pause ->modify req and send
        devTools.addListener(Fetch.requestPaused(),request -> {
            if(request.getRequest().getUrl().contains("shetty")){
                String mockUrl =request.getRequest().getUrl().replace("=shetty","=BadGuy");
                System.out.println(mockUrl);
                //returing optional datatype so used Optional
                devTools.send(Fetch.continueRequest(request.getRequestId(),Optional.of(mockUrl),
                        Optional.of(request.getRequest().getMethod()),
                        Optional.empty(),Optional.empty()));
            }else{
                //no mocking  for all other request and send them as it is and continue them
                devTools.send(Fetch.continueRequest(request.getRequestId(),Optional.of(request.getRequest().getUrl()),
                        Optional.of(request.getRequest().getMethod()),
                        Optional.empty(),Optional.empty()));

            }
        });

        driver.get("https://rahulshettyacademy.com/angularAppdemo");
        driver.findElement(By.cssSelector("button[routerlink*='library']")).click();
        //wait to load error text page
        Thread.sleep(3000);
        //error text page (1 book available page displayed with error text )
        String text =driver.findElement(By.cssSelector("p")).getText();
        System.out.println(text);
        devTools.disconnectSession();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
