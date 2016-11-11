package com.rcta.testobject;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NewRC {
    private AndroidDriver driver;

    private String number = "8552700002";

    private static String password = "Test!123";

    private static Logger logger = LoggerFactory.getLogger(DemoSuite.class);

    @Before
    public void setUp() throws Exception {
        // set up appium
        /* These are the capabilities we must provide to run our test on TestObject */
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
//        System.out.println("Begin set up: Current Date: " + ft.format(new Date()));
//        capabilities.setCapability("testobject_api_key", "7F3422F7BE054B9FA5A9398C6341B76E");
//        capabilities.setCapability("testobject_device", "HTC_One_real");
//        capabilities.setCapability("testobject_appium_version", "1.5.2");
//        capabilities.setCapability("appWaitActivity", "com.ringcentral.android.LoginScreen");
//        capabilities.setCapability("appWaitPackage", "com.ringcentral.android");
//        capabilities.setCapability("appActivity", ".LoginScreen");
//        capabilities.setCapability("appPackage", "com.ringcentral.android");

//        driver = new AndroidDriver(new URL("https://app.testobject.com:443/api/appium/wd/hub"), capabilities);
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "apps");
        File app = new File(appDir, "RCMobile.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("appPackage", "com.ringcentral.android");
        capabilities.setCapability("appActivity", ".LoginScreen");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Test
    public void testRC() throws Exception {
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
        logger.info("Choose US by XPath ");
        driver.findElementByXPath("//*[@text='United States (+1)']").click();
        logger.info("Begin to login with number ");
        Utils.getElementByID(driver, "com.ringcentral.android:id/phone").sendKeys(number);
        Utils.takeScreenshot(driver);
        Utils.clickElement(driver, ((MobileElement) driver.findElementsByClassName("android.widget.EditText").get(2)));
        logger.info("Begin to send password ");
        driver.getKeyboard().sendKeys(password);
        Utils.takeScreenshot(driver);

        logger.info("Try to click Sign In btn by ID ");
        Utils.waitTimeAfterClick();
        Utils.clickElementByID(driver, "com.ringcentral.android:id/btnSignIn");
        logger.info("It need some time to login,then click Accept");
        Utils.clickElementByID(driver, "com.ringcentral.android:id/btnAccept");
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
        if (Utils.getElementByID(driver, "com.ringcentral.android:id/permission_ok_button") != null) {
            logger.info("**The device 's version >= 6.0,Click OK");
            Utils.clickElementByID(driver, "com.ringcentral.android:id/permission_ok_button");
            logger.info("Click Allow RignCentral to make and manage phone calls?");
            Utils.clickElementByID(driver, "com.android.packageinstaller:id/permission_allow_button");
            logger.info("Click Allow RignCentral to record audio?");
            Utils.clickElementByID(driver, "com.android.packageinstaller:id/permission_allow_button");
            logger.info("Click Allow RignCentral to access photos,media,and files on your device?");
            Utils.clickElementByID(driver, "com.android.packageinstaller:id/permission_allow_button");
            logger.info("Click Allow RignCentral to access your contacts?");
            Utils.clickElementByID(driver, "com.android.packageinstaller:id/permission_allow_button");
        }
        if (Utils.getElementByID(driver, "com.ringcentral.android:id/mobile_phone_number") != null) {
            logger.info("Some account navigate to Account SetUp screen ");
            Utils.getElementByID(driver, "com.ringcentral.android:id/mobile_phone_number").sendKeys("12345678901");
            logger.info("Click Next Btn ");
            Utils.getElementByID(driver, "com.ringcentral.android:id/btnTopRight").click();
            logger.info("Click Done Btn ");
            Utils.clickElementByID(driver, "com.ringcentral.android:id/btnTopRight");
        } else {
            logger.info("Some device need not input device number");
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}

