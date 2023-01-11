package ru.zzbo.concretemobile.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ru.zzbo.concretemobile.gui.fragments.factory_config.CementFragment;
import ru.zzbo.concretemobile.gui.fragments.factory_config.ChemyFragment;
import ru.zzbo.concretemobile.gui.fragments.factory_config.DKFragment;
import ru.zzbo.concretemobile.gui.fragments.factory_config.MixerFragment;
import ru.zzbo.concretemobile.gui.fragments.factory_config.OtherFragment;
import ru.zzbo.concretemobile.gui.fragments.factory_config.SkipLTFragment;
import ru.zzbo.concretemobile.gui.fragments.factory_config.WaterFragment;

/**
 * Адаптер фрагментов - переключение между вкладками в уставках
 */
public class FragmentAdditionalOptionsAdapter extends FragmentStateAdapter {
    public FragmentAdditionalOptionsAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: return new DKFragment();
            case 2: return new WaterFragment();
            case 3: return new ChemyFragment();
            case 4: return new CementFragment();
            case 5: return new MixerFragment();
            case 6: return new SkipLTFragment();
        }
        return new OtherFragment();
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
