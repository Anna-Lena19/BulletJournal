package com.example.bulletjournal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DailyFragment extends Fragment {

    //Button
    FloatingActionButton addNoteButton;

    //Date
    TextView dateOfDay;
    Date currentTime;
    String formattendDate;
    String[] splitDate;

    //Recyclerview
    RecyclerView recyclerView;

    //TaskManager
    TaskManager taskManager;
    RelativeLayout relativeLayoutCarbon;


    //SQLite Tutorial
    MyDatabaseHelper myDB;
    ArrayList<String> task_id, task_description, task_date_ident, task_reflection, task_bullet_category, task_deadline, task_project_Ident, task_time_from, task_time_to;
    TaskListAdapter taskListAdapter;

    String date_ident, id, reflection, project_Ident;



    //Progressbar
    Integer progr = 0;
    Button incr,decr;
    ProgressBar progress_bar;
    TextView text_view_progress;

    //SharedPrefereces
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String PROGRESS = "progress";




    @RequiresApi(api = Build.VERSION_CODES.P)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dailyView = inflater.inflate(R.layout.fragment_daily, container, false);

        //Display Time
        currentTime = Calendar.getInstance().getTime();
        formattendDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);
        splitDate = formattendDate.split(",");
        dateOfDay = dailyView.findViewById(R.id.dateOfDay);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        String dateTime = simpleDateFormat.format(currentTime.getTime());

        dateOfDay.setText(splitDate[0] + " " + dateTime + ".");//splitDate[1]
        Log.d("DateFormat",  "Full Date: " + formattendDate + "   SplitDate: " + splitDate + "  split again[0]: " + splitDate[0] + "  split again[1]: " + splitDate[1]);



        //Add Functionality to AddButton
        addNoteButton = dailyView.findViewById(R.id.addNoteButton);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddNoteActivity.class);
                startActivity(intent);
            }
        });


        //Recyclerview
        recyclerView =dailyView.findViewById(R.id.dailyTaskList);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setAdapter( new TaskListAdapter(taskManager));

        //
        //taskManager = new TaskManager(getActivity());
        relativeLayoutCarbon = dailyView.findViewById(R.id.carbon_relativelayout);
        relativeLayoutCarbon.setOutlineAmbientShadowColor(Color.parseColor("#fc0330"));
        //relativeLayoutCarbon.setOutlineSpotShadowColor(Color.RED);




        //SQLite Tutorial
        myDB = new MyDatabaseHelper(getActivity());
        task_id = new ArrayList<>();
        task_description = new ArrayList<>();
        task_date_ident = new ArrayList<>();
        task_reflection = new ArrayList<>();
        task_bullet_category = new ArrayList<>();
        task_deadline = new ArrayList<>();
        task_project_Ident = new ArrayList<>();
        task_time_from = new ArrayList<>();
        task_time_to = new ArrayList<>();
        Log.d("delete", "in Daily nach initialisation is my DB null: " + (myDB == null));

        storeDataInArrays();
        Toast.makeText(getContext(),"daily wurde aufgerufen",Toast.LENGTH_SHORT).show();



        //Init TouchHelper Class
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TaskListTouchCallback(recyclerView,myDB));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        Log.d("delete", "in Daily is my DB null: " + (myDB == null));


        //Menu bar for send data to Future...
        setHasOptionsMenu(true);




        //SQLite Tutorial
        Log.d("noData", "text: " + task_description);
        taskListAdapter = new TaskListAdapter(getActivity(),getActivity(), task_id, task_description, task_date_ident,
                task_reflection, task_bullet_category,task_deadline, task_project_Ident,task_time_from,task_time_to);
        recyclerView.setAdapter(taskListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //get Mobile Android version
        Log.d("versionA", " " + (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.P)) ;
        //text1 = dailyView.findViewById(R.id.text1);
        //text1.setOutlineSpotShadowColor(Color.RED);
        //Integer color = text1.getOutlineSpotShadowColor();
        //Log.d("versionA", "" + color);


        text_view_progress =dailyView.findViewById(R.id.text1);
        progress_bar = dailyView.findViewById(R.id.progressbarCheckt);

        loadData();
        updateProgressBar();
        /*incr = dailyView.findViewById(R.id.button_incr);
        incr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progr <=90){
                    progr+= 10;
                    updateProgressBar();
                    saveData();
                }

            }
        });
        //decr = dailyView.findViewById(R.id.button_decr);
        decr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progr >=10){
                    progr-= 10;
                    updateProgressBar();
                    saveData();
                }

            }
        });*/



        return dailyView;
    }


    //Aufgerufen von AddNoteActivity//test--------------------------
   /* public void addTask(Task task){
        taskManager.addTask(task);
    }

    // Aufgerufen von AddNoteActivity Update Recyclerview//test--------------------------
    public void updateRecyclerView(){
        recyclerView.getAdapter().notifyItemInserted(taskManager.getTaskCount()-1);
    }*/




   //SharedPreferences
   public void saveData() {
       SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.putInt(PROGRESS, progr);
       editor.apply();
       Toast.makeText(getContext(), "Data saved", Toast.LENGTH_SHORT).show();
   }
    public void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        progr = sharedPreferences.getInt(PROGRESS,0);
    }













    private void updateProgressBar() {
        progress_bar.setProgress(progr);
        text_view_progress.setText(progr + " %");
    }





    //SQLite Tutorial
    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            // empty_imageview.setVisibility(View.VISIBLE);
            //no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                task_id.add(cursor.getString(0));
                task_description.add(cursor.getString(1));
                task_date_ident.add(cursor.getString(2));// todo  Reset Data so all are displayed before
                task_reflection.add(cursor.getString(3));
                task_bullet_category.add(cursor.getString(4));
                task_deadline.add(cursor.getString(5));
                task_project_Ident.add(cursor.getString(6));
                task_time_from.add(cursor.getString(9));
                task_time_to.add(cursor.getString(10));
            }
            // empty_imageview.setVisibility(View.GONE);
            //no_data.setVisibility(View.GONE);
        }

    }



   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }*/







    //Menu bar for send data to Future...


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.daily_top_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.option_01:

                Toast.makeText(getContext(),"option 1 selected",Toast.LENGTH_SHORT).show();
                storeDataInArrays();
                //Log.d("reflect", "data was updated and stored in Daily array");
                reflectTask();
                return true;
            case R.id.option_02:

                Toast.makeText(getContext(),"option 2 selected",Toast.LENGTH_SHORT).show();
                return true;

            default:
                break;
        }

        return false;
    }




    public void reflectTask(){//todo nötig, da reflect = dateIdent
        for(int i = 0; i< task_id.size(); i++){
            reflection = task_reflection.get(i);
            Integer reflection_Int = Integer.parseInt(reflection);

            String dateIdent = task_date_ident.get(i).toString();
            Integer dateIdent_Int = Integer.parseInt(dateIdent);

            String deadline = task_deadline.get(i);

            if(dateIdent_Int == 0 && deadline != null){

                Log.d("SearchDeeper"," Reflect Daily task______   Reflection: " + reflection_Int +  "Name: " + task_description.get(i) + "  Deadline: " + task_deadline.get(i) + "  task_date_ident: " + task_date_ident.get(i));

                if(reflection_Int == 0){
                    date_ident = "0";
                }
                else if(reflection_Int == 1){
                    date_ident = "1";
                }
                else if(reflection_Int == 2){
                    date_ident = "2";
                }
                else if(reflection_Int == 3){
                    //todo löschen
                }
                else if(reflection_Int == 4){
                    //todo löschen
                }

                id = task_id.get(i);
                myDB.updateDateIdent(id, date_ident);
                //Log.d("reflect", "author was updated on pictures title: " + task_description.get(i) + " author: " + date_ident);
            }
            else if(deadline == null){
                Toast.makeText(getContext(),"Kein Datum für YearlyLog angegeben!",Toast.LENGTH_SHORT).show();
            }


        }
    }




}
