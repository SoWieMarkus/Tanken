package markus.wieland.tanken.api.models.responses;

import markus.wieland.tanken.api.models.stations.StationDetail;

public class ResponseQueryStationDetail extends Response {

    private StationDetail station;

    public StationDetail getStation() {
        return station;
    }
}
