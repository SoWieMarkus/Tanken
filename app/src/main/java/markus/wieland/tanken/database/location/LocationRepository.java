package markus.wieland.tanken.database.location;

import android.app.Application;
import android.app.ListActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import markus.wieland.databases.BaseRepository;
import markus.wieland.tanken.database.Database;
import markus.wieland.tanken.ui.userlocation.Location;

public class LocationRepository extends BaseRepository<Location, LocationDataAccessObject> {

    public LocationRepository(@NonNull Application application) {
        super(application);
    }

    @Override
    public LocationDataAccessObject initDataAccessObject(@NonNull Application application) {
        return Database.getInstance(application).getLocationDataAccessObject();
    }

    public LiveData<List<Location>> findAll(){
        return getDataAccessObject().findAll();
    }
}
