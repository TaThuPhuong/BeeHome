package net.fpl.beehome.Adapter.hoaDon;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.R;
import net.fpl.beehome.model.DichVu;
import net.fpl.beehome.model.HoaDon;
import net.fpl.beehome.model.HoaDonChiTiet;
import net.fpl.beehome.model.HopDong;
import net.fpl.beehome.model.Phong;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HoaDonAdapter extends RecyclerSwipeAdapter<HoaDonAdapter.HoaDonViewHolder> {
    final NumberFormat formatter = new DecimalFormat("#,###,###,###");
    ArrayList<HoaDonChiTiet> arrHDCT;
    ArrayList<HoaDon> arr;
    Context context;
    FirebaseFirestore fb;
    ArrayList<String> arrTenPhong;
    ArrayList<Phong> arrPhong;
    ArrayList<HopDong> arrhopdong;
    ArrayList<DichVu> arrDichVu;
    SimpleDateFormat dfm = new SimpleDateFormat("dd/MM/yyyy");
    String tenP;
    int tienDVPhong = 0;

    public HoaDonAdapter(ArrayList<HoaDon> arr, Context context, FirebaseFirestore fb, ArrayList<String> arrTenPhong, ArrayList<Phong> arrPhong, ArrayList<HopDong> arrhopdong, ArrayList<DichVu> arrDichVu, ArrayList<HoaDonChiTiet> arrHDCT) {
        this.arr = arr;
        this.context = context;
        this.fb = fb;
        this.arrTenPhong = arrTenPhong;
        this.arrPhong = arrPhong;
        this.arrhopdong = arrhopdong;
        this.arrDichVu = arrDichVu;
        this.arrHDCT = arrHDCT;
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


        if (objHoaDon.getTrangThaiHD() == 1) {
            viewHolder.tv_edit.setVisibility(View.INVISIBLE);
        }

        viewHolder.tongHD.setText(formatter.format(objHoaDon.getTongHD()) + " VNĐ");
        viewHolder.phong.setText("Phòng: " + objHoaDon.getIDPhong());
        viewHolder.hdthang.setText(dfm.format(objHoaDon.getThangHD()));
        if (objHoaDon.getTrangThaiHD() == 0) {
            viewHolder.trangthai.setText("Chưa thanh toán");
            viewHolder.trangthai.setTextColor(Color.parseColor("#cd2457"));
        } else if (objHoaDon.getTrangThaiHD() == 1) {
            viewHolder.trangthai.setText("Đã thanh toán");
            viewHolder.trangthai.setTextColor(Color.parseColor("#92db64"));
        } else {
            viewHolder.trangthai.setText("Quá hạn");
            viewHolder.trangthai.setTextColor(Color.parseColor("#cd2457"));
        }

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
                mItemManger.closeAllItems();
                Dialog dialog = new Dialog(context, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_hoa_don_xoa);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_info);
                Button btn_delete = dialog.findViewById(R.id.btn_yes);
                Button btn_cancel = dialog.findViewById(R.id.btn_no);
                ArrayList<HoaDonChiTiet> arrHDCT = getListHDCT(objHoaDon.getIDHoaDon());
                ProgressBar progressBar = dialog.findViewById(R.id.progress_loadconfirm);
                TextView txt_Massage = dialog.findViewById(R.id.txt_Titleconfirm);
                progressBar.setVisibility(View.INVISIBLE);
                txt_Massage.setText("Bạn có muốn xóa hóa đơn " + objHoaDon.getIDHoaDon() + " hay không ? ");


                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (HoaDonChiTiet objHoaDonChiTiet : arrHDCT) {
                            fb.collection(HoaDonChiTiet.TB_NAME).document(objHoaDonChiTiet.getIDHoaDonCT()).delete();
                        }
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
                mItemManger.closeAllItems();
                Dialog dialog = new Dialog(context, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_hoa_don_info);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_addhd);
                TextView idPhong = dialog.findViewById(R.id.tv_idPhongHD);
                TextView thangHD = dialog.findViewById(R.id.tv_thangHD);
                TextView tienPhong = dialog.findViewById(R.id.tv_tienPhongHD);
                TextView tienDien = dialog.findViewById(R.id.tv_tienDien);
                TextView tienNuoc = dialog.findViewById(R.id.tv_tienNuoc);
                TextView tienDVchung = dialog.findViewById(R.id.tv_tienDVCHung);
                TextView giamGia = dialog.findViewById(R.id.tv_giamGiaHD);
                TextView tongHD = dialog.findViewById(R.id.tv_tongHD);
                TextView hanHD = dialog.findViewById(R.id.tv_hanHD);
                TextView trangThai = dialog.findViewById(R.id.tv_trangThaiHD);
                TextView ngayGD = dialog.findViewById(R.id.tv_ngayThanhToanHD);
                TextView ghiChu = dialog.findViewById(R.id.tv_ghiChuHD);

                ArrayList<HoaDonChiTiet> arrHoaDonChiTiet = new ArrayList<>();
                for (int z = 0; z < arrHDCT.size(); z++) {
                    if (arrHDCT.get(z).getIDHoaDon().equalsIgnoreCase(objHoaDon.getIDHoaDon())) {
                        arrHoaDonChiTiet.add(arrHDCT.get(z));
                    }
                }

                Log.d("arrHDCT", "mang " + arrHoaDonChiTiet);


                idPhong.setText("Phòng: " + objHoaDon.getIDPhong());
                tienPhong.setText(formatter.format(objHoaDon.getTienPhong()) + "");
                giamGia.setText(formatter.format(objHoaDon.getGiamGia()) + "");
                tongHD.setText(formatter.format(objHoaDon.getTongHD()) + "");
                if (objHoaDon.getTrangThaiHD() == 0) {
                    trangThai.setText("Chưa Thanh Toán");
                    trangThai.setTextColor(R.color.bg_del);
                    ngayGD.setText("UnPaid");
                } else if (objHoaDon.getTrangThaiHD() == 1) {
                    trangThai.setTextColor(Color.parseColor("#92db64"));
                    trangThai.setText("Đã Thanh Toán");
                    ngayGD.setText(dfm.format(objHoaDon.getNgayGD()));
                } else {
                    trangThai.setText("Quá Hạn Thanh Toán");
                    trangThai.setTextColor(R.color.bg_del);
                    ngayGD.setText("UnPaid");
                }


                thangHD.setText("HĐ tháng: " + dfm.format(objHoaDon.getThangHD()));
                hanHD.setText(dfm.format(objHoaDon.getHanGD()));

                for (HoaDonChiTiet obj : arrHoaDonChiTiet) {
                    if (obj.getTenDichVu().equalsIgnoreCase("Điện")) {
                        tienDien.setText(formatter.format(obj.getThanhTien()) + "");
                    }

                    if (obj.getTenDichVu().equalsIgnoreCase("Nước")) {
                        tienNuoc.setText(formatter.format(obj.getThanhTien()) + "");
                    }
                }

                tienDVchung.setText(formatter.format(objHoaDon.getTienDVC()) + "");
                ghiChu.setText(objHoaDon.getGhiChu());
                if (ghiChu.getText().toString().length() < 1) {
                    ghiChu.setText("...");
                }

                dialog.show();
                mItemManger.closeAllItems();
            }
        });
        viewHolder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(R.layout.dialog_hoa_don_sua);
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_addhd);
                dialog.show();

                //                ánh xạ
                TextInputLayout L_giam_gia = dialog.findViewById(R.id.L_giamGia);


                TextInputEditText hd_thang = dialog.findViewById(R.id.thang_hd);
                TextInputEditText hd_han = dialog.findViewById(R.id.han_hd);
                TextInputEditText tienPhong = dialog.findViewById(R.id.tienPhong);
                TextInputEditText tienDV = dialog.findViewById(R.id.tienDV);
                TextInputEditText giamGia = dialog.findViewById(R.id.giamGia);
                TextInputEditText idP = dialog.findViewById(R.id.tenP);

                TextView tongTien = dialog.findViewById(R.id.tongTien);
                TextInputLayout ghiChu = dialog.findViewById(R.id.ghiChuHD);

                CheckBox tinhTrang = dialog.findViewById(R.id.tinhTrang);

                Button clear = dialog.findViewById(R.id.btnClear);
                Button add = dialog.findViewById(R.id.btnAddHd);


                hd_thang.setError(null);
                hd_han.setError(null);
                L_giam_gia.setError(null);

                idP.setText(objHoaDon.getIDPhong());
                tenP = idP.getText().toString();
                for (int x = 0; x < arrPhong.size(); x++) {
                    if (arrPhong.get(x).getIDPhong().equals(objHoaDon.getIDPhong())) {
                        Phong objPhong = arrPhong.get(x);
                        tienPhong.setText(objPhong.getGiaPhong() + "");
                    }
                }

                hd_thang.setText(dfm.format(objHoaDon.getThangHD()));

                hd_han.setText(dfm.format(objHoaDon.getHanGD()));

                giamGia.setText(objHoaDon.getGiamGia() + "");
                ghiChu.getEditText().setText(objHoaDon.getGhiChu());

                tongTien.setText(objHoaDon.getTongHD() + "");

                Calendar calendar = Calendar.getInstance();
                final int y = calendar.get(Calendar.YEAR);
                final int m = calendar.get(Calendar.MONTH);
                final int d = calendar.get(Calendar.DAY_OF_MONTH);

                for (int z = 0; z < arrDichVu.size(); z++) {
                    if (arrDichVu.get(z).getDonVi().equals("Phòng")) {
                        tienDVPhong += arrDichVu.get(z).getGia();
                    }
                }

                tienDV.setText(objHoaDon.getTienDV() + "");

//                hd_thang.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        DatePickerDialog datePickerDialog = new DatePickerDialog(context,R.style.datePicker,new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                                final String thangHD = dayOfMonth + "/" + (month + 1) + "/" + year;
//                                month_thang = month + 1;
//                                year_thang = year;
//                                hd_thang.setText(thangHD);
//                                thang = hd_thang.getText().toString();
//                            }
//                        }, y, m, d);
//                        datePickerDialog.show();
//                    }
//                });

//                chotDien.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if(!dien_moi.getText().toString().isEmpty()){
//                            lnLDien.setVisibility(VISIBLE);
//                            Log.d("zzzzzz", "tienDien " + arrDichVu);
//                            for(int x =0; x<arrDichVu.size();x++){
//                                if(arrDichVu.get(x).getTenDichVu().equals("Điện")){
//                                    tienSoDien = arrDichVu.get(x).getGia();
//                                }
//                            }
//                            dienMoi = Integer.parseInt(String.valueOf(dien_moi.getText()));
//                            int dienCu = Integer.parseInt(String.valueOf(dien_cu.getText()));
//                            if(dienMoi < dienCu){
//                                L_dien_moi.setError("Số điện mới phải > số điện cũ");
//                            }else {
//                                L_dien_moi.setError(null);
//                                int sLDien = dienMoi - dienCu;
//
//                                tienDien = sLDien * tienSoDien;
//                                tvDien.setText(tienDien+"");
//
//                                TongtienDV = tienDien+tienNuoc+tienDVPhong;
//                                tienDV.setText(TongtienDV+"");
//                            }
//
////
//
//
//                        }
//
//                    }
//                });
//
//                chotNuoc.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        L_nuoc_moi.setError(null);
//                        if(!nuoc_moi.getText().toString().isEmpty()){
//                            lnLNuoc.setVisibility(VISIBLE);
//                            for(int x =0; x<arrDichVu.size();x++){
//                                if(arrDichVu.get(x).getTenDichVu().equals("Nước")){
//                                    tienSoNuoc = arrDichVu.get(x).getGia();
//                                }
//                            }
//                            int nuocCu = Integer.parseInt(String.valueOf(nuoc_cu.getText()));
//                            nuocMoi = Integer.parseInt(String.valueOf(nuoc_moi.getText()));
//                            int sLNuoc = nuocMoi - nuocCu;
//
//                            tienNuoc = sLNuoc * tienSoNuoc;
//
//                            tvNuoc.setText(tienNuoc+"");
//                            TongtienDV = tienDien+tienNuoc+tienDVPhong;
//                            tienDV.setText(TongtienDV+"");
//                        }
//
//                    }
//                });

//                tvTongHop.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        errAll.setVisibility(View.INVISIBLE);
//                        if(giamGia.getText().toString().isEmpty()){
//                            L_giam_gia.setError("Nếu không có hãy nhập 0");
//                        }else if (lnLDien.getVisibility() != VISIBLE ) {
//                            L_dien_moi.setError("Hãy chốt điện!");
//                            L_giam_gia.setError(null);
//                        }else if(lnLNuoc.getVisibility() != VISIBLE){
//                            L_giam_gia.setError(null);
//                            L_nuoc_moi.setError("Hãy chốt nước!");
//                        }else{
//                            L_giam_gia.setError(null);
//                            TongtienDV = tienDien+tienNuoc+tienDVPhong;
//                            tienDV.setText(TongtienDV+"");
//                            int gGia = Integer.parseInt(String.valueOf(giamGia.getText()));
//                            tongTienPhong = Integer.parseInt(String.valueOf(tienPhong.getText()));
//                            tongHD = TongtienDV+tongTienPhong-gGia;
//                            tongTien.setText(tongHD+"");
//
//                        }
//
//                    }
//                });


                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HoaDon hoaDon = new HoaDon();
                        hoaDon.setIDHoaDon(objHoaDon.getIDHoaDon());
                        hoaDon.setTongHD(objHoaDon.getTongHD());
                        hoaDon.setIDPhong(objHoaDon.getIDPhong());
                        hoaDon.setThangHD(objHoaDon.getThangHD());
                        hoaDon.setHanGD(objHoaDon.getHanGD());
                        hoaDon.setSoDienCuoi(objHoaDon.getSoDienCuoi());
                        hoaDon.setSoNuocCuoi(objHoaDon.getSoNuocCuoi());
                        if (tinhTrang.isChecked()) {
                            hoaDon.setTrangThaiHD(1);
                            hoaDon.setNgayGD(calendar.getTime());
                        }

                        hoaDon.setTienDV(objHoaDon.getTienDV());
                        hoaDon.setTienPhong(objHoaDon.getTienPhong());
                        hoaDon.setGiamGia(objHoaDon.getGiamGia());
                        hoaDon.setGhiChu(String.valueOf(ghiChu.getEditText().getText()));
                        hoaDon.setTienDVC(objHoaDon.getTienDVC());


                        fb.collection(HoaDon.TB_NAME).document(objHoaDon.getIDHoaDon())
                                .set(hoaDon)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Map<String, Object> p = new HashMap<>();
                                        p.put(Phong.COL_SO_DIEN_DAU, objHoaDon.getSoDienCuoi());
                                        p.put(Phong.COL_SO_NUOC_DAU, objHoaDon.getSoNuocCuoi());
                                        fb.collection(Phong.TB_NAME).document(tenP).update(p);
                                        notifyDataSetChanged();
                                        mItemManger.closeAllItems();
                                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
                        notifyDataSetChanged();
                    }

                });
                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

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

    public ArrayList<HoaDonChiTiet> getListHDCT(String str) {
        ArrayList<HoaDonChiTiet> arr = new ArrayList<>();
        fb.collection(HoaDonChiTiet.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arr.clear();
                for (QueryDocumentSnapshot document : value) {
                    HoaDonChiTiet objHoaDonChiTiet = document.toObject(HoaDonChiTiet.class);
                    if (str.equalsIgnoreCase(objHoaDonChiTiet.getIDHoaDon())) {
                        arr.add(objHoaDonChiTiet);
                        notifyDataSetChanged();
                    }
                }
            }
        });
        return arr;
    }

    public class HoaDonViewHolder extends RecyclerView.ViewHolder {
        TextView tongHD, phong, hdthang, trangthai;
        SwipeLayout swipeLayout;
        LinearLayout tv_del, tv_edit, tv_info;

        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tongHD = itemView.findViewById(R.id.tv_tongHD);
            phong = itemView.findViewById(R.id.tv_idPhongHD);
            hdthang = itemView.findViewById(R.id.tv_hdthang);
            trangthai = itemView.findViewById(R.id.tv_tt);

            swipeLayout = itemView.findViewById(R.id.swipe_hd);

            tv_del = itemView.findViewById(R.id.tv_delete);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            tv_info = itemView.findViewById(R.id.tv_info);
        }
    }

}
