package markus.wieland.tanken.database.station;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import markus.wieland.databases.BaseViewModel;
import markus.wieland.tanken.api.models.stations.Station;

public class StationViewModel extends BaseViewModel<Station, StationDataAccessObject, StationRepository> {

    public StationViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected StationRepository initRepository() {
        return new StationRepository(getApplication());
    }

    public void updateStation(Station station) {
        UpdatePointTask task = new UpdatePointTask(station, repository, this);
        task.execute();
    }

    public LiveData<List<Station>> getFavorites() {
        return repository.getFavorites();
    }

    private static class UpdatePointTask extends AsyncTask<Void, Void, Void> {

        private final Station station;
        private final StationRepository repository;
        private final StationViewModel pointViewModel;

        private UpdatePointTask(Station station, StationRepository repository, StationViewModel pointViewModel) {
            this.station = station;
            this.repository = repository;
            this.pointViewModel = pointViewModel;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (repository.doesExist(station.getId())) pointViewModel.update(station);
            else pointViewModel.insert(station);
            return null;
        }
    }
}

