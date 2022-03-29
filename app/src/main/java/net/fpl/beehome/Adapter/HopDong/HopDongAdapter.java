package net.fpl.beehome.Adapter.HopDong;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.Adapter.SuCo.SuCoAdapter;
import net.fpl.beehome.HopDongActivity;
import net.fpl.beehome.R;
import net.fpl.beehome.model.HopDong;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.model.Phong;
import net.fpl.beehome.model.SuCo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HopDongAdapter extends RecyclerSwipeAdapter<HopDongAdapter.HopDongViewHolder> {
    ArrayList<HopDong> arr;
    Context context;
    FirebaseFirestore fb;
    ArrayList<Phong> arrphong;
    ArrayList<NguoiThue> arrnguoithue;

    public HopDongAdapter(ArrayList<HopDong> arr, Context context, FirebaseFirestore fb, ArrayList<Phong> arrphong, ArrayList<NguoiThue> arrnguoithue) {
        this.arr = arr;
        this.context = context;
        this.fb = fb;
        this.arrphong = arrphong;
        this.arrnguoithue = arrnguoithue;
    }

    @Override
    public HopDongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_hopdong, parent, false);
        return new HopDongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HopDongViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        final HopDong objHopDong = arr.get(position);
        final int index = position;

        viewHolder.tv_idphong.setText("Phòng: "+ objHopDong.getId_phong());
        viewHolder.tv_ngayky.setText("Ngày ký: " + objHopDong.getNgayKiHD());
        viewHolder.tv_ngaybd.setText("Ngày Bắt Đầu: "+objHopDong.getNgayBatDau());
        viewHolder.tv_ngaykt.setText("Ngày Kết Thúc: "+objHopDong.getNgayKetThuc());

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
                dialog.setContentView(R.layout.dialog_delete_hopdong);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_info);
                Button btn_delete = dialog.findViewById(R.id.btn_delete);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel);

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fb.collection(Phong.TB_NAME).document(objHopDong.getId_phong()).update(Phong.COL_TRANG_THAI, "Trống");
                        fb.collection(HopDong.TB_NAME).document(objHopDong.getId_hop_dong())
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
                tv_info.setText("Phòng: "+ objHopDong.getId_phong() +"\nThành viên: "+ objHopDong.getId_thanh_vien()+"\nKỳ hạn: "+ objHopDong.getKyHan()+"\nNgày ký: " + objHopDong.getNgayKiHD() + "\nNgày bắt đầu: "+objHopDong.getNgayBatDau()+ "\nNgày kết thúc: " +objHopDong.getNgayKetThuc()+"\nSố người thuê: "+ objHopDong.getSoNguoiThue());
                dialog.show();
                mItemManger.closeAllItems();
            }
        });
        viewHolder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_edit_hopdong);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_addhd);
                Spinner sp_phong = dialog.findViewById(R.id.sp_hd_phong);
                Spinner sp_tvien = dialog.findViewById(R.id.sp_hd_tvien);
                Button btn_add = dialog.findViewById(R.id.btn_add);
                TextInputLayout ed_kyhan = dialog.findViewById(R.id.ed_kyhan);
                TextInputLayout ed_ngayky = dialog.findViewById(R.id.ed_ngaykyhd);
                TextInputLayout ed_ngaybd = dialog.findViewById(R.id.ed_ngaybd);
                TextInputLayout ed_ngaykt = dialog.findViewById(R.id.ed_ngaykt);
                TextInputLayout ed_songuoithue = dialog.findViewById(R.id.ed_songuoithue);

                ed_kyhan.setError(null);
                ed_ngaybd.setError(null);
                ed_ngaykt.setError(null);
                ed_ngayky.setError(null);
                ed_songuoithue.setError(null);

                SpinnerPhongAdapter phongAdapter = new SpinnerPhongAdapter(arrphong);
                sp_phong.setAdapter(phongAdapter);
                SpinnerNguoiThueAdapter nguoiThueAdapter = new SpinnerNguoiThueAdapter(arrnguoithue);
                sp_tvien.setAdapter(nguoiThueAdapter);


                Calendar calendar = Calendar.getInstance();
                final int y = calendar.get(Calendar.YEAR);
                final int m = calendar.get(Calendar.MONTH);
                final int d = calendar.get(Calendar.DAY_OF_MONTH);
                ed_ngayky.getEditText().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog dialog1 = new DatePickerDialog(context, R.style.datePicker , new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String date = i +"-" + i1 +"-" + i2;
                                ed_ngayky.getEditText().setText(date);
                            }
                        },y,m,d);
                        dialog1.show();
                    }
                });
                ed_ngaybd.getEditText().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog dialog1 = new DatePickerDialog(context, R.style.datePicker , new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String date = i +"-" + i1 +"-" + i2;
                                ed_ngaybd.getEditText().setText(date);
                            }
                        },y,m,d);
                        dialog1.show();
                    }
                });
                ed_ngaykt.getEditText().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog dialog1 = new DatePickerDialog(context, R.style.datePicker , new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String date = i +"-" + i1 +"-" + i2;
                                ed_ngaykt.getEditText().setText(date);
                            }
                        },y,m,d);
                        dialog1.show();
                    }
                });

                ed_kyhan.getEditText().setText(objHopDong.getKyHan());
                ed_ngayky.getEditText().setText(objHopDong.getNgayKiHD());
                ed_ngaykt.getEditText().setText(objHopDong.getNgayKetThuc());
                ed_ngaybd.getEditText().setText(objHopDong.getNgayBatDau());
                ed_songuoithue.getEditText().setText(objHopDong.getSoNguoiThue().toString());

                for(int j = 0; j <arrphong.size(); j++){
                    Phong tmp = arrphong.get(j);
                    if(tmp.getIDPhong() == objHopDong.getId_phong()){
                        sp_phong.setSelection(j);
                        sp_phong.setSelected(true);
                        break;
                    }
                }
                for(int j = 0; j <arrnguoithue.size(); j++){
                    NguoiThue tmp = arrnguoithue.get(j);
                    if(tmp.getID_thanhvien() == objHopDong.getId_thanh_vien()){
                        sp_tvien.setSelection(j);
                        sp_tvien.setSelected(true);
                        break;
                    }
                }

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String edkyhan = ed_kyhan.getEditText().getText().toString();
                        String ednbd = ed_ngaybd.getEditText().getText().toString();
                        String ednkt = ed_ngaykt.getEditText().getText().toString();
                        String ednky = ed_ngayky.getEditText().getText().toString();
                        String edsn = ed_songuoithue.getEditText().getText().toString();

                        if(edkyhan.length() == 0){
                            ed_kyhan.setError("Trường không được bỏ trống");
                            return;
                        }else if(ednky.length()==0){
                            ed_ngayky.setError("Trường không được bỏ trống");
                            return;
                        }else if(ednbd.length()==0){
                            ed_ngaybd.setError("Trường không được bỏ trống");
                            return;
                        }else if(ednkt.length()==0){
                            ed_ngaykt.setError("Trường không được bỏ trống");
                            return;
                        }else if(edsn.length()==0){
                            ed_songuoithue.setError("Trường không được bỏ trống");
                            return;
                        }else if(!checkDateFormat(ednky)){
                            ed_ngayky.setError("Sai định dạng");
                            return;
                        }else if(!checkDateFormat(ednbd)){
                            ed_ngaybd.setError("Sai định dạng");
                            return;
                        }else if(!checkDateFormat(ednkt)){
                            ed_ngaykt.setError("Sai định dạng");
                            return;
                        }

                        Phong objPhong = (Phong) sp_phong.getSelectedItem();
                        NguoiThue objNguoiThue = (NguoiThue) sp_tvien.getSelectedItem();
                        objHopDong.setId_chu_tro("1");
                        objHopDong.setId_phong(objPhong.getIDPhong());
                        objHopDong.setId_thanh_vien(objNguoiThue.getID_thanhvien());
                        objHopDong.setKyHan(ed_kyhan.getEditText().getText().toString());
                        objHopDong.setNgayKiHD(ed_ngayky.getEditText().getText().toString());
                        objHopDong.setNgayBatDau(ed_ngaybd.getEditText().getText().toString());
                        objHopDong.setNgayKetThuc(ed_ngaykt.getEditText().getText().toString());
                        objHopDong.setSoNguoiThue(Double.parseDouble(ed_songuoithue.getEditText().getText().toString()));

                        fb.collection(HopDong.TB_NAME).document(objHopDong.getId_hop_dong())
                                .set(objHopDong)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        arr.set(index, objHopDong);
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

    public class HopDongViewHolder extends RecyclerView.ViewHolder {
        TextView tv_idphong, tv_ngayky, tv_ngaybd, tv_ngaykt;
        SwipeLayout swipeLayout;
        LinearLayout tv_del, tv_edit, tv_info;
        public HopDongViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_idphong = itemView.findViewById(R.id.tv_idphong);
            tv_ngayky = itemView.findViewById(R.id.tv_ngayky);
            tv_ngaybd = itemView.findViewById(R.id.tv_ngaybatdau);
            tv_ngaykt = itemView.findViewById(R.id.tv_ngayketthuc);
            swipeLayout =itemView.findViewById(R.id.swipe);

            tv_del = itemView.findViewById(R.id.tv_delete);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            tv_info = itemView.findViewById(R.id.tv_info);
        }
    }

    public Boolean checkDateFormat(String date){
        if (date == null || !date.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$"))
            return false;
        SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd");
        try {
            format.parse(date);
            return true;
        }catch (ParseException e){
            return false;
        }
    }

}
