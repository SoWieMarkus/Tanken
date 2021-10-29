package markus.wieland.tanken.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import markus.wieland.tanken.api.models.stations.Station;
import markus.wieland.tanken.database.station.StationDataAccessObject;

@androidx.room.Database(entities = Station.class, version = 1)
public abstract class Database extends RoomDatabase {

    public abstract StationDataAccessObject getStationDataAccessObject();

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
