package markus.wieland.tanken.database.station;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import markus.wieland.databases.BaseRepository;
import markus.wieland.tanken.api.models.stations.Station;
import markus.wieland.tanken.database.Database;

public class StationRepository extends BaseRepository<Station, StationDataAccessObject> {

    public StationRepository(@NonNull Application application) {
        super(application);
    }

    @Override
    public StationDataAccessObject initDataAccessObject(@NonNull Application application) {
        return Database.getInstance(application).getStationDataAccessObject();
    }

    public boolean doesExist(String id) {
        return dataAccessObject.doesExist(id);
    }

    public LiveData<List<Station>> getFavorites() {
        return dataAccessObject.getFavorites();
    }

}
