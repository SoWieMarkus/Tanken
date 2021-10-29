package markus.wieland.tanken.api.models.prices;

import markus.wieland.tanken.api.models.stations.FuelType;

public class Price {

    public static final Float NO_PRICE = -1f;

    private String e5;
    private String e10;
    private String diesel;

    private double convert(String value) {
        return value.equalsIgnoreCase("false") ? NO_PRICE : Double.parseDouble(value);
    }

    public FuelPrice getFuelPrice(FuelType fuelType) {
        switch (fuelType) {
            case E5:
                return new FuelPrice(e5);
            case E10:
                return new FuelPrice(e10);
            case DIESEL:
                return new FuelPrice(diesel);
            default:
                return null;
        }
    }

    public double getE5() {
        return convert(e5);
    }

    public double getE10() {
        return convert(e10);
    }

    public double getDiesel() {
        return convert(diesel);
    }

    private StationStatus status;

    public StationStatus getStatus() {
        return status;
    }

}
