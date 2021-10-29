package markus.wieland.tanken.ui;

import java.io.Serializable;

import markus.wieland.defaultappelements.uielements.adapter.iteractlistener.OnItemInteractListener;
import markus.wieland.tanken.api.models.stations.Station;

public interface FuelStationInteractionListener extends OnItemInteractListener<Station>, Serializable {

    void onDetail(Station station);

}
