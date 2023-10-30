package org.project;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.project.holder.PropertyHolder;
import org.project.pageobject.pages.HomePage;
import org.project.pageobject.pages.StartPage;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class BaseUITest {
    public WebDriver driver;
    PropertyHolder prop = new PropertyHolder();
    String username = prop.readProperty("username");
    String password = prop.readProperty("password");

    public void setUp() {
        WebDriverManager.firefoxdriver().setup();

        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }
    public void close() {
        driver.close();
        driver.quit();
    }
    public void login() {
        StartPage startPage = new StartPage(driver);
        startPage
                .openLoginPage()
                .typeCredentials(username, password)
                .successLogin();
    }
    public void createNewPlaylist() {
        HomePage homePage = new HomePage(driver);
        homePage
                .createNewPlaylist("Create a new playlist");
    }
    public void addSongToPlaylist(String author, String trackName, String playlistName) {
        HomePage homePage = new HomePage(driver);
        homePage
                .searchForSong(author)
                .filterForSongs()
                .addSongToPlaylist(trackName, playlistName)
                .openPlaylistWithAddedSong();
    }
    public void deletePlaylist() {
        HomePage homePage = new HomePage(driver);
        homePage
                .selectDeleteOptionFromContextMenu("Delete")
                .clickDeleteInConfirmationPopup();
    }
}
