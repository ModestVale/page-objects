package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    @ParameterizedTest
    @CsvSource({
            "'1',1",
            "'10000',10000",
            "'555',555",
            "'100,1',100.1"
    })
    void shouldTransferMoneyBetweenOwnCards(String amountStr, double amount) throws InterruptedException {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);

        val firstCardBalance = dashboardPage.getCardBalance(0);
        val secondCardBalance = dashboardPage.getCardBalance(1);

        val transferPage = dashboardPage.transferCard2Card( 0);

        transferPage.setAmount(amountStr);
        transferPage.setCardNumber(DataHelper.getSecondCard(authInfo));
        val dashboardPage2 = transferPage.clickMoneyTransfer();

        val firstCardBalanceBefore = dashboardPage2.getCardBalance(0);
        val secondCardBalanceBefore = dashboardPage2.getCardBalance(1);

        assertEquals(firstCardBalance + amount, firstCardBalanceBefore);
        assertEquals(secondCardBalance - amount, secondCardBalanceBefore);
    }
}

