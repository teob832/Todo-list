package com.example.ud4.todo_list;


public class ListItem
{
    private String mText;

    //0,1   -- Regular (unchecked/checked)
    //90,91 -- Parent (unchecked/checked)
    //80,81 -- Child (unchecked/checked)
    private int mFlag;



    //Constructors
    //***********************************  
    public ListItem(String str)
    {
        mText = str;
        mFlag = 0; //Regular unchecked
    }
    public ListItem(int flag, String str)
    {
        mText = str;
        mFlag = flag;
    }
    
    //Methods
    //***********************************  
    public String getText()
    {
        return mText;
    }
    public void setText(String str)
    {
        mText = str;
    }

    public boolean isChecked()
    {
        return (mFlag % 10 == 1) ? true: false;
    }
    public void setChecked(boolean f)
    {
        //Set to CHECKED
        if (f == true)
        {
            //Make sure flag ends with 1
            if (mFlag % 10 == 0)
                mFlag++;
        }
        //Set to UNCHECKED
        else
        {
            //Make sure flag ends with 0
            if (mFlag % 10 == 1)
                mFlag--;
        }
    }

    public boolean isParent()
    {
        return (mFlag >= 90) ? true: false; 
    }
    public void setParent(boolean f)
    {
        //Set to Parent
        if (f == true)
        {
            if (isChecked() == false)
                mFlag = 90;
            else
                mFlag = 91;
        }
        else
        {
            if (isChecked() == false)
                mFlag = 0;
            else
                mFlag = 1;
        }
    }

    public boolean isChild()
    {
        return (mFlag == 80 || mFlag == 81) ? true: false; 
    }
    public void setChild(boolean f)
    {
        //Set to Parent
        if (f == true)
        {
            if (isChecked() == false)
                mFlag = 80;
            else
                mFlag = 81;
        }
        else
        {
            if (isChecked() == false)
                mFlag = 0;
            else
                mFlag = 1;
        }
    }

    public int getFlag()
    {
        return mFlag;
    }
}//end-class
