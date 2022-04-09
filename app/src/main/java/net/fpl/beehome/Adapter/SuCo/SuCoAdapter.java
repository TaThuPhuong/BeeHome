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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.R;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.model.SuCo;

import java.util.ArrayList;

public class SuCoAdapter extends RecyclerSwipeAdapter<SuCoAdapter.SuCoViewHolder> {
    ArrayList<SuCo> arr;
    Context context;
    FirebaseFirestore fb;
    NguoiThue objNguoiThue;

    public SuCoAdapter(ArrayList<SuCo> arr, NguoiThue objNguoiThue, Context context, FirebaseFirestore fb) {
        this.arr = arr;
        this.context = context;
        this.fb = fb;
        this.objNguoiThue = objNguoiThue;
    }

    @Override
    public SuCoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_suco, parent, false);

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

        viewHolder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_edit_sc);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_addhd);
                TextInputLayout ed_mota = dialog.findViewById(R.id.ed_mota);
                TextInputLayout ed_ngbao = dialog.findViewById(R.id.ed_ngaybaocao);
                Button btn_bc = dialog.findViewById(R.id.btn_bc);
                TextView tv_phong = dialog.findViewById(R.id.tv_p_d);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel_bc);
                tv_phong.setText(objNguoiThue.getId_phong());


                ed_mota.getEditText().setText(objSuCo.getMoTa());
                ed_ngbao.getEditText().setText(objSuCo.getNgayBaoCao());

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        mItemManger.closeAllItems();
                    }
                });

                btn_bc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (ed_mota.getEditText().getText().toString().length() == 0 || ed_mota.getEditText().getText().toString().length() < 5) {
                            ed_mota.setError("Độ dài ký tự không hợp lệ (5 đến 30 ký tự)");
                            return;
                        } else if (!checkMT(ed_mota.getEditText().getText().toString())) {
                            ed_mota.setError("Bạn đã báo cáo sự cố này rồi");
                            return;
                        }

                        objSuCo.setId_phong(objNguoiThue.getId_phong());
                        objSuCo.setMoTa(ed_mota.getEditText().getText().toString());
                        objSuCo.setNgayBaoCao(ed_ngbao.getEditText().getText().toString());
                        fb.collection(SuCo.TB_NAME).document(objSuCo.getId_suco())
                                .set(objSuCo)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        arr.set(index, objSuCo);
                                        notifyDataSetChanged();
                                        mItemManger.closeAllItems();
                                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                });
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

    public boolean checkMT(String str) {
        for (SuCo objSuCo : arr) {
            if (objSuCo.getMoTa().equalsIgnoreCase(str)) {
                return false;
            }
        }
        return true;
    }

    public class SuCoViewHolder extends RecyclerView.ViewHolder {
        TextView tv_phong, tv_mota, tv_ngaybc;
        SwipeLayout swipeLayout;
        LinearLayout tv_del, tv_edit, tv_info;

        public SuCoViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_phong = itemView.findViewById(R.id.tv_phong);
            tv_mota = itemView.findViewById(R.id.tv_mota);
            tv_ngaybc = itemView.findViewById(R.id.tv_ngaybc);
            swipeLayout = itemView.findViewById(R.id.swipe);

            tv_del = itemView.findViewById(R.id.tv_delete);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            tv_info = itemView.findViewById(R.id.tv_info);

        }
    }
}
