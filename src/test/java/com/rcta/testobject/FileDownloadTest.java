package com.rcta.testobject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FileDownloadTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("testobject_api_key", "7F3422F7BE054B9FA5A9398C6341B76E");
        capabilities.setCapability("testobject_device", "Lenovo_TAB_2_A10_70F_real");
        capabilities.setCapability("testobject_app_id", 1);
        capabilities.setCapability("testobject_appium_version", "1.6.0");

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
        String testFile = new String(driver.pullFile("/sdcard/test.txt"), StandardCharsets.UTF_8);
        System.out.println("File contents: " + testFile);
    }
}
