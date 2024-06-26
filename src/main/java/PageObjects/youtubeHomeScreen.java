package PageObjects;

import HelperClass.AbstractMethods;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

public class youtubeHomeScreen extends AbstractMethods {
    AndroidDriver driver;

    public youtubeHomeScreen(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(accessibility = "Search")
    WebElement searchBar;

    @AndroidFindBy(id="com.google.android.youtube:id/search_edit_text")
    WebElement videoSearch;

    AppiumBy by  = (AppiumBy) AppiumBy.id("com.google.android.youtube:id/appbar_layout");

    public youtubeVideoPlay searchVideos() throws IOException {
        presenceOfElementLocated(by);
        searchBar.click();
        videoSearch.sendKeys("toys");
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        youtubeVideoPlay playVideo = new youtubeVideoPlay(driver);
        return playVideo;
    }

}
