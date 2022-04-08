package net.fpl.beehome.Adapter.LienHe;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.CountDownTimer;
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

public class ContactUserAdapter extends RecyclerView.Adapter<ContactUserAdapter.ContactUserViewHolder> {

    private ArrayList<NguoiThue> list;
    private Animation animationUp, animationDown;
    Admin admin;
    private final int COUNTDOWN_RUNNING_TIME = 500;
    private static final int REQUEST_CALL = 1;
    private Context context;

    public ContactUserAdapter(ArrayList<NguoiThue> list, Admin admin, Context context) {
        this.list = list;
        this.admin = admin;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_contact, parent, false);
            return new ContactUserViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ContactUserViewHolder holder, int position) {
        NguoiThue user = this.list.get(position);

        holder.tvName.setText(user.getHoTen());
        holder.tvPhone.setText(user.getSdt());
            String phone = holder.tvPhone.getText().toString();
        holder.imgMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MessageActivity.class);
                    intent.putExtra("user_nhan", user);
                    intent.putExtra("admin_gui", admin);
                    context.startActivity(intent);
                }
            });
        holder.imgCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                    } else {
                        String dial = "tel: " + phone;
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:"+list.get(holder.getAdapterPosition()).getSdt()));
                        context.startActivity(intent);
                    }
                }
            });
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    animationDown = AnimationUtils.loadAnimation(view.getContext(), R.anim.down);
                    animationUp = AnimationUtils.loadAnimation(view.getContext(), R.anim.up);

                    if(holder.layoutExpand.isShown()){
                        holder.layoutExpand.startAnimation(animationUp);
                        CountDownTimer countDownTimer = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                holder.layoutExpand.setVisibility(View.GONE);
                            }
                        };
                        countDownTimer.start();
                    } else {

                        holder.layoutExpand.setVisibility(View.VISIBLE);
                        holder.layoutExpand.startAnimation(animationDown);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ContactUserViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvPhone;
        private LinearLayout layoutItem,layoutExpand;
        private ImageView imgCall, imgMessage;

        public ContactUserViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            layoutItem = itemView.findViewById(R.id.layout_item);
            layoutExpand = itemView.findViewById(R.id.layout_expand);
            imgCall = itemView.findViewById(R.id.img_call);
            imgMessage = itemView.findViewById(R.id.img_message);
        }
    }

}
