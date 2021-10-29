package markus.wieland.tanken.api.models.prices;

import com.google.gson.annotations.SerializedName;

public enum StationStatus {

    @SerializedName("open") OPEN,
    @SerializedName("closed") CLOSED,
    @SerializedName("no prices") NO_PRICES;

}
