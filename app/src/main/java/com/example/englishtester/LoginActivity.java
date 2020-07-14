package com.example.englishtester;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    TextView txtQNK, txtTaoTaiKhoan;
    EditText edEmail, edPass;
    Button btnLogin;
    CheckBox cbLogin,cbShowPas;
    public static String urlData = "http://192.168.1.17/English/";
    public static String urlCheckAccount = urlData+"checklogin.php";
    public static String urlInsert = urlData+"insert.php";
    public static String urlGetDataB1 = "http://192.168.1.17/English/cauhoib1.php";
    SharedPreferences sharedPreferences;
    String email;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        anhxa();

        sharedPreferences = getSharedPreferences("dataLogin",MODE_PRIVATE);

        //laygia tri
        edEmail.setText(sharedPreferences.getString("email",""));
        edPass.setText(sharedPreferences.getString("password",""));
        cbLogin.setChecked(sharedPreferences.getBoolean("",false));

        showPass();

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

    private void showPass() {
        cbShowPas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    cbShowPas.setText("Show Password");
                    edPass.setInputType(InputType.TYPE_CLASS_TEXT);
                    edPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    cbShowPas.setText("Hide Password");
                    edPass.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void anhxa(){
        txtQNK  =(TextView)findViewById(R.id.txtQMK);
        txtTaoTaiKhoan = (TextView)findViewById(R.id.txtDangki);
        btnLogin = findViewById(R.id.btnDangNhap);
        edEmail = findViewById(R.id.edtEmail);
        edPass = findViewById(R.id.edtMatKhau);
        cbLogin = findViewById(R.id.cbLogin);
        cbShowPas = findViewById(R.id.cbShowpass);
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

    void dangNhap(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edEmail.getText().toString().trim();
                pass = edPass.getText().toString().trim();
                if(email.isEmpty() || pass.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    checkAccount(urlCheckAccount);
                }
            }
        });
    }

    void checkAccount(String url){
        email = edEmail.getText().toString().trim();
        pass = edPass.getText().toString().trim();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            if(cbLogin.isChecked()){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email",email);
                                editor.putString("password",pass);
                                editor.putBoolean("checked",true);
                                editor.commit();
                            }
                            else {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("email");
                                editor.remove("password");
                                editor.remove("checked");
                                editor.commit();
                            }
                            finish();
                        }
                        else {
                            if (response.trim().equals("tk")){
                                Toast.makeText(LoginActivity.this, "Email của bạn không hợp lệ", Toast.LENGTH_SHORT).show();
                            }else{
                                if (response.trim().equals("mk")){
                                    Toast.makeText(LoginActivity.this, "Password của bạn không đúng", Toast.LENGTH_SHORT).show();
                                }
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