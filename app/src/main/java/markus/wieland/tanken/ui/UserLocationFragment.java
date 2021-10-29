package markus.wieland.tanken.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import markus.wieland.tanken.R;
import markus.wieland.tanken.api.models.stations.FuelType;
import markus.wieland.tanken.api.models.stations.Station;
import markus.wieland.tanken.ui.createlocation.UserLocation;

public class UserLocationFragment extends Fragment {

    private static final String USER_LOCATION = "markus.wieland.tanken.ui.USER_LOCATION";
    private static final String STATION_INTERACTION = "markus.wieland.tanken.ui.STATION_INTERACTION";

    private UserLocation userLocation;
    private FuelStationAdapter fuelStationAdapter;

    public UserLocationFragment() {
        // Required empty public constructor
    }

    public UserLocation getUserLocation(){
        return userLocation;
    }

    public void submitList(List<Station> stations) {
        fuelStationAdapter.submitList(stations);
    }

    public static UserLocationFragment newInstance(UserLocation userLocation, FuelStationInteractionListener fuelStationInteractionListener) {
        UserLocationFragment fragment = new UserLocationFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER_LOCATION, userLocation);
        args.putSerializable(STATION_INTERACTION, fuelStationInteractionListener);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) throw new IllegalStateException("Arguments are missing");
        userLocation = (UserLocation)getArguments().getSerializable(USER_LOCATION);
        FuelStationInteractionListener fuelStationInteractionListener =
                (FuelStationInteractionListener) getArguments().getSerializable(STATION_INTERACTION);
        fuelStationAdapter = new FuelStationAdapter(fuelStationInteractionListener, FuelType.E5);
    }

    public void setOnInteractionListener(FuelStationInteractionListener fuelStationInteractionListener){
        fuelStationAdapter.setOnItemInteractListener(fuelStationInteractionListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_location, container, false);
        RecyclerView stationList = (RecyclerView)view.findViewById(R.id.fragment_user_location_stations);
        stationList.setHasFixedSize(false);
        stationList.setLayoutManager(new LinearLayoutManager(getContext()));
        stationList.setAdapter(fuelStationAdapter);
        return view;
    }
}