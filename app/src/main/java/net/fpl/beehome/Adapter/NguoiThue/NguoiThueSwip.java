package net.fpl.beehome.Adapter.NguoiThue;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import net.fpl.beehome.R;
import net.fpl.beehome.model.NguoiThue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NguoiThueSwip extends RecyclerSwipeAdapter<NguoiThueSwip.NguoiThueViewHolder> implements Filterable {
    ArrayList<NguoiThue> arr;
    Context context;
    FirebaseFirestore fb;
    ArrayList<NguoiThue> arr1;

    public NguoiThueSwip(ArrayList<NguoiThue> arr, Context context, FirebaseFirestore fb) {
        this.arr = arr;
        this.context = context;
        this.fb = fb;
        this.arr1 = arr;
    }

    @Override
    public NguoiThueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_nguoithue, parent, false);

        return new NguoiThueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NguoiThueViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        final NguoiThue objNguoiThue = arr.get(position);

        viewHolder.tv_tennguoithue.setText("Tên : " + objNguoiThue.getHoTen());
        viewHolder.tv_phongnguoithue.setText("Phòng : " + objNguoiThue.getId_phong());
        viewHolder.tv_sdtnguoithue.setText("SĐT : " + objNguoiThue.getSdt());

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
                dialog.setContentView(R.layout.dialog_delete_nguoithue);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_info);
                Button btn_delete = dialog.findViewById(R.id.btn_deletenguoithue);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancelnguoithue);

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (objNguoiThue.getId_phong().equals("Trống")) {
                            fb.collection(NguoiThue.TB_NGUOITHUE).document(objNguoiThue.getId_thanhvien())
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                            notifyDataSetChanged();
                                            mItemManger.closeAllItems();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Đã Có Phòng Không Thể Xóa", Toast.LENGTH_SHORT).show();
                            mItemManger.closeAllItems();
                            dialog.dismiss();
                        }
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        mItemManger.closeAllItems();
                    }
                });
                dialog.show();
            }
        });

        viewHolder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                view = View.inflate(context, R.layout.dialog_sua_nguoithue, null);
                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                TextInputLayout ed_suaten, ed_suasdt, ed_suaemail, ed_suacccd;
                Button btn_update, btn_xoa;
                ed_suaten = view.findViewById(R.id.ed_suahotennguoithue);
                ed_suasdt = view.findViewById(R.id.ed_suasdtnguoithue);
//                ed_suaemail = view.findViewById(R.id.ed_suaemailnguoithue);
                ed_suacccd = view.findViewById(R.id.ed_suacccdnguoithue);
                btn_update = view.findViewById(R.id.btn_updatenguoithue);
                btn_xoa = view.findViewById(R.id.btn_xoanguoithue);
                ed_suaten.setError(null);
                ed_suasdt.setError(null);
//                ed_suaemail.setError(null);
                ed_suacccd.setError(null);
                ed_suaten.getEditText().setText(objNguoiThue.getHoTen());
                ed_suasdt.getEditText().setText(objNguoiThue.getSdt());
//                ed_suaemail.getEditText().setText(objNguoiThue.getEmail());
                ed_suacccd.getEditText().setText(objNguoiThue.getCccd());
                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ten = ed_suaten.getEditText().getText().toString();
                        String sdt = ed_suasdt.getEditText().getText().toString();
//                        String email = ed_suaemail.getEditText().getText().toString();
                        String cccd = ed_suacccd.getEditText().getText().toString();
                        setUnErrNguoithue(ed_suaten);
                        setUnErrNguoithue(ed_suasdt);
//                        setUnErrNguoithue(ed_suaemail);
                        setUnErrNguoithue(ed_suacccd);
                        if (TextUtils.isEmpty(ten)) {
                            ed_suaten.setError("Không Được Để Trống Tên");
                            return;
                        } else if (!isNumber(sdt)) {
                            ed_suasdt.setError("Không Đúng Số Điện Thoại");
                            return;
                        } else if (cccd.length() != 12) {
                            ed_suacccd.setError("Căn Cước 12 Số");
                            return;
                        } else {
                            if (checkSDTNguoiThue(objNguoiThue) != true) {
                                ed_suasdt.setError("Số Điện Thoại Trùng Lặp");
                                return;
                            } else if (checkCMND(objNguoiThue) != true) {
                                ed_suacccd.setError("Trùng Căn Cước Công Dân");
                                return;
                            } else {
                                Map<String, Object> p = new HashMap<>();
                                p.put(NguoiThue.COL_HOTEN, ten);
                                p.put(NguoiThue.COL_SDT, sdt);
                                p.put(NguoiThue.COL_CCCD, cccd);
                                fb.collection(NguoiThue.TB_NGUOITHUE).document(objNguoiThue.getSdt()).update(p);

                                mItemManger.closeAllItems();
                                notifyDataSetChanged();
                                dialog.dismiss();
                                Toast.makeText(context, "Update Thành Công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                btn_xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        mItemManger.closeAllItems();
                    }
                });
            }
        });
        viewHolder.tv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, androidx.transition.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_info_nguoithue);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_info);
                TextView tv_info = dialog.findViewById(R.id.tv_info_nguoithue);
                tv_info.setText("Tên: " + objNguoiThue.getHoTen() + "\nPhòng : " + objNguoiThue.getId_phong() + "\nEmail : " + objNguoiThue.getEmail() + "\nSĐT : " + objNguoiThue.getSdt() + "\nCCCD : " + objNguoiThue.getCccd());
                mItemManger.closeAllItems();
                dialog.show();
            }
        });

        mItemManger.bindView(viewHolder.itemView, position);
    }

//    public static boolean isEmail(CharSequence charSequence) {
//        return !TextUtils.isEmpty(charSequence) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
//    }

    public static boolean isNumber(String input) {
        Pattern b = Pattern.compile("(84|0[3|5|7|8|9])+([0-9]{8})\\b");
        Matcher m = b.matcher(input);
        return m.matches();
    }

    public boolean checkSDTNguoiThue(NguoiThue nguoiThue) {
        ArrayList<NguoiThue> arrayList = new ArrayList<>();
        for (NguoiThue nguoiThue2 : arr) {
            if (!nguoiThue2.getSdt().equals(nguoiThue.getSdt())) {
                arrayList.add(nguoiThue2);
            }
        }

        for (int i = 0; i < arrayList.size(); i++) {
            NguoiThue objNguoithue = arrayList.get(i);
            if (objNguoithue.getSdt().equals(nguoiThue.getSdt())) {
                Toast.makeText(context, "Trùng SĐT", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public boolean checkCMND(NguoiThue nguoiThue) {
        ArrayList<NguoiThue> arrayList = new ArrayList<>();
        for (NguoiThue nguoiThue1 : arr) {
            if (!nguoiThue1.getCccd().equals(nguoiThue.getCccd())) {
                arrayList.add(nguoiThue1);
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            NguoiThue objNguoiThue = arrayList.get(i);
            if (objNguoiThue.getCccd().equals(nguoiThue.getCccd())) {
                Toast.makeText(context, "Trùng Căn Cước Công Dân", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public void setUnErrNguoithue(TextInputLayout textInputLayout) {
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (textInputLayout.getEditText().getText().toString().length() != 0) {
                    textInputLayout.setError(null);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public class NguoiThueViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tennguoithue, tv_phongnguoithue, tv_sdtnguoithue;
        SwipeLayout swipeLayout;
        LinearLayout tv_del, tv_edit, tv_info;

        public NguoiThueViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tennguoithue = itemView.findViewById(R.id.tv_tennguoithue);
            tv_phongnguoithue = itemView.findViewById(R.id.tv_phongnguoithue);
            tv_sdtnguoithue = itemView.findViewById(R.id.tv_sdtnguoithue);
            swipeLayout = itemView.findViewById(R.id.swipe);

            tv_del = itemView.findViewById(R.id.tv_deletenguoithue);
            tv_edit = itemView.findViewById(R.id.tv_editnguoithue);
            tv_info = itemView.findViewById(R.id.tv_infonguoithue);

        }
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
                    ArrayList<NguoiThue> nguoiThues = new ArrayList<>();
                    for (NguoiThue nguoiThue1 : arr1) {
                        if (nguoiThue1.getHoTen().toLowerCase().contains(search.toLowerCase())) {
                            nguoiThues.add(nguoiThue1);
                        }
                    }
                    arr = nguoiThues;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arr;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arr = (ArrayList<NguoiThue>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
