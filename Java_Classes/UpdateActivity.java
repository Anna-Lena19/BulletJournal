package com.example.bulletjournal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{

    EditText title_input;
    Button update_Button, delete_Button;
    String title, id, bullet_category, deadline;
    Spinner bullet_Ident;
    String bulletIdent_String;

    TextView showDate_Input, showTime_Start, showTime_End;
    Button setDate, task_time_beginn, task_time_end;
    String currentDateString, startTimeString, endTimeString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.title_input);
        update_Button = findViewById(R.id.update_Button);
        delete_Button = findViewById(R.id.delete_Button);
        showDate_Input = findViewById(R.id.showDate_Input);
        setDate = findViewById(R.id.dateDeadline_input);
        task_time_beginn = findViewById(R.id.task_time_beginn);
        task_time_end = findViewById(R.id.task_time_end);
        showTime_Start = findViewById(R.id.showTimeBeginn);
        showTime_End = findViewById(R.id.showTimeEnd);



        bullet_Ident = findViewById(R.id.bulletIdent_input);
        ArrayAdapter<CharSequence> spinner_Adapter = ArrayAdapter.createFromResource(this, R.array.date_identification, android.R.layout.simple_spinner_item);
        spinner_Adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        bullet_Ident.setAdapter(spinner_Adapter);
        bullet_Ident.setOnItemSelectedListener(this);


        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;


        getAndSetIntentData();

        update_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                title = title_input.getText().toString().trim();
                myDB.updateData(id,title,bulletIdent_String,currentDateString,startTimeString,endTimeString);
                Log.d("Save","SaveData " + title);
                finish();
            }
        });

        delete_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        task_time_beginn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(UpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                showTime_Start.setText("From: " + selectedHour + ":" + selectedMinute);
                                startTimeString = selectedHour + ":" + selectedMinute;
                            }
                        }, hour, minute, true);//Yes 24 hour time
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();

                    }

        });

        task_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(UpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        showTime_End.setText("To: " + selectedHour + ":" + selectedMinute);
                        endTimeString = selectedHour + ":" + selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }

        });






    }


    void getAndSetIntentData(){
        if(getIntent().hasExtra("description") && getIntent().hasExtra("id")){
            title = getIntent().getStringExtra("description");
            id = getIntent().getStringExtra("id");
            bullet_category = getIntent().getStringExtra("bulletIdent");
            deadline = getIntent().getStringExtra("deadline");
            startTimeString = getIntent().getStringExtra("time_start");
            endTimeString = getIntent().getStringExtra("time_end");

            showTime_Start.setText(" From: " + startTimeString);
            showTime_End.setText("To: " + endTimeString);

            //todo check why sometimes null
            if (deadline == "null") {
                showDate_Input.setText("The page has not been updated yet");
            }
            else{
                showDate_Input.setText(deadline);
                currentDateString = deadline;
            }

            Log.d("StringTest", " nach übergabe" + deadline );
            title_input.setText(title);

            Integer bullet_category_Int = Integer.parseInt(bullet_category);
            bullet_Ident.setSelection(bullet_category_Int);

        }
        else{
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
        }
    }






    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getItemAtPosition(i).toString()){
            case ("Task"): bulletIdent_String = "0";
                break;
            case ("Note"): bulletIdent_String = "2";
                break;
            case ("Event"): bulletIdent_String = "1";
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());

        Log.d("StringTest", " nach auswahl" + currentDateString );
        if (currentDateString == "null") {
            showDate_Input.setText("The page has not been updated yet");
        }
        else{
            showDate_Input.setText(currentDateString);
        }


    }
}