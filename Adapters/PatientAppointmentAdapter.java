package com.development.smarterclinics.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.development.smarterclinics.CancelAppointment;
import com.development.smarterclinics.MainActivity;
import com.development.smarterclinics.Models.Constants;
import com.development.smarterclinics.Models.PatientAppointmentObject;
import com.development.smarterclinics.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientAppointmentAdapter extends BaseAdapter {
    private Context cn;
    private LayoutInflater lInflater;
    private List<PatientAppointmentObject> listStorage;
    ProgressDialog pd;
    public PatientAppointmentAdapter(Context context, List<PatientAppointmentObject> customizedListView) {
        lInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
        cn=context;
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

        ViewHolder listViewHolder;

        if(convertView == null){

            listViewHolder = new ViewHolder();

            convertView = lInflater.inflate(R.layout.patient_appointment_listview, parent, false);

            listViewHolder.PatientName = (TextView)convertView.findViewById(R.id.textView);

            listViewHolder.BookedSlot = (TextView)convertView.findViewById(R.id.textView2);

            listViewHolder.BookedDate = (TextView)convertView.findViewById(R.id.textView3);

            listViewHolder.Cancel     = (ImageButton)convertView.findViewById(R.id.cancel);

            listViewHolder.Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        AlertDialog.Builder al = new AlertDialog.Builder(CancelAppointment.ContextForDialog);
                        al.setMessage("Are you sure to cancel this Appointment\n");
                        al.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                // Write your code here to invoke YES event
                                pd = new ProgressDialog(CancelAppointment.ContextForDialog);
                                pd.setTitle("Cancelling Appointment");
                                pd.setMessage("Please Wait...");
                               // pd.setCancelable(false);
                                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                pd.show();
                                cancelAppointment(position);
                            }
                        });
                        al.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to invoke NO event
                                Toast.makeText(cn, "You clicked on NO", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                        // Showing Alert Message
                        al.show();

                    }catch (Exception e){     Toast.makeText(cn,
                            " at dialog builder Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();}
                }
            });

            convertView.setTag(listViewHolder);

        }else{

            listViewHolder = (ViewHolder)convertView.getTag();

        }

        listViewHolder.PatientName.setText("Doctor Name : " + listStorage.get(position).getDoctorName());

        listViewHolder.BookedSlot.setText("Time : " + listStorage.get(position).getBookedSlot());

        listViewHolder.BookedDate.setText("date : " + listStorage.get(position).getBookedDate());

        return convertView;

    }

    private void cancelAppointment(final int position)
    {
        String CancelURL= Constants.baseURL+"cancelappointment/"+ CancelAppointment.P_id;
        JSONObject body=new JSONObject();
        try {
            body.put("appointment_slot", CancelAppointment.SlotTime.get(position));
            body.put("appointment_date", CancelAppointment.AppointDate.get(position));
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
                            if(pd.isShowing()){pd.dismiss();}
                            final AlertDialog.Builder alsuccess = new AlertDialog.Builder(CancelAppointment.ContextForDialog);
                            alsuccess.setMessage("Appointment Cancelled Successfully");
                            alsuccess.setCancelable(false);
                            alsuccess.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    CancelAppointment.CancelPatientAppointmentIDList.remove(position);
                                    Intent intent=new Intent(cn,MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    cn.startActivity(intent);
                                    CancelAppointment.ContextForDialog.finish();

                                }
                            });
                            alsuccess.setNegativeButton("Cancel Another", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {
                                    CancelAppointment.fetchAllApppointments(CancelAppointment.P_id);
                                    dialogInterface.dismiss();
                                }
                            });

                            alsuccess.show();

                        }else{Toast.makeText(cn,
                                "Error: " + response.toString(),
                                Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(cn,
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }
                }},
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if(pd.isShowing()){pd.dismiss();}
                            CancelAppointment.fetchAllApppointments(CancelAppointment.P_id);
                            Toast.makeText(cn ,error.getMessage(),Toast.LENGTH_LONG).show();
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
            RequestQueue Cancelqueue= Volley.newRequestQueue(cn);
            cancelAppoint.setRetryPolicy(new DefaultRetryPolicy(
                    15000, 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Cancelqueue.add(cancelAppoint);


        }catch(Exception e){Toast.makeText(cn,e.getMessage(),Toast.LENGTH_LONG).show();}

    }

    static class ViewHolder{

        TextView PatientName;

        TextView BookedSlot;

        TextView BookedDate;

        ImageButton Cancel;
    }
}