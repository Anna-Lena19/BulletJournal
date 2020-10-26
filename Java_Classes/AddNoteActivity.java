package com.example.bulletjournal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    //Buttons
    Button save_Button;
    ImageButton back_Button;

    //Input Fields
    EditText taskDescription;
    //EditText setBulletCategory;
    Spinner setBulletIdent;
    String bulletIdent;
    Button setDate;
    TextView showDate;
    String currentDateString;

    //Scripts
    TaskManager taskManager;

    DailyFragment dailyFragment; //test--------------------------





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        save_Button = findViewById(R.id.save_button);
        save_Button.setOnClickListener(this);
        back_Button = findViewById(R.id.back_button);
        back_Button.setOnClickListener(this);

        taskDescription = findViewById(R.id.setDescription);
        //setBulletCategory = findViewById(R.id.setDateIdent);
        setBulletIdent = findViewById(R.id.setBulletIdent);
        ArrayAdapter<CharSequence> spinner_Adapter = ArrayAdapter.createFromResource(this, R.array.date_identification, android.R.layout.simple_spinner_item);
        spinner_Adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);//android.R.layout.simple_spinner_dropdown_item
        setBulletIdent.setAdapter(spinner_Adapter);
        setBulletIdent.setOnItemSelectedListener(this);
        //taskManager = new TaskManager(this); //test--------------------------

        //todo toolbar mit back arrow

        setDate = findViewById(R.id.dateDeadline);
        setDate.setOnClickListener(this);
        showDate = findViewById(R.id.showDate);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_button:
                finish();
                break;
            case R.id.save_button:
                saveData();
                finish();//todo testen finish freischalten
                break;
            case R.id.dateDeadline:
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
        }
    }


   //Get Data from Fields and send to Taskmanager
    private void saveData() {
        //Get all Data
        //String taskDescriptionString = taskDescription.getText().toString();
        //todo prevent empty fields? see Context

        //Create new Task
        Task task = new Task();

        //Set Content of Task
        //task.setTaskDescription(taskDescriptionString);

        //Add Task
        //taskManager.addTask(task);   //test--------------------------
        //dailyFragment.addTask(task);

        // Update Recyclerview
        //dailyFragment.updateRecyclerView();


        //SQLite Tutorial
        String dateIdent_input = "0";//todo nötig ?
        Integer reflection_input = 0;
        String project_identification = "0";

        MyDatabaseHelper myDB = new MyDatabaseHelper(AddNoteActivity.this);
        myDB.addBook(taskDescription.getText().toString().trim(),
                dateIdent_input.trim(),
                Integer.valueOf(reflection_input.toString().trim()),
                bulletIdent.trim(),
                currentDateString,
                project_identification);

                Toast.makeText(this, "First: " + taskDescription.getText().toString().trim() + " Second: " +  bulletIdent.trim() , Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getItemAtPosition(i).toString()){
            case ("Task"): bulletIdent = "0";
                            break;
            case ("Note"): bulletIdent = "2";
                            break;
            case ("Event"): bulletIdent = "1";
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

        showDate.setText(currentDateString);


    }
}