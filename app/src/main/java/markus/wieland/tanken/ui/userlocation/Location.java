package markus.wieland.tanken.ui.userlocation;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import markus.wieland.databases.DatabaseEntity;
import markus.wieland.tanken.api.models.location.Position;
import markus.wieland.tanken.api.models.stations.FuelType;

@Entity
public class Location implements Serializable, DatabaseEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String label;
    private FuelType fuelType;
    private int radius;

    private Position position;

    private String fuelStationId;

    public String getFuelStationId() {
        return fuelStationId;
    }

    public void setFuelStationId(String fuelStationId) {
        this.fuelStationId = fuelStationId;
    }

    private boolean isOpen;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public long getUniqueId() {
        return getId();
    }
}
