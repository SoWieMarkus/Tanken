package markus.wieland.tanken.api.models.location;

import markus.wieland.defaultappelements.uielements.adapter.QueryableEntity;

public class Location implements QueryableEntity<String> {

    private String postCode;
    private String city;
    private String state;
    private String community;
    private double latitude;
    private double longitude;

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
}
