package practice.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.function.Predicate;
/*
 *@classname - BasicAuthenticationTest
 *@objective- basic authentication handle using uriPredicate function
 */
public class BasicAuthenticationTest {
    ChromeDriver driver;
    @BeforeClass
    public void driverSetup() {
        //driver setup
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void basicAuthentication(){
        //predicate , consumer
        Predicate<URI> uriPredicate = uri -> uri.getHost().contains("httpbin.org");
        //auth casting driver and registering driver with predicate condition
        ((HasAuthentication)driver).register(uriPredicate, UsernameAndPassword.of("foo","bar"));
        driver.get("http://httpbin.org/basic-auth/foo/bar");
    }


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
