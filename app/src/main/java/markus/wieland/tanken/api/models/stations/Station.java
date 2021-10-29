package markus.wieland.tanken.api.models.stations;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Inherited;

import markus.wieland.databases.DatabaseEntity;
import markus.wieland.defaultappelements.uielements.adapter.QueryableEntity;
import markus.wieland.tanken.api.models.prices.FuelPrice;

@Entity
public class Station extends Address implements QueryableEntity<String>, DatabaseEntity {

    @NonNull
    @PrimaryKey
    private String id;

    private boolean isOpen;

    @SerializedName("dist")
    private float distance;

    private String brand;

    private String name;

    private Float e5;
    private Float e10;
    private Float diesel;

    @Ignore
    private static final String UNIT_METERS = "m";

    @Ignore
    private static final String UNIT_KILO_METERS = "km";

    public FuelPrice getPrice(FuelType type) {
        switch (type) {
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

    public Float getE5() {
        return e5;
    }

    public Float getE10() {
        return e10;
    }

    public Float getDiesel() {
        return diesel;
    }

    @Override
    public @NotNull String getId() {
        return id;
    }

    @Override
    public String getStringToApplyQuery() {
        return toString();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public float getDistance() {
        return distance;
    }


    @Ignore
    public String getDistanceAsString() {
        return distance < 1 ? (int)(distance * 1000) + " " + UNIT_METERS : distance + " " + UNIT_KILO_METERS;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    @Override
    public long getUniqueId() {
        return 0;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setE5(Float e5) {
        this.e5 = e5;
    }

    public void setE10(Float e10) {
        this.e10 = e10;
    }

    public void setDiesel(Float diesel) {
        this.diesel = diesel;
    }
}
