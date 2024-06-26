package PageObjects;

import HelperClass.AbstractMethods;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.IOException;

public class youtubeVideoPlay extends AbstractMethods {

    AndroidDriver driver;
    String adTimeCountdown = null;
    int adTimer = 0;
    int adsNo = 0;
    Boolean adPresent;
    WebElement skipAdElement;

    public youtubeVideoPlay(AndroidDriver driver)
    {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "com.google.android.youtube:id/ad_progress_text")
            WebElement progressBarText;

    @AndroidFindBy(xpath = "//android.support.v7.widget.RecyclerView/android.view.ViewGroup[4]")
    WebElement firstVideo;

    @AndroidFindBy(xpath = "com.google.android.youtube:id/navigation_bar_divider_frame")
            WebElement navbar;

    @AndroidFindBy(xpath = "com.google.android.youtube:id/watch_while_time_bar_view")
    WebElement timeBar;

    @AndroidFindBy(className = "android.widget.SeekBar")
    WebElement seekBar;

    public void checkForAds() throws IOException {
        try {
            adPresent = invisibilityOf(progressBarText);
            if (!adPresent) {
                skipAdElement = driver.findElement(AppiumBy.xpath("//android.widget.LinearLayout[@content-desc=\"Skip ad\"]"));
                if (skipAdElement != null) {
                    Thread.sleep(5000);
                    if (skipAdElement.getAttribute("content-desc").contains("Skip ad")) {
                        skipAdElement.click();
                    }
                } else {
                    adTimeCountdown = progressBarText.getText();
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
    }

    enum VideoTime{
        START,
        MIDDLE,
        END,
        ONEFOURTH,
        THREEFOURTH
    }

    public void seekVideoTillTime()
    {
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
                int middlePoint = (int) (endX * 0.5);
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
    }

    public void playVideo() throws IOException {
        visibilityOf(navbar);
        firstVideo.click();
        visibilityOf(timeBar);

        checkForAds();
        seekVideoTillTime();

    }
}
