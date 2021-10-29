package markus.wieland.tanken.api.models.location;

import java.io.Serializable;

public class Coordinate implements Serializable {

    private final double latitude;
    private final double longitude;

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
