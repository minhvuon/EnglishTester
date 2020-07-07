package com.example.englishtester;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView txtQNK, txtTaoTaiKhoan;
    EditText edEmail, edPass;
    Button btnLogin;
    public static String urlData = "http://192.168.1.10/English/";
    public static String urlCheckAccount = urlData+"checkAccount.php";
    public static String urlInsert = urlData+"insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

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

        dangNhap();
    }

    public void anhxa(){
        txtQNK  =(TextView)findViewById(R.id.txtQMK);
        txtTaoTaiKhoan = (TextView)findViewById(R.id.txtDangki);
        btnLogin = findViewById(R.id.btnDangNhap);
        edEmail = findViewById(R.id.edtEmail);
        edPass = findViewById(R.id.edtMatKhau);
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

        edtHoTen = (EditText) dialog.findViewById(R.id.edtHoTen);
        edtEmail = (EditText) dialog.findViewById(R.id.edtEmail);
        edtMatKhau = (EditText) dialog.findViewById(R.id.edtMatKhau1);
        edtMatKhauNhapLai = (EditText) dialog.findViewById(R.id.edtMatKhau2);
        btnDangKi = (Button)dialog.findViewById(R.id.btnDangKi);
        btnThoat = (Button)dialog.findViewById(R.id.btnThoat);

        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = edtHoTen.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String matKhau = edtMatKhau.getText().toString().trim();
                String matKhau2 = edtMatKhauNhapLai.getText().toString().trim();

                if(hoTen.isEmpty()||email.isEmpty()||matKhau.isEmpty()||matKhau2.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Vui long nhap du thong tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(matKhau.equals(matKhau2)) {
                        taoTaiKhoan(urlInsert, email, hoTen, matKhau);
                        dialog.cancel();
                    }
                    else
                        Toast.makeText(LoginActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
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

    void taoTaiKhoan(String url, final String email, final String hoTen, final String matKhau){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("co"))
                    Toast.makeText(LoginActivity.this, "Email da ton tai", Toast.LENGTH_SHORT).show();
                if (response.trim().equals("thanhcong")) {
                    Toast.makeText(LoginActivity.this, "Dang ki Thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Dang ki that bai", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Xay ra loi", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "Loi\n" + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("username", hoTen);
                params.put("password", matKhau);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    void dangNhap(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString().trim();
                String pass = edPass.getText().toString().trim();
                if(email.isEmpty() || pass.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    checkAccount(urlCheckAccount);
                }
            }
        });
    }

    void checkAccount(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                        else {
                            if (response.trim().equals("tk")){
                                Toast.makeText(LoginActivity.this, "Email của bạn không hợp lệ", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(LoginActivity.this, "Password của bạn không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Có lỗi đã xảy ra", Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", edEmail.getText().toString().trim());
                params.put("password", edPass.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}