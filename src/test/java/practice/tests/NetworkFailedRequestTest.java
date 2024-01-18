package practice.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.fetch.Fetch;
import org.openqa.selenium.devtools.v85.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v85.network.model.ErrorReason;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class NetworkFailedRequestTest {
    /*
     *@className-NetworkFailedRequestTest
     * @objective- The objective of test is to fail the network request after getting response back
     */
         ChromeDriver driver;
        @BeforeClass
        public void driverSetup() {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }

        @Test
        public void networkFaildReq(){
            DevTools devTools = driver.getDevTools();
            devTools.createSession();
            //http req matches the pattern regular expression *GetBook*
            //list is expected for pattern which is optional datatype
             Optional<List<RequestPattern>> patterns =Optional.of(Arrays.asList(new RequestPattern(Optional.of("*GetBook*"),
                    Optional.empty(),Optional.empty())));
            devTools.send(Fetch.enable(patterns,Optional.empty()));
            devTools.addListener(Fetch.requestPaused(),request ->{
                //req is failed example to see when API server failed to give response can get error
                // or not but here its  blank table in website
                devTools.send(Fetch.failRequest(request.getRequestId(), ErrorReason.FAILED));
            });
            driver.get("https://rahulshettyacademy.com/angularAppdemo");
            driver.findElement(By.cssSelector("button[routerlink*='library']")).click();

        }

//        @AfterClass
//        public void tearDown() {
//            if (driver != null) {
//                driver.quit();
//            }
//        }

}
