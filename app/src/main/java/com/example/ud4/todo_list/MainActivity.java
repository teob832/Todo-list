package com.example.ud4.todo_list;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.PrintWriter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.FloatingActionButton;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.InputType;
import android.content.Context;
import android.graphics.Paint;

public class MainActivity extends AppCompatActivity 
{
    //Data members
    private DrawerLayout leftDrawerLayout;  
    private ListView drawerListView;
    private ListView listView;

    public static int CURRENT_LOADED;
    public static final String PREFS_NAME = "MyPrefsFile";

    private ArrayList<String> indexContent = new ArrayList<String>();

    private ArrayList<ListItem> listItems = new ArrayList<ListItem>();
    private ItemAdapter adapter;
    private DrawerItemAdapter drawerAdapter;

    /**OnCreate*/
    // ********************************************************************** 
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Grab views
        leftDrawerLayout = (DrawerLayout) findViewById(R.id.left_drawer);
        drawerListView = (ListView) findViewById(R.id.drawer_list);
        listView = (ListView) findViewById(R.id.incomplete_list);

        //Default indexContent -- overwritten if Index aleady exists
        indexContent.add("General");
        indexContent.add("School");
        indexContent.add("Project");

        //Create if !exists
        initIndexFile(this);

        //Init all files
        for (int i = 0; i < indexContent.size(); ++i)
            initDataFile(this, indexContent.get(i) + ".txt");

        //Set adapter for DrawerListView
        drawerAdapter = new DrawerItemAdapter(this, indexContent);  
        drawerListView.setAdapter(drawerAdapter);
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());

        //Set Adapter for ListItems
        adapter = new ItemAdapter(this, listItems);
        listView.setAdapter(adapter);

        //Set or restore CURRENT_LOADED
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        CURRENT_LOADED = settings.getInt("current_loaded", 0);


        /**Item Long Click -- Edit item*/ // ********************************************* 
        listView.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id)
            {
                // Cast currentView
                CheckBox currentView = (CheckBox) view.findViewById(R.id.checkbox);
                final String originalText = currentView.getText().toString();

                //Pop open a dialog with EditText
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Edit Item:");
                
                // Set up the input
                final EditText input = new EditText(MainActivity.this);

                // Specify the type of input expected
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText(originalText);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() 
                { 
                    @Override
                    public void onClick(DialogInterface dialog, int which) 
                    {
                        String grabbedText = input.getText().toString();

                        //Find index of edited value in the ArrayList
                        for (int i = 0; i < listItems.size(); ++i)
                        {
                            if (listItems.get(i).getText() == originalText)
                            {
                                listItems.get(i).setText(grabbedText);
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                // Set up the buttons
                builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() 
                { 
                    @Override
                    public void onClick(DialogInterface dialog, int which) 
                    {
                        //Find index of edited value in the ArrayList
                        for (int i = 0; i < listItems.size(); ++i)
                        {
                            if (listItems.get(i).getText() == originalText)
                            {
                                listItems.remove(i);
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                dialog.show();

                return true;
            }
        });//end-onItemLongClick

        /**Add Button*/
        // *********************************** 
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() 
        {
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
                        //Create new item with the grabbed text and append to listItems
                        String grabbedText = input.getText().toString();
                        ListItem newItem = new ListItem(grabbedText);
                        listItems.add(newItem);                
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

                AlertDialog dialog = builder.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                dialog.show();
            }
        });//end-FAB
    }//end-create

    /**OnPause*/
    // ********************************************************************** 
    @Override
    protected void onPause() 
    {
        super.onPause();

        saveIndex(this);
        String filename = indexContent.get(CURRENT_LOADED) + ".txt";
        saveData(this, filename);

        listItems.clear();
        indexContent.clear();

        //Save CURRENT_LOADED
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("current_loaded", CURRENT_LOADED);
        editor.commit();
    }//end-onPause

    /**OnResume -- restore state*/
    // ********************************************************************** 
    @Override
    protected void onResume() 
    {
        super.onResume();

        //Check if index needs reloading
        if (indexContent.isEmpty())
            loadIndex(this);

        //Grab CURRENT_LOADED
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        CURRENT_LOADED = settings.getInt("current_loaded", 0);
        String listname = indexContent.get(CURRENT_LOADED);

        setTitle(listname);
        loadData(this, listname + ".txt");
    }//end-onResume

    /**SaveData -- Write content of listItems to file */
    // ********************************************************************** 
    public void saveData(Context context, String filename)
    {
        //Create dir
        File file = new File(context.getFilesDir(),"mydir");
        if (!file.exists())
            file.mkdir();

        try{
            File outFile = new File(file, filename);

            //Clear content of file
            PrintWriter cleaner = new PrintWriter(outFile);
            cleaner.close();

            //Start Writing
            FileWriter writer = new FileWriter(outFile);
            for (int i = 0; i < listItems.size(); ++i)
            {
                int flag = (listItems.get(i).isChecked()) ? 1: 0;
                writer.append(Integer.toString(flag) + "\n");
                writer.append(listItems.get(i).getText() + "\n");
            }

            writer.flush();
            writer.close();

        }catch (Exception e){
        }
    }//end

    /**LoadData -- Read from file and initialize the passed arraylist*/
    // ********************************************************************** 
    public void loadData(Context context, String filename)
    {
        String line1 = "";
        String line2 = "";
        File file = new File(context.getFilesDir(),"mydir");
        
        try{
            File inFile = new File(file, filename);
            // open input stream test.txt for reading purpose.
            BufferedReader reader = new BufferedReader(new FileReader(inFile));

            while ((line1 = reader.readLine()) != null) 
            {
                int flag = Integer.parseInt(line1);
                line2 = reader.readLine();
                listItems.add(new ListItem(flag, line2));
            }
            reader.close();
            adapter.notifyDataSetChanged();
        }

        catch(Exception e){
        }
    }//end

    /**SaveIndex -- Write content of listItems to file */
    // ********************************************************************** 
    public void saveIndex(Context context)
    {
        String filename = "Index.txt";
        //Create dir
        File file = new File(context.getFilesDir(),"mydir");
        if (!file.exists())
            file.mkdir();
        try{
            File outFile = new File(file, filename);
            //Clear content of file
            PrintWriter cleaner = new PrintWriter(outFile);
            cleaner.close();

            //Start Writing
            FileWriter writer = new FileWriter(outFile);
            for (int i = 0; i < indexContent.size(); ++i)
                writer.append(indexContent.get(i) + "\n");

            writer.flush();
            writer.close();
        }catch (Exception e){}
    }//end

    /**LoadIndex -- Read from file and initialize the passed array*/
    // ********************************************************************** 
    public void loadIndex(Context context)
    {
        String filename = "Index.txt";
        String line2 = "";
        File file = new File(context.getFilesDir(),"mydir");
        
        try{
            File inFile = new File(file, filename);
            // open input stream test.txt for reading purpose.
            BufferedReader reader = new BufferedReader(new FileReader(inFile));

            while ((line2 = reader.readLine()) != null) 
                indexContent.add(line2);

            reader.close();
            drawerAdapter.notifyDataSetChanged();
        }
        catch(Exception e){}
    }//end

    /**InitDataFile -- checkExists && create the data file*/
    // ********************************************************************** 
    public void initDataFile(Context context, String filename)
    {
        //Create dir
        File file = new File(context.getFilesDir(),"mydir");
        if (!file.exists())
        {
            file.mkdir();
            try{
                File outFile = new File(file, filename);

                //Create file
                PrintWriter creator = new PrintWriter(outFile);
                creator.close();
            }
            catch(Exception e){
            }

        }
    }//end

    /**InitIndexFile -- checkExists && create the data file*/
    // ********************************************************************** 
    public void initIndexFile(Context context)
    {
        String filename = "Index.txt";
        //Create dir
        File file = new File(context.getFilesDir(),"mydir");
        //First time
        if (!file.exists())
        {
            file.mkdir();
            
            try{
                File outFile = new File(file, filename);

                //Create file
                PrintWriter creator = new PrintWriter(outFile);
                
                //Start Writing
                FileWriter writer = new FileWriter(outFile);
                writer.append("General" + "\n");

                writer.flush();
                writer.close();
                creator.close();
            }
            catch(Exception e){
            }

        }
        //If Index file already exists
        else
        {
            indexContent.clear();
            loadIndex(MainActivity.this);
        }
    }//end

    /**DrawerItemClickListener*/
    // ********************************************************************** 
    private class DrawerItemClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }//end-drawerListener

    /**SelectItem -- Swaps listItems in the main content view*/
    // ********************************************************************** 
    private void selectItem(int position) 
    {
        // If CURRENT_LOADED is clicked again -- do nothing
        if (CURRENT_LOADED == position)
        {
            drawerListView.setItemChecked(position, true);
            leftDrawerLayout.closeDrawer(drawerListView);
            return;
        }

        //Save currently-loaded data and clear listItems
        String currentfile = indexContent.get(CURRENT_LOADED);
        saveData(MainActivity.this, currentfile + ".txt"); 
        listItems.clear();

        //Load the file corresponding to the clickedDrawerItem 
        String newfile = indexContent.get(position);
        loadData(MainActivity.this, newfile + ".txt");

        //House-keeping
        setTitle(newfile);
        drawerListView.setItemChecked(position, true);
        CURRENT_LOADED = position;
        adapter.notifyDataSetChanged();
        drawerAdapter.notifyDataSetChanged();
        leftDrawerLayout.closeDrawer(drawerListView);
    }//end-select
    
}//end-class
