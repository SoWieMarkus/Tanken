package markus.wieland.tanken.api.models.responses;

import java.util.List;

import markus.wieland.tanken.api.models.stations.Station;

public class ResponseQueryStations extends Response {

    private List<Station> stations;

    public List<Station> getStations() {
        return stations;
    }

}
