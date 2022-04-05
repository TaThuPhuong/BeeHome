package net.fpl.beehome.Adapter.hoaDon;

import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.R;
import net.fpl.beehome.model.DichVu;
import net.fpl.beehome.model.HoaDon;
import net.fpl.beehome.model.HopDong;
import net.fpl.beehome.model.Phong;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HoaDonNguoiThueAdapter extends RecyclerSwipeAdapter<HoaDonNguoiThueAdapter.HoaDonViewHolder> {
    ArrayList<HoaDon> arr;
    Context context;
    FirebaseFirestore fb;
    ArrayList<String> arrTenPhong ;
    ArrayList<Phong> arrPhong;
    ArrayList<HopDong> arrhopdong;
    ArrayList<DichVu> arrDichVu;
    SimpleDateFormat dfm = new SimpleDateFormat("dd/MM/yyyy");
    String tenP;
    String thang,han,ngayGD;
    int tienSoDien, tienSoNuoc,tienNuoc,tienDien,tongTienPhong,TongtienDV,tongHD,dienMoi,nuocMoi, tienDVPhong = 0,month_han,year_han,month_thang,year_thang;


    public HoaDonNguoiThueAdapter(ArrayList<HoaDon> arr, Context context, FirebaseFirestore fb, ArrayList<String> arrTenPhong, ArrayList<Phong> arrPhong, ArrayList<HopDong> arrhopdong, ArrayList<DichVu> arrDichVu) {
        this.arr = arr;
        this.context = context;
        this.fb = fb;
        this.arrTenPhong = arrTenPhong;
        this.arrPhong = arrPhong;
        this.arrhopdong = arrhopdong;
        this.arrDichVu = arrDichVu;
    }

    @Override
    public HoaDonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoa_don_nguoi_dung, parent, false);
        return new HoaDonNguoiThueAdapter.HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HoaDonViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
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
                tienPhong.setText(objHoaDon.getTienPhong()+"");
                tienDV.setText(objHoaDon.getTienDV()+"");
                giamGia.setText(objHoaDon.getGiamGia()+"");
                tongHD.setText(objHoaDon.getTongHD()+"");
                if(objHoaDon.getTrangThaiHD() == 0){
                    trangThai.setText("Chưa Thanh Toán");
                    trangThai.setTextColor(R.color.red);
                    ngayGD.setText("UnPaid");
                }else if(objHoaDon.getTrangThaiHD() == 1) {
                    trangThai.setText("Đã Thanh Toán");
                    ngayGD.setText(dfm.format(objHoaDon.getNgayGD()));
                }else {
                    trangThai.setText("Quá Hạn Thanh Toán");
                }

                ghiChu.setText(objHoaDon.getGhiChu());
                thangHD.setText(dfm.format(objHoaDon.getThangHD()));
                hanHD.setText(dfm.format(objHoaDon.getHanGD()));
                tienDien.setText(objHoaDon.getTienDien()+"");
                tienNuoc.setText(objHoaDon.getTienNuoc()+"");
                tienDVchung.setText(objHoaDon.getTienDVC()+"");


                dialog.show();
                mItemManger.closeAllItems();
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
        LinearLayout tv_info;
        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tongHD = itemView.findViewById(R.id.tv_tongHD);
            phong = itemView.findViewById(R.id.tv_idPhongHD);
            tienNha = itemView.findViewById(R.id.tv_tienPhongHD);
            tienDv = itemView.findViewById(R.id.tv_tienDvHD);
            giamGia = itemView.findViewById(R.id.tv_giamGiaHD);

            swipeLayout =itemView.findViewById(R.id.swipe_hd);

            tv_info = itemView.findViewById(R.id.tv_info);
        }
    }




}
