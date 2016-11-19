package com.rcta.testobject;

import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FileDownloadTest {

//    private AppiumDriver driver;
    private AndroidDriver driver;
    private String number = "8552700002";
    private static String password = "Test!123";
    private static Logger logger = LoggerFactory.getLogger(NewRCSuite.class);


    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("testobject_api_key", "7F3422F7BE054B9FA5A9398C6341B76E");
        capabilities.setCapability("testobject_device", "Lenovo_TAB_2_A10_70F_real");
        capabilities.setCapability("testobject_app_id", 1);
        capabilities.setCapability("testobject_appium_version", "1.6.0");
        capabilities.setCapability("appWaitActivity", "com.ringcentral.android.LoginScreen");
        capabilities.setCapability("appWaitPackage", "com.ringcentral.android");
        capabilities.setCapability("appActivity", ".LoginScreen");
        capabilities.setCapability("appPackage", "com.ringcentral.android");

        driver = new AndroidDriver(new URL("http://appium.testobject.com/wd/hub"), capabilities);

        System.out.println(driver.getCapabilities().getCapability("testobject_test_report_url"));
        System.out.println(driver.getCapabilities().getCapability("testobject_test_live_view_url"));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void getPageSourceTest() throws Exception {
        String path = "/sdcard/rc_test.txt";
        driver.pushFile(path, "test retrieve file function".getBytes());
        processFirstLogin();
        System.out.println("++++++++++++++++++++++++++++++++++");
        String testFile = new String(driver.pullFile("/sdcard/rc_test.txt"), StandardCharsets.UTF_8);
        System.out.println("File contents: " + testFile);
    }

    private void processFirstLogin() throws Exception {
        logger.info("Begin to Click Sign In ");
        Utils.clickElementByID(driver, "com.ringcentral.android:id/sign_in");
        logger.info("Begin to Click Phone ");
        Utils.clickElementByID(driver, "com.ringcentral.android:id/loginWithEmail");
        logger.info("Try to switch US ");
        Utils.clickElementByID(driver, "com.ringcentral.android:id/login_left_view");
    }
}
