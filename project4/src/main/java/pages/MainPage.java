package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

    public class MainPage {

        private WebDriver driver;

        public MainPage(WebDriver driver) {
            this.driver = driver;
        }

        // Кнопка "Заказать" вверху
        private By orderTopButton = By.xpath("//button[text()='Заказать']");

        // Кнопка "Заказать" внизу
        private By orderBottomButton = By.xpath("//button[text()='Заказать']");

        // FAQ: стрелка выпадающего списка
        private By faqArrow = By.className("accordion__heading");

        // текст ответа
        private By faqAnswer = By.className("accordion__panel");

        public void clickOrderTopButton() {
            WebElement button = driver.findElement(orderTopButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }

        public void clickOrderBottomButton() {
            WebElement button = driver.findElement(orderBottomButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
            button.click();
        }

        public void clickFaqArrow(int index) {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement arrow = driver.findElements(faqArrow).get(index);
            wait.until(ExpectedConditions.elementToBeClickable(arrow));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", arrow);
            arrow.click();
        }

        // Получить текст ответа по индексу
        public String getFaqAnswerText(int index) {
            List<WebElement> answers = driver.findElements(faqAnswer);
            var answer = answers.get(index);
            return answer.getText();
        }
    }
