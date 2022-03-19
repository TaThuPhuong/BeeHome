package net.fpl.beehome.ui.doiMatKhau;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.fpl.beehome.R;

public class DoiMatKhauFragment extends Fragment {
    LinearLayout lnL;
    Animation animation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        lnL = v.findViewById(R.id.aniLogin);

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.login);
        lnL.startAnimation(animation);

        return v;
    }
}
