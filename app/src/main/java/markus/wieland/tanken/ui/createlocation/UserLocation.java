package markus.wieland.tanken.ui.createlocation;

import java.io.Serializable;

import markus.wieland.tanken.api.models.stations.FuelType;

public class UserLocation implements Serializable {

    private String label;
    private FuelType fuelType;
    private int radius;
    private String street;
    private String postCode;
    private String houseNumber;
    private double longitude;
    private double latitude;
    private boolean isOpen;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getLabel() {
        return label;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public int getRadius() {
        return radius;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
