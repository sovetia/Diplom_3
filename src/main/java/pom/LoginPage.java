package pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    //Поле "Email"
    private By emailField = By.xpath("//input[@type='text' and @name='name']");
    //Поле "Пароль"
    private By passwordField = By.xpath("//input[@type='password' and @name='Пароль']");
    //Кнопка "Войти"
    private By loginButton = By.xpath("//*[text() = 'Войти']");
    //Кнопка "Зарегистрироваться"
    private By registrationButton = By.xpath("//*[text() = 'Зарегистрироваться']");
    //Кнопка "Восстановить пароль"
    private By forgotPasswordButton = By.xpath("//*[text() = 'Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Wait for load Login Page")
    public void waitForLoadPage(int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/login"));
    }

    @Step("Login")
    public void login(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    @Step("Check login button is displayed")
    public boolean isLoginButtonDisplayed() {
        return driver.findElement(loginButton).isDisplayed();
    }

    @Step("Click registration button")
    public void clickRegistrationButton() {
        driver.findElement(registrationButton).click();
    }

    @Step("Click password recovery button")
    public void clickPwRecoveryButton() {
        driver.findElement(registrationButton).click();
    }
}
