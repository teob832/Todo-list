package com.example.ud4.todo_list;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.CompoundButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.content.Context;
import android.content.res.Resources;

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

        //Set genral values
        checkBox.setText(currentText);
        checkBox.setFocusable(false);   //Required for clicking
        checkBox.setTag(position);      //Store pos in tag 
        checkBox.setChecked(currentFlag);

        //Listener -- Modify the actual listItem object's flag 
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() 
        {
            @Override
            public void onCheckedChanged(CompoundButton v, boolean flag) 
            {
                CheckBox cb = (CheckBox) v;
                boolean cbFlag = cb.isChecked();
                int pos = Integer.parseInt(v.getTag().toString());//Use stored tag to access actual array
                getItem(pos).setChecked(cbFlag);
            }
        });
        
        Resources resources = MainActivity.getContext().getResources();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) checkBox.getLayoutParams();

        //Parent
        if (getItem(position).isParent())
        {
            checkBox.setTextAppearance(R.style.parent_item);
            checkBox.setButtonTintList(resources.getColorStateList(R.color.color_state_parent, null));

            params.setMargins(0, 0, 0, 0); //substitute parameters for left, top, right, bottom
            checkBox.setLayoutParams(params);
        }
        //Child 
        else if (getItem(position).isChild())
        {
            checkBox.setTextAppearance(R.style.child_item);
            checkBox.setButtonTintList(resources.getColorStateList(R.color.color_state_child, null));

            params.setMargins(65, 0, 0, 0); //substitute parameters for left, top, right, bottom
            checkBox.setLayoutParams(params);
        }
        //Regular
        else
        {
            checkBox.setTextAppearance(R.style.regular_item);
            checkBox.setButtonTintList(resources.getColorStateList(R.color.color_state_regular, null));

            params.setMargins(0, 0, 0, 0); //substitute parameters for left, top, right, bottom
            checkBox.setLayoutParams(params);
        }
        return listItemView;
    }
}//end-class
