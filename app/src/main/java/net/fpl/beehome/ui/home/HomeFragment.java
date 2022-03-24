package net.fpl.beehome.ui.home;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.fpl.beehome.Adapter.Message.MessageAdapter;
import net.fpl.beehome.HopDongActivity;
import net.fpl.beehome.NguoiThue_Activity;
import net.fpl.beehome.R;
import net.fpl.beehome.SuCoActivity;
import net.fpl.beehome.detail.hoaDon.HoaDonMain;
import net.fpl.beehome.DichVuActivity;
import net.fpl.beehome.model.Message;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ImageView btnDichVu, btnNguoiThue, btnHoaDon, btnSuCo, btnHopDong, btnHuongDan;
    FloatingActionButton button;

    RecyclerView rcvMess;
    EditText edMess;
    ImageView imgMess;
    ArrayList<Message> list;
    MessageAdapter messageAdapter;


    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnDichVu = view.findViewById(R.id.btn_dich_vu);
        btnNguoiThue = view.findViewById(R.id.btn_nguoi_thue);
        btnHoaDon = view.findViewById(R.id.btn_hoa_don);
        btnSuCo = view.findViewById(R.id.btn_su_co);
        btnHopDong = view.findViewById(R.id.brn_hop_dong);
        btnHuongDan = view.findViewById(R.id.btn_huong_dan);
        button = view.findViewById(R.id.floating_action_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogMessage();
            }
        });

        btnDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DichVuActivity.class));
            }
        });

        btnNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NguoiThue_Activity.class);
                startActivity(intent);
            }
        });


        btnHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HoaDonMain.class));
            }
        });

        btnNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NguoiThue_Activity.class);
                startActivity(intent);
            }
        });

        btnHopDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HopDongActivity.class);
                startActivity(intent);
            }
        });

        btnSuCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SuCoActivity.class);
                startActivity(intent);
            }
        });

    }

    public void showDialogMessage(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = View.inflate(getContext(),R.layout.dialog_message, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        rcvMess = view.findViewById(R.id.rcv_mess);
        edMess = view.findViewById(R.id.ed_mess);
        imgMess = view.findViewById(R.id.img_mess);
        list = new ArrayList<>();
        messageAdapter = new MessageAdapter();
        messageAdapter.setData(list);

        rcvMess.setAdapter(messageAdapter);

        imgMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String strMess = edMess.getText().toString().trim();
        if (TextUtils.isEmpty(strMess)) {
            return;
        }
        Log.e("TAG", "sendMessage: " + strMess );

        Message message = new Message();
        message.setMess(strMess);

        message = new Message("Hello");

        list.add(message);
        messageAdapter.notifyDataSetChanged();
        rcvMess.scrollToPosition(list.size() - 1);
        Log.e("TAG", "sendMessage: " + list.size() );
        Log.e("TAG", "sendMessage: " +  message.toString());
        edMess.setText("");

    }

}