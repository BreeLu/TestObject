package com.rcta.testobject;

import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GlipOneDevice {
    private AndroidDriver driver;
    private String email = "william.ye@gliprc.com";
    private static String password = "Test123!";
    private static Logger logger = LoggerFactory.getLogger(GlipOneDevice.class);

    @Before
    public void setUp() throws Exception {
        // set up appium
        /* These are the capabilities we must provide to run our test on TestObject */
        DesiredCapabilities capabilities = new DesiredCapabilities();
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        System.out.println("Begin set up: Current Date: " + ft.format(new Date()));
        capabilities.setCapability("testobject_api_key", "581C36AA6E254775906C1B1AE917C203");
        capabilities.setCapability("testobject_device", "Asus_Google_Nexus_7_2013_real");
        capabilities.setCapability("testobject_appium_version", "1.5.2");

        driver = new AndroidDriver(new URL("https://app.testobject.com:443/api/appium/wd/hub"), capabilities);

    }

    @Test
    public void testGlip() throws Exception {
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        logger.info("Begin test: Current Date: " + ft.format(new Date()));

        logger.info("Click Sign In");
        Utils.clickElementByID(driver, "com.glip.mobile:id/sign_in_button");

        logger.info("Input email");
        Utils.getElementByID(driver,"com.glip.mobile:id/common_email_input_layout").sendKeys(email);
        logger.info("Input password");
        Utils.getElementByID(driver,"com.glip.mobile:id/common_password_edit").sendKeys(password);
        logger.info("Click Sign In");
        Utils.clickElementByID(driver,"com.glip.mobile:id/sign_form_post_button");

        Assert.assertTrue(Utils.getElementByID(driver, "com.glip.mobile:id/spinner_item_text_view")!=null);
    }

    @After
    public void tearDown(){
        driver.quit();
    }
}
