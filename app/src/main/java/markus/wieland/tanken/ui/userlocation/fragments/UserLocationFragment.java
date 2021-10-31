package markus.wieland.tanken.ui.userlocation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import markus.wieland.tanken.R;
import markus.wieland.tanken.api.models.stations.Station;
import markus.wieland.tanken.ui.FuelStationAdapter;
import markus.wieland.tanken.ui.FuelStationInteractionListener;
import markus.wieland.tanken.ui.userlocation.Location;

public class UserLocationFragment extends DefaultLocationFragment {

    private static final String USER_LOCATION = "markus.wieland.tanken.ui.USER_LOCATION";
    private static final String STATION_INTERACTION = "markus.wieland.tanken.ui.STATION_INTERACTION";

    private Location userLocation;
    private FuelStationAdapter fuelStationAdapter;

    private LocationFragmentInteractionListener locationFragmentInteractionListener;

    private TextView textViewLocationLabel;
    private TextView textViewLocationAddress;

    public UserLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public Location getUserLocation() {
        return userLocation;
    }

    @Override
    public void submitList(List<Station> items) {
        fuelStationAdapter.submitList(items);
    }

    public static UserLocationFragment newInstance(Location userLocation, LocationFragmentInteractionListener locationFragmentInteractionListener) {
        UserLocationFragment fragment = new UserLocationFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER_LOCATION, userLocation);
        args.putSerializable(STATION_INTERACTION, locationFragmentInteractionListener);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) throw new IllegalStateException("Arguments are missing");
        userLocation = (Location) getArguments().getSerializable(USER_LOCATION);
        if (userLocation == null) return;
        locationFragmentInteractionListener =
                (LocationFragmentInteractionListener) getArguments().getSerializable(STATION_INTERACTION);


        fuelStationAdapter = new FuelStationAdapter((FuelStationInteractionListener) station -> locationFragmentInteractionListener.onStationDetail(station), userLocation.getFuelType());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_location, container, false);
        RecyclerView stationList = (RecyclerView) view.findViewById(R.id.fragment_user_location_stations);
        textViewLocationAddress = (TextView) view.findViewById(R.id.fragment_user_location_address);
        textViewLocationLabel = (TextView) view.findViewById(R.id.fragment_user_location_label);
        view.findViewById(R.id.fragment_user_location_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationFragmentInteractionListener.onEdit(userLocation);
            }
        });
        textViewLocationLabel.setText(userLocation.getLabel());
        textViewLocationAddress.setText(userLocation.getPosition().toString());
        stationList.setHasFixedSize(false);
        stationList.setLayoutManager(new LinearLayoutManager(getContext()));
        stationList.setAdapter(fuelStationAdapter);
        return view;
    }
}