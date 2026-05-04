package com.androidide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.androidide.ui.CodeEditorFragment;
import com.androidide.ui.LayoutPreviewFragment;

public class EditorPagerAdapter extends FragmentStateAdapter {

    public EditorPagerAdapter(@NonNull AppCompatActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CodeEditorFragment();
            case 1:
                return new LayoutPreviewFragment();
            default:
                return new CodeEditorFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
