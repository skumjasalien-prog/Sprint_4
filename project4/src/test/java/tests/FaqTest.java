package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.MainPage;

public class FaqTest {

    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/"); // адрес тестируемого сайта

        mainPage = new MainPage(driver);
    }

    @Test
    public void faqDropdownTest() {
        for (int i = 0; i < 8; i++) {
            mainPage.clickFaqArrow(i);
            String answerText = mainPage.getFaqAnswerText(i);
            System.out.println("Ответ FAQ " + i + ": " + answerText);
            // Проверка, что текст ответа не пустой
            Assert.assertTrue("Текст ответа FAQ должен отображаться", answerText != null && !answerText.isEmpty());
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
