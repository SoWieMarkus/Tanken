package markus.wieland.tanken;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.tanken.api.models.location.Coordinate;
import markus.wieland.tanken.ui.map.MapView;
import markus.wieland.tanken.ui.map.SelectLocationInterface;

public class SelectLocationActivity extends DefaultActivity implements SelectLocationInterface {

    private MapView selectLocationMap;
    private Geocoder geocoder;
    private TextView selectLocationAddress;
    private LinearLayout selectLocationCommit;
    private Coordinate currentLocation;

    public SelectLocationActivity() {
        super(R.layout.activity_select_location);
    }

    @Override
    public void bindViews() {
        selectLocationMap = findViewById(R.id.activity_select_location_map);
        selectLocationAddress = findViewById(R.id.activity_select_location_address);
        selectLocationCommit = findViewById(R.id.activity_select_location_commit);
    }

    @Override
    public void initializeViews() {
        selectLocationMap.addInterfaceToMap(this);
        selectLocationCommit.setOnClickListener(this::commitLocation);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.activity_select_location_title));*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED, new Intent());
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void execute() {
        geocoder = new Geocoder(this);
        currentLocation = new Coordinate(51.0366, 13.7588);
        onSelectLocation(currentLocation.getLatitude(), currentLocation.getLongitude());
    }

    private void commitLocation(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("location", currentLocation);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onSelectLocation(double latitude, double longitude) {

        currentLocation = new Coordinate(latitude, longitude);

        List<Address> addresses = new ArrayList<>();
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses.isEmpty()) {
            selectLocationAddress.setText(latitude + ", " + longitude);
            return;
        }

        selectLocationAddress.setText(buildAddressString(addresses.get(0)));

    }

    private String buildAddressString(@NonNull Address address) {
        List<String> addressLines = new ArrayList<>();
        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            addressLines.add(address.getAddressLine(i));
        }
        return String.join(", ", addressLines); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
    }
}