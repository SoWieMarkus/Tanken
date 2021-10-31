package markus.wieland.tanken.ui.userlocation.fragments;

import androidx.fragment.app.Fragment;

import java.util.List;

import markus.wieland.tanken.api.models.stations.Station;
import markus.wieland.tanken.ui.userlocation.Location;

public abstract class DefaultLocationFragment extends Fragment {

    public abstract Location getUserLocation();
    public abstract void submitList(List<Station> stations);

}
