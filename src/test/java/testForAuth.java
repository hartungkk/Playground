import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.restassured.http.ContentType;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;

import static com.codeborne.selenide.Selenide.$;
import static io.restassured.RestAssured.given;

public class testForAuth {

    @BeforeClass
    public void authBeforeTests() throws InterruptedException {
        Selenide.open("https://at-sandbox.workbench.lanit.ru/tickets/");
        String csrftoken = WebDriverRunner.getWebDriver().manage().getCookieNamed("csrftoken").getValue();
        String sessionId = given().contentType(ContentType.MULTIPART)
                 .cookie("csrftoken",csrftoken)
                 .multiPart("username","admin")
                 .multiPart("password","adminat")
                 .multiPart("csrfmiddlewaretoken", csrftoken)
                 .multiPart("next","/")
                 .post("https://at-sandbox.workbench.lanit.ru/login/")
                 .then().log().all().extract().cookie("sessionid");

        Date expiryDate = new Date();
        expiryDate.setTime(expiryDate.getTime()+(1000*10000));
        Cookie cookie = new Cookie("sessionid",sessionId,
                "at-sandbox.workbench.lanit.ru","/",expiryDate);
        WebDriverRunner.getWebDriver().manage().addCookie(cookie);

        Selenide.refresh();
        $(By.id("userDropdown")).shouldBe(Condition.visible);
    }

    @Test
    public void firstTest(){
        $(By.id("search_query")).setValue("offers waiting me").pressEnter();
    }


}

