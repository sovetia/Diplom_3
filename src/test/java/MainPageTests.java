import api.UsersApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
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
import pom.ProfilePage;

import java.util.concurrent.TimeUnit;

import static constants.Constants.*;

@RunWith(Parameterized.class)
public class MainPageTests {
    private WebDriver driver;
    private String driverName;
    private MainPage mainPage;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private UsersApi usersApi = new UsersApi();
    private String name, email, password;
    private String token;

    public MainPageTests(String driverName){
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

        //register random user api
        ValidatableResponse registerUserResponse = usersApi.registerUserResponse(new UsersApi(email, password, name));
        token = registerUserResponse.extract().jsonPath().getString("accessToken");

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);

        mainPage.clickLoginButton();
        loginPage.login(email, password);
    }

    @Test
    @DisplayName("Переход по клику на «Личный кабинет»")
    public void navigateToProfilePage() {
        mainPage.clickProfileButton();

        Assert.assertTrue(profilePage.isLogoutButtonDisplayed());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на «Конструктор»")
    public void navigateFromProfilePageToConstructorTab() {
        mainPage.clickProfileButton();
        mainPage.clickConstructorButton();

        Assert.assertTrue(mainPage.isOrderButtonDisplayed());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на логотипу Stellar Burgers")
    public void navigateFromProfilePageToLogo() {
        mainPage.clickProfileButton();
        mainPage.clickLogo();

        Assert.assertTrue(mainPage.isOrderButtonDisplayed());
    }

    @Test
    @DisplayName("Переход к разделу «Булки»")
    public void navigateConstructorBunsTab() throws InterruptedException {
        mainPage.clickSaucesTab();
        mainPage.clickBunsTab();

        String expected = "Булки";
        String actual = mainPage.checkTextSelectIngredient();
        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Переход к разделу «Соусы»")
    public void navigateConstructorSaucesTab() {
        mainPage.clickSaucesTab();

        String expected = "Соусы";
        String actual = mainPage.checkTextSelectIngredient();
        Assert.assertEquals(expected, actual);    }

    @Test
    @DisplayName("Переход к разделу «Начинки»")
    public void navigateConstructorFillingTab() {
        mainPage.clickFillingTab();

        String expected = "Начинки";
        String actual = mainPage.checkTextSelectIngredient();
        Assert.assertEquals(expected, actual);    }

    @After
    public void tearDown() {
        driver.quit();
        usersApi.deleteUser(token);
    }
}
