package pages.amazon;

import base.CommonAPI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class JavaBookPage extends CommonAPI{

    public JavaBookPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "a-color-state")
    WebElement searchForText;

    public String getSearchForElementText(){
        return searchForText.getText();
    }
}
