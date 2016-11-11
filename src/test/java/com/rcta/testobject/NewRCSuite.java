package com.rcta.testobject;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testobject.appium.junit.TestObjectAppiumSuite;
import org.testobject.appium.junit.TestObjectTestResultWatcher;
import org.testobject.rest.api.appium.common.TestObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


@TestObject(testObjectApiKey = "7F3422F7BE054B9FA5A9398C6341B76E", testObjectSuiteId = 7)
@RunWith(TestObjectAppiumSuite.class)
public class NewRCSuite {
    private AndroidDriver driver;
    private String number = "8552700002";
    private static String password = "Test!123";
    private static Logger logger = LoggerFactory.getLogger(DemoSuite.class);

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
        System.out.println("Begin set up: Current Date: " + ft.format(new Date()));
        capabilities.setCapability("testobject_api_key", "A3955736738C4BA6A95CA09E472486B9");
        capabilities.setCapability("testobject_device", "LG_Nexus_5X_real");
        capabilities.setCapability("appWaitActivity", "com.ringcentral.android.LoginScreen");
        capabilities.setCapability("appWaitPackage", "com.ringcentral.android");
        capabilities.setCapability("appActivity", ".LoginScreen");
        capabilities.setCapability("appPackage", "com.ringcentral.android");

        driver = new AndroidDriver(new URL("https://app.testobject.com:443/api/appium/wd/hub"), capabilities);
        resultWatcher.setAppiumDriver(driver);
    }

    @Test
    public void testNewRCSuite() throws Exception {
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        logger.info("Begin test: Current Date: " + ft.format(new Date()));
        processFirstLogin();
//        processLogOut();
    }

    private void processLogOut() throws Exception {
        logger.info("Begin to execute logout,Click Myprofile");
        Utils.clickElementByID(driver, "com.ringcentral.android:id/title_bar_photo");
        driver.scrollTo("About");
        Utils.waitTimeAfterClick();
        logger.info("Click logout");
        Utils.clickElementByID(driver, "com.ringcentral.android:id/item_logout");
        assertTrue(Utils.getElementByID(driver, "com.ringcentral.android:id/sign_in") != null);
        logger.info("Logout successfully");
    }

    private void processFirstLogin() throws Exception {
        logger.info("Begin to Click Sign In ");
        Utils.clickElementByID(driver, "com.ringcentral.android:id/sign_in");
        logger.info("Begin to Click Phone ");
        Utils.clickElementByID(driver, "com.ringcentral.android:id/loginWithEmail");
        logger.info("Try to switch US ");
        Utils.clickElementByID(driver, "com.ringcentral.android:id/login_left_view");
        logger.info("Choose US ");
        Utils.clickElementByName(driver,"United States (+1)");
        logger.info("Begin to login with number ");
        Utils.getElementByName(driver, "Phone number (digits only)").sendKeys(number);
        Utils.takeScreenshot(driver);
        Utils.clickElement(driver, ((MobileElement) driver.findElementsByClassName("android.widget.EditText").get(2)));
        logger.info("Begin to send password ");
        driver.getKeyboard().sendKeys(password);
        Utils.takeScreenshot(driver);

        logger.info("Try to click Sign In btn by ID ");
        Utils.waitTimeAfterClick();
        Utils.clickElementByID(driver, "com.ringcentral.android:id/btnSignIn");
        logger.info("It need some time to login,then click Accept");
        Utils.clickElementByID(driver,"com.ringcentral.android:id/btnAccept");
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
            Utils.getElementByID(driver,"com.ringcentral.android:id/username_edit").clear();

            logger.info("Send device number ");
            Utils.getElementByID(driver,"com.ringcentral.android:id/username_edit").sendKeys("12345678901");
            Utils.waitTimeAfterClick();

            logger.info("Try to find if exists OK button");
            if (Utils.getElementByName(driver, "OK") != null) {
                logger.info("--Exists the pop up dialog");
                Utils.clickElementByName(driver, "OK");
            }
            driver.hideKeyboard();
            Utils.waitTimeAfterClick();
            logger.info("Click Save btn ");
            Utils.clickElementByName(driver, "Save");
        } else if(Utils.getElementByID(driver,"com.ringcentral.android:id/mobile_phone_number")!=null){
            logger.info("Some account navigate to Account SetUp screen ");
            Utils.getElementByID(driver,"com.ringcentral.android:id/mobile_phone_number").sendKeys("12345678901");
            logger.info("Click Next Btn ");
            Utils.getElementByID(driver,"com.ringcentral.android:id/btnTopRight").click();
            logger.info("Click Done Btn ");
            Utils.getElementByName(driver, "Done").click();
        } else {
            logger.info("Some device need not input device number");
        }

    }

    @After
    public void tearDown(){
        driver.quit();
    }

}

