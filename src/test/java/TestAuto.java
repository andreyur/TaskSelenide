import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.testng.ScreenShooter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Listeners({ ScreenShooter.class})
public class TestAuto {

    public String urlOfSite="https://auto.ria.com/";
    public String title="AUTO.RIA™ — Автобазар №1. Купить и продать авто легко как никогда";
    public By searchForm =By.cssSelector("div.form-search");

    @Test
    public void testHomePage(){
        ScreenShooter.captureSuccessfulTests = true;
        open(urlOfSite);
        $("title").shouldHave(attribute("text", title));
        $(searchForm).shouldBe(visible).shouldBe(enabled);
        $("a.logo[href='/']").shouldBe(visible).shouldBe(enabled);
    }
    @Test
    public void testLogin(){
        open(urlOfSite);
        $("a.item[href*='login.html']").click();
        $("div.login-block").shouldBe(visible).shouldBe(enabled);
        switchTo().frame(0);
        screenshot("testLogin/Login Form");
        $("input#emailloginform-email").shouldBe(visible).sendKeys("alushpigan@gmail");
        $("#emailloginform-password").shouldBe(visible).sendKeys("andrey12");
        $("[type='submit']").click();
        $("a.item[href*='login.html']").click();
        $("div.user-menu").shouldBe(visible).shouldBe(enabled).click();
        $("a.item[href='/mymenu/']").shouldHave(text("Andrey"));
        $("a.item[href*='exit']").shouldBe(visible).shouldBe(enabled).click();
        $("a.item[href*='login.html']").shouldBe(visible).shouldBe(enabled).click();
    }
    @Test
    public void testSearch(){
        Configuration.browser = "chrome";
        Configuration.timeout = 8000;
        String category = "Легковые";
        String brand = "Volkswagen";
        String model = "Jetta";
        String year = "2015";
        open(urlOfSite);
        $("[for='buRadioType']").click();
        $("#categories").selectOption(category);
        $("#brandTooltipBrandAutocompleteInput-brand").shouldBe(visible).shouldBe(enabled).sendKeys(brand);
        $(By.linkText(brand)).shouldBe(visible).shouldBe(enabled).click();
        $("#brandTooltipBrandAutocompleteInput-model").shouldBe(visible).shouldBe(enabled).sendKeys(model);
        $(By.linkText(model)).shouldBe(visible).shouldBe(enabled).click();
        $("#yearFrom").shouldBe(visible).selectOption(year);
        screenshot("testSearch/Search Form");
        $("[type='submit']").click();
        $$("section.ticket-item").shouldHaveSize(10);
        screenshot("testSearch/Search Results");
        ElementsCollection list = $$("section.ticket-item a.address>span");
        for (SelenideElement element : list) {
            element.shouldHave(text(brand+" "+model));
        }
    }
    @Test
    public void testProductPage(){
        Configuration.browser = "chrome";
        Configuration.timeout = 8000;
        String category = "Легковые";
        String brand = "Volkswagen";
        String model = "Jetta";
        String year = "2015";
        open(urlOfSite);
        $("[for='buRadioType']").click();
        $("#categories").selectOption(category);
        $("#brandTooltipBrandAutocompleteInput-brand").shouldBe(visible).shouldBe(enabled).sendKeys(brand);
        $(By.linkText(brand)).shouldBe(visible).shouldBe(enabled).click();
        $("#brandTooltipBrandAutocompleteInput-model").shouldBe(visible).shouldBe(enabled).sendKeys(model);
        $(By.linkText(model)).shouldBe(visible).shouldBe(enabled).click();
        $("#yearFrom").shouldBe(visible).selectOption(year);
        $("[type='submit']").click();
        $("div.d-table[data-search-dfp]").shouldBe(visible).shouldBe(enabled);
        $$("section.ticket-item div.ticket-photo").shouldHaveSize(10);
        $$("section.ticket-item div.ticket-photo").get(new Random().nextInt($$("section.ticket-item").size())).shouldBe(visible).shouldBe(enabled).click();
        screenshot("testProductPage/Random Product Page");
        $("h1.head[title]").shouldHave(text(brand+" "+model));
        $("span.next_advert").shouldHave(text(brand+" "+model));
        $("h3.auto-content_title").shouldHave(text(brand+" "+model));
    }
    @Test
    public void testShareButtonsOnFooter(){
        ScreenShooter.captureSuccessfulTests = true;
        Configuration.timeout = 8000;
        open(urlOfSite);
        $("div.title-panel").scrollIntoView(true);
        screenshot("testShareButtonsOnFooter/Share Buttons");
        $("a.i-btn-instagram").shouldBe(visible).shouldBe(enabled);
        $("a.i-btn-twitter").shouldBe(visible).shouldBe(enabled);
        $("a.i-btn-facebook").shouldBe(visible).shouldBe(enabled);
        $("a.i-btn-youtube").shouldBe(visible).shouldBe(enabled);
        $("a.i-btn-ok").shouldBe(visible).shouldBe(enabled);
        $("a.i-btn-vk").shouldBe(visible).shouldBe(enabled);
    }
}
