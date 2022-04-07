package net.fpl.beehome.Adapter.Message;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.fpl.beehome.R;
import net.fpl.beehome.model.Mess;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    ArrayList<Mess> list;
    private String email;
    public static final int VIEW_TYPE_SEND = 1;
    public static final int VIEW_TYPE_RECIEVED = 2;

    public MessageAdapter( ArrayList<Mess> list, String email) {
        this.list = list;
        this.email = email;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_send_message, null));
        } else {
            return new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_receiver_message, null));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Mess mess = list.get(position);

        holder.tvMess.setText(mess.getMess());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getSdt().equals(email)) {
            return VIEW_TYPE_SEND;
        } else {
            return VIEW_TYPE_RECIEVED;
        }
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{

        public TextView tvMess;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMess = itemView.findViewById(R.id.tv_mess);
        }
    }
}
