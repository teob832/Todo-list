package com.example.ud4.todo_list;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.CompoundButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import java.util.ArrayList;


public class ItemAdapter extends ArrayAdapter<ListItem>
{
    public ItemAdapter(Activity context, ArrayList<ListItem> list) 
    {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LinearLayout listItemView = (LinearLayout) view;

        // Check if the existing view is being reused, otherwise inflate the view
        if(listItemView == null) 
        {
            listItemView = (LinearLayout) LayoutInflater.from(getContext()).inflate(
                R.layout.list_items, parent, false);
        }

        //Grab values at given position 
        String currentText = getItem(position).getText();
        boolean currentFlag = getItem(position).isChecked();

        CheckBox checkBox = (CheckBox) listItemView.findViewById(R.id.checkbox);

        //Set the values
        checkBox.setText(currentText);
        checkBox.setChecked(currentFlag);
        checkBox.setFocusable(false);   //Need for clicking
        checkBox.setTag(position);      //Store pos in tag 
        //Set Listener -- Modify the actual listItem object's mCheckFlag data member
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() 
        {
            @Override
            public void onCheckedChanged(CompoundButton v, boolean flag) 
            {
                String toastStr = "Incomplete";

                CheckBox cb = (CheckBox) v;
                boolean cbFlag = cb.isChecked();
                int pos = Integer.parseInt(v.getTag().toString());//Use stored tag to access actual array
                getItem(pos).setChecked(cbFlag);

                if (getItem(pos).isChecked())
                    toastStr = "Done";

                Toast.makeText(getContext(), toastStr, Toast.LENGTH_SHORT).show();
            }
        });


        return listItemView;
    }
}//end-class
