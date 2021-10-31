package markus.wieland.tanken.api.models.location;

import java.io.Serializable;

import markus.wieland.defaultappelements.uielements.adapter.QueryableEntity;

public class Position implements QueryableEntity<String>, Serializable {

    private String postCode;
    private String city;
    private String state;
    private String community;

    private double latitude;
    private double longitude;

    private String address;

    public String getAddress() {
        return address;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCommunity() {
        return community;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String getId() {
        return latitude + " " + longitude;
    }

    @Override
    public String getStringToApplyQuery() {
        return getCity() + getCommunity() + getPostCode();
    }

    @Override
    public String toString() {
        if (address != null) return address;
        return postCode + ", " + city +", " + state;
    }
}
