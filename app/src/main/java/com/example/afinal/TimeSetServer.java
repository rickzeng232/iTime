package com.example.afinal;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TimeSetServer {
    public TimeSetServer(Context context, int list_view_timeset, List<TimeSet> timeSets) {
        this.context = context;
    }

    Context context;

    public ArrayList<TimeSet> getTimeSets() {
        return timeSets;
    }

    ArrayList<TimeSet>timeSets=new ArrayList<TimeSet>();

    public void save()
    {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput("Serializable.txt",Context.MODE_PRIVATE)
            );
            outputStream.writeObject(timeSets);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<TimeSet> load()
    {
        try{
            ObjectInputStream inputStream = new ObjectInputStream(
                    context.openFileInput("Serializable.txt")
            );
            timeSets = (ArrayList<TimeSet>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeSets;
    }
}
