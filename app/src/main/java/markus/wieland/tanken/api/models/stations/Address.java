package markus.wieland.tanken.api.models.stations;

import android.content.Intent;
import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class Address {

    private String street;
    private String place;
    private String houseNumber;
    private String postCode;

    @SerializedName("lat")
    private float latitude;

    @SerializedName("lng")
    private float longitude;

    public String getStreet() {
        return street == null ? "" : street;
    }

    public String getAddressLineOne() {
        return getStreet() + " " + getHouseNumber();
    }

    public String getAddressLineTwo() {
        return getPostCode() + " " + getPlace();
    }

    public String getPlace() {
        return place == null ? "" : place;
    }

    public String getHouseNumber() {
        return houseNumber == null ? "" : houseNumber;
    }

    public String getPostCode() {
        if (postCode == null) return "";
        if (postCode.length() < 5) return "0" + postCode;
        return postCode;
    }

    public Intent getGoogleMapsIntent() {
        Uri googleMapsIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
        return new Intent(Intent.ACTION_VIEW, googleMapsIntentUri).setPackage("com.google.android.apps.maps");
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
