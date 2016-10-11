package com.example.ud4.todo_list;

import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.support.design.widget.FloatingActionButton;



public class MainActivity extends AppCompatActivity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ListView set up
        // *********************************** 
        ArrayList<String> listItems = new ArrayList<String>();

        listItems.add("FFinish udacity tutFinish udacity tutFinish udacity tutFinish udacity tutiFinish udacity tutFinish udacity tutFinish udacity tutFinish udacity tutFinish udacity tutFinish udacity tutnish udacity tut");
        listItems.add("Wrap up this app");
        listItems.add("Finish udacity tut");
        listItems.add("Wrap up this app");
        listItems.add("Finish udacity tut");
        listItems.add("Wrap up this app");
        listItems.add("Finish udacity tut");
        listItems.add("Wrap up this app");
        listItems.add("Finish udacity tut");
        listItems.add("Wrap up this app");
        listItems.add("Finish udacity tut");
        listItems.add("Wrap up this app");

        ItemAdapter adapter = new ItemAdapter(this, listItems);
        ListView listView = (ListView) findViewById(R.id.incomplete_list);
        listView.setAdapter(adapter);


        // Add Button
        // *********************************** 
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_button);

        fab.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                //Pop up a dialog with an entry box
            }
        });


    }//end-onCreate

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
