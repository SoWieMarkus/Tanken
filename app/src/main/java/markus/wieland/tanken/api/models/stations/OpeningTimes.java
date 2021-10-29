package markus.wieland.tanken.api.models.stations;

public class OpeningTimes {

    private String text;
    private String start;
    private String end;

    public String getText() {
        return text;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return text + "\n" + start + "-\n" + end;
    }
}
