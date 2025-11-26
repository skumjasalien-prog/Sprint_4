package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

    public class MainPage {

        private WebDriver driver;

        public MainPage(WebDriver driver) {
            this.driver = driver;
        }

        // Кнопка "Заказать" вверху
        private By orderTopButton = By.className("Button_Button__ra12g");

        // Кнопка "Заказать" внизу
        private By orderBottomButton = By.className("Button_Button__ra12g");

        // FAQ: стрелка выпадающего списка
        private By faqArrow = By.className("accordion__heading");

        // текст ответа
        private By faqAnswer = By.className("accordion__panel");

        public void clickOrderTopButton() {
            // Ищем первую кнопку "Заказать" (вверху страницы)
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(orderTopButton));
            List<WebElement> buttons = driver.findElements(orderTopButton);
            WebElement topButton = buttons.stream()
                    .filter(button -> button.getText().equals("Заказать"))
                    .findFirst()
                    .orElse(buttons.get(0));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", topButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", topButton);
        }

        public void clickOrderBottomButton() {
            // Ищем последнюю кнопку "Заказать" (внизу страницы)
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(orderBottomButton));
            List<WebElement> buttons = driver.findElements(orderBottomButton);
            WebElement bottomButton = buttons.stream()
                    .filter(button -> button.getText().equals("Заказать"))
                    .reduce((first, second) -> second)
                    .orElse(buttons.get(buttons.size() - 1));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bottomButton);
            bottomButton.click();
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
