package baseTest;

import PageObjects.youtubeHomeScreen;
import PageObjects.youtubeVideoPlay;
import org.testng.annotations.Test;
import testHelpers.helperMethods;

import java.io.IOException;

public class baseTestPOM extends helperMethods {

    @Test
    public void youtubeAutomation() throws IOException {

        youtubeHomeScreen home = initiateYoutubeApp();
        youtubeVideoPlay videoPage = home.searchVideos();
        videoPage.playVideo();


    }
}
