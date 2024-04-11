package pegasus.currency;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Represents a currency.
 */
public class Currency implements Serializable {
    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0;

    /**
     * The symbol of this currency. (e.g. {@code USD})
     */
    public final String symbol;

    /**
     * The number of digits allowed after the decimal point.
     */
    public final int digitsAfterDecimalPoint;

    /**
     * Creates a new currency.
     *
     * @param symbol                  The symbol of this currency
     * @param digitsAfterDecimalPoint The number of digits allowed after the decimal point
     */
    public Currency(String symbol, int digitsAfterDecimalPoint) {
        this.symbol = symbol;
        this.digitsAfterDecimalPoint = digitsAfterDecimalPoint;
    }

    /**
     * Returns the minimum amount allowed for this currency.
     *
     * @return The minimum amount allowed for this currency
     */
    public BigDecimal getMinimumAmount() {
        var multiplier = BigDecimal.TEN.pow(-digitsAfterDecimalPoint);
        return BigDecimal.ONE.multiply(multiplier);
    }

    /**
     * Returns the zero value of this currency, including all of its decimal points. (e.g. {@code 0.00})
     *
     * @return The zero value of this currency
     */
    public BigDecimal getZero() {
        return BigDecimal.ZERO.setScale(digitsAfterDecimalPoint, RoundingMode.HALF_DOWN);
    }

    /**
     * Checks for equality between this currency and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if all variables are equal
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Currency c)) return false;
        return Objects.equals(symbol, c.symbol) &&
                Objects.equals(digitsAfterDecimalPoint, c.digitsAfterDecimalPoint);
    }

    /**
     * Serializes this currency into a string.
     *
     * @return The string representation of this currency
     */
    @Override
    public String toString() {
        return symbol;
    }
}
