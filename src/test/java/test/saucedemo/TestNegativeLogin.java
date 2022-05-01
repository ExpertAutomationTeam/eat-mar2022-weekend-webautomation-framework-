package test.saucedemo;

import base.CommonAPI;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.saucedemo.LoginPage;

public class TestNegativeLogin extends CommonAPI{

    Faker faker = new Faker();

    String username = prop.getProperty("username");
    String password = prop.getProperty("password");
    String lockedUsername = prop.getProperty("locked.user");
    String invalidPassword = faker.internet().password(8, 10);

    @Test
    public void loginWithLockedUsername(){
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.enterUsername(lockedUsername);
        loginPage.enterPassword(password);
        loginPage.clickOnLoginBtn();
        Assert.assertEquals(loginPage.getErrorMsg(), "Epic sadface: Sorry, this user has been locked out.");
    }

    @Test
    public void loginWithInvalidPassword(){
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.enterUsername(username);
        loginPage.enterPassword(invalidPassword);
        loginPage.clickOnLoginBtn();
        Assert.assertEquals(loginPage.getErrorMsg(), "Epic sadface: Username and password do not match any user in this service");
    }
}
