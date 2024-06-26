package baseTest;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;

public class baseTest {

    enum VideoTime {
        START,
        MIDDLE,
        END,
        ONEFOURTH,
        THREEFOURTH
    }

    public static void main(String[] args) throws InterruptedException, IOException {

        AppiumDriverLocalService service = new AppiumServiceBuilder().usingAnyFreePort().build();
        service.start();

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");

        AndroidDriver driver = new AndroidDriver(service.getUrl(),options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.activateApp("com.google.android.youtube/com.google.android.apps.youtube.app.watchwhile.WatchWhileActivity");

        TouchAction action =new TouchAction(driver);

//        driver.executeScript("mobile:startActivity",
//                ImmutableMap.of("intent", "com.google.android.youtube/com.google.android.apps.youtube.app.watchwhile.WatchWhileActivity"));

        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.id("com.google.android.youtube:id/appbar_layout")));
        driver.findElement(AppiumBy.accessibilityId("Search")).click();

        driver.findElement(AppiumBy.id("com.google.android.youtube:id/search_edit_text"))
                .sendKeys("toys");
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));

       // driver.findElements(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(Nothing Phone (3) First Look, POCO F6 Vs realme GT 6T,Galaxy Z Flip 6, OnePlus 13,NARZO N65-#TTN1577))"));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(AppiumBy.id("com.google.android.youtube:id/results"))));
        driver.findElement(AppiumBy.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[4]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.google.android.youtube:id/watch_while_time_bar_view")));

        String adTimeCountdown = null;
        int adTimer = 0;
        int adsNo = 0;
        WebElement skipAdElement;

        try {
            Boolean adPresent = wait.until(ExpectedConditions.invisibilityOf(driver.findElement(AppiumBy.id("com.google.android.youtube:id/ad_progress_text"))));
            if (!adPresent) {
                skipAdElement = driver.findElement(AppiumBy.xpath("//android.widget.LinearLayout[@content-desc=\"Skip ad\"]"));
                if (skipAdElement != null) {
                    Thread.sleep(5000);
                    if (skipAdElement.getAttribute("content-desc").contains("Skip ad")) {
                        skipAdElement.click();
                    }
                } else {
                    adTimeCountdown = driver.findElement(AppiumBy.id("com.google.android.youtube:id/ad_progress_text")).getText();
                    adsNo = Integer.parseInt(adTimeCountdown.split(" ")[4].trim());
                    if (adsNo > 1) {
                        for (int i = 1; i <= adsNo; i++) {
                            adTimer = Integer.parseInt(adTimeCountdown.split(":")[1].trim());
                            System.out.println(adTimer);
                            if (adTimer < 30) {
                                Thread.sleep(adTimer);
                            }
                        }
                    } else {
                        if (adTimer < 30) {
                            Thread.sleep(adTimer);
                        }
                    }

                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        String startVideoTime = driver.findElement(AppiumBy.className("android.widget.SeekBar")).getAttribute("content-desc");

        //Locating seekbar using resource id
        WebElement seekBar=driver.findElement(AppiumBy.className("android.widget.SeekBar"));

        //Get start point of seekbar.
        int startX = seekBar.getLocation().getX();
        System.out.println(startX);

        //Get vertical location of seekbar.
        int startY = seekBar.getLocation().getY();
        System.out.println(startY);

        //Get end point of seekbar.
        int endX = seekBar.getSize().getWidth();
        System.out.println(endX);

        //Set seekbar move to position.
        //endX * 0.6 means at 60% of seek bar width.
        //endX * 0.9 means at 100% of seek bar width.

        VideoTime time = VideoTime.MIDDLE;

        switch(time)
        {
            case START:
                int startPoint = (int) (endX * 0);
                System.out.println("Moving seek bar at " + startPoint+" In X direction.");

                //Moving seekbar using TouchAction class.
//        TouchAction act=new TouchAction(driver);
//        act.press(PointOption.point(startX,startY)).moveTo(PointOption.point(moveToXDirectionAt,startY)).release().perform();

                ((JavascriptExecutor)driver).executeScript("mobile:dragGesture", ImmutableMap.of("elementId", ((RemoteWebElement)seekBar).getId(),
                        "endX", startPoint,
                        "endY", startY));
                break;

            case MIDDLE:
                int middlePoint = startX + (int) (endX * 0.5);
                System.out.println("Moving seek bar at " + middlePoint+" In X direction.");

                ((JavascriptExecutor)driver).executeScript("mobile:dragGesture", ImmutableMap.of("elementId", ((RemoteWebElement)seekBar).getId(),
                        "endX", middlePoint,
                        "endY", startY));
                break;

            case END:
                int endPoint = (int) (endX * 0.99);
                System.out.println("Moving seek bar at " + endPoint+" In X direction.");

                ((JavascriptExecutor)driver).executeScript("mobile:dragGesture", ImmutableMap.of("elementId", ((RemoteWebElement)seekBar).getId(),
                        "endX", endPoint,
                        "endY", startY));
                break;

            case ONEFOURTH:
                int oneFourthPoint = (int) (endX * 0.25);
                System.out.println("Moving seek bar at " + oneFourthPoint +" In X direction.");

                ((JavascriptExecutor)driver).executeScript("mobile:dragGesture", ImmutableMap.of("elementId", ((RemoteWebElement)seekBar).getId(),
                        "endX", oneFourthPoint,
                        "endY", startY));
                break;

            case THREEFOURTH:
                int threefourthPoint = (int) (endX * 0.75);
                System.out.println("Moving seek bar at " + threefourthPoint+" In X direction.");

                ((JavascriptExecutor)driver).executeScript("mobile:dragGesture", ImmutableMap.of("elementId", ((RemoteWebElement)seekBar).getId(),
                        "endX", threefourthPoint,
                        "endY", startY));
                break;
        }

        String endVideoTime = driver.findElement(AppiumBy.xpath("//android.widget.SeekBar")).getAttribute("content-desc");

        if(endVideoTime != startVideoTime)
        {
            Assert.assertTrue(true);
        }
        else {
            Assert.assertTrue(false);
        }
    }
}
