package markus.wieland.tanken.database.station;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import markus.wieland.databases.BaseDataAccessObject;
import markus.wieland.tanken.api.models.stations.Station;

@Dao
public interface StationDataAccessObject extends BaseDataAccessObject<Station> {

    @Query("SELECT EXISTS(SELECT * FROM station WHERE id = :id)")
    boolean doesExist(String id);

    @Query("SELECT * FROM station")
    LiveData<List<Station>> getFavorites();

}
