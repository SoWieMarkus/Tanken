package markus.wieland.tanken;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.tanken.api.TankenApi;
import markus.wieland.tanken.api.models.responses.ResponseQueryStations;
import markus.wieland.tanken.api.models.stations.Station;
import markus.wieland.tanken.database.location.LocationViewModel;
import markus.wieland.tanken.ui.DynamicViewPageAdapter;
import markus.wieland.tanken.ui.FuelStationInteractionListener;
import markus.wieland.tanken.ui.map.MapView;
import markus.wieland.tanken.ui.userlocation.CreateLocationActivity;
import markus.wieland.tanken.ui.userlocation.Location;
import markus.wieland.tanken.ui.userlocation.fragments.DefaultLocationFragment;
import markus.wieland.tanken.ui.userlocation.fragments.LocationFragmentInteractionListener;
import markus.wieland.tanken.ui.userlocation.fragments.UserLocationFragment;

public class MainActivity extends DefaultActivity implements LocationFragmentInteractionListener, ViewTreeObserver.OnGlobalLayoutListener, ViewPager.OnPageChangeListener {

    private LocationViewModel locationViewModel;
    private ActivityResultLauncher<Intent> selectLocationResultLauncher;

    private ViewPager viewPager;
    private DynamicViewPageAdapter viewPageAdapter;

    private FloatingActionButton addLocationButton;

    private MapView mapView;
    private TankenApi tankenApi;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    public void onGlobalLayout() {
        BottomSheetBehavior.from(viewPager).setPeekHeight(findViewById(R.id.fragment_user_location_header).getHeight());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Location location = viewPageAdapter.getItem(position).getUserLocation();
        tankenApi.queryStations(this::onLoad, location.getPosition().getLatitude(),
                location.getPosition().getLongitude(), location.getRadius());
    }

    private void onLoad(ResponseQueryStations responseQueryStations) {
        viewPageAdapter.getItem(viewPager.getCurrentItem()).submitList(responseQueryStations.getStations());
        mapView.showFuelStations(responseQueryStations);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void bindViews() {
        viewPager = findViewById(R.id.activity_main_view_pager);
        mapView = findViewById(R.id.activity_main_map_view);
        addLocationButton = findViewById(R.id.activity_main_floating_button);
    }

    @Override
    public void initializeViews() {
        viewPageAdapter = new DynamicViewPageAdapter(getSupportFragmentManager());
        findViewById(R.id.coordinator_layout).getViewTreeObserver().addOnGlobalLayoutListener(this);
        viewPager.setAdapter(viewPageAdapter);
        viewPager.addOnPageChangeListener(this);
        addLocationButton.setOnClickListener(v -> startActivityLocationForResult(null, false));
    }

    @Override
    public void execute() {

        tankenApi = new TankenApi(this);

        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        locationViewModel.findAll().observe(this, this::onChangesLocation);

        selectLocationResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::onActivityResult);
    }

    private void onChangesLocation(List<Location> locations) {
        if (locations.isEmpty()) {
            startActivityLocationForResult(null, true);
            return;
        }

        List<DefaultLocationFragment> defaultLocationFragments = new ArrayList<>();
        for (Location location : locations) {
            defaultLocationFragments.add(UserLocationFragment.newInstance(location, this));
        }
        viewPager.setCurrentItem(0, true);
        viewPageAdapter.submitList(defaultLocationFragments);
    }

    private void startActivityLocationForResult(Location location, boolean needsResult) {

        Intent selectLocationIntent = new Intent(this, CreateLocationActivity.class);

        selectLocationIntent.putExtra(CreateLocationActivity.NEEDS_RESULT, needsResult);
        selectLocationIntent.putExtra(CreateLocationActivity.OBJECT_TO_EDIT, location);
        selectLocationResultLauncher.launch(selectLocationIntent);
    }

    private void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Location location = (Location) result.getData().getSerializableExtra(CreateLocationActivity.OBJECT_TO_EDIT);
            boolean wasEdited = result.getData().getBooleanExtra(CreateLocationActivity.WAS_EDITED, false);
            if (wasEdited) locationViewModel.update(location);
            else locationViewModel.insert(location);
        } else {
            boolean neededResult = result.getData().getBooleanExtra(CreateLocationActivity.NEEDS_RESULT, true);
            if (neededResult) finish();
        }
    }


    @Override
    public void onBackPressed() {
        if (BottomSheetBehavior.from(viewPager).getState() == BottomSheetBehavior.STATE_EXPANDED) {
            BottomSheetBehavior.from(viewPager).setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Do not call super class method here.
        //super.onSaveInstanceState(outState);
    }

    @Override
    public void onEdit(Location location) {
        startActivityLocationForResult(location, false);
    }

    @Override
    public void onStationDetail(Station station) {
        mapView.focusFuelStation(station);
        BottomSheetBehavior.from(viewPager).setState(BottomSheetBehavior.STATE_COLLAPSED);
    }



    /* TODO sortieren nach Preis/Distanz
     * Auswahl nach favoriten Benzintyp
     * Marker
     * Liste anzeigen die Ergebnisse
     * Favoriten speichern die man direkt beim Start sieht
     * Landing page auf der man suchen kann mit (PLZ, Eigene Position, Position wählen)
     * Da sieht man auch seine favoriten
     * Man kann Dienst benachrichtigen, der Ding sendet wenn gewisse Grenze unterschritten?
     * 3 Arten von Favoriten: Wenn Nutzer seinen Standort teilt nimmt extra art
     */

    /*private MapView mapView;
    private TankenApi tankenApi;
    private StationViewModel stationViewModel;
    private Coordinate coordinate;
    private ImageButton addUserLocation;

    private ViewPager viewPager;
    private DynamicViewPageAdapter viewPageAdapter;

    private ResponseQueryStations responseQueryStations;

    private ActivityResultLauncher<Intent> selectLocationResultLauncher;

    private LocationViewModel locationViewModel;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        if (BottomSheetBehavior.from(viewPager).getState() == BottomSheetBehavior.STATE_EXPANDED) {
            BottomSheetBehavior.from(viewPager).setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void bindViews() {
        mapView = findViewById(R.id.activity_main_map_view);
        viewPager = findViewById(R.id.activity_main_view_pager);
        //addUserLocation = findViewById(R.id.activity_main_floating_button);
    }

    @Override
    public void initializeViews() {
        findViewById(R.id.coordinator_layout).getViewTreeObserver().addOnGlobalLayoutListener(this);
        //addUserLocation.setOnClickListener(v -> startActivityLocationForResult(null, false));
    }

    private void startActivityLocationForResult(Location location, boolean needsResult) {

        Intent selectLocationIntent = new Intent(this, CreateLocationActivity.class);

        selectLocationIntent.putExtra(CreateLocationActivity.NEEDS_RESULT, needsResult);
        selectLocationIntent.putExtra(CreateLocationActivity.OBJECT_TO_EDIT, location);
        selectLocationResultLauncher.launch(selectLocationIntent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Do not call super class method here.
        //super.onSaveInstanceState(outState);
    }

    @Override
    public void onGlobalLayout() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) findViewById(R.id.constraint_layout).getLayoutParams();
        BottomSheetBehavior.from(viewPager).setPeekHeight(findViewById(R.id.fragment_user_location_header).getHeight());
        params.height = findViewById(R.id.coordinator_layout).getHeight() - BottomSheetBehavior.from(viewPager).getPeekHeight();
        findViewById(R.id.constraint_layout).setLayoutParams(params);
        mapView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (responseQueryStations == null) return;
                mapView.showFuelStations(responseQueryStations);
            }
        })
        findViewById(R.id.coordinator_layout).getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void execute() {

        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        locationViewModel.findAll().observe(this, this::onChangesLocation);

        selectLocationResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::onActivityResult);

        tankenApi = new TankenApi(this);


        viewPageAdapter = new DynamicViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.submitList(new ArrayList<>(Arrays.asList(UserLocationFragment.newInstance(null,this))));
        viewPager.setAdapter(viewPageAdapter);
        viewPager.addOnPageChangeListener(this);

    }

    private void onChangesLocation(List<Location> locations) {
        if (locations.isEmpty()) {
            startActivityLocationForResult(null, true);
            return;
        }

        List<DefaultLocationFragment> defaultLocationFragments = new ArrayList<>();
        for (Location location : locations) {
            defaultLocationFragments.add(UserLocationFragment.newInstance(location, this));
        }
        viewPageAdapter.submitList(defaultLocationFragments);
    }

    private void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Location location = (Location) result.getData().getSerializableExtra(CreateLocationActivity.OBJECT_TO_EDIT);
            boolean wasEdited = result.getData().getBooleanExtra(CreateLocationActivity.WAS_EDITED, false);
            if (wasEdited) locationViewModel.update(location);
            else locationViewModel.insert(location);
        } else {
            boolean neededResult = result.getData().getBooleanExtra(CreateLocationActivity.NEEDS_RESULT, true);
            if (neededResult) finish();
        }
    }

    @Override
    public void detailStation(String id) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Isn't needed but must be implemented
    }

    @Override
    public void onPageSelected(int position) {
        Location userLocation = ((DefaultLocationFragment) viewPageAdapter.getItem(position)).getUserLocation();
        tankenApi.queryStations(this::onLoad, userLocation.getPosition().getLatitude(), userLocation.getPosition().getLongitude(), userLocation.getRadius());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // Isn't needed but must be implemented
    }

    public void onLoad(ResponseQueryStations responseQueryStations) {
        this.responseQueryStations = responseQueryStations;
        //mapView.showFuelStations(responseQueryStations);
        ((UserLocationFragment) viewPageAdapter.getItem(viewPager.getCurrentItem()))
                .submitList(responseQueryStations.getStations());
    }

    @Override
    public void onDetail(Station station) {

    }*/

}