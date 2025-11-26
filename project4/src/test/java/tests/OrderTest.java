package tests;

import org.junit.Before;

public class OrderTest extends BaseTest {

    @Before
    @Override
    public void setUp() {
        browserType = "chrome"; // По умолчанию Chrome, можно изменить
        super.setUp();
    }
}
