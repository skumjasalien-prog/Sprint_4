package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.MainPage;
import pages.OrderPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {

    private MainPage mainPage;
    private OrderPage orderPage;

    // Параметры заказа
    private String orderButtonType; // "top" или "bottom"
    private String name;
    private String surname;
    private String address;
    private String metro;
    private String phone;
    private String date;
    private String rentalPeriod;
    private String color;
    private String comment;

    public OrderTest(String browserType, String orderButtonType, String name, String surname, String address, 
                     String metro, String phone, String date, String rentalPeriod, 
                     String color, String comment) {
        this.browserType = browserType;
        this.orderButtonType = orderButtonType;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        // Первый набор данных заказа
        Object[] orderData1 = {
                "Иван",
                "Иванов",
                "Москва, ул. Ленина, д. 1",
                "Сокольники",
                "+79991234567",
                "25",
                "сутки",
                "черный",
                "Позвоните за час до доставки"
        };

        // Второй набор данных заказа
        Object[] orderData2 = {
                "Мария",
                "Петрова",
                "Санкт-Петербург, Невский проспект, д. 10",
                "Черкизовская",
                "+79997654321",
                "30",
                "двое суток",
                "серый",
                "Осторожно, хрупкое"
        };

        String[] browsers = {"chrome", "firefox"};
        String[] orderButtons = {"top", "bottom"};

        List<Object[]> testData = new ArrayList<>();
        for (String browser : browsers) {
            for (String orderButton : orderButtons) {
                // Добавляем первый набор данных
                Object[] testCase1 = new Object[11];
                testCase1[0] = browser;
                testCase1[1] = orderButton;
                System.arraycopy(orderData1, 0, testCase1, 2, 9);
                testData.add(testCase1);

                // Добавляем второй набор данных
                Object[] testCase2 = new Object[11];
                testCase2[0] = browser;
                testCase2[1] = orderButton;
                System.arraycopy(orderData2, 0, testCase2, 2, 9);
                testData.add(testCase2);
            }
        }

        return testData;
    }

    @Before
    @Override
    public void setUp() {
        super.setUp();
        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);
    }

    @Test
    public void testCreateOrder() {
        // Клик на кнопку "Заказать" в зависимости от параметра
        if ("top".equals(orderButtonType)) {
            mainPage.clickOrderTopButton();
        } else {
            mainPage.clickOrderBottomButton();
        }

        // Заполнение первой части формы заказа
        orderPage.fillFirstPartOfOrder(name, surname, address, metro, phone);

        // Переход ко второй части формы
        orderPage.clickNextButton();

        // Заполнение второй части формы заказа
        orderPage.fillSecondPartOfOrder(date, rentalPeriod, color, comment);

        // Подтверждение заказа
        orderPage.clickOrderButton();
        orderPage.confirmOrder();
        
        // Проверка, что модальное окно подтверждения закрылось после нажатия кнопки
        Assert.assertTrue("Модальное окно подтверждения не закрылось после нажатия кнопки подтверждения заказа", 
                orderPage.isConfirmModalClosed());

        // Проверка успешного создания заказа
        Assert.assertTrue("Заказ не был создан успешно", orderPage.isOrderSuccess());
        String successMessage = orderPage.getOrderSuccessMessage();
        System.out.println("Браузер: " + browserType + ", Кнопка: " + orderButtonType + ", Имя: " + name + " " + surname + ", Сообщение: " + successMessage);
    }
}
