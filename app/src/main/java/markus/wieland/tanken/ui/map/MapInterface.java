package markus.wieland.tanken.ui.map;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class MapInterface extends DefaultMapInterface{

    private final MapInteractionInterface mapInteractionInterface;

    public MapInterface(Context context, MapInteractionInterface mapInteractionInterface) {
        super(context);
        this.mapInteractionInterface = mapInteractionInterface;
    }

    @JavascriptInterface
    public void detailStation(String id) {
        mapInteractionInterface.detailStation(id);
    }
}
