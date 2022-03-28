package net.fpl.beehome.Adapter.Phong;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.R;
import net.fpl.beehome.model.Phong;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PhongSwipeRecyclerViewAdapter extends RecyclerSwipeAdapter<PhongSwipeRecyclerViewAdapter.SimpleViewHolder> {


    private Context mContext;
    private ArrayList<Phong> lsPhong;
    private FirebaseFirestore fb;

    public PhongSwipeRecyclerViewAdapter(Context context, ArrayList<Phong> lsPhong, FirebaseFirestore fb) {
        this.mContext = context;
        this.lsPhong = lsPhong;
        this.fb = fb;
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

        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(mContext, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_xoa);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                TextView tvDelete = dialog.findViewById(R.id.tv_co);
                TextView tvCancel = dialog.findViewById(R.id.tv_khong);
                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fb.collection(Phong.TB_NAME).document(phong.getIDPhong())
                                .delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(mContext, "Xóa Thành công", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                        mItemManger.closeAllItems();
                                        dialog.dismiss();
                                    }
                                });
                    }
                });
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });
        viewHolder.tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        LinearLayout tvInfo, tvEdit, tvDelete;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            tvPhong = itemView.findViewById(R.id.tv_phong);
            tvTrangThai = itemView.findViewById(R.id.tv_trang_thai);
            tvInfo = itemView.findViewById(R.id.tv_info);
            tvEdit = itemView.findViewById(R.id.tv_edit);
            tvDelete = itemView.findViewById(R.id.tv_delete);
        }
    }
}