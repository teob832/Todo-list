package com.example.ud4.todo_list;


public class ListItem
{
    private String mText;
    private boolean mCheckFlag;

    //Constructors
    //***********************************  
    public ListItem(String str)
    {
        mText = str;
        mCheckFlag = false;
    }
    public ListItem(int flag, String str)
    {
        mText = str;
        mCheckFlag = (flag == 1) ? true: false;
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
        return mCheckFlag;
    }
    public void toggleFlag()
    {
        mCheckFlag = !mCheckFlag;
    }
    public void setChecked(boolean f)
    {
        mCheckFlag = f;
    }
}//end-class
