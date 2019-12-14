package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static com.example.afinal.ListViewMain2Activity.verifyStoragePermissions;


public class ChangeTimeActivity extends AppCompatActivity {

    public static final int CHOOSE_PHOTO = 2;
    private String day,month,year,hour,min,todowID;
    private Button save, exit;
    private int monthi,yeari,dayi,mini,houri;
    private TextView timed,timec,photo,countdownt;
    private EditText editTitle, editDescription;
    private ImageView imageView;
    private int insertPosition;
    private Button photoChoose;
    private Button timedChoose;
    private Button timecChoose;

    //选择日期Dialogt
    private DatePickerDialog datePickerDialog;
    //选择时间Dialog
    private TimePickerDialog timePickerDialog;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_time);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editDescription = (EditText) findViewById(R.id.editDescription);
        timed=(TextView) findViewById(R.id.timed) ;
        timec=(TextView) findViewById(R.id.timec) ;
        countdownt=(TextView) findViewById(R.id.comedown) ;
        photo=(TextView) findViewById((R.id.photo)) ;
        timedChoose = (Button) findViewById(R.id.timedChoose);
        timecChoose = (Button) findViewById(R.id.timecChoose);
        photoChoose = (Button) findViewById(R.id.photoChoose);
        save = (Button) findViewById(R.id.save);
        exit = (Button) findViewById(R.id.exit);
        imageView=(ImageView)findViewById(R.id.imageView);

        editTitle.setText(getIntent().getStringExtra("title"));
        editDescription.setText(getIntent().getStringExtra("description"));

        photo.setText(getIntent().getStringExtra("todowID"));

        dayi = getIntent().getIntExtra("day",0);

        monthi = getIntent().getIntExtra("month",0);

        yeari = getIntent().getIntExtra("year",0);

        houri = getIntent().getIntExtra("hour",0);
        mini = getIntent().getIntExtra("min",0);
        todowID=getIntent().getStringExtra("todowID");
        Bitmap bitmap = BitmapFactory.decodeFile(todowID);
        imageView.setImageBitmap(bitmap);


        insertPosition = getIntent().getIntExtra("insert_position", 0);
        countdownt.setText(countdown(yeari,monthi,dayi,houri,mini));
        timed.setText(" " +yeari+" "+monthi+" "+dayi);
        timec.setText(" "+houri+" "+mini);

        timedChoose.setOnClickListener(new ChangeTimeActivity.mClick());
        timecChoose.setOnClickListener(new ChangeTimeActivity.mClick());
        photoChoose.setOnClickListener(new ChangeTimeActivity.mClick());

        calendar = Calendar.getInstance();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String dayString=day.getText().toString();
                String photoString=photo.getText().toString();
                String finalTime=timed.getText().toString()+" "+timec.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("title", editTitle.getText().toString());
                intent.putExtra("description", editDescription.getText().toString());
                intent.putExtra("day", dayi);
                intent.putExtra("month", monthi);
                intent.putExtra("year", yeari);
                intent.putExtra("hour",houri);
                intent.putExtra("min",mini);
                intent.putExtra("todowID",photoString );
                intent.putExtra("finalTime",finalTime);
                intent.putExtra("insert_position", insertPosition);
                setResult(RESULT_OK, intent);
                ChangeTimeActivity.this.finish();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeTimeActivity.this.finish();
            }
        });

        verifyStoragePermissions(this);

    }
    class mClick implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.timedChoose:
                    showDailog();
                    break;
                case R.id.timecChoose:
                    showtime();
                    break;
                case R.id.photoChoose:
                    showPhoto();
                    break;

            }
        }}

    private void showDailog () {

        datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int monthOfYear1, int dayOfMonth1) {
                //monthOfYear 得到的月份会减1所以我们要加1
                String time2 = String.valueOf(year1) + "　" + String.valueOf(monthOfYear1 + 1) + "  " + Integer.toString(dayOfMonth1);
                year=String.valueOf(year1);
                month=String.valueOf(monthOfYear1);
                day=String.valueOf((dayOfMonth1));
                timed.setText(" "+time2);
                //Log.d("测试", time);
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        //自动弹出键盘问题解决
        datePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


    }

    private void showtime(){
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Log.d("测试", Integer.toString(hourOfDay));
                Log.d("测试", Integer.toString(minute));
                hour = Integer.toString(hourOfDay);
                min = Integer.toString(minute);
                String time1 = hour + ':' + min;
                //String timef = year+","+month+","+day + " " + time1;
                timec.setText(" "+time1);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
        timePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    private void showPhoto(){
        Intent intent = new Intent();
        intent.setClass(this, ImageShowActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("id", CHOOSE_PHOTO);
        intent.putExtras(bundle);

        startActivityForResult(intent,CHOOSE_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK)
                {
                    photo.setText(data.getStringExtra("imagePath"));

                }
                break;


        }

    }



    public String countdown(int yeari,int monthi,int dayi,int houri,int mini)
    {
        String end;
        int finalday,finalhour,finalmin;
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTimeInMillis(System.currentTimeMillis());
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.set(yeari,monthi,dayi,houri,mini);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);
        finalday = (int)((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
        finalhour = (int)((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (60 * 60 * 1000));
        finalmin = (int)((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime())-finalhour*(60 * 60 * 1000))/(60 * 1000);
        end=finalday+"DAYS "+finalhour+"HOURS "+finalmin+"MINS LEFT";
        return end;
    }


}
