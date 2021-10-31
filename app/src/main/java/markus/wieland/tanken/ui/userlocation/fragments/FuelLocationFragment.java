package markus.wieland.tanken.ui.userlocation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import markus.wieland.tanken.R;
import markus.wieland.tanken.api.models.stations.Station;
import markus.wieland.tanken.ui.userlocation.Location;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FuelLocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FuelLocationFragment extends DefaultLocationFragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FuelLocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FuelLocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FuelLocationFragment newInstance(String param1, String param2) {
        FuelLocationFragment fragment = new FuelLocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fuel_location, container, false);
    }

    @Override
    public Location getUserLocation() {
        return null;
    }

    @Override
    public void submitList(List<Station> stations) {

    }
}