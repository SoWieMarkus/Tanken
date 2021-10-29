package markus.wieland.tanken;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import markus.wieland.defaultappelements.uielements.activities.CreateItemActivity;
import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;

public class CreateLocationActivity extends DefaultActivity {

    private Button search;
    private Button select;

    public CreateLocationActivity() {
        super(R.layout.activity_create_location);
    }

    @Override
    public void bindViews() {
        search = findViewById(R.id.search);
        select = findViewById(R.id.select);
    }

    @Override
    public void initializeViews() {
        search.setOnClickListener(v -> startActivity(new Intent(CreateLocationActivity.this, SearchActivity.class)));
        select.setOnClickListener(v -> startActivity(new Intent(CreateLocationActivity.this, SelectLocationActivity.class)));
    }

    @Override
    public void execute() {

    }
}