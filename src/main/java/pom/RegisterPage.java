package pom;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {

    private WebDriver driver;
    //Поле "Имя"
    private By nameField = By.xpath("//label[text() = 'Имя']//following-sibling::input");
    //Поле "Email"
    private By emailField = By.xpath("//label[text() = 'Email']//following-sibling::input");
    //Поле "Пароль"
    private By passwordField = By.xpath("//label[text() = 'Пароль']//following-sibling::input");
    //Кнопка "Зарегистрироваться"
    private By registrationButton = By.xpath("//*[text()='Зарегистрироваться']");
    //Кнопка "Войти"
    private By loginButton = By.xpath("//*[text()='Войти']");
    //Область с текстом ошибки
    private By errorLocator = By.xpath("//p[@class = 'input__error text_type_main-default']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Get error text")
    public String getErrorText() {
        try {
            return driver.findElement(errorLocator).getText();
        }
        catch (Exception e) {
            return null;
        }
    }

    @Step("User registration")
    public void register(String name, String email, String password) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        clickRegistrationButton();
    }

    @Step("Click registration button")
    public void clickRegistrationButton() {
        driver.findElement(registrationButton).click();
    }

    @Step("Click login button")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }
}
