package markus.wieland.tanken.api.models.prices;

public class FuelPrice {

    private final String euros;
    private final String cents;
    private final String upperCents;

    private static final String NO_PRICE = "-";

    public FuelPrice(String price) {
        if (price.equalsIgnoreCase("false") || price.equalsIgnoreCase("-1.0")) {
            euros = NO_PRICE;
            cents = NO_PRICE + NO_PRICE;
            upperCents = NO_PRICE;
            return;
        }

        String[] priceParts = price.split("\\.");
        euros = priceParts[0];
        cents = priceParts[1].substring(0, 2);
        upperCents = priceParts[1].substring(2, 3);
    }

    public String getEuros() {
        return euros;
    }

    public String getCents() {
        return cents;
    }

    public String getUpperCents() {
        return upperCents;
    }

    public String getPriceAsString() {
        return getEuros() + "," + getCents();
    }

    public FuelPrice(Float price) {
        this(String.valueOf(price == null ? Price.NO_PRICE : price));
    }
}
