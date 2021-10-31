package markus.wieland.tanken.database.location;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import markus.wieland.databases.BaseViewModel;
import markus.wieland.tanken.ui.userlocation.Location;

public class LocationViewModel extends BaseViewModel<Location, LocationDataAccessObject, LocationRepository> {

    public LocationViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected LocationRepository initRepository() {
        return new LocationRepository(getApplication());
    }

    public LiveData<List<Location>> findAll(){
        return getRepository().findAll();
    }
}
