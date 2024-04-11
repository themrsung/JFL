package pegasus;

import pegasus.currency.Currency;
import pegasus.wallet.Wallet;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        var USD = new Currency("USD", 2);
        var KRW = new Currency("KRW", 0);

        var w1 = new Wallet();
        var w2 = new Wallet();

        w1.addCash(USD, BigDecimal.valueOf(0.01));
        w2.addCash(KRW, BigDecimal.valueOf(1000));

        w1.removeCash(USD, BigDecimal.valueOf(1));
        w1.addCash(USD, BigDecimal.valueOf(10));

        System.out.println(w1.getCash(USD));
        System.out.println(w2.getCash(USD));
    }
}