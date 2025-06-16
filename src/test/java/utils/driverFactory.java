package utils;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class driverFactory {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Faker faker;

    public static WebDriver getDriver(){
        if (driver == null) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            driver = new EdgeDriver(options);
            driver.manage().window().maximize();

        }
        return driver;
        }

        public static WebDriverWait getWait(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait;
        }

    public static Faker getFaker() {
        if (faker == null) {
            faker = new Faker();
        }
        return faker;

    }

    public static void quitDriver(){
        if (driver != null){
            driver.quit();
            driver = null ;
        }
    }
}

