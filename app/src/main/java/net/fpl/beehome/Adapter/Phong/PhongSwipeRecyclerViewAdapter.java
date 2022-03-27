package net.fpl.beehome.Adapter.Phong;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import net.fpl.beehome.R;
import net.fpl.beehome.model.Phong;

import java.util.ArrayList;

public class PhongSwipeRecyclerViewAdapter extends RecyclerSwipeAdapter<PhongSwipeRecyclerViewAdapter.SimpleViewHolder> {


    private Context mContext;
    private ArrayList<Phong> lsPhong;

    public PhongSwipeRecyclerViewAdapter(Context context, ArrayList<Phong> lsPhong) {
        this.mContext = context;
        this.lsPhong = lsPhong;
    }
    @SuppressLint("ResourceAsColor")
    public void mauTrangThai(String trangThai, TextView tv) {
        switch (trangThai) {
            case "Đang thuê":
                tv.setTextColor(Color.parseColor("#000000"));
                break;
            case "Trống":
                tv.setTextColor(Color.parseColor("#FF0000"));
                break;
            case "Đang sửa chữa":
                tv.setTextColor(Color.parseColor("#5EA3CD"));
                break;
        }
    }
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phong_swipe, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        final Phong phong = lsPhong.get(position);

        viewHolder.tvPhong.setText(phong.getSoPhong());
        viewHolder.tvTrangThai.setText(phong.getTrangThai());
        mauTrangThai(phong.getTrangThai(), viewHolder.tvTrangThai);


        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        // Drag From Left
//        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        // Drag From Right
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));


        // Handling different events when swiping
        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });

        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
//                studentList.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, studentList.size());
//                mItemManger.closeAllItems();
//                Toast.makeText(view.getContext(), "Deleted " + viewHolder.tvName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(viewHolder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return lsPhong.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
    //  ViewHolder Class

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView tvPhong, tvTrangThai;
        LinearLayout btnEdit, btnDelete;
        ImageButton btnLocation;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            tvPhong = itemView.findViewById(R.id.tv_phong);
            tvTrangThai = itemView.findViewById(R.id.tv_trang_thai);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
//            btnLocation = (ImageButton) itemView.findViewById(R.id.btnLocation);


        }
    }
}