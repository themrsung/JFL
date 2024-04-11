package pegasus.wallet;

import pegasus.currency.Currency;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a wallet.
 */
public class Wallet implements Serializable {
    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0;

    /**
     * The unique identifier of this wallet.
     */
    public final UUID uniqueId;

    /**
     * The cash this wallet contains.
     */
    protected final Map<Currency, BigDecimal> cash;

    /**
     * Creates an empty wallet with a random unique identifier.
     */
    public Wallet() {
        this(UUID.randomUUID());
    }

    /**
     * Creates an empty wallet with the provided unique identifier.
     *
     * @param uniqueId The unique identifier of this wallet
     */
    public Wallet(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.cash = new HashMap<>();
    }

    /**
     * Returns a map of every currency this wallet is currently holding.
     *
     * @return A map of every currency this wallet is currently holding
     */
    public Map<Currency, BigDecimal> getCash() {
        return Map.copyOf(cash);
    }

    /**
     * Returns the amount of cash this wallet has in the provided currency.
     *
     * @param currency The currency to query
     * @return The cash balance this wallet contains
     */
    public BigDecimal getCash(Currency currency) {
        var balance = cash.get(currency);

        if (balance == null) return currency.getZero();
        return balance;
    }

    /**
     * Adds cash to this wallet.
     *
     * @param currency The currency to add
     * @param amount   The amount to add
     */
    public void addCash(Currency currency, BigDecimal amount) {
        if (amount.scale() > currency.digitsAfterDecimalPoint) {
            throw new IllegalArgumentException("Invalid denomination of " + currency.symbol + ".");
        }

        if (cash.containsKey(currency)) {
            var balance = cash.get(currency);
            var updatedBalance = balance.add(amount)
                    .setScale(currency.digitsAfterDecimalPoint, RoundingMode.HALF_DOWN);

            cash.put(currency, updatedBalance);
        } else {
            cash.put(currency, amount);
        }
    }

    /**
     * Removes cash from this wallet.
     *
     * @param currency The currency to remove
     * @param amount   The amount to remove
     */
    public void removeCash(Currency currency, BigDecimal amount) {
        addCash(currency, amount.negate());
    }

    /**
     * Checks for equality between this wallet and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the unique identifier and contents are equal
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Wallet w)) return false;
        return Objects.equals(uniqueId, w.uniqueId) &&
                Objects.equals(cash, w.cash);
    }

    /**
     * Serializes this wallet into a string.
     *
     * @return The string representation of this wallet
     */
    @Override
    public String toString() {
        return "Wallet{" +
                "uniqueId=" + uniqueId +
                ", cash=" + cash +
                '}';
    }
}
