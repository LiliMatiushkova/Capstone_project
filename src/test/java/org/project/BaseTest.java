package org.project;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.project.holder.PropertyHolder;
import org.project.pageobject.pages.HomePage;
import org.project.pageobject.pages.StartPage;

public class BaseTest {
    private final PropertyHolder prop;
    protected WebDriver driver;
    protected String username;
    protected String password;

    public BaseTest() {
        prop = new PropertyHolder();
        username = prop.readProperty("username");
        password = prop.readProperty("password");
    }

    protected void setUpDriverAndBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    public void closeDriverAndBrowser() {
        driver.close();
        driver.quit();
    }

    protected HomePage loginWithValidCredentials() {
        return new StartPage(driver)
                .openStartPage()
                .openLoginPage()
                .typeCredentials(username, password)
                .successLogin();
    }
}
