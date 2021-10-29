package markus.wieland.tanken.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import markus.wieland.defaultappelements.uielements.adapter.DefaultViewHolder;
import markus.wieland.defaultappelements.uielements.adapter.QueryableAdapter;
import markus.wieland.defaultappelements.uielements.adapter.iteractlistener.OnItemInteractListener;
import markus.wieland.tanken.R;
import markus.wieland.tanken.api.models.location.Location;

public class LocationAdapter extends QueryableAdapter<String, Location, LocationAdapter.LocationViewHolder> {

    public LocationAdapter(OnItemInteractListener<Location> onItemInteractListener) {
        super(onItemInteractListener);
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false));
    }

    public class LocationViewHolder extends DefaultViewHolder<Location> {

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindViews() {

        }

        @Override
        public void bindItemToViewHolder(Location location) {

        }
    }

}
