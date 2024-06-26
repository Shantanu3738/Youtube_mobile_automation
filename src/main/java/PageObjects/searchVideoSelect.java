package PageObjects;

import HelperClass.AbstractMethods;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class searchVideoSelect extends AbstractMethods {
    AndroidDriver driver;
    public searchVideoSelect(AndroidDriver driver)
    {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"Toys from Cars 3 with Speaking Lightning McQueen - 10 minutes, 47 seconds - Go to channel - Kidibli (Kinder Spielzeug Kanal) - 263M views - 4 years ago - play video\"]/android.view.ViewGroup[2]/android.view.ViewGroup[1]")
    WebElement video;

    public void videoSelect()
    {
        video.click();
    }
}
