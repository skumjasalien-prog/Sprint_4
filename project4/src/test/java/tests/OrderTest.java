package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class OrderTest {

    protected WebDriver driver;

    @Before
    public void setUp() {
        // Автоматически управляем драйверами
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();

        // Можно выбрать браузер
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/"); // замените на свой URL
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
