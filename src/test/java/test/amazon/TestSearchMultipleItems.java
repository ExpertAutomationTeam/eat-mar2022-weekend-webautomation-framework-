package test.amazon;

import base.CommonAPI;
import org.testng.annotations.Test;
import pages.amazon.BasePage;
import utility.ConnectDB;

import java.util.ArrayList;
import java.util.List;

public class TestSearchMultipleItems extends CommonAPI{

    public List<String> listOfItems(){
        List<String> list = new ArrayList<String>();
        list.add("ps5");
        list.add("iPhone");
        list.add("laptop");
        list.add("mouse");
        list.add("keyboard");
        return list;
    }

    //@Test
    public void searchMultipleItems(){
        BasePage basePage = new BasePage(getDriver());
        for (String item: listOfItems()) {
            basePage.searchItemAndEnter(item);
            basePage.clearSearchField();
        }
    }

    @Test
    public void searchMultipleItemsUsingDB() throws Exception {
        ConnectDB connectDB = new ConnectDB();
        List<String> items = connectDB.directDatabaseQueryExecute("select * from items", "items");
        BasePage basePage = new BasePage(getDriver());
        for (String item: items) {
            basePage.searchItemAndEnter(item);
            basePage.clearSearchField();
        }
    }
}
