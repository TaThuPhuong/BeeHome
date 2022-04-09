package net.fpl.beehome.Adapter.SuCo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.R;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.model.SuCo;

import java.util.ArrayList;

public class SuCoAdapter2 extends RecyclerSwipeAdapter<SuCoAdapter2.SuCoViewHolder> {
    ArrayList<SuCo> arr;
    Context context;
    FirebaseFirestore fb;
    NguoiThue objNguoiThue;

    public SuCoAdapter2(ArrayList<SuCo> arr, NguoiThue objNguoiThue, Context context, FirebaseFirestore fb) {
        this.arr = arr;
        this.context = context;
        this.fb = fb;
        this.objNguoiThue = objNguoiThue;
    }

    @Override
    public SuCoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_suco2, parent, false);

        return new SuCoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SuCoViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        final SuCo objSuCo = arr.get(position);
        final int index = position;

        viewHolder.tv_mota.setText("Mô tả: " + objSuCo.getMoTa());
        viewHolder.tv_ngaybc.setText("Ngày báo cáo: " + objSuCo.getNgayBaoCao());
        viewHolder.tv_phong.setText("Phòng: " + objSuCo.getId_phong());

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));

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

        viewHolder.tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_delete_suco);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_info);
                Button btn_delete = dialog.findViewById(R.id.btn_delete);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel);

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fb.collection(SuCo.TB_NAME).document(objSuCo.getId_suco())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Xóa Thành công", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                        mItemManger.closeAllItems();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Xóa Thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        dialog.dismiss();
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        viewHolder.tv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_info_suco);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_info);
                TextView tv_info = dialog.findViewById(R.id.tv_info_hopdong);
                tv_info.setText("Phòng: " + objSuCo.getId_phong() + "\nNgày: " + objSuCo.getNgayBaoCao() + "\nMô Tả: " + objSuCo.getMoTa());
                mItemManger.closeAllItems();
                dialog.show();
            }
        });

        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public class SuCoViewHolder extends RecyclerView.ViewHolder {
        TextView tv_phong, tv_mota, tv_ngaybc;
        SwipeLayout swipeLayout;
        LinearLayout tv_del, tv_info;

        public SuCoViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_phong = itemView.findViewById(R.id.tv_phong);
            tv_mota = itemView.findViewById(R.id.tv_mota);
            tv_ngaybc = itemView.findViewById(R.id.tv_ngaybc);
            swipeLayout = itemView.findViewById(R.id.swipe);

            tv_del = itemView.findViewById(R.id.tv_delete);
            tv_info = itemView.findViewById(R.id.tv_info);

        }
    }
}
