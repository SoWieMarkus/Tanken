package markus.wieland.tanken;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.tanken.api.models.location.Coordinate;

public class StartActivity extends DefaultActivity {

    private ImageButton selectLocation;
    private ActivityResultLauncher<Intent> selectLocationResultLauncher;
    private Coordinate currentLocation;
    private TextView location;

    public StartActivity() {
        super(R.layout.activity_start);
    }

    @Override
    public void bindViews() {
        selectLocation = findViewById(R.id.activity_start_select_location);
        location = findViewById(R.id.coordinates);
    }

    @Override
    public void initializeViews() {
        selectLocation.setOnClickListener(this::selectLocation);
    }

    public void selectLocation(View view) {
        Intent selectLocationIntent = new Intent(this, SelectLocationActivity.class);
        selectLocationResultLauncher.launch(selectLocationIntent);
    }

    @Override
    public void execute() {
        selectLocationResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        currentLocation = (Coordinate) result.getData().getSerializableExtra("location");
                        location.setText(currentLocation.getLatitude() + ", " + currentLocation.getLongitude());
                        startActivity(new Intent(this, MainActivity.class).putExtra("TEST", currentLocation));
                    }
                });
    }
}