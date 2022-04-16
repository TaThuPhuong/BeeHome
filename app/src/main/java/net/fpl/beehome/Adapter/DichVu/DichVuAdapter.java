package net.fpl.beehome.Adapter.DichVu;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.R;
import net.fpl.beehome.model.DichVu;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DichVuAdapter extends RecyclerSwipeAdapter<DichVuAdapter.DichVuViewHolder> {

    public static final String TAG = "123";
    final NumberFormat fm = new DecimalFormat("#,###,###");
    ArrayList<DichVu> list;
    Context context;
    FirebaseFirestore db;

    public DichVuAdapter(ArrayList<DichVu> list, Context context, FirebaseFirestore db) {
        this.list = list;
        this.context = context;
        this.db = db;
    }

    @NonNull
    @Override
    public DichVuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.item_dich_vu, parent, false);

        DichVuViewHolder dichVuViewHolder = new DichVuViewHolder(view);

        return dichVuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DichVuViewHolder holder, int position) {

        DichVu dichVu = list.get(position);
        final int index = position;

        holder.tv_tenDV.setText("Tên dịch vụ: " + dichVu.getTenDichVu());
        holder.tv_gia.setText("Giá: " + fm.format(dichVu.getGia()) + " VND / " + dichVu.getDonVi());

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        // Drag From Left
//        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        // Drag From Right
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wrapper));


        // Handling different events when swiping
        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
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


        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view.getContext(), 1, index);
                mItemManger.closeAllItems();

            }
        });

        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSua(view.getContext(), dichVu, index);
                mItemManger.closeAllItems();
            }
        });

        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(holder.itemView, index);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public void showDialog(Context context, int type, int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (type == 0) {
            View view = View.inflate(context, R.layout.dialog_them_dich_vu, null);
            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_addhd);
            dialog.show();

            EditText edTenDichVu = dialog.findViewById(R.id.ed_tenDichVu);
            EditText edGia = dialog.findViewById(R.id.ed_giaDichVu);
            Button btnThem = dialog.findViewById(R.id.btn_themDichVu);
            Button btnHuy = dialog.findViewById(R.id.btn_huy);

            Spinner spinner = dialog.findViewById(R.id.spinner);
            ArrayAdapter adapter = ArrayAdapter.createFromResource(context, R.array.listChiSoDichVu, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinner.setAdapter(adapter);

            btnThem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ten = edTenDichVu.getText().toString().trim();
                    String gia = edGia.getText().toString().trim();

                    if (ten.equals("")) {
                        Toast.makeText(context, "Không để trống tên dịch vụ", Toast.LENGTH_SHORT).show();
                        return;
                    } else if(gia.equals("")){
                        Toast.makeText(context, "Không để trống giá dịch vụ", Toast.LENGTH_SHORT).show();

                    } else {
                        DichVu dichVu = new DichVu();
                        dichVu.setTenDichVu(ten);
                        dichVu.setDonVi((String) spinner.getSelectedItem());
                        dichVu.setGia(Integer.parseInt(gia));
                        for (DichVu dichVu1 : list){
                            if (dichVu.getTenDichVu().equals(dichVu1.getTenDichVu())){
                                Toast.makeText(context, "Dịch vụ đã tồn tại !", Toast.LENGTH_SHORT).show();
                                return;
                            }else{
                                insertDichVu(dichVu);
                                dialog.dismiss();
                                notifyDataSetChanged();
                            }
                        }
                    }
                }
            });

            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {
            View view = View.inflate(context, R.layout.dialog_delete_dichvu, null);
            builder.setView(view);
            Button tvCo = view.findViewById(R.id.btn_delete);
            Button tvKhong = view.findViewById(R.id.btn_cancel);
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_info);
            dialog.show();

            DichVu dichVu = list.get(i);

            tvCo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    deleteDichVu(dichVu);
                }
            });

            tvKhong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

        }
    }


    public void showDialogSua(Context context, DichVu dichVu, int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View view = View.inflate(context, R.layout.dialog_sua_dich_vu, null);
        builder.setView(view);
        TextView edTenDichVu = view.findViewById(R.id.tv_tenDV);
        EditText edGia = view.findViewById(R.id.ed_giaDichVu);
        Button btnSua = view.findViewById(R.id.btn_suaDichVu);
        Button btnHuy = view.findViewById(R.id.btn_huy);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_addhd);

        alertDialog.show();

        TextView tv_chiSo = view.findViewById(R.id.tv_chiSo);

        edTenDichVu.setText(dichVu.getTenDichVu());
        edGia.setText(String.valueOf(dichVu.getGia()));
        if (dichVu.getDonVi().equals("Người")) {
            tv_chiSo.setText("Người");
        } else if (dichVu.getDonVi().equals("Phòng")) {
            tv_chiSo.setText("Phòng");
        } else if (dichVu.getDonVi().equals("Số lần sử dụng")) {
            tv_chiSo.setText("Số lần sử dụng");
        } else if (dichVu.getDonVi().equals("Kw")) {
            tv_chiSo.setText("Kw");
        } else if (dichVu.getDonVi().equals("Khối nước")) {
            tv_chiSo.setText("Khối nước");
        }


        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strGia = edGia.getText().toString();
                if(strGia.equals("")){
                    Toast.makeText(context, "Không để trống giá dịch vụ", Toast.LENGTH_SHORT).show();

                } else {
                    dichVu.setGia(Integer.parseInt(edGia.getText().toString()));

                    updateDichVu(dichVu);
                    notifyDataSetChanged();
                    alertDialog.dismiss();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public void insertDichVu(DichVu dichVu) {

        Map<String, Object> map = new HashMap<>();
        map.put(DichVu.COL_NAME, dichVu.getTenDichVu());
        map.put(DichVu.COL_GIA, dichVu.getGia());
        map.put(DichVu.COL_DONVI, dichVu.getDonVi());

        db.collection(DichVu.TB_NAME).document("DV - " + dichVu.getTenDichVu()).set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void updateDichVu(DichVu dichVu) {

        Map<String, Object> map = new HashMap<>();
        map.put(DichVu.COL_GIA, dichVu.getGia());

        db.collection(DichVu.TB_NAME).document("DV - " + dichVu.getTenDichVu()).update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void deleteDichVu(DichVu dichVu) {

        Map<String, Object> map = new HashMap<>();
        map.put(DichVu.COL_NAME, FieldValue.delete());
        map.put(DichVu.COL_GIA, FieldValue.delete());
        map.put(DichVu.COL_DONVI, FieldValue.delete());

        db.collection(DichVu.TB_NAME).document("DV - " + dichVu.getTenDichVu()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "onFailure: " + dichVu.toString());
                    }
                });
        ;
        db.collection(DichVu.TB_NAME).document(dichVu.getTenDichVu()).update(map);
    }

    public class DichVuViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_tenDV, tv_donVi, tv_gia;
        SwipeLayout swipeLayout;
        private LinearLayout tvEdit, tvDelete;

        public DichVuViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_tenDV = itemView.findViewById(R.id.tv_tenDV);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            tvEdit = itemView.findViewById(R.id.tv_edit);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
        }
    }
}
