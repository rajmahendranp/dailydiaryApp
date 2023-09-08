package com.rampit.rask3.dailydiary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.core.Repo;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;



//Updated on 25/01/2022 by RAMPIT
//Contains buttons of all reports
//onBackPressed() - function called when back button is pressed
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed




public class ReportActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button cur,clo,tod,nip,nipnip,incom,totaaa,finalrepo,remaining;
    SharedPreferences.Editor editor;
    NavigationView navigationView;
    Menu nav_Menu;
    String zero,one,two,three,four,five,six,seven,eight,nine,ten,eleven,twelve,thirteen,fourteen,fifteen,sixteen,seventeen,eighteen,adpr,edpr,depr,vipr,ss,quickbulk1;
    ScrollView sc1,sc2;
    LinearLayout lll;
    Button logou,profil;
    Integer showinng = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("theme","");
        if(ii.equalsIgnoreCase("")){
            ii = "1";
        }
        Log.d("thee",ii);
        Integer in = Integer.valueOf(ii) ;
        quickbulk1 = pref.getString("quickbulk","0");
        if(in == 0){
            Log.d("thee","thh");
            setTheme(R.style.AppTheme1);
        }else{
            Log.d("thee","th1h");
            setTheme(R.style.AppTheme11);
//            recreate();
        }
        String localeName = pref.getString("language","");
        if(localeName == null){
            localeName = "ta";
        }
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_report);
        setTitle(R.string.title_activity_report);
        ((Callback)getApplication()).datee();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        setSupportActionBar(toolbar);
        cur = findViewById(R.id.current);
        clo = findViewById(R.id.closed);
        tod = findViewById(R.id.today);
        nip = findViewById(R.id.NIP);
        remaining = findViewById(R.id.remaining);
        incom = findViewById(R.id.incompleted);
        totaaa = findViewById(R.id.totalcus);
        sc1 =findViewById(R.id.scroll1);
        sc2 =findViewById(R.id.scroll2);
        nipnip = findViewById(R.id.NIPNIP);
        finalrepo = findViewById(R.id.finalreport);
        lll = (findViewById(R.id.linee));
        TextView pro = lll.findViewById(R.id.pro);
        logou = lll.findViewById(R.id.logout);
        profil = lll.findViewById(R.id.profile);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nav_Menu = navigationView.getMenu();
        LinearLayout linear = findViewById(R.id.linearr);
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            //your method here
                            final SharedPreferences pref11 = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                            String isLoading = pref11.getString("isLoading","no");
                            String isLoaded_time = pref11.getString("isLoaded_time","no");
                            Log.d("isLoading",isLoading+" "+isLoaded_time);
                            if (InternetConnection1.checkConnection(getApplicationContext(), ReportActivity.this)) {
                                // Internet Available...
                                Log.d("internet","on");
                                showinng = 0;
                            }
                            else {
//                                progre();
                                // Internet Not Available...
                                Log.d("internet","off");
                                runOnUiThread(new Runnable() {
                                    @SuppressLint("RestrictedApi")
                                    @Override
                                    public void run() {
                                        // Stuff that updates the UI
//                                    fab.hide();
//                                        progressBar_cyclic.setVisibility(View.GONE);
//                                        textView3.setVisibility(View.GONE);
//                                        textView2.setText(getString(R.string.backup_status)+getString(R.string.check_internet));
//                                        textView3.setText(R.string.updated_time+isLoaded_time);
                                        if(isLoading.equalsIgnoreCase("no") && showinng.equals(0)){
                                            Snackbar snackbar = Snackbar
                                                    .make(linear, getString(R.string.on_internet_for_backup), Snackbar.LENGTH_INDEFINITE)
                                                    .setAction(getString(R.string.ok), new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            showinng = 0;
                                                        }
                                                    });
                                            snackbar.setActionTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Red)));
                                            View sbView = snackbar.getView();
                                            TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
                                            textView.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
                                            snackbar.show();
                                            showinng = 1 ;
                                        }

                                    }
                                });


                            }

                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 10000); //execute in every 10 minutes
        if(in == 0){
            cur.setBackgroundResource(R.drawable.report_button);
            clo.setBackgroundResource(R.drawable.report_button);
            tod.setBackgroundResource(R.drawable.report_button);
            nip.setBackgroundResource(R.drawable.report_button);
            incom.setBackgroundResource(R.drawable.report_button);
            nipnip.setBackgroundResource(R.drawable.report_button);
            totaaa.setBackgroundResource(R.drawable.report_button);
            finalrepo.setBackgroundResource(R.drawable.report_button);
            remaining.setBackgroundResource(R.drawable.report_button);
        }else{
            cur.setBackgroundResource(R.drawable.report_button1);
            clo.setBackgroundResource(R.drawable.report_button1);
            tod.setBackgroundResource(R.drawable.report_button1);
            nip.setBackgroundResource(R.drawable.report_button1);
            incom.setBackgroundResource(R.drawable.report_button1);
            nipnip.setBackgroundResource(R.drawable.report_button1);
            totaaa.setBackgroundResource(R.drawable.report_button1);
            finalrepo.setBackgroundResource(R.drawable.report_button1);
            remaining.setBackgroundResource(R.drawable.report_button1);
        }
         editor = pref.edit();
        String sesid = pref.getString("id","");
        Log.d("sesiddd",sesid);
        if(sesid.equalsIgnoreCase("1")){
            if(quickbulk1.equalsIgnoreCase("0")){
                remaining.setVisibility(View.GONE);
            }else{
                remaining.setVisibility(View.VISIBLE);
            }
        }
        else{
            remaining.setVisibility(View.GONE);
            String module_i = "6";
            ((Callback)getApplication()).privilege(sesid,module_i);
            adpr = pref.getString("add_privilege","");
            edpr = pref.getString("edit_privilege","");
            depr = pref.getString("delete_privilege","");
            vipr = pref.getString("view_privilege","");

            zero = pref.getString("company","");
            one = pref.getString("user1","");
            two = pref.getString("debit","");
            three = pref.getString("collection","");
            four = pref.getString("account","");
            five = pref.getString("reports","");
            six = pref.getString("customer","");
            seven = pref.getString("settings","");
            eight = pref.getString("tally","");
            nine = pref.getString("employee_details","");
            ten = pref.getString("today_report","");
            eleven = pref.getString("current_account","");
            twelve = pref.getString("closed_account","");
            thirteen = pref.getString("today_account","");
            fourteen = pref.getString("current_incompleted_account","");
            fifteen = pref.getString("NIP_account","");
            sixteen = pref.getString("NIPNIP_account","");
            seventeen = pref.getString("total_customer","");
            eighteen = pref.getString("final_report","");

            if(zero.equalsIgnoreCase("0")) {
                nav_Menu.findItem(R.id.company).setVisible(false);
            }
            if(one.equalsIgnoreCase("0")) {
                nav_Menu.findItem(R.id.user).setVisible(false);
            }
            if(two.equalsIgnoreCase("0")) {
                nav_Menu.findItem(R.id.debit).setVisible(false);
            }
            if(three.equalsIgnoreCase("0")) {
                nav_Menu.findItem(R.id.collection).setVisible(false);
            }
            if(four.equalsIgnoreCase("0")) {
                nav_Menu.findItem(R.id.account).setVisible(false);
            }
            if(five.equalsIgnoreCase("0")) {
                nav_Menu.findItem(R.id.report).setVisible(false);
            }
            if(six.equalsIgnoreCase("0")) {
                nav_Menu.findItem(R.id.customer).setVisible(false);
            }
            if(seven.equalsIgnoreCase("0")) {
                nav_Menu.findItem(R.id.settings).setVisible(false);
                profil.setVisibility(View.GONE);
                pro.setVisibility(View.GONE);
            }
            if(eight.equalsIgnoreCase("0")) {
                nav_Menu.findItem(R.id.tally).setVisible(false);
            }
            if(nine.equalsIgnoreCase("0")) {
                nav_Menu.findItem(R.id.employee).setVisible(false);
            }
            if(ten.equalsIgnoreCase("0")) {
                nav_Menu.findItem(R.id.today).setVisible(false);
            }


            if(eleven.equalsIgnoreCase("0")) {
                cur.setVisibility(View.GONE);
            }
            if(twelve.equalsIgnoreCase("0")) {
                clo.setVisibility(View.GONE);
            }
            if(thirteen.equalsIgnoreCase("0")) {
                tod.setVisibility(View.GONE);
            }
            if(fourteen.equalsIgnoreCase("0")) {
                incom.setVisibility(View.GONE);
            }
            if(fifteen.equalsIgnoreCase("0")) {
                nip.setVisibility(View.GONE);
            }
            if(sixteen.equalsIgnoreCase("0")) {
                nipnip.setVisibility(View.GONE);
            }
            if(seventeen.equalsIgnoreCase("0")) {
                totaaa.setVisibility(View.GONE);
            }
            if(eighteen.equalsIgnoreCase("0")) {
                finalrepo.setVisibility(View.GONE);
            }
        }


        Integer ses = pref.getInt("session",0);
        String dd = pref.getString("Date","");
        String dsdsd = pref.getString("NAME","");
        if(ses == 1){
            ss = getString(R.string.morning);
        }else if(ses == 2){
            ss = getString(R.string.evening);
        }
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView nn = headerView.findViewById(R.id.name);
        TextView nnn = headerView.findViewById(R.id.date);
        TextView nnnn = headerView.findViewById(R.id.session);
        LinearLayout wq = headerView.findViewById(R.id.navdash);
        ImageView logy = headerView.findViewById(R.id.logy);
        if(in == 0){
            logy.setBackgroundResource(R.drawable.logout);
        }else{
            logy.setBackgroundResource(R.drawable.logoutblue);
        }
        logy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(ReportActivity.this,R.style.AlertDialogTheme);
                String logmsg = getString(R.string.logout_alert);
                String cann = getString(R.string.cancel);
                String warr = getString(R.string.warning);
                String logo = getString(R.string.Logout);
                alertbox.setMessage(logmsg);
                alertbox.setTitle(warr);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setNeutralButton(cann,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                            }
                        });
                alertbox.setPositiveButton(logo,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                editor.clear();
                                editor.putInt("isloginn",0);
                                editor.commit();
                                Intent i = new Intent(ReportActivity.this,Splash.class);
                                startActivity(i);
                                finish();
                            }
                        });
                alertbox.show();
            }
        });
        remaining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent remm  = new Intent(ReportActivity.this,Remaining.class);
                startActivity(remm);
                finish();
            }
        });

        wq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qq = new Intent(ReportActivity.this,NavigationActivity.class);
                startActivity(qq);
                finish();
            }
        });

        nn.setText(dsdsd);
        nnn.setText("Date :"+" "+dd);
        nnnn.setText("Session :"+" "+ss);
//        lll = (findViewById(R.id.linee));
//        logou = lll.findViewById(R.id.logout);
//        profil = lll.findViewById(R.id.profile);
        Button theme = lll.findViewById(R.id.theme);
        if(in == 0){
            logou.setBackgroundResource(R.drawable.logout);
            profil.setBackgroundResource(R.drawable.user1);
            theme.setBackgroundResource(R.drawable.theme);
        }else{
            logou.setBackgroundResource(R.drawable.logoutblue);
            profil.setBackgroundResource(R.drawable.user_blue);
            theme.setBackgroundResource(R.drawable.theme1);
        }
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReportActivity.this,Settings_activity.class);
                startActivity(i);
                finish();
            }
        });
        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String err = pref.getString("theme","");
                if(err.equalsIgnoreCase("")){
                    err = "1";
                }
                Integer trt = Integer.parseInt(err);
                if(trt.equals(0)){
                    editor.putString("theme","1");
                    editor.apply();
                    Intent i = new Intent(ReportActivity.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    editor.putString("theme","0");
                    editor.apply();
                    Intent i = new Intent(ReportActivity.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        });

        logou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(ReportActivity.this,R.style.AlertDialogTheme);
                String logmsg = getString(R.string.logout_alert);
                String cann = getString(R.string.cancel);
                String warr = getString(R.string.warning);
                String logo = getString(R.string.Logout);
                alertbox.setMessage(logmsg);
                alertbox.setTitle(warr);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setNeutralButton(cann,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {

                            }
                        });
                alertbox.setPositiveButton(logo,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                editor.clear();
                                editor.putInt("isloginn",0);
                                editor.commit();
                                Intent i = new Intent(ReportActivity.this,Splash.class);
                                startActivity(i);
                                finish();
                            }
                        });
                alertbox.show();
            }
        });
//        if(adpr.equalsIgnoreCase("0")){
//            sc1.setVisibility(View.VISIBLE);
//            sc2.setVisibility(View.GONE);
//        }
        cur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cur = new Intent(ReportActivity.this,Current_activity.class);
                startActivity(cur);
//                finish();
            }
        });
        clo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cur = new Intent(ReportActivity.this,Closedaccount_activity.class);
                startActivity(cur);
//                finish();
            }
        });
        tod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cur = new Intent(ReportActivity.this,Todaycollection_activity.class);
                startActivity(cur);
//                finish();
            }
        });
        nip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cur = new Intent(ReportActivity.this,NIP_activity.class);
                startActivity(cur);
//                finish();
            }
        });
        incom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cur = new Intent(ReportActivity.this,Incompletedaccount_activity.class);
                startActivity(cur);
//                finish();
            }
        });
        nipnip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cur = new Intent(ReportActivity.this,NIPNIP_activity.class);
                startActivity(cur);
//                finish();
            }
        });
        totaaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cur = new Intent(ReportActivity.this,Totalcustomer_activity.class);
                startActivity(cur);
//                finish();
            }
        });
        finalrepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cur = new Intent(ReportActivity.this,Final_report.class);
                startActivity(cur);
//                finish();
            }
        });

    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final ReportActivity account) {
            final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
//                Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();
                String sss = String.valueOf(activeNetworkInfo.getTypeName());
                Log.d("y7",sss);

                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
//                    yy = 1;
                    return true;
                } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
//                    yy = 2;
                    return true;
                }else{

                }
            }else{
                Log.d("y7","null");
                AlertDialog.Builder alertbox = new AlertDialog.Builder(account,R.style.AlertDialogTheme);
                String enn = account.getString(R.string.on_internet);
                String war = account.getString(R.string.warning);
                String ook = account.getString(R.string.ok);
                alertbox.setMessage(enn);
                alertbox.setTitle(war);
                alertbox.setCancelable(false);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setPositiveButton(ook,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                try {
//                                    login.setMobileDataEnabled(login,true);
//                                    settings_activity.ssss();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
//                alertbox.show();
            }
            return false;
        }
    }

    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
        Intent nn = new Intent(ReportActivity.this,NavigationActivity.class);
        startActivity(nn);
        finish();
    }
    //onCreateOptionsMenu() - when navigation menu created
    //Params - Menu
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main2, menu);

        return true;
    }
    //onOptionsItemSelected() - when navigation item pressed
    //Params - selected item
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
    //onNavigationItemSelected() - when navigation item pressed
    //Params - selected item
    //Created on 25/01/2022
    //Return - NULL
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        if (id == R.id.company) {
            // Handle the camera action
            Intent new1 = new Intent(ReportActivity.this,MainActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.user) {
            Intent new1 = new Intent(ReportActivity.this,HomeActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.debit) {
            Intent new1 = new Intent(ReportActivity.this,Debit.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.collection) {
            Intent new1 = new Intent(ReportActivity.this,AllCollection.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.account) {
            Intent new1 = new Intent(ReportActivity.this,Account.class);
            startActivity(new1);
            finish();
        }  else if (id == R.id.report) {
            Intent new1 = new Intent(ReportActivity.this,ReportActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.customer) {
            Intent new1 = new Intent(ReportActivity.this,Customer_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.settings) {
            Intent new1 = new Intent(ReportActivity.this,Settings_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.tally) {
            Intent new1 = new Intent(ReportActivity.this,Tally_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.employee) {
            Intent new1 = new Intent(ReportActivity.this,Employee_details.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.today) {
            Intent new1 = new Intent(ReportActivity.this,Today_report.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.logout) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(ReportActivity.this,R.style.AlertDialogTheme);
            alertbox.setMessage("Are You Sure want to logout");
            alertbox.setTitle("Warning");
            alertbox.setIcon(R.drawable.logo);
            alertbox.setNeutralButton("CANCEL",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {


                        }
                    });
            alertbox.setPositiveButton("LOGOUT",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {

                            editor.clear();
                            editor.putInt("isloginn",0);
                            editor.commit();
                            Intent i = new Intent(ReportActivity.this,Splash.class);
                            startActivity(i);
                            finish();
                        }
                    });
            alertbox.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
