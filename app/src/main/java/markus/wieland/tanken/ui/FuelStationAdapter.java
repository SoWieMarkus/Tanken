package markus.wieland.tanken.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import markus.wieland.defaultappelements.uielements.adapter.DefaultViewHolder;
import markus.wieland.defaultappelements.uielements.adapter.QueryableAdapter;
import markus.wieland.tanken.R;
import markus.wieland.tanken.api.models.prices.FuelPrice;
import markus.wieland.tanken.api.models.stations.FuelType;
import markus.wieland.tanken.api.models.stations.Station;

public class FuelStationAdapter extends QueryableAdapter<String, Station, FuelStationAdapter.FuelStationViewHolder> {

    private FuelType fuelType;

    public FuelStationAdapter(FuelStationInteractionListener onItemClickListener, FuelType fuelType) {
        super(onItemClickListener);
        this.fuelType = fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FuelStationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FuelStationViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_station, parent, false));
    }

    @Override
    public FuelStationInteractionListener getOnItemInteractListener() {
        return (FuelStationInteractionListener) super.getOnItemInteractListener();
    }

    public class FuelStationViewHolder extends DefaultViewHolder<Station> {

        private TextView stationName;
        private TextView stationAddressLineOne;
        private TextView stationAddressLineTwo;
        private TextView stationPrice;
        private TextView stationPriceUpper;
        private TextView stationOpen;
        private TextView stationDistance;
        private ConstraintLayout stationBackground;

        public FuelStationViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindViews() {
            stationName = findViewById(R.id.item_station_name);
            stationAddressLineOne = findViewById(R.id.item_station_address_1);
            stationAddressLineTwo = findViewById(R.id.item_station_address_2);
            stationBackground = findViewById(R.id.item_station_background);
            stationPrice = findViewById(R.id.item_station_price);
            stationPriceUpper = findViewById(R.id.item_station_price_upper);
            stationOpen = findViewById(R.id.item_station_open);
            stationDistance = findViewById(R.id.item_station_distance);
        }

        @Override
        public void bindItemToViewHolder(Station station) {
            stationBackground.setOnClickListener(v -> getOnItemInteractListener().onDetail(station));
            stationName.setText(station.getName());

            FuelPrice fuelPrice = station.getPrice(fuelType);
            stationOpen.setText(station.isOpen() ? "Geöffnet" : "Geschlossen");
            stationOpen.setTextColor(station.isOpen() ? Color.GREEN : Color.RED);
            stationDistance.setText(station.getDistanceAsString());
            stationPrice.setText(fuelPrice.getPriceAsString());
            stationPriceUpper.setText(fuelPrice.getUpperCents());
            stationAddressLineOne.setText(station.getAddressLineOne());
            stationAddressLineTwo.setText(station.getAddressLineTwo());
        }
    }
}
