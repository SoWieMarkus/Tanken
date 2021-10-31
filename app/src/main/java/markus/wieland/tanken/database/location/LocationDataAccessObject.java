package markus.wieland.tanken.database.location;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import markus.wieland.databases.BaseDataAccessObject;
import markus.wieland.tanken.ui.userlocation.Location;

@Dao
public interface LocationDataAccessObject extends BaseDataAccessObject<Location> {

    @Query("Select * from location")
    LiveData<List<Location>> findAll();
}
