package com.example.englishtester;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.englishtester.model.Answer;
import com.example.englishtester.model.Question;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    BottomNavigationView bottomNavigationView;
    public static String TYPE_QUESTION = "B1";
    public static ArrayList<Question> questionArrayList;
    public static ArrayList<Answer> answerArrayList;
    public static int checkAnswerEl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setWidget();
        questionArrayList = new ArrayList<Question>();
        answerArrayList = new ArrayList<Answer>();

        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawer, toolbar, R.string.navigation_draw_open, R.string.navigation_draw_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
            navigationView.setCheckedItem(R.id.id_nav_home);
        }
        navigationClick();

        bottomNavigationView.setSelectedItemId(R.id.bt_nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);



    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragmentSelected = null;
                    switch (menuItem.getItemId()){
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
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
                        break;
                    case R.id.id_nav_test:
                        Toast.makeText(MainActivity.this, "Test", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.id_nav_learn:
                        Toast.makeText(MainActivity.this, "Learn", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.id_nav_add_account:
                        Toast.makeText(MainActivity.this, "Show dialog ddawng ki", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.id_nav_logout:
                        Toast.makeText(MainActivity.this, "LOG OUT", Toast.LENGTH_SHORT).show();
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

    void setWidget() {
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.bottom_nav);

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
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        if (item.getItemId() == R.id.menu_about) {
            showDialogAbout();
        }
        return super.onOptionsItemSelected(item);
    }

    void showDialogAbout(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_about);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

}