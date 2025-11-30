package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    // Поля первой части формы заказа
    private By nameField = By.xpath("//input[@placeholder='* Имя']");
    private By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    private By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroField = By.className("select-search__input");
    private By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.className("Button_Middle__1CSJM");

    // Поля второй части формы заказа
    private By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private By rentalPeriodField = By.className("Dropdown-root");
    private By rentalPeriodOption = By.className("Dropdown-option");
    private By colorBlackCheckbox = By.id("black");
    private By colorGreyCheckbox = By.id("grey");
    private By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private By orderButton = By.className("Button_Middle__1CSJM");
    private By confirmButton = By.xpath("//button[text()='Да']");
    private By orderSuccessMessage = By.className("Order_ModalHeader__3FDaJ");
    private By confirmModal = By.xpath("//div[contains(@class, 'Modal')]//button[text()='Да']/ancestor::div[contains(@class, 'Modal')]");

    // Заполнение первой части формы
    public void fillFirstPartOfOrder(String name, String surname, String address, String metro, String phone) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);
        
        // Выбор станции метро
        driver.findElement(metroField).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='" + metro + "']")));
        WebElement metroOption = driver.findElement(By.xpath("//div[text()='" + metro + "']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", metroOption);
        metroOption.click();
        
        driver.findElement(phoneField).sendKeys(phone);
    }

    // Нажатие кнопки "Далее"
    public void clickNextButton() {
        // Ищем кнопку "Далее" среди всех кнопок с классом Button_Middle__1CSJM
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(nextButton));
        driver.findElements(nextButton).stream()
                .filter(button -> button.getText().equals("Далее"))
                .findFirst()
                .ifPresent(button -> {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
                    button.click();
                });
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateField));
    }

    // Заполнение второй части формы
    public void fillSecondPartOfOrder(String date, String rentalPeriod, String color, String comment) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateField));
        
        // Заполнение даты
        WebElement dateInput = driver.findElement(dateField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateInput);
        dateInput.click();
        dateInput.sendKeys(date);
        
        // Клик вне поля для закрытия календаря
        driver.findElement(dateField).sendKeys(Keys.ENTER);

        // Выбор срока аренды
        WebElement periodDropdown = driver.findElement(rentalPeriodField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", periodDropdown);
        periodDropdown.click();
        wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodOption));
        driver.findElements(rentalPeriodOption).stream()
                .filter(element -> element.getText().contains(rentalPeriod))
                .findFirst()
                .ifPresent(WebElement::click);

        // Выбор цвета
        if ("черный".equals(color) || "black".equalsIgnoreCase(color)) {
            WebElement checkbox = driver.findElement(colorBlackCheckbox);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        } else if ("серый".equals(color) || "grey".equalsIgnoreCase(color)) {
            WebElement checkbox = driver.findElement(colorGreyCheckbox);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }

        // Комментарий
        if (comment != null && !comment.isEmpty()) {
            driver.findElement(commentField).sendKeys(comment);
        }
    }

    // Нажатие кнопки "Заказать"
    public void clickOrderButton() {
        // Ищем кнопку "Заказать" среди всех кнопок с классом Button_Middle__1CSJM
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(orderButton));
        driver.findElements(orderButton).stream()
                .filter(button -> button.getText().equals("Заказать"))
                .findFirst()
                .ifPresent(button -> {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
                    button.click();
                });
    }

    // Подтверждение заказа
    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
        driver.findElement(confirmButton).click();
    }

    // Проверка успешного создания заказа
    public boolean isOrderSuccess() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage));
        return driver.findElement(orderSuccessMessage).isDisplayed();
    }

    public String getOrderSuccessMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage));
        return driver.findElement(orderSuccessMessage).getText();
    }

    // Проверка, что модальное окно подтверждения закрылось
    public boolean isConfirmModalClosed() {
        try {
            // Ждем, пока модальное окно с кнопкой "Да" станет невидимым
            wait.until(ExpectedConditions.invisibilityOfElementLocated(confirmButton));
            return true;
        } catch (Exception e) {
            // Если элемент не найден или невидим, значит окно закрыто
            try {
                return !driver.findElement(confirmButton).isDisplayed();
            } catch (Exception ex) {
                // Если элемент вообще не найден в DOM, значит окно закрыто
                return true;
            }
        }
    }
}
