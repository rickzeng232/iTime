package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.view.View.OnClickListener;

import java.util.Calendar;

public class NewtimeActivity extends AppCompatActivity{

    public static final int CHOOSE_PHOTO = 2;
    public static final int CHOOSE_PHOTO_OK = 3;
    private String day,month,year,hour,min,photoString;
    private Button save, exit;
    private TextView timed,timec,photo;
    private EditText editTextTitle, editTextDescription;
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
        setContentView(R.layout.activity_newtime);

        timed=(TextView)findViewById(R.id.timed);
        timec=(TextView)findViewById(R.id.timec);
        photo=(TextView)findViewById(R.id.photo);
        save = (Button) findViewById(R.id.save);
        exit = (Button) findViewById(R.id.exit);
        editTextTitle = (EditText) findViewById(R.id.editTitle);
        editTextDescription = (EditText) findViewById(R.id.editDescription);
        timedChoose = (Button) findViewById(R.id.timedChoose);
        timecChoose = (Button) findViewById(R.id.timecChoose);
        photoChoose = (Button) findViewById(R.id.photoChoose);

        editTextTitle.setText(getIntent().getStringExtra("title"));
        editTextDescription.setText(getIntent().getStringExtra("description"));
        insertPosition = getIntent().getIntExtra("insert_position", 0);

        timedChoose.setOnClickListener(new mClick());
        timecChoose.setOnClickListener(new mClick());
        photoChoose.setOnClickListener(new mClick());

        calendar = Calendar.getInstance();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String dayString=day.getText().toString();
                photoString=photo.getText().toString();
                String finalTime=timed.getText().toString()+" "+timec.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("title", editTextTitle.getText().toString());
                intent.putExtra("description", editTextDescription.getText().toString());
                intent.putExtra("day", day);
                intent.putExtra("month", month);
                intent.putExtra("year", year);
                intent.putExtra("hour",hour);
                intent.putExtra("min",min);
                intent.putExtra("todowID",photoString );
                intent.putExtra("finalTime",finalTime);
                intent.putExtra("insert_position", insertPosition);
                setResult(RESULT_OK, intent);
                NewtimeActivity.this.finish();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewtimeActivity.this.finish();
            }
        });

    }
    class mClick implements OnClickListener{
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
                String time2 = String.valueOf(year1) + " " + String.valueOf(monthOfYear1 + 1) + " " + Integer.toString(dayOfMonth1);
                year=String.valueOf(year1);
                month=String.valueOf(monthOfYear1);
                day=String.valueOf((dayOfMonth1));
                timed.setText(" " +time2);
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
                timec.setText(" " +time1);
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



}