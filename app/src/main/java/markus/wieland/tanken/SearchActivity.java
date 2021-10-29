package markus.wieland.tanken;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.defaultappelements.uielements.viewpager.ViewPageAdapter;
import markus.wieland.defaultappelements.uielements.viewpager.ViewPageAdapterItem;
import markus.wieland.tanken.ui.FavoritesFragment;
import markus.wieland.tanken.ui.search.LocationAdapter;

public class SearchActivity extends DefaultActivity {

    private RecyclerView locationList;
    private LocationAdapter locationAdapter;
    private ViewPager viewPager;

    public SearchActivity() {
        super(R.layout.activity_search);
    }

    @Override
    public void bindViews() {
        viewPager = findViewById(R.id.viewPager);

        List<ViewPageAdapterItem> items = new ArrayList<>();
        items.add(new ViewPageAdapterItem("LOL", FavoritesFragment.newInstance("Hallo1")));
        items.add(new ViewPageAdapterItem("LOL", FavoritesFragment.newInstance("Hallo2")));
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), items);
        viewPager.setAdapter(viewPageAdapter);
    }

    @Override
    public void initializeViews() {

    }

    @Override
    public void execute() {
        locationAdapter = new LocationAdapter(null);
    }
}