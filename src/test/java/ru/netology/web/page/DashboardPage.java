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

    public double getCardBalance(int cardNumber) {
        String balance = $$(".list__item")
                .get(cardNumber)
                .getText();
        Pattern pattern = Pattern.compile("(?<=баланс:\\s)-?\\d+,?\\d{0,2}");
        Matcher matcher = pattern.matcher(balance);

        if (matcher.find()) {
            balance = matcher.group(0);
        }
        return Double.parseDouble(balance);
    }

    public MoneyTransferPage transferCard2Card(int targetCardNumber) throws InterruptedException {
        $$("[data-test-id='action-deposit']")
                .get(targetCardNumber)
                .click();

       return page(MoneyTransferPage.class);
    }
}
