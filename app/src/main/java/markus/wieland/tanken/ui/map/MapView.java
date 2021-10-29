package markus.wieland.tanken.ui.map;

import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.google.gson.Gson;

import markus.wieland.tanken.api.models.responses.ResponseQueryStations;
import markus.wieland.tanken.api.models.stations.Station;

public class MapView extends WebView {

    private final Gson gson;

    public MapView(Context context) {
        this(context, null);
    }

    public MapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        gson = new Gson();
        init();
    }

    public void addInterfaceToMap(MapInteractionInterface mapInteractionInterface) {
        MapInterface mapInterface = new MapInterface(getContext(), mapInteractionInterface);
        addJavascriptInterface(mapInterface, "Android");
    }

    public void addInterfaceToMap(SelectLocationInterface selectLocationInterface) {
        LocationInterface locationInterface = new LocationInterface(getContext(), selectLocationInterface);
        addJavascriptInterface(locationInterface, "Android");
    }

    private void init() {
        loadUrl("file:///android_asset/map.html");
        getSettings().setJavaScriptEnabled(true);
        getSettings().setDomStorageEnabled(true);
        setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("MyApplication", consoleMessage.message() + " -- From line "
                        + consoleMessage.lineNumber() + " of "
                        + consoleMessage.sourceId());
                return super.onConsoleMessage(consoleMessage);
            }
        });
    }
    public void showFuelStations(ResponseQueryStations responseQueryStations) {
        execute("showFuelStations", parse(responseQueryStations.getStations()));
    }

    private <T> String parse(T t) {
        return gson.toJson(t);
    }

    public void focusFuelStation(Station station) {
        execute("focus", parse(station));
    }

    private void execute(String function, String... params) {
        loadUrl("javascript:" + function + "(" + String.join(",", params) + ")");
    }

    public void updateUserLocation(Location location) {
        execute("updateUserLocation", String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
    }
}