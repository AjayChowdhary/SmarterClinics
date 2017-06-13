package com.development.smarterclinics;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.development.smarterclinics.Models.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClinicLogin extends AppCompatActivity {
    private boolean doubleBackToExitPressedOnce = false;

    public static final String AppPREFERENCES = "SmarterClinics";

    public static String token;

    private EditText email, pass;
    private Button btn;
    ProgressDialog pd;
    Snackbar snackbar,snackbar1,snackbar2;
    private static final String REGISTER_URL = Constants.baseURL+"login";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliniclogin);


        View parentLayout = (View)findViewById(android.R.id.content);
        snackbar = Snackbar.make(parentLayout, "Please enter the Username & Password", Snackbar.LENGTH_SHORT);
        snackbar1 = Snackbar.make(parentLayout, "Please enter the Username", Snackbar.LENGTH_SHORT);
        snackbar2 = Snackbar.make(parentLayout, "Please enter the Password", Snackbar.LENGTH_SHORT);

        email = (EditText) findViewById(R.id.input_email);
        pass = (EditText) findViewById(R.id.input_password);
        btn = (Button) findViewById(R.id.btn_login);

        pass.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press

                    if(email.getText().toString().isEmpty() && pass.getText().toString().isEmpty())
                    {
                        snackbar.show();
                    }
                    else if (email.getText().toString().isEmpty())
                    {
                        snackbar1.show();
                    }
                    else if(pass.getText().toString().isEmpty())
                    {
                        snackbar2.show();
                    }else
                    {
                        ValidateUser();
                    }

                    return true;
                }
                return false;
            }
        });


        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().isEmpty() && pass.getText().toString().isEmpty())
                {
                    snackbar.show();
                }
                else if (email.getText().toString().isEmpty())
                {
                    snackbar1.show();
                }
                else if(pass.getText().toString().isEmpty())
                {
                    snackbar2.show();
                }else
                {
                    ValidateUser();
                }

            }
        });
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void ValidateUser()
    {
        if(isNetworkConnected())
        {
            if(email.getText().toString().length()!=0 && pass.getText().toString().length()!=0)
            {
            pd = new ProgressDialog(ClinicLogin.this);
            pd.setTitle("Logging In");
            pd.setMessage("Please Wait...");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();
            LoginUser(email.getText().toString().trim(),pass.getText().toString().trim());
            }
            else {
                View pl = (View)findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(pl, "credentials not providede", Snackbar.LENGTH_SHORT);
                snackbar.show();

            }
        }
        else
        {
            View pl = (View)findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(pl, "No Internet Connectivity", Snackbar.LENGTH_SHORT);
            snackbar.show();

        }

    }

    private void LoginUser(String email,String pass) {

        try {

            Map<String, String> params = new HashMap<String, String>();
            params.put("username", email);
            params.put("password", pass);
            JSONObject jsonParams = new JSONObject(params);
            Log.i("username :",jsonParams.getString("username"));
            Log.i("password :",jsonParams.getString("password"));
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, REGISTER_URL, jsonParams,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // Parsing json object response
                                // response will be a json object
                                String TokenDB = response.getString("token");
                                JSONObject user=response.getJSONObject("user");
                                Constants.setClinicName(user.getString("name"));

                                //Constants.setTokenDB(TokenDB);
                                if(pd.isShowing())
                                {
                                    pd.dismiss();
                                }

                                //Maintaining LogIn data till user clicks LogOut
                                SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = app_preferences.edit();
                                editor.putString("Token",TokenDB);
                                //editor.putString("username", username);
                                editor.commit();

                                //Printing Token in Log in case of null token debugging
                                /*String status=manager.getPreferences(ClinicLogin.this,"token");
                                Log.d("token", status);
*/
                                Intent intent=new Intent(ClinicLogin.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                                }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),
                                        "Oops! The Username & Password Do Not Match. Please try again!",
                                        Toast.LENGTH_LONG).show();
                              }

                    }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            //   Handle Error
                            if(pd.isShowing()) {pd.dismiss();}
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

                            error.printStackTrace();

                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            postRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000, 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(postRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}