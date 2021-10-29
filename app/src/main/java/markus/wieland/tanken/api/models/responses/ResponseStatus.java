package markus.wieland.tanken.api.models.responses;

import com.google.gson.annotations.SerializedName;

public enum ResponseStatus {

    @SerializedName("ok") OK,
    @SerializedName("error") ERROR;

}
