package markus.wieland.tanken.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import markus.wieland.tanken.ui.userlocation.fragments.DefaultLocationFragment;

public class DynamicViewPageAdapter extends FragmentPagerAdapter {

    private final List<DefaultLocationFragment> items;

    public DynamicViewPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
        items = new ArrayList<>();
    }

    public void submitList(List<DefaultLocationFragment> defaultLocationFragment) {
        this.items.clear();
        this.items.addAll(defaultLocationFragment);
        notifyDataSetChanged();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    @NonNull
    @Override
    public DefaultLocationFragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
