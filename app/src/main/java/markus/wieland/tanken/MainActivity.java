package markus.wieland.tanken;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.defaultappelements.uielements.viewpager.ViewPageAdapter;
import markus.wieland.defaultappelements.uielements.viewpager.ViewPageAdapterItem;
import markus.wieland.tanken.api.TankenApi;
import markus.wieland.tanken.api.models.location.Coordinate;
import markus.wieland.tanken.api.models.stations.FuelType;
import markus.wieland.tanken.api.models.responses.ResponseQueryStationDetail;
import markus.wieland.tanken.api.models.responses.ResponseQueryStations;
import markus.wieland.tanken.api.models.stations.Station;
import markus.wieland.tanken.database.station.StationViewModel;
import markus.wieland.tanken.ui.FuelStationAdapter;
import markus.wieland.tanken.ui.FuelStationInteractionListener;
import markus.wieland.tanken.ui.UserLocationFragment;
import markus.wieland.tanken.ui.createlocation.UserLocation;
import markus.wieland.tanken.ui.map.MapInteractionInterface;
import markus.wieland.tanken.ui.map.MapView;

public class MainActivity extends DefaultActivity implements FuelStationInteractionListener, MapInteractionInterface, ViewTreeObserver.OnGlobalLayoutListener, ViewPager.OnPageChangeListener {

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

    private MapView mapView;
    private TankenApi tankenApi;
    private StationViewModel stationViewModel;
    private Coordinate coordinate;
    private ImageButton addUserLocation;

    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;

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
        addUserLocation = findViewById(R.id.activity_main_floating_button);
    }

    @Override
    public void initializeViews() {
        findViewById(R.id.coordinator_layout).getViewTreeObserver().addOnGlobalLayoutListener(this);
        addUserLocation.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CreateLocationActivity.class)));
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
                //mapView.showMapData(getRoute());
            }
        });

        findViewById(R.id.coordinator_layout).getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void execute() {

        tankenApi = new TankenApi(this);

        UserLocation userLocation = new UserLocation();
        userLocation.setLatitude(51.0366);
        userLocation.setLongitude(13.7588);

        UserLocation userLocation1 = new UserLocation();
        userLocation1.setLatitude(53.280205);
        userLocation1.setLongitude(10.733830);


        List<ViewPageAdapterItem> viewPageAdapterItems = new ArrayList<>();
        viewPageAdapterItems.add(new ViewPageAdapterItem("LOL", UserLocationFragment.newInstance(userLocation, this)));
        viewPageAdapterItems.add(new ViewPageAdapterItem("LOL", UserLocationFragment.newInstance(userLocation1, this)));


        tankenApi.queryStations(this::onLoad, userLocation.getLatitude(), userLocation.getLongitude(), 5);

        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), viewPageAdapterItems);
        viewPager.setAdapter(viewPageAdapter);
        viewPager.addOnPageChangeListener(this);

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
        UserLocation userLocation = ((UserLocationFragment) viewPageAdapter.getItem(position)).getUserLocation();
        //TODO radius
        tankenApi.queryStations(this::onLoad, userLocation.getLatitude(), userLocation.getLongitude(), 5);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // Isn't needed but must be implemented
    }

    public void onLoad(ResponseQueryStations responseQueryStations) {
        mapView.showFuelStations(responseQueryStations);
        ((UserLocationFragment) viewPageAdapter.getItem(viewPager.getCurrentItem())).submitList(responseQueryStations.getStations());
    }

    @Override
    public void onDetail(Station station) {

    }

    /*public static final String REQUEST_COORDINATE = "markus.wieland.tanken.MainActivity.REQUEST_COORDINATE";

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    public void bindViews() {
        mapView = findViewById(R.id.activity_main_map_view);
    }

    @Override
    public void initializeViews() {
        mapView.addInterfaceToMap(this);
        stationViewModel = ViewModelProviders.of(this).get(StationViewModel.class);
    }

    @Override
    public void execute() {

        coordinate = (Coordinate)getIntent().getSerializableExtra(REQUEST_COORDINATE);

        tankenApi = new TankenApi(this);
        stationViewModel.getFavorites().observe(this,this);

        if (coordinate == null) {
            coordinate = new Coordinate(53.280205, 10.733830);
        }
        tankenApi.queryStations(this::onLoad, coordinate.getLatitude(), coordinate.getLongitude(), 5);
    }


    public void onLoad(ResponseQueryStations responseQueryStations) {
        mapView.showFuelStations(responseQueryStations);
    }

    public void onLoad(ResponseQueryStationDetail responseQueryStationDetail) {
        Toast.makeText(this, responseQueryStationDetail.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void detailStation(String id) {
        tankenApi.queryStationDetail(this::onLoad, id);
    }

    @Override
    public void onDetail(Station station) {
        mapView.focusFuelStation(station);
    }


    @Override
    public void onChanged(List<Station> stations) {
        
    }*/
}