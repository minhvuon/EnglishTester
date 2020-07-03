package com.example.englishtester;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    TextView txtQNK, txtTaoTaiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhxa();

        txtQNK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogQuenMK();
            }
        });

        txtTaoTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDangKi();
            }
        });
    }

    public void anhxa(){
        txtQNK  =(TextView)findViewById(R.id.txtQMK);
        txtTaoTaiKhoan = (TextView)findViewById(R.id.txtDangki);
    }
    private  void DialogQuenMK(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_quenmatkhau);
        dialog.setCanceledOnTouchOutside(false);
        Button btnThoat = (Button)dialog.findViewById(R.id.btnThoat);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    private  void DialogDangKi(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_dangki);
        dialog.setCanceledOnTouchOutside(false);


        //anh xa
        final EditText edtHoTen, edtEmail, edtMatKhau, edtMatKhauNhapLai;
        Button btnDangKi, btnThoat;

        edtHoTen = (EditText) dialog.findViewById(R.id.edtHoTEn);
        edtEmail = (EditText) dialog.findViewById(R.id.edtEmail);
        edtMatKhau = (EditText) dialog.findViewById(R.id.edtMatKhau1);
        edtMatKhauNhapLai = (EditText) dialog.findViewById(R.id.edtMatKhau2);
        btnDangKi = (Button)dialog.findViewById(R.id.btnDangKi);
        btnThoat = (Button)dialog.findViewById(R.id.btnThoat);

        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String matKhau = edtMatKhau.getText().toString().trim();
                String matKhau2 = edtMatKhauNhapLai.getText().toString().trim();
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();

    }
}