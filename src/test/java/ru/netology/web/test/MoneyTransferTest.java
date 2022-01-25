package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPageV1;
import ru.netology.web.page.LoginPageV2;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);

        val firstCardBalance = dashboardPage.getCardBalance(0);
        val secondCardBalance = dashboardPage.getCardBalance(1);

        val amount = 100;
        dashboardPage.transferCard2Card(DataHelper.getSecondCard(authInfo), 0, amount);

        val firstCardBalanceBefore = dashboardPage.getCardBalance(0);
        val secondCardBalanceBefore = dashboardPage.getCardBalance(1);

        assertEquals(firstCardBalance + amount, firstCardBalanceBefore);
        assertEquals(secondCardBalance - amount, secondCardBalanceBefore);

    }
}

