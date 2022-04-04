package net.fpl.beehome.Adapter.LienHe;

import android.app.Activity;
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
import androidx.recyclerview.widget.RecyclerView;

import net.fpl.beehome.R;
import net.fpl.beehome.model.LienHe;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MessageViewHolder> {

    private ArrayList<LienHe> list;
    private Animation animationUp, animationDown;
    private final int COUNTDOWN_RUNNING_TIME = 500;

    public ContactAdapter(ArrayList<LienHe> list, Animation animationUp, Animation animationDown) {
        this.list = list;
        this.animationDown = animationDown;
        this.animationUp = animationUp;
        notifyDataSetChanged();
    }

    public ContactAdapter(ArrayList<LienHe> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        LienHe lienHe = list.get(position);

        holder.tvName.setText(lienHe.getName());
        holder.tvPhone.setText(lienHe.getNumberPhone());
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

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvPhone;
        private LinearLayout layoutItem,layoutExpand;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            layoutItem = itemView.findViewById(R.id.layout_item);
            layoutExpand = itemView.findViewById(R.id.layout_expand);
        }
    }

}
