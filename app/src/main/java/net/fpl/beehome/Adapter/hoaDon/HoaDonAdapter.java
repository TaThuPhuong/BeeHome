package net.fpl.beehome.Adapter.hoaDon;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.R;
import net.fpl.beehome.model.DichVu;
import net.fpl.beehome.model.HoaDon;
import net.fpl.beehome.model.HopDong;
import net.fpl.beehome.model.Phong;

import java.util.ArrayList;

public class HoaDonAdapter extends RecyclerSwipeAdapter<HoaDonAdapter.HoaDonViewHolder> {
    ArrayList<HoaDon> arr;
    Context context;
    FirebaseFirestore fb;
    ArrayList<String> arrTenPhong ;
    ArrayList<Phong> arrphong;
    ArrayList<HopDong> arrhopdong;
    ArrayList<DichVu> arrdichvu;


    public HoaDonAdapter(ArrayList<HoaDon> arr, Context context, FirebaseFirestore fb) {
        this.arr = arr;
        this.context = context;
        this.fb = fb;
    }

    @Override
    public HoaDonAdapter.HoaDonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chua_thanh_toan, parent, false);
        return new HoaDonAdapter.HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HoaDonAdapter.HoaDonViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        final HoaDon objHoaDon = arr.get(position);
        final int index = position;

        viewHolder.tongHD.setText(objHoaDon.getTongHD()+"");
        viewHolder.phong.setText(objHoaDon.getIDPhong());
        viewHolder.tienNha.setText(objHoaDon.getTienPhong()+"");
        viewHolder.tienDv.setText(objHoaDon.getTienDV()+"");
        viewHolder.giamGia.setText(objHoaDon.getGiamGia()+"");

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper_hd));

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
                dialog.setContentView(R.layout.dialog_hoa_don_xoa);
                Button btn_delete = dialog.findViewById(R.id.btn_yes);
                Button btn_cancel = dialog.findViewById(R.id.btn_no);
                ProgressBar progressBar = dialog.findViewById(R.id.progress_loadconfirm);
                TextView txt_Massage = dialog.findViewById(R.id.txt_Titleconfirm);
                progressBar.setVisibility(View.INVISIBLE);
                txt_Massage.setText("Bạn có muốn xóa hóa đơn "+objHoaDon.getIDHoaDon()+" hay không ? ");
                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        fb.collection(HoaDon.TB_NAME).document(objHoaDon.getIDHoaDon())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        txt_Massage.setText("");
                                        progressBar.setVisibility(View.VISIBLE);
                                        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
                                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                        mItemManger.closeAllItems();
                                        dialog.dismiss();

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
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_hoa_don_info);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_info);
                TextView idPhong = dialog.findViewById(R.id.tv_idPhongHD);
                TextView thangHD = dialog.findViewById(R.id.tv_thangHD);
                TextView tienPhong = dialog.findViewById(R.id.tv_tienPhongHD);
                TextView tienDV = dialog.findViewById(R.id.tv_tienDvHD);
                TextView tienDien = dialog.findViewById(R.id.tv_tienDien);
                TextView tienNuoc = dialog.findViewById(R.id.tv_tienNuoc);
                TextView tienDVchung = dialog.findViewById(R.id.tv_tienDVCHung);
                TextView giamGia = dialog.findViewById(R.id.tv_giamGiaHD);
                TextView tongHD = dialog.findViewById(R.id.tv_tongHD);
                TextView hanHD = dialog.findViewById(R.id.tv_hanHD);
                TextView trangThai = dialog.findViewById(R.id.tv_trangThaiHD);
                TextView ngayGD = dialog.findViewById(R.id.tv_ngayThanhToanHD);
                TextView ghiChu = dialog.findViewById(R.id.tv_ghiChuHD);

                idPhong.setText(objHoaDon.getIDPhong());
                thangHD.setText(objHoaDon.getThangHD());
                tienPhong.setText(objHoaDon.getTienPhong()+"");
                tienDV.setText(objHoaDon.getTienDV()+"");
                giamGia.setText(objHoaDon.getGiamGia()+"");
                tongHD.setText(objHoaDon.getTongHD()+"");
                hanHD.setText(objHoaDon.getHanGD());
                if(objHoaDon.getTrangThaiHD() == 0){
                    trangThai.setText("Chưa Thanh Toán");
                    trangThai.setTextColor(R.color.red);
                }else {
                    trangThai.setText("Đã Thanh Toán");
                }

                ngayGD.setText(objHoaDon.getNgayGD());
                ghiChu.setText(objHoaDon.getGhiChu());
                thangHD.setText(objHoaDon.getThangHD());
                tienDien.setText(objHoaDon.getTienDien()+"");
                tienNuoc.setText(objHoaDon.getTienNuoc()+"");
                tienDVchung.setText(objHoaDon.getTienDVC()+"");


                dialog.show();
                mItemManger.closeAllItems();
            }
        });
        viewHolder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_hoa_don_sua);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_addhd);
                dialog.show();

                //                ánh xạ
                Spinner sp_phong = dialog.findViewById(R.id.spn_Phong);
                TextInputLayout L_hd_thang = dialog.findViewById(R.id.L_thang_hd);
                TextInputLayout L_hd_han = dialog.findViewById(R.id.L_han_hd);
                TextInputLayout L_dien_moi = dialog.findViewById(R.id.L_dien_moi);
                TextInputLayout L_nuoc_moi = dialog.findViewById(R.id.L_nuoc_moi);
                TextInputLayout L_giam_gia = dialog.findViewById(R.id.L_giamGia);


                TextInputEditText hd_thang = dialog.findViewById(R.id.thang_hd);
                TextInputEditText hd_han = dialog.findViewById(R.id.han_hd);
                TextInputEditText dien_cu = dialog.findViewById(R.id.dien_cu);
                TextInputEditText dien_moi = dialog.findViewById(R.id.dien_moi);
                TextInputEditText nuoc_cu = dialog.findViewById(R.id.nuoc_cu);
                TextInputEditText nuoc_moi = dialog.findViewById(R.id.nuoc_moi);
                TextInputEditText tienPhong = dialog.findViewById(R.id.tienPhong);
                TextInputEditText tienDV = dialog.findViewById(R.id.tienDV);
                TextInputEditText giamGia = dialog.findViewById(R.id.giamGia);

                LinearLayout lnLDien = dialog.findViewById(R.id.lnLDien);
                LinearLayout lnLNuoc = dialog.findViewById(R.id.lnLNuoc);

                ImageButton chotDien = dialog.findViewById(R.id.chotDien);
                ImageButton chotNuoc = dialog.findViewById(R.id.chotNuoc);

                TextView tvDien = dialog.findViewById(R.id.tvDien);
                TextView tvNuoc = dialog.findViewById(R.id.tvNuoc);
                TextView errAll = dialog.findViewById(R.id.errTongHop);
                MaterialTextView tvTongHop = dialog.findViewById(R.id.tongHop);

                TextView tongTien = dialog.findViewById(R.id.tongTien);
                TextInputLayout ghiChu = dialog.findViewById(R.id.ghiChuHD);

                Button clear = dialog.findViewById(R.id.btnClear);
                Button add = dialog.findViewById(R.id.btnAddHd);


                hd_thang.setError(null);
                hd_han.setError(null);
                L_dien_moi.setError(null);
                L_nuoc_moi.setError(null);
                nuoc_moi.setError(null);
                L_giam_gia.setError(null);


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
        return R.id.swipe_hd;
    }

    public class HoaDonViewHolder extends RecyclerView.ViewHolder {
        TextView tongHD,phong,tienNha,tienDv,giamGia;
        SwipeLayout swipeLayout;
        LinearLayout tv_del, tv_edit, tv_info;
        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tongHD = itemView.findViewById(R.id.tv_tongHD);
            phong = itemView.findViewById(R.id.tv_idPhongHD);
            tienNha = itemView.findViewById(R.id.tv_tienPhongHD);
            tienDv = itemView.findViewById(R.id.tv_tienDvHD);
            giamGia = itemView.findViewById(R.id.tv_giamGiaHD);

            swipeLayout =itemView.findViewById(R.id.swipe_hd);

            tv_del = itemView.findViewById(R.id.tv_delete);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            tv_info = itemView.findViewById(R.id.tv_info);
        }
    }

    public ArrayList<String> getTenPhong(){
        ArrayList<String> arrTenPhong = new ArrayList<>();
        fb.collection(Phong.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arrTenPhong.clear();
                for(QueryDocumentSnapshot document : value){
                    Phong objPhong = document.toObject(Phong.class);
                    if(objPhong.getTrangThai().equalsIgnoreCase("Đang Thuê")) {
                        arrTenPhong.add(objPhong.getIDPhong());
                    }
                }
            }
        });
        return arrTenPhong;
    }



}
