package markus.wieland.tanken.ui.map;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class LocationInterface extends DefaultMapInterface {

    private final SelectLocationInterface selectLocationInterface;

    public LocationInterface(Context context, SelectLocationInterface selectLocationInterface) {
        super(context);
        this.selectLocationInterface = selectLocationInterface;
    }

    @JavascriptInterface
    public void onSelectLocation(double latitude, double longitude) {
        this.selectLocationInterface.onSelectLocation(latitude, longitude);
    }

}
