package pl.com.bottega.parkingmeter.model;

import java.math.BigDecimal;
import java.util.Currency;

public class Money {
    public static final Currency DEFAULT_CURRENCY = Currency.getInstance("PLN");
    public static final Money ZERO = new Money(BigDecimal.ZERO, DEFAULT_CURRENCY);

    private final BigDecimal value;
    private final Currency currency;

    public Money(double value, Currency currency) {
        this.value = BigDecimal.valueOf(value);
        this.currency = currency;
    }

    public Money(int value, Currency currency) {
        this.value = BigDecimal.valueOf(value);
        this.currency = currency;
    }

    public Money(int wholePart, int fractionPart, Currency currency) {
        this.value = BigDecimal.valueOf(wholePart + fractionPart/100);
        this.currency = currency;
    }

    public Money(String value, String currency){
        this.value = new BigDecimal(value);
        this.currency = Currency.getInstance(currency);
    }

    public Money(int value, String currency){
        this.value = new BigDecimal(value);
        this.currency = Currency.getInstance(currency);
    }

    public Money(BigDecimal newValue, Currency currency) {
        this.value = newValue;
        this.currency = currency;
    }

    public Money multiply(long multiplicand){
        BigDecimal newValue = value.multiply(new BigDecimal(multiplicand));
        return new Money(newValue, currency);
    }

    public Money divide(int divider){
        BigDecimal newValue = value.divide(new BigDecimal(divider));
        return new Money(newValue, currency);
    }

    public Money add(Money summand){
        if (!this.currency.equals(summand.currency))
            throw new IllegalArgumentException("different currency");
        BigDecimal sum = this.value.add(summand.value);
        return new Money(sum, currency);
    }

    public Money substract(Money subtrahend){
        if(!this.currency.equals(subtrahend.currency))
            throw new IllegalArgumentException("different currency");

        BigDecimal sum = this.value.subtract(subtrahend.value);
        return new Money(sum, currency);
    }

    public boolean greaterThan(Money comparator){
        return value.compareTo(comparator.value) == 1;
    }

    public boolean greaterEquals(Money comparator){
        return value.compareTo(comparator.value) == 1 || value.compareTo(comparator.value) == 0;
    }

    public boolean lessThan(Money comparator){
        return value.compareTo(comparator.value) == -1;
    }

    public boolean lessEquals(Money comparator){
        return value.compareTo(comparator.value) == -1 || value.compareTo(comparator.value) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;

        Money money = (Money) o;

        if (!value.equals(money.value)) return false;
        return currency.equals(money.currency);

    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return value + " " + currency;
    }
}
