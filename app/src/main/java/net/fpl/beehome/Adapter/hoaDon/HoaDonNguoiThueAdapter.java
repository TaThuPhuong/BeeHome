package net.fpl.beehome.Adapter.hoaDon;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.R;
import net.fpl.beehome.model.DichVu;
import net.fpl.beehome.model.HoaDon;
import net.fpl.beehome.model.HoaDonChiTiet;
import net.fpl.beehome.model.HopDong;
import net.fpl.beehome.model.NguoiThue;
import net.fpl.beehome.model.Phong;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HoaDonNguoiThueAdapter extends RecyclerSwipeAdapter<HoaDonNguoiThueAdapter.HoaDonViewHolder> {
    final NumberFormat formatter = new DecimalFormat("#,###,###,###");
    ArrayList<HoaDonChiTiet> arrHDCT;
    ArrayList<HoaDon> arr;
    Context context;
    FirebaseFirestore fb;
    ArrayList<String> arrTenPhong ;
    ArrayList<Phong> arrPhong;
    ArrayList<HopDong> arrhopdong;
    ArrayList<DichVu> arrDichVu;
    ArrayList<NguoiThue> arrNguoiThue;
    SimpleDateFormat dfm = new SimpleDateFormat("dd/MM/yyyy");

    public HoaDonNguoiThueAdapter(ArrayList<HoaDonChiTiet> arrHDCT, ArrayList<HoaDon> arr, Context context, FirebaseFirestore fb, ArrayList<String> arrTenPhong, ArrayList<Phong> arrPhong, ArrayList<HopDong> arrhopdong, ArrayList<DichVu> arrDichVu, ArrayList<NguoiThue> arrNguoiThue) {
        this.arrHDCT = arrHDCT;
        this.arr = arr;
        this.context = context;
        this.fb = fb;
        this.arrTenPhong = arrTenPhong;
        this.arrPhong = arrPhong;
        this.arrhopdong = arrhopdong;
        this.arrDichVu = arrDichVu;
        this.arrNguoiThue = arrNguoiThue;
    }

    @Override
    public HoaDonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoa_don_nguoi_dung, parent, false);
        return new HoaDonNguoiThueAdapter.HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HoaDonViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        final HoaDon objHoaDon = arr.get(position);

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
