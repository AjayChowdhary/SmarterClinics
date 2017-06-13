package com.development.smarterclinics.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.development.smarterclinics.Models.Patient;
import com.development.smarterclinics.R;

import java.util.List;

public class SearchPatientAdapter extends ArrayAdapter<Patient>{

    private List<Patient> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtFName;
        TextView txtMobile;
        TextView txtGender;
    }

    public SearchPatientAdapter(List<Patient> data, Context context) {
        super(context, R.layout.patient_search_list_item, data);
        this.dataSet = data;
        this.mContext=context;

    }


    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Patient patient = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.patient_search_list_item, parent, false);
            viewHolder.txtFName = (TextView) convertView.findViewById(R.id.cardName);
            viewHolder.txtMobile = (TextView) convertView.findViewById(R.id.cardMobile);
            viewHolder.txtGender = (TextView) convertView.findViewById(R.id.cardGender);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtFName.setText(dataSet.get(position).getName());
        viewHolder.txtMobile.setText(""+dataSet.get(position).getMobile());
        viewHolder.txtGender.setText(dataSet.get(position).getGender());
        // Return the completed view to render on screen
        return convertView;
    }
}