package com.example.ud4.todo_list;

import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.support.design.widget.FloatingActionButton;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.graphics.Paint;

public class MainActivity extends AppCompatActivity 
{
    //Data members
    ArrayList<String> listItems = new ArrayList<String>();
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ListView set up
        // *********************************** 
        listItems.add("FFinish udacity tutFinish udacity tutFinish udacity tutFinish udacity tutiFinish udacity tutFinish udacity tutFinish udacity tutFinish udacity tutFinish udacity tutFinish udacity tutnish udacity tut");
        listItems.add("Wrap up this app");
        listItems.add("Finish udacity tut");
        listItems.add("Finish udacity tut");
        listItems.add("Wrap up this app");

        adapter = new ItemAdapter(this, listItems);
        ListView listView = (ListView) findViewById(R.id.incomplete_list);
        listView.setAdapter(adapter);


        // Add Button
        // *********************************** 
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
        
            public void onClick(View view)
            {
                //Pop up a dialog with an entry box
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Enter new item:");
                
                // Set up the input
                final EditText input = new EditText(MainActivity.this);

                // Specify the type of input expected
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() 
                { 
                    @Override
                    public void onClick(DialogInterface dialog, int which) 
                    {
                        //Create NEW ListView item
                        //Set text to m_Text
                        //Append to incomplete
                        
                        String grabbedText = input.getText().toString();
                        listItems.add(grabbedText);                
                        adapter.notifyDataSetChanged();
                    }
                });

                // Set up the buttons
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
                { 
                    @Override
                    public void onClick(DialogInterface dialog, int which) 
                    {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });


    }//end-onCreate

    // AddItem -- called upon add_button is clicked
    //**********************************************
    public void toggleStrikeThrough(View view)
    {
        //Grab entryBox's text
        CheckBox checkbox = (CheckBox) view;
        
        //Determine if checked
        if (checkbox.isChecked() == true)
        {
            //Set Strikethrough text
            checkbox.setPaintFlags(checkbox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else
        {
            //Reset
            checkbox.setPaintFlags(checkbox.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }




}//end-class
