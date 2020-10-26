package com.example.bulletjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.Calendar;

public class AddProjectActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{

    //Buttons
    Button save_Button;
    ImageButton back_Button;

    //Input Fields
    EditText taskDescription, taskHeading;
    //EditText setBulletCategory;
    Spinner setBulletIdent;
    String bulletIdent;
    Button setDate,saveSubnote, projectDeadline;
    TextView showDate, showSubnote, showDeadline;
    String currentDateString, deadline;

    //Scripts
    TaskManager taskManager;

    DailyFragment dailyFragment; //test--------------------------

    ArrayList<Task> taskArrayList;

  Integer witchDatePicker;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        save_Button = findViewById(R.id.save_button);
        save_Button.setOnClickListener(this);
        back_Button = findViewById(R.id.back_button);
        //back_Button.setOnClickListener(this);
        saveSubnote = findViewById(R.id.subnoteSave);
        saveSubnote.setOnClickListener(this);
        projectDeadline = findViewById(R.id.projectDeadline);
        projectDeadline.setOnClickListener(this);

        taskHeading = findViewById(R.id.setHeading);
        showSubnote = findViewById(R.id.showSubnote);
        showDeadline = findViewById(R.id.showDeadline);

        taskArrayList = new ArrayList<>();

        taskDescription = findViewById(R.id.setDescription);
        //setBulletCategory = findViewById(R.id.setDateIdent);
        /*setBulletIdent = findViewById(R.id.setBulletIdent);
        ArrayAdapter<CharSequence> spinner_Adapter = ArrayAdapter.createFromResource(this, R.array.date_identification, android.R.layout.simple_spinner_item);
        spinner_Adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        setBulletIdent.setAdapter(spinner_Adapter);
        setBulletIdent.setOnItemSelectedListener(this);*/
        //taskManager = new TaskManager(this); //test--------------------------

        //todo toolbar mit back arrow

        setDate = findViewById(R.id.dateDeadline);
        setDate.setOnClickListener(this);
        showDate = findViewById(R.id.showDate);


        currentDateString = "";
        deadline = "";

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.save_button:
                String description = taskDescription.getText().toString();
                String headingTitle = taskHeading.getText().toString();
                String deadline = showDeadline.getText().toString();
                Log.d("description3",showDeadline.getText().toString());
                if(!(TextUtils.isEmpty(description))) {
                    taskDescription.setError("Save Subnote");
                }
                else if(TextUtils.isEmpty(headingTitle)){
                    taskHeading.setError("Set a Heading");
                }
                else if (TextUtils.isEmpty(deadline)){
                    taskHeading.setError("Set a Deadline");
                }
                else{
                    saveData();
                    finish();//todo testen finish freischalten
                }
                break;
            case R.id.dateDeadline:
                witchDatePicker = 1;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                break;
            case R.id.subnoteSave:
                String heading = taskHeading.getText().toString();
                String subHeading = taskDescription.getText().toString();
                if(TextUtils.isEmpty(heading)) {
                    taskHeading.setError("Set a Heading");
                    return;
                }
                else if(TextUtils.isEmpty(subHeading)){
                    taskDescription.setError("Save Subnote");
                }
                else{
                    saveSubnotes();
                }
                break;
            case R.id.projectDeadline:
                witchDatePicker = 0;
                DialogFragment datePicker2 = new DatePickerFragment();
                datePicker2.show(getSupportFragmentManager(), "date picker");
                break;

        }
    }






    private void saveSubnotes() {

        Task saveTask = new Task(taskDescription.getText().toString(),"0",currentDateString,taskHeading.getText().toString());
        taskArrayList.add(saveTask);
        showSubnote.append(taskDescription.getText() + "  ,  " + currentDateString + " \n");
        taskDescription.getText().clear();
        currentDateString = "";
    }


    //Get Data from Fields and send to Taskmanager
    private void saveData() {
        //Get all Data
        //String taskDescriptionString = taskDescription.getText().toString();
        //todo prevent empty fields? see Context

        //Create new Task
        //Task task = new Task();

        //Set Content of Task
        //task.setTaskDescription(taskDescriptionString);

        //Add Task
        //taskManager.addTask(task);   //test--------------------------
        //dailyFragment.addTask(task);

        // Update Recyclerview
        //dailyFragment.updateRecyclerView();


        //SQLite Tutorial
        String dateIdent_input = "0";
        Integer reflection_input = 0;
        String project_Ident = "1";

        MyDatabaseHelper myDB = new MyDatabaseHelper(AddProjectActivity.this);
        /*myDB.addBook(taskDescription.getText().toString().trim(),
                dateIdent_input.trim(),
                Integer.valueOf(reflection_input.toString().trim()),
                bulletIdent.trim(),
                currentDateString,
                project_Ident);*/
        for(int i = 0; i<taskArrayList.size(); i++){

            Task task_01 = taskArrayList.get(i);

            Log.d("DeadlineTest", "addProject, description: "+ task_01.getTaskDescription() + " deadline: "+ deadline);
            myDB.addProject(task_01.getTaskDescription().trim(),
                    dateIdent_input.trim(),
                    Integer.valueOf(reflection_input.toString().trim()),
                    task_01.getBulletIdent().trim(),
                    task_01.getCurrentDate(),
                    project_Ident,
                    task_01.getTaskHeading(),
                    deadline);
        }


        //Toast.makeText(this, "First: " + taskDescription.getText().toString().trim() + " Second: " +  bulletIdent.trim() , Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getItemAtPosition(i).toString()){
            case ("Task"): bulletIdent = "0";
                break;
            case ("Note"): bulletIdent = "1";
                break;
            case ("Event"): bulletIdent = "2";
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

        if(witchDatePicker == 0){
            showDeadline.setText(currentDateString);
            deadline = currentDateString;
        }
        else{
            showDate.setText(currentDateString);
        }




    }






    public class Task  {

        String taskDescription, bulletIdent, currentDate, taskHeading;

        public Task(String taskDescription, String bulletIdent, String currentDate, String taskHeading) {
            this.taskDescription = taskDescription;
            this.bulletIdent = bulletIdent;
            this.currentDate = currentDate;
            this.taskHeading = taskHeading;
        }


        public String getTaskDescription() {
            return taskDescription;
        }

        public String getBulletIdent() {
            return bulletIdent;
        }

        public String getCurrentDate() {
            return currentDate;
        }

        public String getTaskHeading() {
            return taskHeading;
        }



        public void setTaskDescription(String taskDescription) {
            this.taskDescription = taskDescription;
        }

        public void setBulletIdent(String bulletIdent) {
            this.bulletIdent = bulletIdent;
        }

        public void setCurrentDate(String currentDate) {
            this.currentDate = currentDate;
        }

        public void setTaskHeading(String taskHeading) {
            this.taskHeading = taskHeading;
        }
    }
}