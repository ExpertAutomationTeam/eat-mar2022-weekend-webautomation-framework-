package pages.amazon;

import base.CommonAPI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BasePage extends CommonAPI{

    public BasePage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#twotabsearchtextbox")
    WebElement searchField;
    @FindBy(css = "#nav-search-submit-button")
    WebElement searchButton;
    @FindBy(css = ".nav-search-dropdown.searchSelect.nav-progressive-attrubute.nav-progressive-search-dropdown")
    WebElement menuDropdown;
    @FindBy(xpath = "//a[@id='nav-link-accountList']")
    WebElement floatingMenu;
    @FindBy(xpath = "//div[@id='nav-al-your-account']/a[5]/span")
    WebElement watchListLink;

    //reusable steps
    public void searchItem(String item){
        type(searchField, item);
        click(searchButton);
    }

    public void searchItemAndEnter(String item){
        typeAndEnter(searchField, item);
    }

    public void selectFromMenuDropdown(String option){
        selectFromDropdown(menuDropdown, option);
    }

    public void selectOptionFromMenuDropdownList(String option){
        selectOptionFromDropdownList(menuDropdown, option);
    }

    public void clearSearchField(){
        searchField.clear();
    }

    public void clickOnWatchListLink(WebDriver driver){
        hoverOver(driver, floatingMenu);
        click(watchListLink);
    }
}
