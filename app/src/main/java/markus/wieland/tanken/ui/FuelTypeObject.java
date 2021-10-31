package markus.wieland.tanken.ui;

import markus.wieland.tanken.api.models.stations.FuelType;

public class FuelTypeObject {

    private String label;
    private FuelType fuelType;
    private boolean isSelected;

    public FuelTypeObject(String label, FuelType fuelType, boolean isSelected) {
        this.label = label;
        this.fuelType = fuelType;
        this.isSelected = isSelected;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
