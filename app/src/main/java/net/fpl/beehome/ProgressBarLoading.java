package net.fpl.beehome;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class ProgressBarLoading {
    Context context;
    Dialog dialog;

    public ProgressBarLoading(Context context) {
        this.context = context;
    }

    public void showLoading() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView img = dialog.findViewById(R.id.imglogo);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.bounce);
        img.startAnimation(animation);
        dialog.show();
    }

    public void hideLoaing() {
        dialog.dismiss();
    }
}
