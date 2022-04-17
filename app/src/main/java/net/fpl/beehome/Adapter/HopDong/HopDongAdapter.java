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
import net.fpl.beehome.model.HoaDonChiTiet;
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

        viewHolder.tv_idphong.setText("Phòng: " + objHopDong.getId_phong());
        viewHolder.tv_ngayky.setText("Ngày ký: " + objHopDong.getNgayKiHD());
        viewHolder.tv_ngaybd.setText("Ngày bắt đầu: " + objHopDong.getNgayBatDau());
        viewHolder.tv_ngaykt.setText("Ngày kết thúc: " + objHopDong.getNgayKetThuc());

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
                mItemManger.closeAllItems();
                Dialog dialog = new Dialog(context, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_delete_hopdong);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_info);
                Button btn_delete = dialog.findViewById(R.id.btn_delete);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                ArrayList<HoaDon> arrhd = getListHD(objHopDong.getId_phong());

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (HoaDon objHoaDon : arrhd) {
                            if (objHoaDon.getTrangThaiHD() != 1) {
                                Toast.makeText(view.getContext(), "Vui lòng thanh toán hóa đơn trước khi xóa", Toast.LENGTH_SHORT).show();
                                mItemManger.closeAllItems();
                                dialog.dismiss();
                                return;
                            }
                        }

                        mItemManger.closeAllItems();
                        fb.collection(Phong.TB_NAME).document(objHopDong.getId_phong()).update(Phong.COL_TRANG_THAI, "Trống");
                        fb.collection(NguoiThue.TB_NGUOITHUE).document(objHopDong.getId_thanh_vien()).update(NguoiThue.COL_ID_PHONG, "Trống");
                        for (HoaDon objHoaDon : arrhd) {
                            fb.collection(HoaDon.TB_NAME).document(objHoaDon.getIDHoaDon()).delete();
                            deleteAllHDCT(objHoaDon.getIDHoaDon());
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
                mItemManger.closeAllItems();
                Dialog dialog = new Dialog(context, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_info_suco);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_info);
                TextView tv_info = dialog.findViewById(R.id.tv_info_hopdong);
                tv_info.setText("Phòng: " + objHopDong.getId_phong() + "\nSĐT người thuê: " + objHopDong.getId_thanh_vien() + "\nKỳ hạn: " + objHopDong.getKyHan() + "\nNgày ký: " + objHopDong.getNgayKiHD() + "\nNgày bắt đầu: " + objHopDong.getNgayBatDau() + "\nNgày kết thúc: " + objHopDong.getNgayKetThuc() + "\nSố người thuê: " + objHopDong.getSoNguoiThue());
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

    public ArrayList<HoaDon> getListHD(String str) {
        ArrayList<HoaDon> arr = new ArrayList<>();
        fb.collection(HoaDon.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arr.clear();
                for (QueryDocumentSnapshot document : value) {
                    HoaDon objHoaDon = document.toObject(HoaDon.class);
                    if (objHoaDon.getIDPhong().equalsIgnoreCase(str)) {
                        arr.add(objHoaDon);
                        notifyDataSetChanged();
                    }
                }
            }
        });
        return arr;
    }

    public void deleteAllHDCT(String idhd) {
        fb.collection(HoaDonChiTiet.TB_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                arr.clear();
                for (QueryDocumentSnapshot document : value) {
                    HoaDonChiTiet objHoaDonChiTiet = document.toObject(HoaDonChiTiet.class);
                    if (objHoaDonChiTiet.getIDHoaDon().equalsIgnoreCase(idhd)) {
                        fb.collection(HoaDonChiTiet.TB_NAME).document(objHoaDonChiTiet.getIDHoaDonCT()).delete();
                        notifyDataSetChanged();
                    }
                }
            }
        });
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
            swipeLayout = itemView.findViewById(R.id.swipe);

            tv_del = itemView.findViewById(R.id.tv_delete);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            tv_info = itemView.findViewById(R.id.tv_info);
        }
    }
}
