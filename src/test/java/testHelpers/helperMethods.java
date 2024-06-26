package testHelpers;

import PageObjects.youtubeHomeScreen;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

import java.io.IOException;
import java.time.Duration;

public class helperMethods {

    AppiumDriverLocalService service;
    AndroidDriver driver;

    public AndroidDriver initiateDriver(){

        service = new AppiumServiceBuilder().usingAnyFreePort().build();
        service.start();

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");

        AndroidDriver driver = new AndroidDriver(service.getUrl(),options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        return driver;
    }

    public youtubeHomeScreen initiateYoutubeApp() throws IOException {
        driver = initiateDriver();
        driver.executeScript("mobile:startActivity",
                ImmutableMap.of("intent", "com.google.android.youtube/com.google.android.apps.youtube.app.watchwhile.WatchWhileActivity"));

        youtubeHomeScreen home = new youtubeHomeScreen(driver);
        return home;
    }

    @AfterClass
    public void endSession()
    {
        driver.quit();
        service.close();
    }
}
