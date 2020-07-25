package com.example.englishtester;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import android.util.Log;
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
import com.example.englishtester.model.Account;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.example.englishtester.MainActivity.checkLogin1;
import static com.example.englishtester.MainActivity.email_dk;
import static com.example.englishtester.MainActivity.pass_dk;

public class LoginActivity extends AppCompatActivity {
    TextView txtQNK, txtTaoTaiKhoan;
    EditText edEmail, edPass;
    Button btnLogin;
    CheckBox cbLogin, cbShowPas;
    public static String urlData = "http://192.168.1.17/English/";
    public static String urlgetData = urlData + "getaccount.php";
    public static String urlCheckAccount = urlData + "checklogin.php";
    public static String urlInsert = urlData + "insert.php";
    public static String urlgetPass = urlData + "getpass.php";
    public static String urlGetDataB1 = urlData + "getquestion.php";
    public static int checkLogin = 0;
    public static String urlInsertBXH = urlData + "insertscore.php";
    public static final Pattern EMAIL_ADDRESS = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
            + "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,64}" +
            "(" + "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
    SharedPreferences sharedPreferences;
    public static ArrayList<Account> accountArrayList;
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
        accountArrayList = new ArrayList<>();
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);

        if (checkLogin1 == 1) {
            edEmail.setText(email_dk);
            edPass.setText(pass_dk);
            cbLogin.setChecked(sharedPreferences.getBoolean("checked", false));
        } else {
            edEmail.setText(sharedPreferences.getString("email", ""));
            edPass.setText(sharedPreferences.getString("password", ""));
            cbLogin.setChecked(sharedPreferences.getBoolean("checked", false));
        }

        if (!edEmail.getText().toString().trim().isEmpty() && !edPass.getText().toString().trim().isEmpty() && checkLogin == 0) {
            checkAccount(urlCheckAccount, urlgetData);
        }
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
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbShowPas.setText("Show Password");
                    edPass.setInputType(InputType.TYPE_CLASS_TEXT);
                    edPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    cbShowPas.setText("Hide Password");
                    edPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void getPass(String url, final String email) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("no")) {
                            Toast.makeText(LoginActivity.this, "Email does not exist!", Toast.LENGTH_SHORT).show();
                        } else {
                            inPass(response.trim());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    void inPass(String pass) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Your password");
        dialog.setMessage(pass);
        dialog.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    public void anhxa() {
        txtQNK = (TextView) findViewById(R.id.txtQMK);
        txtTaoTaiKhoan = (TextView) findViewById(R.id.txtDangki);
        btnLogin = findViewById(R.id.btnDangNhap);
        edEmail = findViewById(R.id.edtEmail);
        edPass = findViewById(R.id.edtMatKhau);
        cbLogin = findViewById(R.id.cbLogin);
        cbShowPas = findViewById(R.id.cbShowpass);
    }

    private void DialogQuenMK() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_quenmatkhau);
        dialog.setCanceledOnTouchOutside(false);

        Button btnThoat = (Button) dialog.findViewById(R.id.btnThoat);
        Button btnGui = dialog.findViewById(R.id.btnGui);
        final EditText edEmail = dialog.findViewById(R.id.txtEmail);
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPass(urlgetPass, edEmail.getText().toString());
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

    private void DialogDangKi() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_dangki);
        dialog.setCanceledOnTouchOutside(false);

        final EditText edtHoTen, edtEmail, edtMatKhau, edtMatKhauNhapLai;
        Button btnDangKi, btnThoat;

        edtHoTen = (EditText) dialog.findViewById(R.id.edtHoTen);
        edtEmail = (EditText) dialog.findViewById(R.id.edtEmail);
        edtMatKhau = (EditText) dialog.findViewById(R.id.edtMatKhau1);
        edtMatKhauNhapLai = (EditText) dialog.findViewById(R.id.edtMatKhau2);
        btnDangKi = (Button) dialog.findViewById(R.id.btnDangKi);
        btnThoat = (Button) dialog.findViewById(R.id.btnThoat);

        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String hoTen = edtHoTen.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String matKhau = edtMatKhau.getText().toString().trim();
            String matKhau2 = edtMatKhauNhapLai.getText().toString().trim();

            if (hoTen.isEmpty() || email.isEmpty() || matKhau.isEmpty() || matKhau2.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Enter full in the form, please!", Toast.LENGTH_SHORT).show();
            } else if (!EMAIL_ADDRESS.matcher(email).matches()) {
                edtEmail.setError("Email invalid!");
            } else if (matKhau.equals(matKhau2)) {
                taoTaiKhoan(urlInsert, email, hoTen, matKhau);
                dialog.cancel();
            } else
                Toast.makeText(LoginActivity.this, "Password incorrect!", Toast.LENGTH_SHORT).show();

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

    void taoTaiKhoan(String url, final String email, final String hoTen, final String matKhau) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("co"))
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
                }) {
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

    void dangNhap() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edEmail.getText().toString().trim();
                pass = edPass.getText().toString().trim();
                if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter full in the form, please!", Toast.LENGTH_SHORT).show();
                } else if (!EMAIL_ADDRESS.matcher(email).matches()) {
                    edEmail.setError("Email invalid!");
                } else {
                    checkAccount(urlCheckAccount, urlgetData);
                }
            }
        });
    }

    void checkAccount(String url, final String url1) {
        email = edEmail.getText().toString().trim();
        pass = edPass.getText().toString().trim();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            getData(url1);
                            checkLogin = 0;
                            checkLogin1 = 0;
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            if (cbLogin.isChecked()) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", email);
                                editor.putString("password", pass);
                                editor.putBoolean("checked", true);
                                editor.apply();
                            } else {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("email");
                                editor.remove("password");
                                editor.remove("checked");
                                editor.apply();
                            }
                            finish();
                        } else {
                            if (response.trim().equals("tk")) {
                                Toast.makeText(LoginActivity.this, "Email does not exist!", Toast.LENGTH_SHORT).show();
                            } else {
                                if (response.trim().equals("mk")) {
                                    Toast.makeText(LoginActivity.this, "Password incorrect!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Error, please try again!", Toast.LENGTH_SHORT).show();
                    }
                }) {

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

    void getData(String url) {
        final String email_nav = edEmail.getText().toString().trim();
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        Account account = new Account();
                        account.setUserName(jsonObject.getString("user"));
                        account.setEmail(jsonObject.getString("email"));
                        account.setID(jsonObject.getString("id"));
                        account.setPass(jsonObject.getString("pass"));

                        accountArrayList.add(account);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("name", jsonObject.getString("user"));
                        intent.putExtra("email", jsonObject.getString("email"));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, "Error, please try again!", Toast.LENGTH_LONG).show();
                }
            }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email_nav);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
