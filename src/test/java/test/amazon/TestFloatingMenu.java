package test.amazon;

import base.CommonAPI;

import org.testng.annotations.Test;
import pages.amazon.BasePage;

public class TestFloatingMenu extends CommonAPI{

    @Test
    public void test1(){
        BasePage basePage = new BasePage(getDriver());
        basePage.clickOnWatchListLink(getDriver());
    }

}
