package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class MoneyTransferPage {
    @FindBy(css = "[data-test-id='amount'] input")
    private SelenideElement amountInput;
    @FindBy(css = "[data-test-id='from'] input")
    private SelenideElement cardNumberInput;
    @FindBy(css = "[data-test-id='action-transfer']")
    private SelenideElement transferButton;

    public MoneyTransferPage()
    {
        $("h1.heading")
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(Condition.text("Пополнение карты"));
    }

    public void setAmount(String amount)
    {
        amountInput.setValue(amount);
    }
    public void setCardNumber(String cardNumber)
    {
        cardNumberInput.setValue(cardNumber);
    }

    public DashboardPage clickMoneyTransfer()
    {
        transferButton.click();

        return page(DashboardPage.class);
    }
}
