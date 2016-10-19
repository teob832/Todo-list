package com.example.ud4.todo_list;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;


public class DrawerItemAdapter extends ArrayAdapter<String>
{
    public DrawerItemAdapter(Activity context, ArrayList<String> list) 
    {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        TextView tv = (TextView) view;

        // Check if the existing view is being reused, otherwise inflate the view
        if(tv == null) 
        {
            tv = (TextView) LayoutInflater.from(getContext()).inflate(
                R.layout.drawer_list_item, parent, false);
        }
        //Grab value at given position 
        String currentText = getItem(position);

        //Set the values
        tv.setText(currentText);

        return tv;
    }
}//end-class
