package com.example.ud4.todo_list;

import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> listItems = new ArrayList<String>();

        listItems.add("Finish udacity tut");
        listItems.add("Wrap up this app");

        ItemAdapter adapter = new ItemAdapter(this, listItems);

        //Grab the ListView
        ListView listView = (ListView) findViewById(R.id.incomplete_list);

        //Set adapter
        listView.setAdapter(adapter);
    }

    // AddItem -- called upon add_button is clicked
    //**********************************************
    public void addItem(View view)
    {
        ////Grab entryBox's text
        //TextView entryBox = (TextView) findViewById(R.id.entry_box);

        ////Create new checkbox
        //CheckBox newItem = new CheckBox(this);

    }




}//end-class
