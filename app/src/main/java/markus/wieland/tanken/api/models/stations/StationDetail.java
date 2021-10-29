package markus.wieland.tanken.api.models.stations;

import java.util.List;

public class StationDetail extends Station {

    private String state;

    private boolean wholeDay;

    private List<OpeningTimes> openingTimes;

    private List<String> overrides;

    public String getState() {
        return state;
    }

    public boolean isWholeDay() {
        return wholeDay;
    }

    public List<OpeningTimes> getOpeningTimes() {
        return openingTimes;
    }

    public List<String> getOverrides() {
        return overrides;
    }
}
