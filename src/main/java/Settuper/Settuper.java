package Settuper;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;

public interface Settuper {

     static void setUp(){
          String URL = "https://www.post.at";
        Configuration.browser = "chrome";
        Configuration.browserSize = "1280x1024";
        System.setProperty("chromeoptions.args", "--remote-allow-origins=*");
        Configuration.baseUrl = URL;
        Configuration.fastSetValue=true;

    }
}
