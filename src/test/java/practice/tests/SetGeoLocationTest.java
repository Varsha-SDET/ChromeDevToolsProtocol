package practice.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

/*
 *@className- SetGeoLocationTest
 * @objective- The objective of class is to practice localisation testing-> using setGeoLocation
 */
public class SetGeoLocationTest  {
    ChromeDriver driver;
    @BeforeClass
    public void driverSetup(){
        WebDriverManager.chromedriver().setup();
      //  ChromeOptions options = new ChromeOptions();
//        options.addArguments("--enable-features=Geolocation");
//        options.addArguments("--lang=es");
//        Set the browser language to Spanish
        // Launch Chrome browser with the defined options
      //  driver = new ChromeDriver(options);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    //spanish proxy server  alongside with location required to access netflix in spanish( Netflix
    // checked your Location by IP
    @Test
    public void geoLocation(){

        DevTools devTools =driver.getDevTools();
        devTools.createSession();
        Map<String,Object> coordinatesMap = new HashMap<String,Object>();
        //spain :40,3
        // for spain , hyderabad etc (like searching restaurant in hydrabad or spain title search)
        coordinatesMap.put("latitude",40);
        coordinatesMap.put("longitude",3);
        coordinatesMap.put("accuracy",1);
        driver.executeCdpCommand("Emulation.setGeolocationOverride",coordinatesMap);
        //another way
        //devtool.send(Emulation.setGeolocationOverride(40,3, 1) should actually be devTools.send(Emulation.setGeolocationOverride(Optional.of(17), Optional.of(78), Optional.of(1)));
        driver.get("http://google.com");
        driver.findElement(By.name("q")).sendKeys("netflix", Keys.ENTER);
        //1st search result click
        driver.findElement(By.cssSelector(".LC20lb")).click();
        String title = driver.findElement(By.cssSelector(".our-story-card-title")).getText();
        System.out.println(title);
       // Assert.assertEquals(title,"");

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
