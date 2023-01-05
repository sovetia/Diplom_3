import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pom.*;

import java.util.concurrent.TimeUnit;

import static constants.Constants.*;

@RunWith(Parameterized.class)
public class LoginPageTests {

    private WebDriver driver;

    private String driverName;

    private MainPage mainPage;
    private LoginPage loginPage;

    private RegisterPage registerPage;

    private ForgotPasswordPage forgotPasswordPage;

    private ProfilePage profilePage;

    public LoginPageTests(String driverName){
        this.driverName = driverName;
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
        forgotPasswordPage = new ForgotPasswordPage(driver);
        profilePage = new ProfilePage(driver);
    }


    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void loginViaHomePage() {
        mainPage.clickLoginButton();
        loginPage.login(USER_EMAIL, USER_PASSWORD);

        Assert.assertTrue(mainPage.isOrderButtonDisplayed());
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void loginViaProfilePage() {
        mainPage.clickProfileButton();
        loginPage.login(USER_EMAIL, USER_PASSWORD);
        Assert.assertTrue(mainPage.isOrderButtonDisplayed());
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void loginViaRegistrationPage() {
        mainPage.clickLoginButton();
        loginPage.clickRegistrationButton();
        registerPage.clickLoginButton();
        loginPage.login(USER_EMAIL, USER_PASSWORD);

        Assert.assertTrue(mainPage.isOrderButtonDisplayed());
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void loginViaPasswordRecoveryPage() {
        mainPage.clickLoginButton();
        loginPage.clickPwRecoveryButton();
        forgotPasswordPage.clickLoginButton();
        loginPage.login(USER_EMAIL, USER_PASSWORD);

        Assert.assertTrue(mainPage.isOrderButtonDisplayed());
    }

    @Test
    @DisplayName("Выход по кнопке «Выйти» в личном кабинете»,")
    public void logoutViaProfilePage() {
        mainPage.clickProfileButton();
        loginPage.login(USER_EMAIL, USER_PASSWORD);
        mainPage.clickProfileButton();
        profilePage.clickLogoutButton();

        Assert.assertTrue(loginPage.isLoginButtonDisplayed());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
