package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    public static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();  // автоматически скачивает драйвер
        return new ChromeDriver();
    }

    public static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup(); // автоматически скачивает драйвер
        return new FirefoxDriver();
    }
}
