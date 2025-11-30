package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.MainPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class FaqTest extends BaseTest {

    private MainPage mainPage;

    private int faqIndex;
    private String expectedAnswerText;

    public FaqTest(int faqIndex, String expectedAnswerText, String browserType) {
        this.faqIndex = faqIndex;
        this.expectedAnswerText = expectedAnswerText;
        this.browserType = browserType;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] faqData = {
                {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        };
        
        String[] browsers = {"chrome", "firefox"};
        
        List<Object[]> testData = new ArrayList<>();
        for (Object[] faq : faqData) {
            for (String browser : browsers) {
                testData.add(new Object[]{faq[0], faq[1], browser});
            }
        }
        
        return testData;
    }

    @Before
    @Override
    public void setUp() {
        super.setUp();
        mainPage = new MainPage(driver);
    }

    @Test
    public void faqDropdownTest() {
        mainPage.clickFaqArrow(faqIndex);
        String actualAnswerText = mainPage.getFaqAnswerText(faqIndex);
        System.out.println("Браузер: " + browserType + ", Ответ FAQ " + faqIndex + ": " + actualAnswerText);
        // Проверка, что текст ответа соответствует ожидаемому
        Assert.assertEquals("Текст ответа FAQ не соответствует ожидаемому (браузер: " + browserType + ")", expectedAnswerText, actualAnswerText);
    }

}
