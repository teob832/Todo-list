package com.example.ud4.todo_list;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ArrayAdapter;
import java.util.ArrayList;



public class ItemAdapter extends ArrayAdapter<String>
{
    public ItemAdapter(Activity context, ArrayList<String> list) 
    {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View listItemView, ViewGroup parent)
    {
        // Check if the existing view is being reused, otherwise inflate the view
        if(listItemView == null) 
        {
            listItemView = LayoutInflater.from(getContext()).inflate(
                R.layout.list_items, parent, false);
        }

        //Grab word at given position 
        String currentItem = getItem(position);

        //Grab checkbox
        CheckBox checkBox = (CheckBox) listItemView.findViewById(R.id.checkbox);

        //Set the text
        checkBox.setText(currentItem);


        return listItemView;
    }

}//end-class
