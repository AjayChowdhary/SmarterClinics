package com.development.smarterclinics.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class DoctorAdapter extends ArrayAdapter{
    Context context;
    int layoutResourceId;
    ArrayList<String> docNames;

    public DoctorAdapter(Context context, int layoutResourceId, ArrayList<String> docNames) {
        super(context, layoutResourceId, docNames);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.docNames = docNames;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return super.getView(position, convertView, parent);
    }

}
