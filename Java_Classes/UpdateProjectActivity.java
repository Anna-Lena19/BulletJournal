package com.example.bulletjournal;

import android.animation.ArgbEvaluator;
import android.app.DatePickerDialog;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UpdateProjectActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Button  delete_Button, update_Button, updateDate;
    String title, id, bullet_category, deadline, updateDeadline;
    String bulletIdent_String;
    TextView showDate, title_input;

    String currentDateString;
    ArrayList<String> id_to_delete;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_update);

        title_input = findViewById(R.id.title_input);

        showDate = findViewById(R.id.showDate_Input);

        delete_Button = findViewById(R.id.delete_Button);
        update_Button = findViewById(R.id.update_Button);
        updateDate = findViewById(R.id.dateDeadline_input);


        getAndSetIntentData();


        delete_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateProjectActivity.this);
                for(int i = 0; i<id_to_delete.size();i++){
                    id = id_to_delete.get(i);
                    Log.d("StringTest1", "id die gelöscht wird title: " + id);
                    myDB.deleteOneRow(id);
                }

                finish();
            }
        });


        update_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateProjectActivity.this);
                //title = title_input.getText().toString().trim();
                //updateDeadline = deadline_input.getText().toString().trim(
                for(int i = 0; i<id_to_delete.size();i++){
                    id = id_to_delete.get(i);
                    Log.d("StringTest1", "id die gelöscht wird title: " + id);
                    myDB.updateProjectDeadline(id,currentDateString);
                }
                finish();
            }
        });


        updateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


    }


    void getAndSetIntentData(){
        if(getIntent().hasExtra("description") && getIntent().hasExtra("id")){
            title = getIntent().getStringExtra("description");
            deadline = getIntent().getStringExtra("deadline");
            //id_to_delete = getIntent().getStringExtra("id");


            Bundle extra = getIntent().getBundleExtra("id");
            id_to_delete = (ArrayList<String>) extra.getSerializable("objects");


            Log.d("StringTest1", " nach übergabe getIntent" + id_to_delete );
            title_input.setText(title);
            showDate.setText(deadline);
            currentDateString = deadline;


        }
        else{
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
        }
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
            showDate.setText("The page has not been updated yet");
        }
        else{
            showDate.setText(currentDateString);
        }


    }
}