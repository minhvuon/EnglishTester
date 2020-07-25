package com.example.englishtester;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.example.englishtester.model.Question;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.englishtester.LoginActivity.EMAIL_ADDRESS;
import static com.example.englishtester.LoginActivity.checkLogin;
import static com.example.englishtester.LoginActivity.urlInsert;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    Toolbar toolbar;
    TextView txtName, txtEmail;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    BottomNavigationView bottomNavigationView;
    public static String TYPE_QUESTION = "B1";
    public static ArrayList<Question> questionArrayList;
    public static String pass_dk = "";
    public static String email_dk = "";
    public static int checkLogin1 = 0;

    public static int checkAnswerEl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setWidget();
        questionArrayList = new ArrayList<Question>();


        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawer, toolbar, R.string.navigation_draw_open, R.string.navigation_draw_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
            navigationView.setCheckedItem(R.id.id_nav_home);
        }
        navigationClick();

        bottomNavigationView.setSelectedItemId(R.id.bt_nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        Intent intent = getIntent();


        txtName.setText(intent.getStringExtra("name"));
        txtEmail.setText(intent.getStringExtra("email"));

    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragmentSelected = null;
                    switch (menuItem.getItemId()) {
                        case R.id.bt_nav_home:
                            fragmentSelected = new FragmentHome();
                            break;
                        case R.id.bt_nav_me:

                            fragmentSelected = new FragmentMe();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentSelected).commit();
                    return true;
                }
            };


    private void navigationClick() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.id_nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
                        break;
                    case R.id.id_nav_test:
                        questionArrayList.clear();
                        checkAnswerEl = 0;
                        Intent intent = new Intent(MainActivity.this, TimeActivity.class);
                        intent.putExtra("typeQuestion", "T");
                        startActivity(intent);
                        break;
                    case R.id.id_nav_learn:
                        Toast.makeText(MainActivity.this, "Learn", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.id_nav_add_account:
                        Dialog();
                        break;
                    case R.id.id_nav_logout:
                        Toast.makeText(MainActivity.this, "LOG OUT", Toast.LENGTH_SHORT).show();
                        checkLogin = 1;
                        startActivity(new Intent(MainActivity.this, DelayActivity.class));
                        finish();
                        break;
                    default:
                        return true;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    void Dialog() {
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
                Toast.makeText(MainActivity.this, "Enter full in the form, please!", Toast.LENGTH_SHORT).show();
            } else if (!EMAIL_ADDRESS.matcher(email).matches()) {
                edtEmail.setError("Email invalid!");
            } else if (matKhau.equals(matKhau2)) {
                taoTaiKhoan(urlInsert, email, hoTen, matKhau);
                dialog.cancel();
            } else
                Toast.makeText(MainActivity.this, "Password incorrect!", Toast.LENGTH_SHORT).show();
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

    void thongBao() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Notification");
        dialog.setMessage("You want to switch account now!");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                checkLogin1 = 1;
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        dialog.show();
    }

    void taoTaiKhoan(String url, final String email, final String hoTen, final String matKhau) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("co")) {
                    Toast.makeText(MainActivity.this, "Email already exists!", Toast.LENGTH_SHORT).show();
                } else {
                    if (response.trim().equals("thanhcong")) {
                        email_dk = email;
                        pass_dk = matKhau;
                        thongBao();
                    } else {
                        Toast.makeText(MainActivity.this, "Register fail!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error, please try again!", Toast.LENGTH_SHORT).show();
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

    void setWidget() {
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        txtName = navigationView.getHeaderView(0).findViewById(R.id.txtName_nav);
        txtEmail = navigationView.getHeaderView(0).findViewById(R.id.txtEmail_nav);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.menu_about) {
            showDialogAbout();
        }
        return super.onOptionsItemSelected(item);
    }

    void showDialogAbout() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_about);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

}