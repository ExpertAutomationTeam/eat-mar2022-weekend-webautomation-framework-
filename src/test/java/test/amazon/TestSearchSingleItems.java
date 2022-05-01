package test.amazon;

import base.CommonAPI;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.amazon.BasePage;
import pages.amazon.JavaBookPage;
import utility.DataReader;
import utility.Utilities;

public class TestSearchSingleItems extends CommonAPI{

    //@Test
    public void test1(){
        BasePage basePage = new BasePage(getDriver());
        JavaBookPage javaBookPage = new JavaBookPage(getDriver());
        basePage.searchItem("java book");

        String expectedResultsFor = "java book";
        System.out.println("expected results for : "+expectedResultsFor);
        String actualResultsFor = javaBookPage.getSearchForElementText();
        System.out.println("actual results for : "+actualResultsFor);
        Assert.assertEquals(expectedResultsFor, actualResultsFor);
    }

    //@Test
    public void test2(){
        BasePage basePage = new BasePage(getDriver());
        basePage.searchItem("selenium book");
    }

    //@Test
    public void test3(){
        BasePage basePage = new BasePage(getDriver());
        basePage.searchItemAndEnter("toys");
    }

    //@Test
    public void test4(){
        BasePage basePage = new BasePage(getDriver());
        basePage.selectFromMenuDropdown("Alexa Skills");
        captureScreenshot();
        basePage.searchItemAndEnter("play music");
    }

    //@Test
    public void test5(){
        BasePage basePage = new BasePage(getDriver());
        basePage.selectOptionFromMenuDropdownList("alexa skills");
    }

    @Test
    public void test6(){
        DataReader dataReader = new DataReader(Utilities.root+"/data/my_data.xlsx");
        BasePage basePage = new BasePage(getDriver());
        basePage.searchItem(dataReader.getValueForGivenHeaderAndKey("Sheet1", "id", "id005"));
    }

}
