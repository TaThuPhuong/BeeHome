package net.fpl.beehome.Adapter.LienHe;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import net.fpl.beehome.MessageActivity;
import net.fpl.beehome.R;
import net.fpl.beehome.model.Admin;
import net.fpl.beehome.model.NguoiThue;

import java.util.ArrayList;

public class ContactAdminAdapter extends RecyclerView.Adapter<ContactAdminAdapter.ContacAdminViewHolder> {

    private static final int REQUEST_CALL = 1;
    private final int COUNTDOWN_RUNNING_TIME = 500;
    NguoiThue nguoiThue;
    private ArrayList<Admin> list;
    private Context context;
    private Animation animationUp, animationDown;
    public ContactAdminAdapter(ArrayList<Admin> list, Context context, NguoiThue nguoiThue) {
        this.list = list;
        this.context = context;
        this.nguoiThue = nguoiThue;
    }

    @NonNull
    @Override
    public ContacAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_contact, parent, false);
        return new ContacAdminViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull ContacAdminViewHolder holder, int position) {
        Admin admin = this.list.get(position);
        holder.tvNamead.setText(admin.getHoTen());
        holder.tvPhonead.setText(admin.getSdt());
        holder.imgCallad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + list.get(holder.getAdapterPosition()).getSdt()));
                    context.startActivity(intent);
                }
            }
        });
        holder.imgMessagead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("admin_nhan", admin);
                intent.putExtra("user_gui", nguoiThue);
                Log.e("TAG", "onClick: " + admin);
                context.startActivity(intent);
            }
        });
        holder.layoutItemad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animationDown = AnimationUtils.loadAnimation(view.getContext(), R.anim.down);
                animationUp = AnimationUtils.loadAnimation(view.getContext(), R.anim.up);

                if (holder.layoutExpandad.isShown()) {
                    holder.layoutExpandad.startAnimation(animationUp);
                    CountDownTimer countDownTimer = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            holder.layoutExpandad.setVisibility(View.GONE);
                        }
                    };
                    countDownTimer.start();
                } else {

                    holder.layoutExpandad.setVisibility(View.VISIBLE);
                    holder.layoutExpandad.startAnimation(animationDown);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ContacAdminViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNamead, tvPhonead;
        private LinearLayout layoutItemad, layoutExpandad;
        private ImageView imgCallad, imgMessagead;

        public ContacAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamead = itemView.findViewById(R.id.tv_name);
            tvPhonead = itemView.findViewById(R.id.tv_email);
            layoutItemad = itemView.findViewById(R.id.layout_item);
            layoutExpandad = itemView.findViewById(R.id.layout_expand);
            imgMessagead = itemView.findViewById(R.id.img_message);
            imgCallad = itemView.findViewById(R.id.img_call);
        }
    }
}
