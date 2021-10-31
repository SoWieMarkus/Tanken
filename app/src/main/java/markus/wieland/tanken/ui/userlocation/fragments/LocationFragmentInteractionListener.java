package markus.wieland.tanken.ui.userlocation.fragments;

import java.io.Serializable;

import markus.wieland.tanken.api.models.stations.Station;
import markus.wieland.tanken.ui.userlocation.Location;

public interface LocationFragmentInteractionListener extends Serializable {

    void onEdit(Location location);
    void onStationDetail(Station station);

}
