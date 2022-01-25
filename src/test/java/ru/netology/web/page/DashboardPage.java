package ru.netology.web.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(int cardNumber) {
        String balance = $$(".list__item")
                .get(cardNumber)
                .getText();
        Pattern pattern = Pattern.compile("(?<=баланс:\\s)\\d+");
        Matcher matcher = pattern.matcher(balance);

        if (matcher.find()) {
            balance = matcher.group(0);
        }
        return Integer.parseInt(balance);
    }

    public void transferCard2Card(String sourceCardNumber, int targetCardNumber, double amount) {
        $$("[data-test-id='action-deposit']")
                .get(targetCardNumber)
                .click();

        $("h1.heading")
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(Condition.text("Пополнение карты"));

        $("[data-test-id='amount'] input").setValue(String.valueOf(amount));
        $("[data-test-id='from'] input").setValue(String.valueOf(sourceCardNumber));
        $("[data-test-id='action-transfer']").click();
    }
}
