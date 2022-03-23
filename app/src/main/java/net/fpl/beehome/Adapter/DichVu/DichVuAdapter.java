package net.fpl.beehome.Adapter.DichVu;

import android.content.Context;
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
import net.fpl.beehome.R;
import net.fpl.beehome.model.DichVu;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DichVuAdapter extends RecyclerView.Adapter<DichVuAdapter.DichVuViewHolder> {

    DichVuDAO dichVuDAO;
    ArrayList<DichVu> list;

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

        holder.tv_tenDV.setText(dichVu.getTenDichVu());
        holder.tv_donVi.setText(dichVu.getDonVi());
        holder.tv_gia.setText(String.valueOf(dichVu.getGia()));

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
            tv_donVi = itemView.findViewById(R.id.tv_donVi);
            tv_gia = itemView.findViewById(R.id.tv_gia);
        }
    }

    public void showDialog(int type, Context context, int... i){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        if (type == 0){
            View view = View.inflate(context, R.layout.dialog_them_dich_vu, null);
            dialog.setView(view);
            EditText edTenDichVu= view.findViewById(R.id.ed_tenDichVu);
            EditText edGia = view.findViewById(R.id.ed_giaDichVu);
            EditText edDonVi = view.findViewById(R.id.ed_chiSo);

            AlertDialog alertDialog = dialog.create();
            alertDialog.show();

            Button btnThem = view.findViewById(R.id.btn_themDichVu);
            Button btnHuy = view.findViewById(R.id.btn_huy);

            DichVu dichVu = new DichVu();
            dichVu.setTenDichVu(edTenDichVu.getText().toString());
            dichVu.setDonVi(edDonVi.getText().toString());
            dichVu.setGia(Integer.parseInt(edGia.getText().toString()));

            btnThem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dichVuDAO.insertDichVu(dichVu);
                    alertDialog.dismiss();
                }
            });

            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });



        } else {
            View view = View.inflate(context, R.layout.dialog_sua_dich_vu, null);
            dialog.setView(view);
            EditText edTenDichVu= view.findViewById(R.id.ed_tenDichVu);
            EditText edGia = view.findViewById(R.id.ed_giaDichVu);
            Button btnThem = view.findViewById(R.id.btn_themDichVu);
            Button btnHuy = view.findViewById(R.id.btn_huy);

            DichVu dichVu = new DichVu();
            dichVu.setTenDichVu(edTenDichVu.getText().toString());
            dichVu.setGia(Integer.parseInt(edGia.getText().toString()));

            btnThem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dichVuDAO.updateDichVu(dichVu);
                }
            });

            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

}
