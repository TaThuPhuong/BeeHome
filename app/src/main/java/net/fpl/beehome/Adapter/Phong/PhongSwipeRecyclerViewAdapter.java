package net.fpl.beehome.Adapter.Phong;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.R;
import net.fpl.beehome.model.Phong;
import net.fpl.beehome.ui.phong.PhongFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhongSwipeRecyclerViewAdapter extends RecyclerSwipeAdapter<PhongSwipeRecyclerViewAdapter.SimpleViewHolder> {


    private Context mContext;
    private ArrayList<Phong> lsPhong;
    private FirebaseFirestore fb;
    private PhongFragment fragment;

    public PhongSwipeRecyclerViewAdapter(Context context, ArrayList<Phong> lsPhong, FirebaseFirestore fb, PhongFragment phongFragment) {
        this.mContext = context;
        this.lsPhong = lsPhong;
        this.fb = fb;
        this.fragment = phongFragment;
    }

    @SuppressLint("ResourceAsColor")
    public void mauTrangThai(String trangThai, TextView tv) {
        switch (trangThai) {
            case "Đang thuê":
                tv.setTextColor(Color.parseColor("#92db64"));
                break;
            case "Trống":
                tv.setTextColor(Color.parseColor("#cd2457"));
                break;
            case "Đang sửa chữa":
                tv.setTextColor(Color.parseColor("#707070"));
                break;
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phong_swipe, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        final Phong phong = lsPhong.get(position);

        viewHolder.tvPhong.setText(phong.getSoPhong());
        viewHolder.tvTrangThai.setText(phong.getTrangThai());
        mauTrangThai(phong.getTrangThai(), viewHolder.tvTrangThai);

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        // Drag From Right
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));


        // Handling different events when swiping
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
        // update phòng
        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSua(phong);
            }
        });

        // Delete 1 dòng
        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(mContext, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_delete_phong);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_addhd);
                dialog.show();
                Button tvDelete = dialog.findViewById(R.id.btn_delete);
                Button tvCancel = dialog.findViewById(R.id.btn_cancel);
                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (phong.getTrangThai().equalsIgnoreCase("Đang thuê")) {
                            Toast.makeText(mContext, "Đã xóa", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            fb.collection(Phong.TB_NAME).document(phong.getIDPhong())
                                    .delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            mItemManger.closeAllItems();
                                            Toast.makeText(mContext, "Xóa Thành công", Toast.LENGTH_SHORT).show();
                                            notifyDataSetChanged();
                                            dialog.dismiss();
                                        }
                                    });
                        }

                    }
                });
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        // Show detail
        viewHolder.tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetail(phong);
            }
        });
        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return lsPhong.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    // Dialog thông tin chi tiết phòng
    public void showDetail(Phong phong) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.dialog_detail_phong, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView tvPhong, tvGia, tvVatTu, tvTrangThai, tvSoDien, tvSoNuoc;
        tvSoDien = view.findViewById(R.id.tv_so_dien);
        tvSoNuoc = view.findViewById(R.id.tv_so_nuoc);
        tvGia = view.findViewById(R.id.tv_gia);
        tvPhong = view.findViewById(R.id.tv_so_phong);
        tvVatTu = view.findViewById(R.id.tv_vat_tu);
        tvTrangThai = view.findViewById(R.id.tv_trang_thai);

        tvSoDien.setText("Số điện: " + phong.getSoDienDau());
        tvSoNuoc.setText("Số nước: " + phong.getSoNuocDau());
        tvGia.setText("Giá: " + phong.getGiaPhong());
        tvVatTu.setText("Đồ dùng: " + phong.getVatTu());
        tvPhong.setText("Phòng - " + phong.getSoPhong());
        tvTrangThai.setText("Trạng thái: " + phong.getTrangThai());
        mauTrangThai(phong.getTrangThai(), tvTrangThai);
    }

    // Dialog sửa thông tin phòng
    public void showDialogSua(Phong phong) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.dialog_sua_phong, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_addhd);
        dialog.show();
        TextInputLayout edSoPhong, edGiaPhong, edVatTu, edSoNuocDau, edSoDienDau;
        EditText ed_VatTu, edTrangThai;
        Button btnThem, btnHuy;
        CheckBox chkGiuong, chkTu, chkDieuHoa, chkNL, chkMayGiat, chkBan, chkBep;
        ImageButton btnChon, btnCancel, btnChonTatCa;
        RadioGroup rdgTrangThai;
        edSoPhong = view.findViewById(R.id.ed_so_phong);
        edGiaPhong = view.findViewById(R.id.ed_gia_phong);
        edVatTu = view.findViewById(R.id.ed_vattu);
        ed_VatTu = view.findViewById(R.id.ed_vat_tu);
        edTrangThai = view.findViewById(R.id.ed_trang_thai);
        edSoDienDau = view.findViewById(R.id.ed_so_dien_dau);
        edSoNuocDau = view.findViewById(R.id.ed_so_nuoc_dau);
        btnThem = view.findViewById(R.id.btn_them_phong);
        btnHuy = view.findViewById(R.id.btn_huy);
        btnChon = view.findViewById(R.id.btn_chon);
        btnChonTatCa = view.findViewById(R.id.btn_chon_tat_ca);
        btnCancel = view.findViewById(R.id.btn_cancel);
        chkGiuong = view.findViewById(R.id.chk_vt_giuong);
        chkBan = view.findViewById(R.id.chk_vt_ban);
        chkBep = view.findViewById(R.id.chk_vt_bep);
        chkDieuHoa = view.findViewById(R.id.chk_vt_dieu_hoa);
        chkNL = view.findViewById(R.id.chk_vt_binh_nl);
        chkTu = view.findViewById(R.id.chk_vt_tu);
        chkMayGiat = view.findViewById(R.id.chk_vt_may_giat);
        rdgTrangThai = view.findViewById(R.id.rdgTrangThai);
        edSoPhong.setError(null);
        edGiaPhong.setError(null);
        edVatTu.setError(null);
        edSoDienDau.setError(null);
        edSoNuocDau.setError(null);
        fragment.setUnErr(edGiaPhong);
        fragment.setUnErr(edSoDienDau);
        fragment.setUnErr(edSoNuocDau);
        fragment.setUnErr(edVatTu);
        // set phòng sửa lên dialog
        edSoPhong.getEditText().setText(phong.getSoPhong());
        edGiaPhong.getEditText().setText(phong.getGiaPhong() + "");
        edVatTu.getEditText().setText(phong.getVatTu());
        edTrangThai.setText(phong.getTrangThai());

        if (phong.getVatTu().contains("Bàn")) {
            chkBan.setChecked(true);
        }

        if (phong.getVatTu().contains("Bếp")) {
            chkBep.setChecked(true);
        }
        if (phong.getVatTu().contains("Điều hòa")) {
            chkDieuHoa.setChecked(true);
        }
        if (phong.getVatTu().contains("Giường")) {
            chkGiuong.setChecked(true);
        }
        if (phong.getVatTu().contains("Máy giặt")) {
            chkMayGiat.setChecked(true);
        }
        if (phong.getVatTu().contains("Bình nước nóng")) {
            chkNL.setChecked(true);
        }
        if (phong.getVatTu().contains("Tủ")) {
            chkTu.setChecked(true);
        }
        if (phong.getTrangThai().equalsIgnoreCase("Trống")) {
            RadioButton rdoTrong = view.findViewById(R.id.rdo_trong);
            rdoTrong.setChecked(true);
        }
//        if (phong.getTrangThai().equalsIgnoreCase("Đang thuê")) {
//            RadioButton rdoDangThue = view.findViewById(R.id.rdo_dang_thue);
//            rdoDangThue.setChecked(true);
//        }
        if (phong.getTrangThai().equalsIgnoreCase("Đang sửa chữa")) {
            RadioButton rdoDangSua = view.findViewById(R.id.rdo_dang_sua);
            rdoDangSua.setChecked(true);
        }
        edSoDienDau.getEditText().setText(phong.getSoDienDau() + "");
        edSoNuocDau.getEditText().setText(phong.getSoNuocDau() + "");
        btnChonTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edVatTu.getEditText().setText("Giường, Bàn, Bếp, Tủ, Điều hòa, Máy giặt, Bình nước nóng");
                chkBan.setChecked(true);
                chkBep.setChecked(true);
                chkDieuHoa.setChecked(true);
                chkGiuong.setChecked(true);
                chkMayGiat.setChecked(true);
                chkNL.setChecked(true);
                chkTu.setChecked(true);
            }
        });
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vt = "";
                if (chkBan.isChecked()) {
                    vt += chkBan.getText().toString() + ", ";
                }
                if (chkBep.isChecked()) {
                    vt += chkBep.getText().toString() + ", ";
                }
                if (chkDieuHoa.isChecked()) {
                    vt += chkDieuHoa.getText().toString() + ", ";
                }
                if (chkGiuong.isChecked()) {
                    vt += chkGiuong.getText().toString() + ", ";
                }
                if (chkMayGiat.isChecked()) {
                    vt += chkMayGiat.getText().toString() + ", ";
                }
                if (chkNL.isChecked()) {
                    vt += chkNL.getText().toString() + ", ";
                }
                if (chkTu.isChecked()) {
                    vt += chkTu.getText().toString() + ", ";
                }
                edVatTu.getEditText().setText(vt);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edVatTu.getEditText().setText("");
                chkBan.setChecked(false);
                chkBep.setChecked(false);
                chkDieuHoa.setChecked(false);
                chkGiuong.setChecked(false);
                chkMayGiat.setChecked(false);
                chkNL.setChecked(false);
                chkTu.setChecked(false);
            }
        });
        rdgTrangThai.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
//                    case R.id.rdo_dang_thue:
//                        edTrangThai.setText("Đang thuê");
//                        break;
                    case R.id.rdo_trong:
                        edTrangThai.setText("Trống");
                        break;
                    case R.id.rdo_dang_sua:
                        edTrangThai.setText("Đang sửa chữa");
                        break;
                }
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strVatTu = edVatTu.getEditText().getText().toString();
                String giaPhong = edGiaPhong.getEditText().getText().toString();
                String strTrangThai = edTrangThai.getText().toString();
                String soDienDau = edSoDienDau.getEditText().getText().toString();
                String soNuocDau = edSoNuocDau.getEditText().getText().toString();
                if (TextUtils.isEmpty(strVatTu) || TextUtils.isEmpty(giaPhong) ||
                        TextUtils.isEmpty(strTrangThai) || TextUtils.isEmpty(soDienDau) || TextUtils.isEmpty(soNuocDau)) {
                    if (TextUtils.isEmpty(giaPhong)) {
                        edGiaPhong.setError("Giá phòng không được để trống");
                    }
                    if (TextUtils.isEmpty(strVatTu)) {
                        edVatTu.setError("Chọn trang bị có trong phòng");
                    }
                    if (TextUtils.isEmpty(soDienDau)) {
                        edSoDienDau.setError("Số điện hiện tại không được để trống");
                    }
                    if (TextUtils.isEmpty(soNuocDau)) {
                        edSoNuocDau.setError("Số nước hiện tại không được để trống");
                    }
                    mItemManger.closeAllItems();
                    return;
                } else {
                    Map<String, Object> p = new HashMap<>();
                    p.put(Phong.COL_GIA_PHONG, Integer.parseInt(giaPhong));
                    p.put(Phong.COL_VAT_TU, strVatTu);
                    p.put(Phong.COL_TRANG_THAI, strTrangThai);
                    p.put(Phong.COL_SO_DIEN_DAU, Integer.parseInt(soDienDau));
                    p.put(Phong.COL_SO_NUOC_DAU, Integer.parseInt(soNuocDau));
                    fb.collection(Phong.TB_NAME).document(phong.getIDPhong()).update(p);
                    mItemManger.closeAllItems();
                    Toast.makeText(mContext, "Đã cập nhập", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    dialog.dismiss();
                }

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.closeAllItems();
                dialog.dismiss();
            }
        });
    }

    //  ViewHolder Class
    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView tvPhong, tvTrangThai;
        LinearLayout tvInfo, tvEdit, tvDelete;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            tvPhong = itemView.findViewById(R.id.tv_phong);
            tvTrangThai = itemView.findViewById(R.id.tv_trang_thai);
            tvInfo = itemView.findViewById(R.id.tv_info);
            tvEdit = itemView.findViewById(R.id.tv_edit);
            tvDelete = itemView.findViewById(R.id.tv_delete);
        }
    }
}
// #################################################################
// #                             _`
// #                          _ooOoo_
// #                         o8888888o
// #                         88" . "88
// #                        (|   😑  |)
// #                         O\  =  /O
// #                      ____/`---'\____
// #                    .'  \\|     |//  `.
// #                   /  \\|||  :  |||//  \
// #                  /  _||||| -:- |||||_  \
// #                  |   | \\\  -  /'| |   |
// #                  | \_|  `\`---'//  |_/ |
// #                  \  .-\__ `-. -'__/-.  /
// #                ___`. .'  /--.--\  `. .'___
// #             ."" '<  `.___\_<|>_/___.' _> \"".
// #            | | :  `- \`. ;`. _/; .'/ /  .' ; |
// #            \  \ `-.   \_\_`. _.'_/_/  -' _.' /
// #=============`-.`___`-.__\ \___  /__.-'_.'_.-'=================#
//                            `=--=-'
//           _.-/`)
//          // / / )
//       .=// / / / )
//      //`/ / / / /
//     // /     ` /
//    ||         /
//     \\       /
//      ))    .'
//     //    /
//          /