package markus.wieland.tanken.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import markus.wieland.tanken.api.models.stations.Station;
import markus.wieland.tanken.database.location.LocationDataAccessObject;
import markus.wieland.tanken.database.station.StationDataAccessObject;
import markus.wieland.tanken.ui.userlocation.Location;

@androidx.room.Database(entities = {Station.class, Location.class}, version = 2)
@TypeConverters({PositionConverter.class})
public abstract class Database extends RoomDatabase {

    public abstract StationDataAccessObject getStationDataAccessObject();
    public abstract LocationDataAccessObject getLocationDataAccessObject();

    private static Database instance;

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, "station_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
