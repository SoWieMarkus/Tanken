package markus.wieland.tanken.ui.userlocation;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.tanken.R;
import markus.wieland.tanken.SelectLocationActivity;
import markus.wieland.tanken.api.models.location.Position;
import markus.wieland.tanken.ui.FuelTypeAdapter;

public class CreateLocationActivity extends DefaultActivity implements SeekBar.OnSeekBarChangeListener {

    public static final String OBJECT_TO_EDIT = "markus.wieland.tanken.ui.userlocation.CreateLocationActivity.OBJECT_TO_EDIT";
    public static final String NEEDS_RESULT = "markus.wieland.tanken.ui.userlocation.CreateLocationActivity.NEEDS_RESULT";
    public static final String WAS_EDITED = "markus.wieland.tanken.ui.userlocation.CreateLocationActivity.WAS_EDITED";

    private Location location;

    private boolean isEditMode;

    private RecyclerView selectFuelType;
    private FuelTypeAdapter fuelTypeAdapter;
    private TextView textViewLocationPosition;
    private EditText editTextLocationLabel;

    private ActivityResultLauncher<Intent> selectLocationResultLauncher;

    private Position position;
    private Button buttonCommit;
    private SeekBar seekBarLocationRadius;
    private Switch switchLocationOpen;
    private TextView textViewLocationRadiusValue;

    public CreateLocationActivity() {
        super(R.layout.activity_create_location);
    }

    @Override
    public void bindViews() {
        selectFuelType = findViewById(R.id.activity_create_location_select_fuel_type);
        textViewLocationPosition = findViewById(R.id.activity_create_location_position);
        editTextLocationLabel = findViewById(R.id.activity_create_location_label);
        buttonCommit = findViewById(R.id.activity_create_location_commit);
        switchLocationOpen = findViewById(R.id.activity_create_location_open);
        seekBarLocationRadius = findViewById(R.id.activity_create_location_radius);
        textViewLocationRadiusValue = findViewById(R.id.activity_create_location_radius_value);
    }

    @Override
    public void initializeViews() {

        setSupportActionBar(findViewById(R.id.activity_create_location_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectFuelType.setHasFixedSize(true);
        selectFuelType.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        fuelTypeAdapter = new FuelTypeAdapter(this);
        selectFuelType.setAdapter(fuelTypeAdapter);

        textViewLocationPosition.setOnClickListener(this::startPositionActivity);


        seekBarLocationRadius.setOnSeekBarChangeListener(this);

        location = (Location) getIntent().getSerializableExtra(OBJECT_TO_EDIT);
        isEditMode = location != null;
        if (location == null) location = new Location();

        if (isEditMode) initializeViewEditMode();
        else initializeViewAddMode();

        buttonCommit.setOnClickListener(this::commit);

    }

    private void commit(View view) {
        location.setFuelType(fuelTypeAdapter.getSelectedFuelType());
        location.setRadius(seekBarLocationRadius.getProgress());
        location.setOpen(switchLocationOpen.isChecked());

        if (checkLabel() && checkPosition()) {

            location.setPosition(position);
            location.setLabel(getLabel());

            Intent resultIntent = new Intent();
            resultIntent.putExtra(OBJECT_TO_EDIT, location);
            resultIntent.putExtra(NEEDS_RESULT, true);
            resultIntent.putExtra(WAS_EDITED, isEditMode);
            setResult(RESULT_OK, resultIntent);
            finish();
        }

    }

    private String getLabel() {
        return editTextLocationLabel.getText().toString().trim();
    }

    private boolean checkLabel() {
        boolean isEmpty = getLabel().isEmpty();
        findViewById(R.id.activity_create_location_error_label).setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        return !isEmpty;
    }

    private boolean checkPosition() {
        boolean isNull = position == null;
        findViewById(R.id.activity_create_location_error_position).setVisibility(isNull ? View.VISIBLE : View.GONE);
        return !isNull;
    }

    private void startPositionActivity(View view) {
        Intent selectLocationIntent = new Intent(this, SelectLocationActivity.class);
        selectLocationResultLauncher.launch(selectLocationIntent);
    }

    private void initializeViewEditMode() {
        setTitle(R.string.activity_create_location_title_edit);
        position = location.getPosition();
        buttonCommit.setText(R.string.activity_create_location_save);
        switchLocationOpen.setChecked(location.isOpen());
        seekBarLocationRadius.setProgress(location.getRadius());
        textViewLocationRadiusValue.setText(getRadiusInKm(location.getRadius()));
        editTextLocationLabel.setText(location.getLabel());
        textViewLocationPosition.setText(location.getPosition().toString());
        fuelTypeAdapter.select(location.getFuelType());
    }

    private String getRadiusInKm(int radius) {
        return radius + " km";
    }

    private void initializeViewAddMode() {
        setTitle(R.string.activity_create_location_title_add);
        buttonCommit.setText(R.string.activity_create_location_commit);
        seekBarLocationRadius.setProgress(5);
        textViewLocationRadiusValue.setText(getRadiusInKm(5));
    }

    @Override
    public void execute() {
        selectLocationResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::onActivityResult);
    }

    private void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            position = (Position) result.getData().getSerializableExtra("location");
            textViewLocationPosition.setText(position.toString());
            if (editTextLocationLabel.getText().toString().isEmpty() && position.getCity() != null) {
                editTextLocationLabel.setText(position.getCity());
            }
            findViewById(R.id.activity_create_location_error_position).setVisibility(View.GONE);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        textViewLocationRadiusValue.setText(getRadiusInKm(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}