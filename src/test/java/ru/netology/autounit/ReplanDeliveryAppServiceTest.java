package ru.netology.autounit;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ReplanDeliveryAppServiceTest {

    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:9999");
    }

    @Test
    void shouldReplanMeeting() {

        DataGenerator.UserInfo user = DataGenerator.Registration.generateUser("ru");

        String firstDate = DataGenerator.generateDate(4);
        String secondDate = DataGenerator.generateDate(7);

        $("[data-test-id=city] input").setValue(user.getCity());

        SelenideElement dateField = $("[data-test-id=date] input");
        dateField.doubleClick().sendKeys(Keys.BACK_SPACE);
        dateField.setValue(firstDate);

        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();

        $$("button").findBy(text("Запланировать")).click();

        $("[data-test-id=success-notification]")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Успешно"))
                .shouldHave(text("Встреча успешно запланирована на " + firstDate));

        dateField.doubleClick().sendKeys(Keys.BACK_SPACE);
        dateField.setValue(secondDate);

        $$("button").findBy(text("Запланировать")).click();

        $("[data-test-id=replan-notification]")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Перепланировать"));

        $$("button").findBy(text("Перепланировать")).click();

        $("[data-test-id=success-notification]")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Встреча успешно запланирована на " + secondDate));
    }
}