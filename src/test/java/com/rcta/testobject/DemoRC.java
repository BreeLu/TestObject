package com.rcta.testobject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testobject.appium.testng.AppiumDriverProvider;
import org.testobject.appium.testng.TestObjectTestNGTestResultWatcher;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@Listeners({ TestObjectTestNGTestResultWatcher.class })
public class DemoRC implements AppiumDriverProvider {
    private AndroidDriver driver;
    private AndroidDriver driverReceiver;
    private String number= "16506429019";
    private static String password = "Test!123";
    private String[] devices = {
            "Asus_Google_Nexus_7_real",
            "LG_Nexus_4_E960_real",
            "LG_Nexus_5X_real",
            "Samsung_Google_Nexus_10_P8110_real"
    };

    @BeforeMethod
    public void setUp(Object[] params) throws Exception {
        // set up appium
        /* These are the capabilities we must provide to run our test on TestObject */
        DesiredCapabilities capabilities = new DesiredCapabilities();
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        System.out.println("Begin set up: Current Date: " + ft.format(new Date()));

        capabilities.setCapability("testobject_api_key", "A4CBB9174B6A4958BD94D1BA2E258EE4");
        capabilities.setCapability("testobject_app_id", "1");
//        Test annotation = method.getDeclaredAnnotation(Test.class);
//        int index = annotation.invocationCount();
        String deviceId = (String) params[0];

        capabilities.setCapability("testobject_device", deviceId);
//
//        /* The driver will take care of establishing the connection, so we must provide
//        * it with the correct endpoint and the requested capabilities. */
        driver = new AndroidDriver(new URL("https://app.testobject.com:443/api/appium/wd/hub"), capabilities);
    }

    private void initSecondDevice() throws MalformedURLException {
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "apps");
        File app = new File(appDir, "RCMobile_8.1.0.1.2_UAT_Automation.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android");
        capabilities.setCapability("platformVersion", "5.0.1");
        capabilities.setCapability("appPackage", "com.ringcentral.android");
        capabilities.setCapability("appActivity", ".LoginScreen");
        driverReceiver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        if(driver!=null){
            driver.quit();
        }

        if(driverReceiver!=null){
            driverReceiver.quit();
        }
    }


    @DataProvider(name="devices")
    public Object[][] createDeviceList(){
        return new Object[][] {
                new Object[] {"Asus_Google_Nexus_7_real"},
                new Object[] {"LG_Nexus_4_E960_real"},
                new Object[] {"LG_Nexus_5X_real"},
                new Object[] {"Samsung_Google_Nexus_10_P8110_real"}
        };
    }


    @Test(dataProvider = "devices", singleThreaded = true)
    public void testRC(String deviceId) throws Exception {
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

        System.out.println("Begin test: Current Date: " + ft.format(new Date()));
//        Thread.sleep(15000);
        System.out.println("Begin to login with number ");

        processFirstLogin();
//        processMakeCall();
//        processLogOut();
    }
    private void processLogOut() throws Exception {
        System.out.println("Click Myprofile");
        clickElementByID(driver, "com.ringcentral.android:id/title_bar_photo");
        System.out.println("Pull up the window");
        getElementByName(driver, "Extension Settings").swipe(SwipeElementDirection.UP,5);
        System.out.println("Click logout");
        clickElementByID(driver,"com.ringcentral.android:id/item_logout");
        assertTrue(getElementByID(driver,"com.ringcentral.android:id/phone") != null, "Logout successfully");
        System.out.println("Logout successfully");
    }
    private void processMakeCall() throws Exception {
        System.out.println("Navigate to Dial pad");
        clickElementByName(driver,"Dial Pad");

        System.out.println("----Remote init");
        initSecondDevice();
        Thread.sleep(6000);

        System.out.println("Press 108");
        driver.findElementById("com.ringcentral.android:id/one").click();
        driver.findElementById("com.ringcentral.android:id/zero").click();
        driver.findElementById("com.ringcentral.android:id/eight").click();
        System.out.println("Press Call btn");
        driver.findElementById("com.ringcentral.android:id/btnCall").click();
        Thread.sleep(3000);

        System.out.println("---Navigate to remote device");
        clickElementByID(driverReceiver, "com.ringcentral.android:id/btn_call_answer");
        assertTrue(getElementByID(driverReceiver,"com.ringcentral.android:id/btn_call_mute_bottom")!=null,"remote accept the call");
        Thread.sleep(5000);
        clickElementByID(driverReceiver,"com.ringcentral.android:id/btn_call_endcall");
        Thread.sleep(3000);

        System.out.println("check local the end call finish");
        assertTrue(getElementByID(driver,"com.ringcentral.android:id/btnCall")!=null,"back to dial screen after clicking end call");
    }
    private void processFirstLogin() throws Exception {
        driver.getScreenshotAs(OutputType.BASE64);
        getElementByName(driver, "Phone number (digits only)").sendKeys(number);
        ((MobileElement) driver.findElementsByClassName("android.widget.EditText").get(2)).click();
        System.out.println("Begin to send password ");
        driver.getKeyboard().sendKeys(password);
        clickElementByName(driver,"Log In");
        Thread.sleep(20000);

        System.out.println("Click Accept ");
        clickElementByName(driver,"Accept");
        System.out.println("Click Accept ");
        Thread.sleep(2000);
        clickElementByName(driver,"Accept");

        System.out.println("Click Skip ");
        clickElementByName(driver,"Skip");
        Thread.sleep(10000);

        if(getElementByName(driver,"Save")!=null){
            System.out.println("Send device number ");
            driver.getKeyboard().sendKeys("12345678901");
            Thread.sleep(2000);
            System.out.println("Click Save btn ");
            clickElementByName(driver,"Save");
        }else {
            System.out.println("Do not have the device number");
        }

        String title = getElementByName(driver,"All Messages").getText();
        assertEquals(title, "All Messages");
    }

    private MobileElement getElementByName(AndroidDriver driver,String name) throws Exception {
        MobileElement element = null;
        for (int i = 0; i < 10; i++) {
            try {
                element = (MobileElement) driver.findElementByName(name);
                return element;
            } catch (Exception e) {
                System.out.println("Wait one " + (i + 1) + " second for " + name);
                Thread.sleep(1000);
            }
        }
        return element;
    }

    private void clickElementByName(AndroidDriver driver,String name) throws Exception {
        for (int i = 0; i < 10; i++) {
            try {
                driver.findElementByName(name).click();
                break;
            } catch (Exception e) {
                System.out.println("Wait one " + (i + 1) + " second for " + name);
                Thread.sleep(1000);
                if (i == 9) {
                    throw new Exception("can not click the " + name, e);
                }
            }
        }
    }

    private MobileElement getElementByID(AndroidDriver driver,String id) throws Exception {
        MobileElement element = null;
        for (int i = 0; i < 10; i++) {
            try {
                element = (MobileElement) driver.findElementById(id);
                return element;
            } catch (Exception e) {
                System.out.println("Wait one " + (i + 1) + " second for " + id);
                Thread.sleep(1000);
            }
        }
        return element;
    }
    private void clickElementByID(AndroidDriver driver,String id) throws Exception {
        for (int i = 0; i < 10; i++) {
            try {
                driver.findElementById(id).click();
                break;
            } catch (Exception e) {
                System.out.println("Wait one " + (i + 1) + " second for " + id);
                Thread.sleep(1000);
                if (i == 9) {
                    throw new Exception("can not click the " + id, e);
                }
            }
        }
    }

    @Override
    public AppiumDriver getAppiumDriver() {
        return this.driver;
    }
}

