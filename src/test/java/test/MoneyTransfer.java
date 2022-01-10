package test;



import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.DataHelper;
import page.DashboardPage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransfer {

    int amount = 200;
    int bigAmount = 50000;

    @BeforeEach
    void start() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferBetweenTwoCards() {
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance(0);
        var balanceSecondBefore = dashboardPage.getCardBalance(1);
        var balanceFirstAfter = balanceFirstBefore + amount;
        var balanceSecondAfter = balanceSecondBefore - amount;
        var verificationFirstCard = DataHelper.getFirstCardsInfo(amount);
        dashboardPage.personFirstCard().card(verificationFirstCard);

        assertEquals(balanceFirstAfter, dashboardPage.getCardBalance(0));
        assertEquals(balanceSecondAfter, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldTransferBetweenTwoCardsTwoIsOne() {
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance(0);
        var balanceSecondBefore = dashboardPage.getCardBalance(1);
        var balanceFirstAfter = balanceFirstBefore - amount;
        var balanceSecondAfter = balanceSecondBefore + amount;
        var verificationSecondCard = DataHelper.getSecondCardsInfo(amount);
        dashboardPage.personSecondCard().card(verificationSecondCard);

        assertEquals(balanceFirstAfter, dashboardPage.getCardBalance(0));
        assertEquals(balanceSecondAfter, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsBigAmount() {
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance(0);
        var balanceSecondBefore = dashboardPage.getCardBalance(1);
        var balanceFirstAfter = balanceFirstBefore - bigAmount;
        var balanceSecondAfter = balanceSecondBefore + bigAmount;
        var verificationSecondCard = DataHelper.getSecondCardsInfo(bigAmount);
        dashboardPage.personSecondCard().card(verificationSecondCard);

        assertEquals(balanceFirstAfter, dashboardPage.getCardBalance(0));
        assertEquals(balanceSecondAfter, dashboardPage.getCardBalance(1));
    }

}

