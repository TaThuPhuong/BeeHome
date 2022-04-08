package net.fpl.beehome.Adapter.HopDong;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.fpl.beehome.R;
import net.fpl.beehome.model.HoaDon;
import net.fpl.beehome.model.HopDong;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.model.Phong;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HopDongAdapter extends RecyclerSwipeAdapter<HopDongAdapter.HopDongViewHolder> implements Filterable {
    ArrayList<HopDong> arr;
    ArrayList<HopDong> arr1;

    Context context;
    FirebaseFirestore fb;
    ArrayList<Phong> arrphong;
    ArrayList<NguoiThue> arrnguoithue;


    public HopDongAdapter(ArrayList<HopDong> arr, Context context, FirebaseFirestore fb, ArrayList<Phong> arrphong, ArrayList<NguoiThue> arrnguoithue) {
        this.arr = arr;
        this.arr1 = arr;
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
                ArrayList<HoaDon> arrhd = getListHD(objHopDong.getId_phong());
                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(HoaDon objHoaDon : arrhd){
                            if(objHoaDon.getTrangThaiHD() != 1){
                                Toast.makeText(view.getContext(), "Vui lòng thanh toán hóa đơn trước khi xóa", Toast.LENGTH_SHORT).show();
                                mItemManger.closeAllItems();
                                dialog.dismiss();
                                return;
                            }
                        }


                        mItemManger.closeAllItems();
                        fb.collection(Phong.TB_NAME).document(objHopDong.getId_phong()).update(Phong.COL_TRANG_THAI, "Trống");
                        fb.collection(NguoiThue.TB_NGUOITHUE).document(objHopDong.getId_thanh_vien()).update(NguoiThue.COL_ID_PHONG, "Trống");
                        for(HoaDon objHoaDon : arrhd){
                            fb.collection(HoaDon.TB_NAME).document(objHoaDon.getIDHoaDon()).delete();
                        }
                        fb.collection(HopDong.TB_NAME).document(objHopDong.getId_hop_dong())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Xóa Thành công", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
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
                tv_info.setText("Phòng: "+ objHopDong.getId_phong() +"\nSĐT người thuê: "+ objHopDong.getId_thanh_vien() +"\nKỳ hạn: "+ objHopDong.getKyHan()+"\nNgày ký: " + objHopDong.getNgayKiHD() + "\nNgày bắt đầu: "+objHopDong.getNgayBatDau()+ "\nNgày kết thúc: " +objHopDong.getNgayKetThuc()+"\nSố người thuê: "+ objHopDong.getSoNguoiThue());
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
                Spinner sp_kyhan = dialog.findViewById(R.id.sp_hd_kyhan);

                Button btn_add = dialog.findViewById(R.id.btn_add);
                TextInputLayout ed_ngayky = dialog.findViewById(R.id.ed_ngaykyhd);
                TextInputLayout ed_ngaybd = dialog.findViewById(R.id.ed_ngaybd);
                TextInputLayout ed_ngaykt = dialog.findViewById(R.id.ed_ngaykt);
                TextInputLayout ed_songuoithue = dialog.findViewById(R.id.ed_songuoithue);
                TextView tv_er_p = dialog.findViewById(R.id.tv_er_phong);
                TextView tv_er_ngthue = dialog.findViewById(R.id.tv_er_ngthue);
                TextView tv_p_d = dialog.findViewById(R.id.tv_p_d);
                TextView tv_p_nt = dialog.findViewById(R.id.tv_p_nt);

                tv_p_d.setText(objHopDong.getId_phong());

                ed_ngaybd.setError(null);
                ed_ngaykt.setError(null);
                ed_ngayky.setError(null);
                ed_songuoithue.setError(null);

                for (NguoiThue objNguoiThue : arrnguoithue){
                    if(objNguoiThue.getId_phong().equalsIgnoreCase(objHopDong.getId_thanh_vien())){
                        tv_p_nt.setText(objNguoiThue.getId_phong());
                    }
                }

                Calendar calendar = Calendar.getInstance();
                final int y = calendar.get(Calendar.YEAR);
                final int m = calendar.get(Calendar.MONTH);
                final int d = calendar.get(Calendar.DAY_OF_MONTH);

                ArrayList<Integer> arrkyhan = new ArrayList<>(); arrkyhan.add(1); arrkyhan.add(3); arrkyhan.add(6); arrkyhan.add(12);
                ArrayAdapter arrayAdapter = new ArrayAdapter(dialog.getContext(), android.R.layout.simple_spinner_dropdown_item, arrkyhan);
                sp_kyhan.setAdapter(arrayAdapter);

                ed_ngayky.getEditText().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog dialog1 = new DatePickerDialog(context, R.style.datePicker , new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                i1=i1+1;
                                String monthbd = String.valueOf(i1);
                                String daybd = String.valueOf(i2);

                                if(String.valueOf(i1).length() == 1){
                                    monthbd = "0"+i1;
                                }
                                if(String.valueOf(i2).length() == 1){
                                    daybd = "0"+i2;
                                }

                                ed_ngayky.getEditText().setText(i + "-" +monthbd + "-" + daybd);
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
                                i1=i1+1;
                                String monthbd = String.valueOf(i1);
                                String daybd = String.valueOf(i2);

                                if(String.valueOf(i1).length() == 1){
                                    monthbd = "0"+i1;
                                }
                                if(String.valueOf(i2).length() == 1){
                                    daybd = "0"+i2;
                                }

                                ed_ngaybd.getEditText().setText(i + "-" +monthbd + "-" + daybd);

                                if(Integer.parseInt(sp_kyhan.getSelectedItem()+"")+i1 > 12){
                                    int imonthkt = (Integer.parseInt(sp_kyhan.getSelectedItem()+"")+i1) - 12 ;
                                    int iyearkt = i+1;
                                    String daykt = String.valueOf(i2);
                                    String monthkt = String.valueOf(imonthkt);

                                    if(String.valueOf(imonthkt).length() == 1){
                                        monthkt = "0"+monthkt;
                                    }
                                    if(String.valueOf(i2).length() == 1){
                                        daykt = "0"+i2;
                                    }
                                    ed_ngaykt.getEditText().setText(iyearkt + "-" +monthkt + "-" + daykt);
                                }else{
                                    int imonthkt = Integer.parseInt(sp_kyhan.getSelectedItem()+"") + i1;
                                    String daykt = String.valueOf(i2);
                                    String monthkt = String.valueOf(imonthkt);
                                    if(String.valueOf(imonthkt).length() == 1){
                                        monthkt = "0"+monthkt;
                                    }
                                    if(String.valueOf(i2).length() == 1){
                                        daykt = "0"+i2;
                                    }
                                    ed_ngaykt.getEditText().setText(i + "-" +monthkt + "-" + daykt);

                                }
                                sp_kyhan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        DatePickerDialog dialog1 = new DatePickerDialog(context , R.style.datePicker , new DatePickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                                String monthbd = String.valueOf(i1);
                                                String daybd = String.valueOf(i2);

                                                if(String.valueOf(i1).length() == 1){
                                                    monthbd = "0"+i1;
                                                }
                                                if(String.valueOf(i2).length() == 1){
                                                    daybd = "0"+i2;
                                                }

                                                ed_ngaybd.getEditText().setText(i + "-" +monthbd + "-" + daybd);

                                                if(Integer.parseInt(sp_kyhan.getSelectedItem()+"")+i1 > 12){
                                                    int imonthkt = (Integer.parseInt(sp_kyhan.getSelectedItem()+"")+i1) - 12 ;
                                                    int iyearkt = i+1;
                                                    String daykt = String.valueOf(i2);
                                                    String monthkt = String.valueOf(imonthkt);

                                                    if(String.valueOf(imonthkt).length() == 1){
                                                        monthkt = "0"+monthkt;
                                                    }
                                                    if(String.valueOf(i2).length() == 1){
                                                        daykt = "0"+i2;
                                                    }
                                                    ed_ngaykt.getEditText().setText(iyearkt + "-" +monthkt + "-" + daykt);
                                                }else{
                                                    int imonthkt = Integer.parseInt(sp_kyhan.getSelectedItem()+"") + i1;
                                                    String daykt = String.valueOf(i2);
                                                    String monthkt = String.valueOf(imonthkt);
                                                    if(String.valueOf(imonthkt).length() == 1){
                                                        monthkt = "0"+monthkt;
                                                    }
                                                    if(String.valueOf(i2).length() == 1){
                                                        daykt = "0"+i2;
                                                    }
                                                    ed_ngaykt.getEditText().setText(i + "-" +monthkt + "-" + daykt);

                                                }
                                            }
                                        },y,m,d);
                                        dialog1.show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                            }
                        },y,m,d);
                        dialog1.show();
                    }
                });

                ed_ngayky.getEditText().setText(objHopDong.getNgayKiHD());
                ed_ngaykt.getEditText().setText(objHopDong.getNgayKetThuc());
                ed_ngaybd.getEditText().setText(objHopDong.getNgayBatDau());
                ed_songuoithue.getEditText().setText(objHopDong.getSoNguoiThue().toString());


                for(int j = 0; j <arrkyhan.size(); j++){
                    int tmp = arrkyhan.get(j);
                    if(tmp == objHopDong.getKyHan()){
                        sp_kyhan.setSelection(j);
                        sp_kyhan.setSelected(true);
                        break;
                    }
                }

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ednbd = ed_ngaybd.getEditText().getText().toString();
                        String ednkt = ed_ngaykt.getEditText().getText().toString();
                        String ednky = ed_ngayky.getEditText().getText().toString();
                        String edsn = ed_songuoithue.getEditText().getText().toString();

                        if(ednky.length()==0){
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

                        objHopDong.setId_chu_tro("1");
                        objHopDong.setId_phong(tv_p_d.getText().toString());
                        objHopDong.setId_thanh_vien(tv_p_nt.getText().toString());
                        objHopDong.setKyHan(Integer.parseInt(sp_kyhan.getSelectedItem()+""));
                        objHopDong.setNgayKiHD(ed_ngayky.getEditText().getText().toString());
                        objHopDong.setNgayBatDau(ed_ngaybd.getEditText().getText().toString());
                        objHopDong.setNgayKetThuc(ed_ngaykt.getEditText().getText().toString());
                        objHopDong.setSoNguoiThue(Integer.parseInt(ed_songuoithue.getEditText().getText().toString()));

                        fb.collection(HopDong.TB_NAME).document(objHopDong.getId_hop_dong())
                                .set(objHopDong)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        fb.collection(Phong.TB_NAME).document(objHopDong.getId_phong())
                                                .update(Phong.COL_TRANG_THAI, "Đang thuê");
                                        fb.collection(NguoiThue.TB_NGUOITHUE).document(objHopDong.getId_thanh_vien())
                                                .update(NguoiThue.COL_ID_PHONG, objHopDong.getId_phong());
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


    public ArrayList<HoaDon> getListHD(String str){
        ArrayList<HoaDon> arr = new ArrayList<>();
        fb.collection(HoaDon.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arr.clear();
                for(QueryDocumentSnapshot document : value){
                    HoaDon objHoaDon = document.toObject(HoaDon.class);
                    if(objHoaDon.getIDPhong().equalsIgnoreCase(str)){
                        arr.add(objHoaDon);
                        notifyDataSetChanged();
                    }
                }
            }
        });
        return arr;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();
                if (search.isEmpty()) {
                    arr = arr1;
                } else {
                    ArrayList<HopDong> arrhd = new ArrayList<>();
                    for (HopDong objHopDong : arr1) {
                        if (objHopDong.getId_phong().toLowerCase().contains(search.toLowerCase())) {
                            arrhd.add(objHopDong);
                        }
                    }
                    arr = arrhd;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arr;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arr = (ArrayList<HopDong>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
