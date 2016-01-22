package com.example.andrey.myfragment;

/**
 * Created by andrey on 22.01.16.
 */
public class ItemObject {
    private String text;
    private String date;
    private String userName;

    @SuppressWarnings("unused")
    public ItemObject(){

    }
    public ItemObject(String text, String date, String userName){

        this.text = text;
        this.date = date;
        this.userName = userName;
    }




    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getText() {
        return text;
    }

    public void setText(String time) {
        this.text = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
