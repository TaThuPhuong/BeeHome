package net.fpl.beehome.Adapter.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.fpl.beehome.R;
import net.fpl.beehome.model.Mess;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private final Context context;
    ArrayList<Mess> list;

    public MessageAdapter(Context context, ArrayList<Mess> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message,null));
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

    class MessageViewHolder extends RecyclerView.ViewHolder{

        public TextView tvMess;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMess = itemView.findViewById(R.id.tv_mess);
        }
    }
}
