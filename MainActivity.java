package com.development.smarterclinics;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.development.smarterclinics.Models.Constants;
import com.development.smarterclinics.Models.Doc;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private boolean doubleBackToExitPressedOnce = false;

    BottomBar mBottomBar;
    TextView tvNavClinicName,dateTextView;
    private ListView AppointList;
    ListView listView;
    RequestQueue rqueue;
    FloatingActionButton fab;
    NavigationView navigationView;
    ProgressDialog pd;
    private static SwipeRefreshLayout swipeContainer;

    public static String DoctorDetail_URL = Constants.baseURL+"dashboard";

    static List<String>listOfDoctorsRegisteredWithClinic=new ArrayList<>();
    static List<String>listID=new ArrayList<>();
    static List<Integer>listSlotLength=new ArrayList<>();



    private RecyclerView recyclerView;
    public static MainActivity mcontext;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static List<Doc>dataArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences app=PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String token=app.getString("Token","");
        Constants.setTokenDB(token);

        swipeContainer=(SwipeRefreshLayout)findViewById(R.id.swipeContainer);

        navigationView=(NavigationView)findViewById(R.id.nav_view);
        View hView=navigationView.getHeaderView(0);

        fab=(FloatingActionButton)findViewById(R.id.fab) ;
       // fab.setBackgroundColor(getResources().getColor(R.color.orange));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),PatientSearchNew.class);
                startActivity(intent);
            }
        });

        mcontext=MainActivity.this;
        recyclerView = (RecyclerView) findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);



        dateTextView = (TextView)findViewById(R.id.todayDate);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String yyyy,mm,dd;
        dd = date.substring(8,10);
        mm = date.substring(5,7);
        yyyy = date.substring(0,4);
        String finalDateFormat = dd+"."+mm+"."+yyyy;


        dateTextView.setText(finalDateFormat);

        tvNavClinicName=(TextView)hView.findViewById(R.id.navHeaderText);
        tvNavClinicName.setText(Constants.getClinicName());
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.bottom_menu_bar);
        mBottomBar.setDefaultTabPosition(2);
        mBottomBar.noTopOffset();
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {

                if (menuItemId == R.id.dashboard)
                {/*
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);*/
                }
                if (menuItemId == R.id.set_schedule) {
                    // The user selected item number one.
                    Intent intent = new Intent(getApplicationContext(),SetSchedule.class);
                    startActivity(intent);
                }
                if (menuItemId == R.id.patient_search) {
                    // The user selected item number two.
                    Intent intent = new Intent(getApplicationContext(),PatientSearchNew.class);
                    startActivity(intent);
                }
                if (menuItemId == R.id.book_appointment) {
                    // The user selected item number three.
                    Intent intent=new Intent(getApplicationContext(),PatientRegistration.class);
                    startActivity(intent);
                }
                if (menuItemId == R.id.cancel_appointment) {
                    // The user selected item number four.
                    Intent intent=new Intent(getApplicationContext(),SearchPatientCancelAppointment.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.dashboard)
                {
                    Toast.makeText(getApplicationContext(),"You are on your Dashboard!",Toast.LENGTH_SHORT).show();
                    /*
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);*/
                }
                if (menuItemId == R.id.set_schedule) {
                    // The user selected item number one.
                    Intent intent = new Intent(getApplicationContext(),SetSchedule.class);
                    startActivity(intent);
                }
                if (menuItemId == R.id.patient_search) {
                    // The user selected item number two.
                    Intent intent = new Intent(getApplicationContext(),PatientSearchNew.class);
                    startActivity(intent);
                }
                if (menuItemId == R.id.book_appointment) {
                    // The user selected item number three.
                    Intent intent=new Intent(getApplicationContext(),PatientRegistration.class);
                    startActivity(intent);
                }
                if (menuItemId == R.id.cancel_appointment) {
                    // The user selected item number four.
                    Intent intent=new Intent(getApplicationContext(),SearchPatientCancelAppointment.class);
                    startActivity(intent);
                }
            }
        });

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
        mBottomBar.mapColorForTab(0, "#7B1FA2");
        mBottomBar.mapColorForTab(1, "#F36D44");
        mBottomBar.mapColorForTab(2, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(3, "#FF5252");
        mBottomBar.mapColorForTab(4, "#00acc1");
        rqueue=Volley.newRequestQueue(MainActivity.this);
        pd=new ProgressDialog(MainActivity.this);
        pd.setMessage("Fetching Doctors list");
        pd.setCancelable(true);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Toast.makeText(getApplicationContext(),"fetching data in background",Toast.LENGTH_SHORT).show();
            }
        });
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
        getDocname();
        swipeContainer.setColorSchemeResources(R.color.red_color,
                R.color.aa_black,
                R.color.anchor_color,
                R.color.bb_darkBackgroundColor);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataArray.clear();
                pd.setMessage("Fetching Doctors list");
                pd.setCancelable(false);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.show();
                getDocname();

            }
        });
        AppointList = (ListView) findViewById(R.id.listView);

        View parentLayout = (View)findViewById(android.R.id.content);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    // Set Doctor Detail in the Card
    private void getDocname()
    {
        swipeContainer.setRefreshing(true);
        listOfDoctorsRegisteredWithClinic.clear();
        listID.clear();
        listSlotLength.clear();


        try{

            JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET,DoctorDetail_URL,null,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            try {dataArray.clear();
                                for(int i = 0; i<response.length(); i++)
                                {
                                    JSONObject DocDetail=(JSONObject)response.get(i);
                                    String id=DocDetail.getString("_id");
                                    Integer Slot_length=DocDetail.getInt("slot_length");
                                    JSONObject profile=DocDetail.getJSONObject("profile");
                                    String fname=profile.getString("first_name");
                                    String lname,gender;
                                   try{lname=profile.getString("last_name");}catch(Exception e){lname="";}
                                    String speciality=profile.getString("specialist");
                                    String mobile=profile.getString("phone_number");
                                    try{gender=profile.getString("gender");}catch (Exception e){gender="";}
                                    String fullName=fname+" "+lname;
                                    listOfDoctorsRegisteredWithClinic.add(fullName);
                                    listID.add(id);
                                    listSlotLength.add(Slot_length);
                                    Doc d1=new Doc(fullName,speciality,mobile,gender);
                                    dataArray.add(d1);
                                }

                                String[] DoctorNameReception =new String[listOfDoctorsRegisteredWithClinic.size()];
                                DoctorNameReception =listOfDoctorsRegisteredWithClinic.toArray(DoctorNameReception);
                                Constants.nameArrayDoc= DoctorNameReception;

                                String[] DoctorIdReception =new String[listID.size()];
                                DoctorIdReception =listID.toArray(DoctorIdReception);
                                Constants.idArray= DoctorIdReception;

                                Integer[] DoctorSlotLength =new Integer[listSlotLength.size()];
                                DoctorSlotLength =listSlotLength.toArray(DoctorSlotLength);
                                Constants.slotOfDocs= DoctorSlotLength;

                                adapter = new RecycleAdapter(dataArray,getApplicationContext());
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                if(pd.isShowing()){pd.dismiss();}
                            }

                            catch (Exception e)
                            {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),
                                        "Error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();

                            }
                            swipeContainer.setRefreshing(false);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            //   Handle Error
                            swipeContainer.setRefreshing(false);
                            Toast.makeText(getApplicationContext(), "We are facing some issues while fetching the complete doctor list, please log out & log in again.", Toast.LENGTH_SHORT).show();
                            if(pd.isShowing()){pd.dismiss();}
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                error.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof AuthFailureError) {
                                //TODO
                                error.printStackTrace();
                                Toast.makeText(getApplicationContext(), "User not authorized", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ServerError) {
                                //TODO
                                error.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NetworkError) {
                                //TODO
                                error.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ParseError) {
                                //TODO
                                error.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Error consuming request", Toast.LENGTH_SHORT).show();
                            }
                            else error.printStackTrace();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("x-access-token",Constants.getTokenDB());
                    return headers;
                }
            };
            RequestQueue rqueue= Volley.newRequestQueue(getApplicationContext());
            getRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rqueue.add(getRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                finish();
            }
            else {
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Press back again to EXIT the app", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //Setting message manually and performing action on button click
            builder.setTitle("Confirm Log Out");
            builder.setIcon(R.drawable.ic_exit);
            builder.setMessage("Are you sure you want to log out?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Action for 'YES' Button

                            //Remove the session of the current logged in user

                            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = app_preferences.edit();
                            editor.putString("Token","");
                            //editor.putString("username", username);
                            editor.commit();
                            Constants.setTokenDB("");
                            try{trimCache(this);}catch (Exception e){Toast.makeText(getApplicationContext(),"Error clearing cache",Toast.LENGTH_SHORT).show();}
                            Intent intent=new Intent(MainActivity.this,ClinicLogin.class);
                            startActivity(intent);
                            finish();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.dismiss();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();

            return true;
        }else if(id==R.id.exit)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            //Setting message manually and performing action on button click
            alert.setTitle("Confirm Exit");
            alert.setIcon(R.drawable.ic_logout);
            alert.setMessage("Are you sure you want to exit the application?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Action for 'YES' Button
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.dismiss();
                        }
                    });

            AlertDialog ad = alert.create();
            ad.show();

        }
        return super.onOptionsItemSelected(item);
    }

    private void trimCache(DialogInterface.OnClickListener onClickListener) {
            try {
                File dir = this.getCacheDir();
                if (dir != null && dir.isDirectory()) {
                    deleteDir(dir);

                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"unable to clear cache! Please try again",Toast.LENGTH_LONG).show();
                // TODO: handle exception
            }
        }


    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
    return dir.delete();
    }

    // Navigation menu
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        // for support class
        if (id == R.id.Support)
        {
            final Intent email = new Intent(Intent.ACTION_SEND);
            email.setType("message/rfc822");
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{ getString(R.string.mail_feedback_email) });
            email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_feedback_subject));
            email.putExtra(Intent.EXTRA_TEXT, getString(R.string.mail_feedback_message));
            startActivity(Intent.createChooser(email, getString(R.string.title_send_feedback)));
        }
        else if (id == R.id.SearchPatient)
        {
            Intent intent=new Intent(getApplicationContext(),PatientSearchNew.class);
            this.startActivity(intent);
        }
        else if (id == R.id.RegiPatient)
        {

            Intent intent=new Intent(getApplicationContext(),PatientRegistration.class);
            this.startActivity(intent);
        }

        else if (id == R.id.CancelAppointment)
        {
            Intent intent=new Intent(getApplicationContext(),SearchPatientCancelAppointment.class);
            this.startActivity(intent);
        }
        else if (id == R.id.SetScheduleNav)
        {
            Intent intent=new Intent(getApplicationContext(),SetSchedule.class);
            this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
