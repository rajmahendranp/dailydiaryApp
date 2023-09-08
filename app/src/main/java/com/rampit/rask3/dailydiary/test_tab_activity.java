package com.rampit.rask3.dailydiary;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.rampit.rask3.dailydiary.Fragment.close_fragment;
import com.rampit.rask3.dailydiary.Fragment.delete_data_fragment;

;
//import com.rampit.rask3.weekly.Fragment.attendance_fragment;
//import com.rampit.rask3.weekly.Fragment.employee_fragment;


//Updated on 25/01/2022 by RAMPIT
//Contains Delete and close fragments


public class test_tab_activity extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView closed;
    ImageView logout;
    Button add_employee;
    AppBarLayout appBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.AppTheme11);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_test_tabs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(com.rampit.rask3.dailydiary.test_tab_activity.this,Settings_activity.class);
                startActivity(main);
                finish();
            }
        });
        appBarLayout = findViewById(R.id.appbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);


        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new delete_data_fragment(), "Delete Data ");
        adapter.addFragment(new close_fragment(), "Close Data ");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
        Intent main = new Intent(com.rampit.rask3.dailydiary.test_tab_activity.this,Settings_activity.class);
        startActivity(main);
        finish();
        super.onBackPressed();
    }
}