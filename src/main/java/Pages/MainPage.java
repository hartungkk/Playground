package Pages;

import Headers.MainPageHeader;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByAttribute;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class MainPage extends MainPageHeader {
   public SelenideElement searchInput = $(By.className("searchbar__input"));
   public SelenideElement searchConfirmButton = $(By.className("searchbar__submit"));
       public void waitForOpen()
    {
        searchInput.shouldBe(Condition.visible,Duration.ofSeconds(4));
        searchConfirmButton.shouldBe(Condition.visible,Duration.ofSeconds(7));
    }
}
