package markus.wieland.tanken;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.tanken.api.TankenApi;
import markus.wieland.tanken.api.models.location.Position;
import markus.wieland.tanken.ui.search.LocationAdapter;

public class SearchActivity extends DefaultActivity implements SearchView.OnQueryTextListener {

    private RecyclerView locationList;
    private LocationAdapter locationAdapter;
    private SearchView searchView;
    private TankenApi tankenApi;

    public SearchActivity() {
        super(R.layout.activity_search);
    }

    @Override
    public void bindViews() {
        searchView = findViewById(R.id.activity_search_location_search_view);
        locationList = findViewById(R.id.activity_search_location_list);
    }

    @Override
    public void initializeViews() {
        locationList.setLayoutManager(new LinearLayoutManager(this));
        locationList.setHasFixedSize(true);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void execute() {
        tankenApi = new TankenApi(this);
        locationAdapter = new LocationAdapter(null);
        locationList.setAdapter(locationAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        startQuery(query);
        return false;
    }

    private void onLoad(Position[] locations) {
        locationAdapter.submitList(locations);
    }

    private void startQuery(String query) {
        if (query.length() > 2) {
            if (query.matches("[0-9]+"))
                tankenApi.queryLocationByPostCode(this::onLoad, query);
            else
                tankenApi.queryLocationByCity(this::onLoad, query);
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        startQuery(newText);
        return false;
    }
}