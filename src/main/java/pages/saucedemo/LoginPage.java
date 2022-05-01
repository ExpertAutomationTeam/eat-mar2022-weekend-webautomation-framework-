package pages.saucedemo;

import base.CommonAPI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends CommonAPI{

    //constructor that initialize elements
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    //find elements
    @FindBy(css = "#user-name")
    WebElement usernameField;
    @FindBy(css = "#password")
    WebElement passwordField;
    @FindBy(css = "#login-button")
    WebElement loginBtn;
    @FindBy(css = "h3[data-test='error']")
    WebElement errorMsg;

    //reusable steps
    public void enterUsername(String username){
        type(usernameField, username);
    }
    public void enterPassword(String password){
        type(passwordField, password);
    }
    public void clickOnLoginBtn(){
        click(loginBtn);
    }
    public String getErrorMsg(){
        return errorMsg.getText();
    }
}
