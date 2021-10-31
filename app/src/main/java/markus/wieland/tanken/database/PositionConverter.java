package markus.wieland.tanken.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import markus.wieland.tanken.api.models.location.Position;

public class PositionConverter {

    private static final Gson gson = new Gson();

    @TypeConverter
    public static Position fromString(String value) {
        return value == null ? null : gson.fromJson(value, Position.class);
    }

    @TypeConverter
    public static String positionToString(Position position) {
        return position == null ? null : gson.toJson(position);
    }

}
