package net.fpl.beehome.Adapter.NguoiThue;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.Adapter.HopDong.SpinnerPhongAdapter;
import net.fpl.beehome.DAO.NguoiThueDAO;
import net.fpl.beehome.R;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.model.Phong;
import net.fpl.beehome.model.SuCo;

import java.util.ArrayList;

public class NguoiThueSwip extends RecyclerSwipeAdapter<NguoiThueSwip.NguoiThueViewHolder> {
    ArrayList<NguoiThue> arr;
    Context context;
    NguoiThueDAO nguoiThueDAO;
    FirebaseFirestore fb;

    public NguoiThueSwip(ArrayList<NguoiThue> arr, Context context, FirebaseFirestore fb) {
        this.arr = arr;
        this.context = context;
        this.fb = fb;
    }

    @Override
    public NguoiThueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_nguoithue, parent, false);

        return new NguoiThueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NguoiThueViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        final NguoiThue objNguoiThue = arr.get(position);

        viewHolder.tv_tennguoithue.setText("Tên : " + objNguoiThue.getHoTen());
        viewHolder.tv_phongnguoithue.setText("Phòng : "+objNguoiThue.getID_phong());
        viewHolder.tv_sdtnguoithue.setText("SĐT : "+ objNguoiThue.getSDT());

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
                dialog.setContentView(R.layout.dialog_delete_nguoithue);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_info);
                Button btn_delete = dialog.findViewById(R.id.btn_deletenguoithue);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancelnguoithue);

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fb.collection(NguoiThue.TB_NGUOITHUE).document(objNguoiThue.getID_thanhvien())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        nguoiThueDAO.thongbaonguoithue(1,"Xóa Thành Công");
                                        notifyDataSetChanged();
                                        mItemManger.closeAllItems();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        nguoiThueDAO.thongbaonguoithue(0,"Xóa Thất Bại");
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

        viewHolder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_sua_nguoithue);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_addhd);

                dialog.show();
            }
        });

        viewHolder.tv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_info_nguoithue);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_info);
                TextView tv_info = dialog.findViewById(R.id.tv_info_nguoithue);
                tv_info.setText("Tên: "+objNguoiThue.getHoTen() + "\nPhòng : " +objNguoiThue.getID_phong() + "\nEmail : "+ objNguoiThue.getEmail() + "\nSĐT : "+objNguoiThue.getSDT() + "\nCCCD : "+objNguoiThue.getCCCD());
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

    public class NguoiThueViewHolder extends RecyclerView.ViewHolder{
        TextView tv_tennguoithue, tv_phongnguoithue, tv_sdtnguoithue;
        SwipeLayout swipeLayout;
        LinearLayout tv_del, tv_edit, tv_info;
        public NguoiThueViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tennguoithue = itemView.findViewById(R.id.tv_tennguoithue);
            tv_phongnguoithue = itemView.findViewById(R.id.tv_phongnguoithue);
            tv_sdtnguoithue = itemView.findViewById(R.id.tv_sdtnguoithue);
            swipeLayout =itemView.findViewById(R.id.swipe);

            tv_del = itemView.findViewById(R.id.tv_deletenguoithue);
            tv_edit = itemView.findViewById(R.id.tv_editnguoithue);
            tv_info = itemView.findViewById(R.id.tv_infonguoithue);

        }
    }
}
