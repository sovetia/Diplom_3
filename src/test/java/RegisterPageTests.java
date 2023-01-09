import api.UsersApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pom.LoginPage;
import pom.MainPage;
import pom.RegisterPage;

import java.util.concurrent.TimeUnit;

import static constants.Constants.BASE_URL;

@RunWith(Parameterized.class)
public class RegisterPageTests {
    private WebDriver driver;
    private String driverName;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private String name, email, password;
    private String token;

    private UsersApi usersApi = new UsersApi();

    public RegisterPageTests(String driverName) {
        this.driverName = driverName;
        name = RandomStringUtils.randomAlphanumeric(6);
        email = RandomStringUtils.randomAlphanumeric(6) + "@yandex.ru";
        password = RandomStringUtils.randomAlphanumeric(6);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Object[][] getData() {
        return new Object[][]{
                {"yandexdriver"},
                {"chromedriver"},
        };
    }

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/"+ driverName +".exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get(BASE_URL);

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
    }

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Минимальный пароль — шесть символов")
    public void positiveUserRegistration() throws InterruptedException {
        mainPage.clickLoginButton();

        loginPage.clickRegistrationButton();

        registerPage.register(name, email, password);

        loginPage.waitForLoadPage(3);

        loginPage.login(email, password);

        Assert.assertTrue(mainPage.isOrderButtonDisplayed());

        //get token api
        token = usersApi.loginUserResponse(new UsersApi(email, password, name)).extract().jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Ошибка для некорректного пароля")
    @Description("Минимальный пароль — шесть символов")
    public void negativeUserRegistration() {
        password = RandomStringUtils.randomAlphanumeric(5);

        mainPage.clickLoginButton();

        loginPage.clickRegistrationButton();

        registerPage.register(name, email, password);

        Assert.assertEquals("Некорректный пароль", registerPage.getErrorText());

        //get token api
        token = usersApi.loginUserResponse(new UsersApi(email, password, name)).extract().jsonPath().getString("accessToken");
    }

    @After
    public void tearDown() {
        driver.quit();
        if (token != null)
            usersApi.deleteUser(token);
    }
}
