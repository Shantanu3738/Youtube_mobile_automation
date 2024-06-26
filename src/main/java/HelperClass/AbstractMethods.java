package HelperClass;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AbstractMethods
{
    AppiumDriver driver;
    WebDriverWait wait;
    public AbstractMethods(AppiumDriver driver)
    {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void presenceOfElementLocated(AppiumBy by)
    {
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public Boolean invisibilityOf(WebElement ele)
    {
       Boolean isPresent = wait.until(ExpectedConditions.invisibilityOf(ele));
       return isPresent;
    }

    public void visibilityOf(WebElement by){
        wait.until(ExpectedConditions.visibilityOf(by));
    }

    public void visibilityOfElementLocated(AppiumBy by)
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

}
