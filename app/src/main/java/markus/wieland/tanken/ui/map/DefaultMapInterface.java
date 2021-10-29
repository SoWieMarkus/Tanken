package markus.wieland.tanken.ui.map;

import android.content.Context;

public abstract class DefaultMapInterface {

    private final Context context;

    public DefaultMapInterface(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
