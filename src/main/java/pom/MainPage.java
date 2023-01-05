package pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class MainPage {
    private final WebDriver driver;

    //Кнопка "Войти в аккаунт"
    private By loginButton = By.xpath("//*[text()='Войти в аккаунт']");
    //Кнопка "Личный Кабинет"
    private By profileButton = By.xpath("//*[text()='Личный Кабинет']");
    //Кнопка "Оформить заказ"
    private By orderButton = By.xpath("//*[text()='Оформить заказ']");
    //Кнопка лого "Stellar Burgers"
    private By logoButton = By.xpath("//*[@class = 'AppHeader_header__logo__2D0X2']");
    //Кнопка "Конструктор"
    private By constructorButton = By.xpath("//*[text()='Конструктор']");
    //Заголовок "Булки"
    private By headingBuns = By.xpath("//h2[text() = 'Булки']");
    //Заголовок "Соусы"
    private By headingSauces = By.xpath("//h2[text() = 'Соусы']");
    //Заголовок "Начинки"
    private By headingFilling = By.xpath("//h2[text() = 'Булки']	");
    //Вкладка "Булки"
    private By tabBuns = By.xpath("//span[text() = 'Булки']");
    //Вкладка "Соусы"
    private By tabSauces = By.xpath("//span[text() = 'Соусы']");
    //Вкладка "Начинки"
    private By tabFilling = By.xpath("//span[text() = 'Начинки']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Click logo button")
    public void clickLogo() {
        driver.findElement(logoButton).click();
    }

    @Step("Click login button")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Click profile button")
    public void clickProfileButton() {
        driver.findElement(profileButton).click();
    }

    @Step("Click constructor button")
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    @Step("Check Buns heading is displayed")
    public boolean isBunsDisplayed() {
        return driver.findElement(headingBuns).isDisplayed();
    }

    @Step("Check Sauces heading is displayed")
    public boolean isSaucesDisplayed() {
        return driver.findElement(headingSauces).isDisplayed();
    }

    @Step("Check Filling heading is displayed")
    public boolean isFillingDisplayed() {
        return driver.findElement(headingFilling).isDisplayed();
    }

    @Step("Click Buns tab")
    public void clickBunsTab() {
        driver.findElement(tabBuns).click();
    }

    @Step("Click Sauces tab")
    public void clickSaucesTab() {
        driver.findElement(tabSauces).click();
    }

    @Step("Check if order button is displayed")
    public boolean isOrderButtonDisplayed() {
        return driver.findElement(orderButton).isDisplayed();
    }

    @Step("Click Filling tab")
    public void clickFillingTab() {
        driver.findElement(tabFilling).click();
    }
}
