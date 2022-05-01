package base;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reporting.ExtentManager;
import reporting.ExtentTestManager;
import utility.Utilities;

public class CommonAPI {

    public WebDriver driver;

    public Properties prop = Utilities.loadProperties();

    String browserstackUsername = prop.getProperty("browserstack.username");
    String browserstackPassword = prop.getProperty("broswerstack.password");
    String takeScreenshot = prop.getProperty("take.screenshot", "false");
    String implecitWait = prop.getProperty("implicit.wait", "5");
    String maximizeBrowser = prop.getProperty("maximize.browser", "false");

    public static com.relevantcodes.extentreports.ExtentReports extent;

    @BeforeSuite
    public void extentSetup(ITestContext context) {
        ExtentManager.setOutputDirectory(context);
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void startExtent(Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName().toLowerCase();
        ExtentTestManager.startTest(method.getName());
        ExtentTestManager.getTest().assignCategory(className);
    }
    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    @AfterMethod
    public void afterEachTestMethod(ITestResult result) {
        ExtentTestManager.getTest().getTest().setStartedTime(getTime(result.getStartMillis()));
        ExtentTestManager.getTest().getTest().setEndedTime(getTime(result.getEndMillis()));

        for (String group : result.getMethod().getGroups()) {
            ExtentTestManager.getTest().assignCategory(group);
        }

        if (result.getStatus() == 1) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");
        } else if (result.getStatus() == 2) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, getStackTrace(result.getThrowable()));
        } else if (result.getStatus() == 3) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
        }
        ExtentTestManager.endTest();
        extent.flush();
        if (takeScreenshot.equalsIgnoreCase("true")){
            if (result.getStatus() == ITestResult.FAILURE) {
                takeScreenshot(result.getName());
            }
        }
        driver.quit();
    }

    @AfterSuite
    public void generateReport() {
        extent.close();
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    private void getLocalDriver(String browserName, String os){
        if (browserName.equalsIgnoreCase("chrome")){
            if (os.equalsIgnoreCase("OS X")){
                System.setProperty("webdriver.chrome.driver", Utilities.root+"/src/driver/chromedriver");
                driver = new ChromeDriver();
            }else if (os.equalsIgnoreCase("windows")){
                System.setProperty("webdriver.chrome.driver", Utilities.root+"/src/driver/chromedriver.exe");
                driver = new ChromeDriver();
            }

        }else if (browserName.equalsIgnoreCase("firefox")){
            if (os.equalsIgnoreCase("OS X")){
                System.setProperty("webdriver.gecko.driver", Utilities.root+"/src/driver/geckodriver");
                driver = new FirefoxDriver();
            }else if (os.equalsIgnoreCase("windows")){
                System.setProperty("webdriver.gecko.driver", Utilities.root+"/src/driver/geckodriver.exe");
                driver = new FirefoxDriver();
            }

        }
    }

    private void getCloudDriver(String envName, String envUsername, String envAccessKey, String browserName, String browserVersion, String os, String osVersion) throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("browser", browserName);
        cap.setCapability("browser_version", browserVersion);
        cap.setCapability("os", os);
        cap.setCapability("os_version", osVersion);
        if (envName.equalsIgnoreCase("saucelabs")){
            driver = new RemoteWebDriver(new URL("http://"+ envUsername + ":" + envAccessKey + "@ondemand.saucelabs.com:80/wd/hub"), cap);
        }else if (envName.equalsIgnoreCase("browserstack")){
            cap.setCapability("resolution", "1024x768");
            driver = new RemoteWebDriver(new URL("http://"+ envUsername + ":" + envAccessKey + "@hub-cloud.browserstack.com:80/wd/hub"), cap);
        }
    }

    @Parameters({"useCloudEnv","envName","browserName","browserVersion","os","osVersion","url"})
    @BeforeMethod
    public void beforeTest(boolean useCloudEnv, String envName, String browserName, String browserVersion, String os, String osVersion, String url) throws MalformedURLException {
        if (useCloudEnv){
            if (envName.equalsIgnoreCase("browserstack")){
                getCloudDriver(envName, browserstackUsername, browserstackPassword, browserName, browserVersion, os, osVersion);
            }else if (envName.equalsIgnoreCase("saucelabs")){
                getCloudDriver(envName, "", "", browserName, browserVersion, os, osVersion);
            }
        }else {
            getLocalDriver(browserName, os);
        }

        driver.manage().timeouts().implicitlyWait(Integer.parseInt(implecitWait), TimeUnit.SECONDS);
        if (maximizeBrowser.equalsIgnoreCase("true")){
            driver.manage().window().maximize();
        }

        driver.get(url);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getElementText(WebElement element){
        return element.getText();
    }

    public void click(WebElement element){
        element.click();
    }

    public void clear(WebElement element){
        element.clear();
    }

    public void type(WebElement element, String text){
        element.sendKeys(text);
    }

    public void typeAndEnter(WebElement element, String text){
        element.sendKeys(text, Keys.ENTER);
    }

    public void selectFromDropdown(WebElement dropdown, String option){
        Select select = new Select(dropdown);
        try {
            select.selectByVisibleText(option);
        }catch (Exception e){
            select.selectByValue(option);
        }

    }

    public void selectOptionFromDropdownList(WebElement dropdown, String option){
        Select select = new Select(dropdown);
        List<WebElement> elements = select.getOptions();
        for (WebElement element: elements) {
            if (element.getText().equalsIgnoreCase(option)){
                element.click();
            }
        }
    }

    public void hoverOver(WebDriver driver, WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
    }

    public void captureScreenshot() {
        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file,new File("screenshots/screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void takeScreenshot(String screenshotName){
        DateFormat df = new SimpleDateFormat("(MM.dd.yyyy-HH:mma)");
        Date date = new Date();
        df.format(date);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(Utilities.root+File.separator+ "screenshots"+File.separator+screenshotName+" "+df.format(date)+".png"));
            System.out.println("Screenshot captured");
        } catch (Exception e) {
            String path = Utilities.root+ "/screenshots/"+screenshotName+" "+df.format(date)+".png";
            System.out.println(path);
            System.out.println("Exception while taking screenshot "+e.getMessage());;
        }
    }
}
