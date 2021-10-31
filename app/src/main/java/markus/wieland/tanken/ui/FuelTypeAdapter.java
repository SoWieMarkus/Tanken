package markus.wieland.tanken.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

import markus.wieland.defaultappelements.uielements.adapter.DefaultAdapter;
import markus.wieland.defaultappelements.uielements.adapter.DefaultViewHolder;
import markus.wieland.defaultappelements.uielements.adapter.iteractlistener.OnItemClickListener;
import markus.wieland.tanken.R;
import markus.wieland.tanken.api.models.stations.FuelType;

public class FuelTypeAdapter extends DefaultAdapter<FuelTypeObject, FuelTypeAdapter.FuelTypeViewHolder> implements OnItemClickListener<FuelTypeObject> {

    protected final Context context;

    private static final int VIEW_TYPE_SELECTED = 1;
    private static final int VIEW_TYPE_NOT_SELECTED = 0;

    public FuelTypeAdapter(Context context) {
        super(null);
        this.context = context;
        setOnItemInteractListener(this);
        submitList(buildList());
    }

    @NonNull
    @Override
    public FuelTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int id = viewType == VIEW_TYPE_SELECTED ? R.layout.item_fuel_type_selected : R.layout.item_fuel_type;
        return new FuelTypeViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(id, parent, false));
    }

    @Override
    public void onClick(FuelTypeObject fuelType) {
        select(fuelType.getFuelType());
    }

    public void select(FuelType fuelType){
        List<FuelTypeObject> fuelTypeObjects = new ArrayList<>(getList());
        for (FuelTypeObject fuelTypeObject : fuelTypeObjects) {
            fuelTypeObject.setSelected(fuelType.equals(fuelTypeObject.getFuelType()));
        }
        submitList(fuelTypeObjects);
    }

    public FuelType getSelectedFuelType() {
        for (FuelTypeObject fuelTypeObject : getList()) {
            if (fuelTypeObject.isSelected()) return fuelTypeObject.getFuelType();
        }
        throw new IllegalStateException("At least one fuel type has to be selected");
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isSelected() ? VIEW_TYPE_SELECTED : VIEW_TYPE_NOT_SELECTED;
    }

    @Override
    public OnItemClickListener<FuelTypeObject> getOnItemInteractListener() {
        return (OnItemClickListener<FuelTypeObject>) super.getOnItemInteractListener();
    }

    private List<FuelTypeObject> buildList() {
        List<FuelTypeObject> fuelTypeObjects = new ArrayList<>();
        fuelTypeObjects.add(new FuelTypeObject(translate(FuelType.E5), FuelType.E5, true));
        fuelTypeObjects.add(new FuelTypeObject(translate(FuelType.E10), FuelType.E10, false));
        fuelTypeObjects.add(new FuelTypeObject(translate(FuelType.DIESEL), FuelType.DIESEL, false));
        return fuelTypeObjects;
    }

    protected String translate(FuelType fuelType) {
        switch (fuelType) {
            case E5:
                return context.getString(R.string.fuel_type_e5);
            case E10:
                return context.getString(R.string.fuel_type_e10);
            case DIESEL:
                return context.getString(R.string.fuel_type_diesel);
            default:
                return "";
        }
    }

    public class FuelTypeViewHolder extends DefaultViewHolder<FuelTypeObject> {

        private TextView fuelTypeLabel;
        private ConstraintLayout fuelTypeBackground;

        public FuelTypeViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindViews() {
            fuelTypeLabel = findViewById(R.id.item_fuel_type_label);
            fuelTypeBackground = findViewById(R.id.item_fuel_type_background);
        }

        @Override
        public void bindItemToViewHolder(FuelTypeObject fuelType) {
            fuelTypeLabel.setText(fuelType.getLabel());
            fuelTypeBackground.setOnClickListener(v -> getOnItemInteractListener().onClick(fuelType));
        }
    }
}
