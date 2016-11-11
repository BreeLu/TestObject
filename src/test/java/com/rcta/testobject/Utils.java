package com.rcta.testobject;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utils {
    public static int delayTime = 20;

    public static void waitTimeAfterClick() throws InterruptedException {
        Thread.sleep(3000);
    }
    public static MobileElement getElementByName(AndroidDriver driver, String name) throws Exception {
        MobileElement element=null;
        try {
            element= findElementWithTimeout(driver,By.name(name),delayTime);
        } catch (Exception e) {
            System.out.println("---Element name:"+name+" can not find");
        }
        return element;
    }
    public static MobileElement getElementByID(AndroidDriver driver, String id) throws Exception {
        MobileElement element=null;
        try {
            element= findElementWithTimeout(driver,By.id(id),delayTime);
        } catch (Exception e) {
            System.out.println("---Element id:"+id+" can not find");
        }
        return element;
    }
    public static MobileElement getElementByClassIndex(AndroidDriver driver,String classname,int index) throws Exception {
        MobileElement element=null;
        for (int i = 0; i < delayTime; i++) {
            try {
                element= (MobileElement) driver.findElementsByClassName(classname).get(index);
                break;
            } catch (Exception e) {
                Thread.sleep(1000);
                if (i == (delayTime-1)) {
                    System.out.println("---Element Class:"+classname+" and index "+index+"can not find");
                }
            }
        }
        return element;
    }
    public static MobileElement getElementByXpath(AndroidDriver driver,String xpath) throws Exception {
        MobileElement element=null;
        for (int i = 0; i < delayTime; i++) {
            try {
                element= (MobileElement) driver.findElementByXPath(xpath);
                break;
            } catch (Exception e) {
                Thread.sleep(1000);
                if (i == (delayTime-1)) {
                    System.out.println("---Element xpath:"+xpath+" can not find");
                }
            }
        }
        return element;
    }
    public static void clickElementByName(AndroidDriver driver, String name) throws Exception {
        try {
            findElementWithTimeout(driver,By.name(name),delayTime).click();
            Utils.waitTimeAfterClick();
            takeScreenshot(driver);
        } catch (Exception e) {
            takeScreenshot(driver);
            throw new Exception("---Element name:"+name+" can not click");
        }
    }
    public static void clickElementByID(AndroidDriver driver, String id) throws Exception {
        try {
            findElementWithTimeout(driver,By.id(id),delayTime).click();
            Utils.waitTimeAfterClick();
            takeScreenshot(driver);
        } catch (Exception e) {
            takeScreenshot(driver);
            throw new Exception("---Element id:"+id+" can not click");
        }
    }

    public static void clickElement(AndroidDriver driver,MobileElement element) throws Exception {
        try{
            element.click();
            Utils.waitTimeAfterClick();
            takeScreenshot(driver);
        }catch (Exception e){
            takeScreenshot(driver);
            throw new Exception("---Element can not click");
        }
    }
    public static MobileElement findElementWithTimeout(AndroidDriver driver,By by, int timeOutInSeconds) {
        MobileElement element=null;
        try {
            element = (MobileElement)(new WebDriverWait(driver, timeOutInSeconds)).until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            System.out.println("---Element Can not find");
        }
        return element;
    }

    public static void takeScreenshot(AndroidDriver driver){
        driver.getScreenshotAs(OutputType.BASE64);
    }

    public static void pullWindowUp(AndroidDriver driver){
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();
        driver.swipe(width/2,100,width/2,height*3/4,500);
    }
}
