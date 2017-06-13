package com.development.smarterclinics.Adapters;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RadioButton;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.development.smarterclinics.Models.Constants;
import com.development.smarterclinics.DocCurrentDaySchedule;
import com.development.smarterclinics.Models.ItemObject;
import com.development.smarterclinics.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// uses two networking thread so keep on changing thr url for testing and production purposes
// this class feeds the data into the list of patient in DoctorCurrentDaySchedule Activity
// it changes the status of the patients according o the current status of the patient
// on successful hit for to_report and checkin function(changeStatus and status checkin function) it recursively calls to the fetchSchedule
// ... function of the wrapping class(DocCurrentSchedule) so that thee radio button changes automatically

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater lInflater;
    private List<ItemObject> listStorage;
    public Context mContext;
    RequestQueue requestQueue;
    ProgressDialog pd, pd1,pdCancel;
    String callOn;
    ViewHolder listViewHolder;

    public CustomAdapter(Context context, List<ItemObject> customizedListView) {
        mContext = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;

    }

    @Override

    public int getCount() {

        return listStorage.size();

    }

    @Override

    public Object getItem(int position) {

        return position;

    }

    @Override

    public long getItemId(int position) {

        return position;

    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            listViewHolder = new ViewHolder();

            convertView = lInflater.inflate(R.layout.customizedlistview, parent, false);

            listViewHolder.PatientName = (TextView) convertView.findViewById(R.id.textView);

            listViewHolder.BookedSlot = (TextView) convertView.findViewById(R.id.textView2);

            //listViewHolder.BookedStatus = (TextView) convertView.findViewById(R.id.ind1);

            listViewHolder.imageButton = (ImageButton)convertView.findViewById(R.id.call_button);

            listViewHolder.cancelButton = (ImageButton)convertView.findViewById(R.id.cancel_button);

            listViewHolder.r1 = (RadioButton)convertView.findViewById(R.id.blue);

            listViewHolder.r2 = (RadioButton)convertView.findViewById(R.id.yellow);

            listViewHolder.r3 = (RadioButton)convertView.findViewById(R.id.green);

            listViewHolder.changeStatus = (ImageButton) convertView.findViewById(R.id.change_status);


            listViewHolder.phone = (TextView) convertView.findViewById(R.id.textView3);

            convertView.setTag(listViewHolder);

        }else{

            listViewHolder = (ViewHolder)convertView.getTag();

        }

        listViewHolder.PatientName.setText(listStorage.get(position).getPatientName());

        listViewHolder.BookedSlot.setText(listStorage.get(position).getBookedSlot());


        if(listStorage.get(position).getStatus().equals("confirmed")) {
                listViewHolder.r1.setChecked(true);
                listViewHolder.r2.setChecked(false);
                listViewHolder.r3.setChecked(false);

            }else if(listStorage.get(position).getStatus().equals("to_report"))
            {
                listViewHolder.r1.setChecked(false);
                listViewHolder.r2.setChecked(true);
                listViewHolder.r3.setChecked(false);
            }
            else if(listStorage.get(position).getStatus().equals("checked_in"))
            {
                listViewHolder.r1.setChecked(false);
                listViewHolder.r2.setChecked(false);
                listViewHolder.r3.setChecked(true);
            }
        listViewHolder.changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (listStorage.get(position).getStatus().equals("confirmed"))
                {
                    changeStatus(position);
                }
                else if(listStorage.get(position).getStatus().equals("to_report"))
                {
                    statusCheckin(position);
                }
                else if(listStorage.get(position).getStatus().equals("checked_in"))
                {
                    Toast.makeText(mContext,"no further action to take",Toast.LENGTH_SHORT).show();
                }
            }
        });
        listViewHolder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder alrt=new AlertDialog.Builder(mContext);
                alrt.setTitle("Cancel Appointment !");
                alrt.setMessage("Are you sure ?");
                alrt.setCancelable(false);

                alrt.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        pdCancel=new ProgressDialog(DocCurrentDaySchedule.ContextForPD);
                        pdCancel.setTitle("Cancelling..");
                        pdCancel.setMessage("Please wait");
                        pdCancel.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pdCancel.setCancelable(false);
                        pdCancel.show();
                        cancelAppointment(position);
                    }
                });
                alrt.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog al=alrt.create();
                al.show();

            }
        });


        listViewHolder.phone.setText(""+listStorage.get(position).getPhone());
        callOn=Long.toString(listStorage.get(position).getPhone());

        listViewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+listStorage.get(position).getPhone()));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                    return;
                }
                mContext.startActivity(callIntent);
            }
        });


        return convertView;

    }

    private void cancelAppointment(final int position)
    {
        String CancelURL= Constants.baseURL+"cancelappointment/"+Constants.PatientIDofSelectedDocs.get(position);
        JSONObject body=new JSONObject();
        try {
            body.put("appointment_slot", DocCurrentDaySchedule.slotTime.get(position));
            body.put("appointment_date", DocCurrentDaySchedule.DocCurrentDayDate);
        }catch (JSONException j){j.printStackTrace();}
        try
        {

            JsonObjectRequest cancelAppoint=new JsonObjectRequest(Request.Method.PUT,CancelURL,body,new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        Boolean success=response.getBoolean("success");
                        String message=response.getString("message");
                        if(success) {
                            if(pdCancel.isShowing()){pdCancel.dismiss();}
                            final AlertDialog.Builder alsuccess = new AlertDialog.Builder(DocCurrentDaySchedule.ContextForPD);
                            alsuccess.setMessage("Appointment Cancelled Successfully");
                            alsuccess.setCancelable(false);
                            alsuccess.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    DocCurrentDaySchedule.slotTime.remove(position);
                                    Constants.PatientIDofSelectedDocs.remove(position);
                                    DocCurrentDaySchedule.fetchCurrentDaySchedule(Constants.getDoctorSelectedId());
                                }

                            });
                            alsuccess.setNegativeButton("Cancel Another", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {
                                    dialogInterface.dismiss();
                                    DocCurrentDaySchedule.fetchCurrentDaySchedule(Constants.getDoctorSelectedId());
                                }
                            });

                            alsuccess.show();

                        }else
                        {
                            Toast.makeText(mContext,
                                    "Error: " + response.toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext,
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }
                }},
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if(pdCancel.isShowing()){pdCancel.dismiss();}
                            DocCurrentDaySchedule.fetchCurrentDaySchedule(Constants.getDoctorSelectedId());
                            Toast.makeText(mContext ,error.getMessage(),Toast.LENGTH_LONG).show();
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                error.printStackTrace();
                                Toast.makeText(mContext, "Network Error", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof AuthFailureError) {
                                //TODO
                                error.printStackTrace();
                                Toast.makeText(mContext, "User not authorized", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ServerError) {
                                //TODO
                                error.printStackTrace();
                                Toast.makeText(mContext, "Server error", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NetworkError) {
                                //TODO
                                error.printStackTrace();
                                Toast.makeText(mContext, "Network Error", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ParseError) {
                                //TODO
                                error.printStackTrace();
                                Toast.makeText(mContext, "Error consuming request", Toast.LENGTH_SHORT).show();
                            }
                            else error.printStackTrace();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("x-access-token",Constants.getTokenDB());
                    return headers;
                }
            };
            RequestQueue Cancelqueue= Volley.newRequestQueue(mContext);
            cancelAppoint.setRetryPolicy(new DefaultRetryPolicy(
                    15000, 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Cancelqueue.add(cancelAppoint);


        }catch(Exception e){Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_LONG).show();}
    }

    // this function will change the current status of patient from to_report to check_in
    private void statusCheckin(final int position)
    {
        requestQueue = Volley.newRequestQueue(mContext);

        String ChangeStatusCheck_in=Constants.EmrbaseURL+"checkin";
        try {
            final JSONObject jsonParams = new JSONObject();
            jsonParams.put("slot_id",Constants.SlotIDCurrentAppoint.get(position));
            jsonParams.put("doctor_id",Constants.DoctorSelectedId);
            try{jsonParams.put("email",Constants.EmailCurrentDayPatients.get(position));}catch (Exception e){jsonParams.put("email","");}
            jsonParams.put("gender",Constants.GenderCurrentDayPatients.get(position));
            jsonParams.put("age",Constants.AgeCurrentDayPatients.get(position));
            jsonParams.put("patient_id",Constants.PatientIDofSelectedDocs.get(position));
            jsonParams.put("first_name",Constants.FnameCurrentDayPatients.get(position));
            jsonParams.put("last_name",Constants.LnameCurrentDayPatients.get(position));
            jsonParams.put("mobile_number",Constants.MobileCurrentDayPatients.get(position));
            JsonObjectRequest postRequestCheck = new JsonObjectRequest(Request.Method.POST, ChangeStatusCheck_in, jsonParams,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try
                            {

                                Boolean success=response.getBoolean("success");

                                if(success)
                                {
                                    listViewHolder.r1.setChecked(false);
                                    listViewHolder.r2.setChecked(false);
                                    listViewHolder.r3.setChecked(true);
                                    DocCurrentDaySchedule.fetchCurrentDaySchedule(Constants.getDoctorSelectedId());
                                    Toast.makeText(mContext,"Checked-in",Toast.LENGTH_LONG).show();}
                                else
                                {
                                    Toast.makeText(mContext,response.getString("message"),Toast.LENGTH_LONG).show();
                                }

                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                                Toast.makeText(mContext,
                                        "Unable to change",
                                        Toast.LENGTH_LONG).show();

                            }
                            DocCurrentDaySchedule.Appoint_list.notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            //   Handle Error
                            if(pd1.isShowing()){pd1.dismiss();}
                            Toast.makeText(mContext, "Error in changing status :"+error.getMessage(), Toast.LENGTH_SHORT).show();
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                error.printStackTrace();
                                Toast.makeText(mContext, "Network Error", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof AuthFailureError) {
                                //TODO
                                error.printStackTrace();
                                Toast.makeText(mContext, "User not authorized", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ServerError) {
                                //TODO
                                error.printStackTrace();
                                Toast.makeText(mContext, "Server error", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NetworkError) {
                                //TODO
                                error.printStackTrace();
                                Toast.makeText(mContext, "Network Error", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ParseError) {
                                //TODO
                                error.printStackTrace();
                                Toast.makeText(mContext, "Error consuming request", Toast.LENGTH_SHORT).show();
                            }
                            else error.printStackTrace();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("x-access-token", Constants.getTokenDB());
                    return headers;
                }
            };
            postRequestCheck.setRetryPolicy(new DefaultRetryPolicy(
                    7000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(postRequestCheck);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this function will change the status of patient in the list
    private void changeStatus(int position)
    {
        requestQueue = Volley.newRequestQueue(mContext);
        String ChangeStatus=Constants.baseURL+"patient/toreport";

        try {
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("slot_id",Constants.SlotIDCurrentAppoint.get(position));
            jsonParams.put("doctor_id",Constants.DoctorSelectedId);
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, ChangeStatus, jsonParams,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try
                            {
                                Boolean success=response.getBoolean("success");

                                if(success)
                                {
                                    listViewHolder.r1.setChecked(false);
                                    listViewHolder.r2.setChecked(true);
                                    listViewHolder.r3.setChecked(false);
                                    Toast.makeText(mContext,"Reported",Toast.LENGTH_SHORT).show();
                                    DocCurrentDaySchedule.fetchCurrentDaySchedule(Constants.getDoctorSelectedId());
                                }
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                                Toast.makeText(mContext,
                                        "unable to change",
                                        Toast.LENGTH_LONG).show();
                            }
                            DocCurrentDaySchedule.Appoint_list.notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            //   Handle Error
                            //if(pd.isShowing()){pd.dismiss();}
                            Toast.makeText(mContext, "Error in Changing Status :"+error.getMessage(), Toast.LENGTH_SHORT).show();
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                error.printStackTrace();
                                Toast.makeText(mContext, "Network Error", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof AuthFailureError) {
                                //TODO
                                error.printStackTrace();
                                Toast.makeText(mContext, "User not authorized", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ServerError) {
                                //TODO
                                error.printStackTrace();
                                Toast.makeText(mContext, "Server error", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NetworkError) {
                                //TODO
                                error.printStackTrace();
                                Toast.makeText(mContext, "Network Error", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ParseError) {
                                //TODO
                                error.printStackTrace();
                                Toast.makeText(mContext, "Error consuming request", Toast.LENGTH_SHORT).show();
                            }
                            else error.printStackTrace();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("x-access-token", Constants.getTokenDB());
                    return headers;
                }
            };
            postRequest.setRetryPolicy(new DefaultRetryPolicy(
                    7000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(postRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class ViewHolder{


        TextView PatientName;

        TextView BookedSlot;

        TextView phone;

        ImageButton imageButton,cancelButton;

        // TextView BookedStatus;

        RadioButton r1,r2,r3;

        ImageButton changeStatus;

    }
}