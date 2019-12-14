package com.example.afinal;

import java.io.Serializable;

public class TimeSet implements Serializable {


    private String title;
    private String description;
    private String finalTime;
    private String todowID;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int min;


    public TimeSet(String title, String description, String finalTime, String todowID, int day, int month, int year,int hour,int min) {
        this.setTitle(title);
        this.setDescription(description);
        this.setFinalTime(finalTime);
        this.setTodowID(todowID);
        this.setDay(day);
        this.setMonth(month);
        this.setYear(year);
        this.setHour(hour);
        this.setMin(min);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(String finalTime) {
        this.finalTime = finalTime;
    }

    public String getTodowID() {
        return todowID;
    }

    public void setTodowID(String todowID) {
        this.todowID = todowID;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

}


/*<activity android:name=".ImageShowActivity">
<intent-filter>
<action android:name="android.intent.action.MAIN" />

<category android:name="android.intent.category.LAUNCHER" />
</intent-filter>
</activity>
<activity android:name=".ChangeTimeActivity">
<intent-filter>
<action android:name="android.intent.action.MAIN" />

<category android:name="android.intent.category.LAUNCHER" />
</intent-filter>
</activity>
<activity android:name=".NewtimeActivity">
<intent-filter>
<action android:name="android.intent.action.MAIN" />

<category android:name="android.intent.category.LAUNCHER" />
</intent-filter>
</activity>*/