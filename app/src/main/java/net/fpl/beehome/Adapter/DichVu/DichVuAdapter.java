package net.fpl.beehome.Adapter.DichVu;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import net.fpl.beehome.DAO.DichVuDAO;
import net.fpl.beehome.DichVuActivity;
import net.fpl.beehome.R;
import net.fpl.beehome.model.DichVu;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DichVuAdapter extends RecyclerView.Adapter<DichVuAdapter.DichVuViewHolder> {

    DichVuDAO dichVuDAO;
    ArrayList<DichVu> list;

    public static final String TAG = "123";


    public DichVuAdapter(DichVuDAO dichVuDAO, ArrayList<DichVu> list) {
        this.dichVuDAO = dichVuDAO;
        this.list = list;
    }

    @NonNull
    @Override
    public DichVuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view  =layoutInflater.inflate(R.layout.item_dich_vu,parent,false);

        DichVuViewHolder dichVuViewHolder = new DichVuViewHolder(view);

        return dichVuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DichVuViewHolder holder, int position) {

        DichVu dichVu = list.get(position);

        holder.tv_tenDV.setText("Tên dịch vụ: " + dichVu.getTenDichVu());
        holder.tv_gia.setText("Giá: " + String.valueOf(dichVu.getGia()) + " / " + dichVu.getDonVi());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DichVuViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_tenDV, tv_donVi, tv_gia;

        public DichVuViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_tenDV = itemView.findViewById(R.id.tv_tenDV);
            tv_gia = itemView.findViewById(R.id.tv_gia);
        }
    }

    public void showDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

//        if (type == 0){
        View view = View.inflate(context, R.layout.dialog_them_dich_vu, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        EditText edTenDichVu = dialog.findViewById(R.id.ed_tenDichVu);
        EditText edGia = dialog.findViewById(R.id.ed_giaDichVu);
        EditText edDonVi = dialog.findViewById(R.id.ed_chiSo);
        Button btnThem = dialog.findViewById(R.id.btn_themDichVu);
        Button btnHuy = dialog.findViewById(R.id.btn_huy);



        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edTenDichVu.getText().toString().trim();
                String gia = edGia.getText().toString().trim();
                String donvi = edDonVi.getText().toString().trim();
                if (ten.equals("") || gia.equals("") || donvi.equals("")) {
                    dichVuDAO.thongbao(1, "Điền đầy đủ các thông tin");
                    Log.e("xxx", "onClick: " + ten + gia + donvi);
                    return;
                } else {
                    DichVu dichVu = new DichVu();
                    dichVu.setTenDichVu(ten);
                    dichVu.setDonVi(donvi);
                    dichVu.setGia(Integer.parseInt(gia));

                    dichVuDAO.insertDichVu(dichVu);
                    dialog.dismiss();
                    Log.e("xxx", "onClick: " + ten + gia + donvi);

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
    }

}
