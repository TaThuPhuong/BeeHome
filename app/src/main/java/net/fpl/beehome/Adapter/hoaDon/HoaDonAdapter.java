package net.fpl.beehome.Adapter.hoaDon;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import net.fpl.beehome.model.DichVu;
import net.fpl.beehome.model.HoaDon;
import net.fpl.beehome.model.HopDong;
import net.fpl.beehome.model.Phong;

import java.util.ArrayList;

public class HoaDonAdapter extends RecyclerSwipeAdapter<HoaDonAdapter.HoaDonViewHolder> {
    ArrayList<HoaDon> arr;
    Context context;
    FirebaseFirestore fb;
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
//        viewHolder.tv_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Dialog dialog = new Dialog(context, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
//                dialog.setContentView(R.layout.dialog_edit_hopdong);
//                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_addhd);
//                Spinner sp_phong = dialog.findViewById(R.id.sp_hd_phong);
//                Spinner sp_tvien = dialog.findViewById(R.id.sp_hd_tvien);
//                Button btn_add = dialog.findViewById(R.id.btn_add);
//                TextInputLayout ed_kyhan = dialog.findViewById(R.id.ed_kyhan);
//                TextInputLayout ed_ngayky = dialog.findViewById(R.id.ed_ngaykyhd);
//                TextInputLayout ed_ngaybd = dialog.findViewById(R.id.ed_ngaybd);
//                TextInputLayout ed_ngaykt = dialog.findViewById(R.id.ed_ngaykt);
//                TextInputLayout ed_songuoithue = dialog.findViewById(R.id.ed_songuoithue);
//
//                ed_kyhan.setError(null);
//                ed_ngaybd.setError(null);
//                ed_ngaykt.setError(null);
//                ed_ngayky.setError(null);
//                ed_songuoithue.setError(null);
//
//                SpinnerPhongAdapter phongAdapter = new SpinnerPhongAdapter(arrphong);
//                sp_phong.setAdapter(phongAdapter);
//                SpinnerNguoiThueAdapter nguoiThueAdapter = new SpinnerNguoiThueAdapter(arrnguoithue);
//                sp_tvien.setAdapter(nguoiThueAdapter);
//
//
//                Calendar calendar = Calendar.getInstance();
//                final int y = calendar.get(Calendar.YEAR);
//                final int m = calendar.get(Calendar.MONTH);
//                final int d = calendar.get(Calendar.DAY_OF_MONTH);
//                ed_ngayky.getEditText().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        DatePickerDialog dialog1 = new DatePickerDialog(context, R.style.datePicker , new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                                String date = i +"-" + i1 +"-" + i2;
//                                ed_ngayky.getEditText().setText(date);
//                            }
//                        },y,m,d);
//                        dialog1.show();
//                    }
//                });
//                ed_ngaybd.getEditText().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        DatePickerDialog dialog1 = new DatePickerDialog(context, R.style.datePicker , new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                                String date = i +"-" + i1 +"-" + i2;
//                                ed_ngaybd.getEditText().setText(date);
//                            }
//                        },y,m,d);
//                        dialog1.show();
//                    }
//                });
//                ed_ngaykt.getEditText().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        DatePickerDialog dialog1 = new DatePickerDialog(context, R.style.datePicker , new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                                String date = i +"-" + i1 +"-" + i2;
//                                ed_ngaykt.getEditText().setText(date);
//                            }
//                        },y,m,d);
//                        dialog1.show();
//                    }
//                });
//
//                ed_kyhan.getEditText().setText(objHoaDon.getKyHan());
//                ed_ngayky.getEditText().setText(objHoaDon.getNgayKiHD());
//                ed_ngaykt.getEditText().setText(objHoaDon.getNgayKetThuc());
//                ed_ngaybd.getEditText().setText(objHoaDon.getNgayBatDau());
//                ed_songuoithue.getEditText().setText(objHoaDon.getSoNguoiThue().toString());
//
//                for(int j = 0; j <arrphong.size(); j++){
//                    Phong tmp = arrphong.get(j);
//                    if(tmp.getIDPhong() == objHoaDon.getId_phong()){
//                        sp_phong.setSelection(j);
//                        sp_phong.setSelected(true);
//                        break;
//                    }
//                }
//                for(int j = 0; j <arrnguoithue.size(); j++){
//                    NguoiThue tmp = arrnguoithue.get(j);
//                    if(tmp.getID_thanhvien() == objHoaDon.getId_thanh_vien()){
//                        sp_tvien.setSelection(j);
//                        sp_tvien.setSelected(true);
//                        break;
//                    }
//                }
//
//                btn_add.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        String edkyhan = ed_kyhan.getEditText().getText().toString();
//                        String ednbd = ed_ngaybd.getEditText().getText().toString();
//                        String ednkt = ed_ngaykt.getEditText().getText().toString();
//                        String ednky = ed_ngayky.getEditText().getText().toString();
//                        String edsn = ed_songuoithue.getEditText().getText().toString();
//
//                        if(edkyhan.length() == 0){
//                            ed_kyhan.setError("Trường không được bỏ trống");
//                            return;
//                        }else if(ednky.length()==0){
//                            ed_ngayky.setError("Trường không được bỏ trống");
//                            return;
//                        }else if(ednbd.length()==0){
//                            ed_ngaybd.setError("Trường không được bỏ trống");
//                            return;
//                        }else if(ednkt.length()==0){
//                            ed_ngaykt.setError("Trường không được bỏ trống");
//                            return;
//                        }else if(edsn.length()==0){
//                            ed_songuoithue.setError("Trường không được bỏ trống");
//                            return;
//                        }else if(!checkDateFormat(ednky)){
//                            ed_ngayky.setError("Sai định dạng");
//                            return;
//                        }else if(!checkDateFormat(ednbd)){
//                            ed_ngaybd.setError("Sai định dạng");
//                            return;
//                        }else if(!checkDateFormat(ednkt)){
//                            ed_ngaykt.setError("Sai định dạng");
//                            return;
//                        }
//
//                        Phong objPhong = (Phong) sp_phong.getSelectedItem();
//                        NguoiThue objNguoiThue = (NguoiThue) sp_tvien.getSelectedItem();
//                        objHoaDon.setId_chu_tro("1");
//                        objHoaDon.setId_phong(objPhong.getIDPhong());
//                        objHoaDon.setId_thanh_vien(objNguoiThue.getID_thanhvien());
//                        objHoaDon.setKyHan(ed_kyhan.getEditText().getText().toString());
//                        objHoaDon.setNgayKiHD(ed_ngayky.getEditText().getText().toString());
//                        objHoaDon.setNgayBatDau(ed_ngaybd.getEditText().getText().toString());
//                        objHoaDon.setNgayKetThuc(ed_ngaykt.getEditText().getText().toString());
//                        objHoaDon.setSoNguoiThue(Double.parseDouble(ed_songuoithue.getEditText().getText().toString()));
//
//                        fb.collection(HopDong.TB_NAME).document(objHoaDon.getId_hop_dong())
//                                .set(objHoaDon)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        arr.set(index, objHoaDon);
//                                        notifyDataSetChanged();
//                                        mItemManger.closeAllItems();
//                                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//            }
//        });
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



}
