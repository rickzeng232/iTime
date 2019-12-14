package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ListViewMain2Activity extends AppCompatActivity {

    public static final int TIME_ADD = 1;
    public static final int TIME_DELETE = TIME_ADD+1;
    public static final int TIME_CHANGE = TIME_DELETE+1;
    public static final int REQUEST_CODE_NEW = 901;
    public static final int REQUEST_CODE_CHANGE = 902;
    private int DELYED= 1000;
    ListView listView;
    private TimeSetAdapter timeSetadapter;
    TimeSetServer timeSetServer;
    private List<TimeSet>timeSets=new ArrayList<>();

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };

    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeSetServer.save();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_main2);
        listView=this.findViewById(R.id.list_view);
        init();

        verifyStoragePermissions(this);



        timeSetadapter = new TimeSetAdapter(
                this, R.layout.list_view_timeset, timeSets);
        listView.setAdapter(timeSetadapter);

        this.registerForContextMenu(listView);

        handler.postDelayed(runnable, DELYED);


    }

    Handler handler = new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            try{
                for(int i=timeSets.size();i>0;i--)
                {
                    autoFlash(i);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    };



    private void autoFlash(int i){

        TimeSet timeSet=timeSets.get(i);
        //int finalday;
        int hour=timeSet.getHour();
        int min=timeSet.getMin();
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTimeInMillis(System.currentTimeMillis());
        int hourn = nowCalendar.get(Calendar.HOUR_OF_DAY);
        int minn=nowCalendar.get(Calendar.MINUTE);
        if(hour==hourn&min==minn)
        {
            //finalday=counttime(i);
            //timeSet.setFinalTime();
            timeSetadapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v==listView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //设置标题
            menu.setHeaderTitle(timeSets.get(info.position).getTitle());
            //设置内容 参数1为分组，参数2对应条目的id，参数3是指排列顺序，默认排列即可
            menu.add(0, TIME_ADD, 0, "添加");
            menu.add(0, TIME_CHANGE, 0, "修改");
            menu.add(0, TIME_DELETE, 0, "删除");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_NEW:
                if(resultCode==RESULT_OK)
                {

                    String title = data.getStringExtra("title");
                    String finalTime = data.getStringExtra("finalTime");
                    String description = data.getStringExtra("description");
                    String day = data.getStringExtra("day");
                    String month = data.getStringExtra("month");
                    String year = data.getStringExtra("year");
                    String todowID = data.getStringExtra("todowID");
                    String hour = data.getStringExtra("hour");
                    int insertPosition=data.getIntExtra("inserPosition", 0);
                    String min = data.getStringExtra("min");
                    int dayi = Integer.parseInt(day);
                    int monthi = Integer.parseInt(month);
                    int yeari = Integer.parseInt(year);
                    int houri = Integer.parseInt(hour);
                    int mini = Integer.parseInt(min);


                    timeSets.add(insertPosition,new TimeSet(title,description,finalTime,todowID,dayi,monthi,yeari,houri,mini));
                    timeSetadapter.notifyDataSetChanged();
                    Toast.makeText(this, "新建成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_CHANGE:
                if(resultCode==RESULT_OK)
                {
                    int insertPosition=data.getIntExtra("inserPosition", 0);
                    String finalTime = data.getStringExtra("finalTime");
                    String title = data.getStringExtra("title");
                    String description = data.getStringExtra("description");
                    int dayi = getIntent().getIntExtra("day",0);
                    int monthi = getIntent().getIntExtra("month",0);
                    int yeari = getIntent().getIntExtra("year",0);
                    int houri = getIntent().getIntExtra("hour",0);
                    int mini = getIntent().getIntExtra("min",0);

                    String todowID = data.getStringExtra("todowID");


                    TimeSet timeSet=timeSets.get(insertPosition);
                    timeSet.setTodowID(todowID);
                    timeSet.setDay(dayi);
                    timeSet.setDescription(description);
                    timeSet.setFinalTime(finalTime);
                    timeSet.setHour(houri);
                    timeSet.setMin(mini);
                    timeSet.setMonth(monthi);
                    timeSet.setTitle(title);
                    timeSet.setYear(yeari);


                    //timeSets.set(insertPosition,new TimeSet(title,description,finalTime,todowID,dayi,monthi,yeari,houri,mini));
                    timeSetadapter.notifyDataSetChanged();
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                    /*runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          timeSetadapter.notifyDataSetChanged(); //Ui线程中更新listview
                                      }
                                  });*/

                }
                break;


            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }


    }



    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case TIME_ADD:
                Intent intent=new Intent(this,NewtimeActivity.class);
                intent.putExtra("title","Brithday,festival,exam...");
                intent.putExtra("description","Goals and mttto");
                startActivityForResult(intent,REQUEST_CODE_NEW);

                break;
            case TIME_DELETE:
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                final int itemPosition=menuInfo.position;
                new android.app.AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("询问")
                        .setMessage("你确定要删除这个吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                timeSets.remove(itemPosition);
                timeSetadapter.notifyDataSetChanged();
                Toast.makeText(ListViewMain2Activity.this,"删除成功",Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create().show();
                break;
            case TIME_CHANGE:

                int postion=((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;
                //TimeSet timeSet=
                Intent intent1=new Intent(this,ChangeTimeActivity.class);
                intent1.putExtra("title",timeSets.get(postion).getTitle());
                intent1.putExtra("description", timeSets.get(postion).getDescription());
                intent1.putExtra("day", timeSets.get(postion).getDay());
                intent1.putExtra("month", timeSets.get(postion).getMonth());
                intent1.putExtra("hour", timeSets.get(postion).getHour());
                intent1.putExtra("min", timeSets.get(postion).getMin());
                intent1.putExtra("year", timeSets.get(postion).getYear());
                intent1.putExtra("todowID",timeSets.get(postion).getTodowID() );


                startActivityForResult(intent1,REQUEST_CODE_CHANGE);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void init() {
        timeSetServer=new TimeSetServer(this,R.layout.list_view_timeset, timeSets);
        timeSets=timeSetServer.load();
        if(timeSets.size()==0)
        {
            timeSets.add(new TimeSet("newtime",null,null,null ,00,00,0000,0,0));
        }


    }

    protected class TimeSetAdapter extends ArrayAdapter<TimeSet> {


        private int resourceId;

        public TimeSetAdapter(Context context, int resource, List<TimeSet> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position,@Nullable  View convertView,@NonNull ViewGroup parent) {


            LayoutInflater mInflater= LayoutInflater.from(this.getContext());
            View view = mInflater.inflate(this.resourceId,null);
            TimeSet time = getItem(position);

            int finalday;
            //finalday=counttime(position);

            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.setTimeInMillis(System.currentTimeMillis());
            fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
            fromCalendar.set(Calendar.MINUTE, 0);
            fromCalendar.set(Calendar.SECOND, 0);
            fromCalendar.set(Calendar.MILLISECOND, 0);

            Calendar toCalendar = Calendar.getInstance();
            toCalendar.set(time.getYear(),time.getMonth(),time.getDay());
            toCalendar.set(Calendar.HOUR_OF_DAY, 0);
            toCalendar.set(Calendar.MINUTE, 0);
            toCalendar.set(Calendar.SECOND, 0);
            toCalendar.set(Calendar.MILLISECOND, 0);
            finalday = (int)((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
            if(finalday<0)
            {
                finalday=0;
            }


            /*ImageView img = (ImageView)view.findViewById(R.id.todown);
            TextView des = (TextView)view.findViewById(R.id.description);
            TextView tit = (TextView)view.findViewById(R.id.title);
            TextView time = (TextView)view.findViewById(R.id.time);
            TextView com = (TextView)view.findViewById(R.id.comedown);
            Bitmap bitmap = BitmapFactory.decodeFile(timeSet.getTodowID());
            img.setImageBitmap(bitmap);
            des.setText(timeSet.getDescription());
            tit.setText(timeSet.getTitle());
            time.setText(timeSet.getFinalTime());
            com.setText(finalday + "DAY");*/

                Bitmap bitmap = BitmapFactory.decodeFile(time.getTodowID());
                ((ImageView) view.findViewById(R.id.todown)).setImageBitmap(bitmap);

                ((ImageView) view.findViewById(R.id.todown)).setAlpha(50);
                ((TextView) view.findViewById(R.id.description)).setText(time.getDescription());
                ((TextView) view.findViewById(R.id.title)).setText(time.getTitle());

                ((TextView) view.findViewById(R.id.time)).setText(time.getFinalTime());
                ((TextView) view.findViewById(R.id.comedown)).setText(" "+finalday + "DAY LEFT");




            return view;
        }


        public int counttime(int position){

            TimeSet time = getItem(position);
            int finalday;
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.setTimeInMillis(System.currentTimeMillis());
            fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
            fromCalendar.set(Calendar.MINUTE, 0);
            fromCalendar.set(Calendar.SECOND, 0);
            fromCalendar.set(Calendar.MILLISECOND, 0);

            Calendar toCalendar = Calendar.getInstance();
            toCalendar.set(time.getYear(),time.getMonth(),time.getDay());
            toCalendar.set(Calendar.HOUR_OF_DAY, 0);
            toCalendar.set(Calendar.MINUTE, 0);
            toCalendar.set(Calendar.SECOND, 0);
            toCalendar.set(Calendar.MILLISECOND, 0);
            finalday = (int)((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
            if(finalday<0)
            {
                finalday=0;
            }
            return finalday;

        }


    }


}
