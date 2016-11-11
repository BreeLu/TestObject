package com.rcta.testobject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.testng.Assert.assertEquals;
import org.testobject.appium.junit.TestObjectAppiumSuite;
import org.testobject.appium.junit.TestObjectTestResultWatcher;
import org.testobject.appium.testng.AppiumDriverProvider;
import org.testobject.rest.api.appium.common.TestObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@TestObject(testObjectApiKey = "A4CBB9174B6A4958BD94D1BA2E258EE4", testObjectSuiteId = 11)
@RunWith(TestObjectAppiumSuite.class)
public class DemoSuite implements AppiumDriverProvider {
    private static Logger logger = LoggerFactory.getLogger(DemoSuite.class);
    private AndroidDriver driver;
    private String number = "16506429019";
    private static String password = "Test!123";

    @Rule
    public TestName testName = new TestName();

    @Rule
    public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();


    @Before
    public void setUp() throws Exception {
        // set up appium
        /* These are the capabilities we must provide to run our test on TestObject */
        DesiredCapabilities capabilities = new DesiredCapabilities();
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        logger.info("Begin set up: Current Date: " + ft.format(new Date()));
        capabilities.setCapability("testobject_api_key", resultWatcher.getApiKey());
        capabilities.setCapability("testobject_test_report_id", resultWatcher.getTestReportId());

        /* The driver will take care of establishing the connection, so we must provide
        * it with the correct endpoint and the requested capabilities. */
        driver = new AndroidDriver(new URL("https://app.testobject.com:443/api/appium/wd/hub"), capabilities);
        resultWatcher.setAppiumDriver(driver);


//        File classpathRoot = new File(System.getProperty("user.dir"));
//        File appDir = new File(classpathRoot, "apps");
//        File app = new File(appDir, "RCMobile_8.1.0.1.2_UAT_Automation.apk");
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("app", app.getAbsolutePath());
//        capabilities.setCapability("platformName", "Android");
//        capabilities.setCapability("deviceName", "Android");
//        capabilities.setCapability("platformVersion", "5.0.1");
//        capabilities.setCapability("appPackage", "com.ringcentral.android");
//        capabilities.setCapability("appActivity", ".LoginScreen");
//        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Test
    public void testRC() throws Exception {
        Thread.sleep(15000);
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        logger.info("Begin test: Current Date: " + ft.format(new Date()));
        processFirstLogin();
        processLogOut();
    }

    private void processLogOut() throws Exception {
        logger.info("Wait 20s to download message download");
        Thread.sleep(15000);
        logger.info("Begin to execute logout,Click Myprofile");
        if(Utils.getElementByID(driver,"com.ringcentral.android:id/title_bar_photo")!=null){
            Utils.clickElementByID(driver, "com.ringcentral.android:id/title_bar_photo");
        }else {
            //HTC_One_XL_real can not click myprofile by id
            logger.info("--Click Myprofile by id can not click,then click by package");
            Utils.clickElement(driver,(MobileElement)driver.findElementsByClassName("android.widget.RelativeLayout").get(0));
        }

        driver.scrollTo("About");
//        Utils.pullWindowUp(driver);
        Utils.waitTimeAfterClick();
        logger.info("Click logout");
        Utils.clickElementByID(driver, "com.ringcentral.android:id/item_logout");
        assertTrue(Utils.getElementByName(driver, "Single Sign On") != null);
        logger.info("Logout successfully");
    }

    private void processFirstLogin() throws Exception {
        logger.info("Begin to login with number ");
        assertTrue("login screen pop up",Utils.getElementByName(driver,"Phone number (digits only)")!=null);
        Utils.getElementByName(driver, "Phone number (digits only)").sendKeys(number);
        Utils.takeScreenshot(driver);
        Utils.clickElement(driver, ((MobileElement) driver.findElementsByClassName("android.widget.EditText").get(2)));
        Utils.takeScreenshot(driver);
        logger.info("Begin to send password ");
        driver.getKeyboard().sendKeys(password);
        Utils.takeScreenshot(driver);

        logger.info("Try to click Login btn by name ");
        Utils.waitTimeAfterClick();
        if (Utils.getElementByName(driver, "Log In") != null) {
            Utils.clickElementByName(driver, "Log In");
        } else {
            logger.info("--Try to click Login btn by name failed,then by id ");
            Utils.clickElementByID(driver, "com.ringcentral.android:id/btnSignIn");
        }

        logger.info("It need some time to login");
        Utils.findElementWithTimeout(driver, By.name("Accept"), 180);

        Utils.takeScreenshot(driver);
        Thread.sleep(3000);
        assertTrue("Accept exists", Utils.getElementByName(driver, "Accept") != null);
        logger.info("Click First Accept ");
        Utils.clickElementByName(driver, "Accept");
        logger.info("Click Second Accept ");
        Thread.sleep(10000);
        Utils.takeScreenshot(driver);
        Utils.clickElementByName(driver, "Accept");
        Utils.waitTimeAfterClick();
        Thread.sleep(10000);

        Utils.takeScreenshot(driver);
        logger.info("Click skip btn");
        if (Utils.getElementByID(driver, "com.ringcentral.android:id/whats_new_button_left") != null) {
            logger.info("Click skip btn by id");
            Utils.clickElementByID(driver, "com.ringcentral.android:id/whats_new_button_left");
        } else if (Utils.getElementByName(driver, "Skip") != null) {
            logger.info("Click skip btn by name");
            Utils.clickElementByName(driver, "Skip");
        } else {
            throw new Exception("Could not click skip btn");
        }
//        assertTrue("Skip btn exists",Utils.getElementByID(driver,"com.ringcentral.android:id/whats_new_button_left")!=null);
//        Utils.clickElementByID(driver, "com.ringcentral.android:id/whats_new_button_left");

        logger.info("Try to find if exists OK button");
        if (Utils.getElementByName(driver, "OK") != null) {
            logger.info("**The device 's version >= 6.0,Click OK");
            Utils.clickElementByName(driver, "OK");
            logger.info("Click Allow RignCentral to make and manage phone calls?");
            Utils.clickElementByName(driver, "Allow");
            logger.info("Click Allow RignCentral to record audio?");
            Utils.clickElementByName(driver, "Allow");
            logger.info("Click Allow RignCentral to access photos,media,and files on your device?");
            Utils.clickElementByName(driver, "Allow");
            logger.info("Click Allow RignCentral to access your contacts?");
            Utils.clickElementByName(driver, "Allow");
        }

        if (Utils.getElementByName(driver, "Save") != null) {
            logger.info("Clear device number ");
            Utils.getElementByID(driver, "com.ringcentral.android:id/username_edit").clear();

            logger.info("Send device number ");
//            driver.getKeyboard().sendKeys("12345678901");
            Utils.getElementByID(driver, "com.ringcentral.android:id/username_edit").sendKeys("12345678901");
            Utils.waitTimeAfterClick();

            logger.info("Try to find if exists OK button");
            if (Utils.getElementByName(driver, "OK") != null) {
                logger.info("--Exists the pop up dialog");
                Utils.clickElementByName(driver, "OK");
            }
//        Utils.getElementByID(driver, "com.ringcentral.android:id/username_edit").sendKeys("12345678901");
            driver.hideKeyboard();
            Utils.waitTimeAfterClick();
            logger.info("Click Save btn ");
            Utils.clickElementByName(driver, "Save");
        } else {
            logger.info("Some device need not input device number");
        }

        if (Utils.getElementByName(driver, "All Messages") == null) {
            logger.info("Try to find if exists OK button");
            if (Utils.getElementByName(driver, "OK") != null) {
                logger.info("It pop up the important Information alert");
                Utils.clickElementByName(driver, "OK");
                logger.info("Click OK successfully");
            }
            if (Utils.getElementByName(driver, "OK") != null) {
                logger.info("It pop up the Ignore optimizations alert");
                Utils.clickElementByName(driver, "OK");
                logger.info("Click OK successfully");
            }
            if (Utils.getElementByName(driver, "YES") != null) {
                logger.info("It pop up the Ignore battery optimization alert,YES");
                Utils.clickElementByName(driver, "YES");
                logger.info("Click YES successfully");
            }
            if (Utils.getElementByName(driver, "Yes") != null) {
                logger.info("It pop up the Ignore battery optimization alert,Yes");
                Utils.clickElementByName(driver, "Yes");
                logger.info("Click Yes successfully");
            }
        }
        String title = Utils.getElementByName(driver, "All Messages").getText();
        Utils.takeScreenshot(driver);
        assertEquals(title, "All Messages");
    }


    @Override
    public AppiumDriver getAppiumDriver() {
        return this.driver;
    }
}
