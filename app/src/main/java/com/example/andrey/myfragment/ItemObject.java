package com.example.andrey.myfragment;

/**
 * Created by andrey on 22.01.16.
 */
public class ItemObject {
    int id;
    String text;
    String date;
    public ItemObject(int id, String text, String date){
        this.id = id;
        this.text = text;
        this.date = date;
    }






    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
