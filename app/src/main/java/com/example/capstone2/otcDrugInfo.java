package com.example.capstone2;

import android.widget.EditText;

import java.io.StringReader;
import java.util.StringTokenizer;

public class otcDrugInfo {
    private String name;
    private String reason;
    private String date;

    public  otcDrugInfo(String name, String reason, String date){
        this.name=name;
        this.reason=reason;
        this.date=date;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getReason(){
        return this.reason;
    }
    public void setReason(String reason){
        this.reason=reason;
    }

    public String getDate(){
        return this.date;
    }
    public void setDate(String date){
        this.date=date;
    }
}
