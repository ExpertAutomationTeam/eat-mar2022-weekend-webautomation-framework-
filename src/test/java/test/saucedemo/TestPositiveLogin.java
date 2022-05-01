package test.saucedemo;

import base.CommonAPI;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.saucedemo.HomePage;
import pages.saucedemo.LoginPage;

public class TestPositiveLogin extends CommonAPI{

    String username = prop.getProperty("username");
    String password = prop.getProperty("password");

    @Test
    public void loginWithValidCred(){
        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homePage = new HomePage(getDriver());
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickOnLoginBtn();
        Assert.assertEquals(homePage.getProductsHeaderText(), "PRODUCTS");
    }
}
