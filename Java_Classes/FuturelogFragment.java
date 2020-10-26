package com.example.bulletjournal;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FuturelogFragment extends Fragment {

    //Button
    FloatingActionButton addNoteButton;

    //Date
    TextView dateOfDay;
    Date currentTime;
    String formattendDate;
    String[] splitDate;

    //Recyclerview
    RecyclerView recyclerView;
    RecyclerView recyclerViewYear;

    //TaskManager
    TaskManager taskManager;


    //SQLite Tutorial
    MyDatabaseHelper myDB;
    ArrayList<String> task_id, task_description, task_date_ident, task_reflection, task_bullet_category, task_deadline, task_project_Ident, task_time_from,task_time_to;
    FutureListAdapter futureListAdapter;
    FutureListAdapterMonth futureListAdapterMonth;

    String date_ident, id, reflection, project_Ident;





    //Adapter from Project page ******************************
    RecyclerView rvGroup;
    ArrayList<String> arrayListGroup;
    LinearLayoutManager layoutManagerGroup;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View futureView = inflater.inflate(R.layout.fragment_futurelog, container, false);

        //Display Time
        /*currentTime = Calendar.getInstance().getTime();
        formattendDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);
        splitDate = formattendDate.split(",");*/
        dateOfDay = futureView.findViewById(R.id.dateOfMonth);
       /* dateOfDay.setText(splitDate[0] + splitDate[1]);
        Calendar.get(Calendar.MONTH)*/
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        dateOfDay.setText(month_name);



        //Add Functionality to AddButton
        /*addNoteButton = futureView.findViewById(R.id.addNoteButton);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddNoteActivity.class);
                startActivity(intent);
            }
        });*/


        //Recyclerview
        recyclerView = futureView.findViewById(R.id.monthlyTaskList);
        //recyclerViewYear = futureView.findViewById(R.id.monthlyTaskList_01);

        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setAdapter( new TaskListAdapter(taskManager));

        //
        //taskManager = new TaskManager(getActivity());






        //SQLite Tutorial
        myDB = new MyDatabaseHelper(getActivity());
        task_id = new ArrayList<>();
        task_description = new ArrayList<>();
        task_date_ident = new ArrayList<>();
        task_reflection = new ArrayList<>();
        task_bullet_category  = new ArrayList<>();
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



        //Adapter from Project page ******************************
                rvGroup = futureView.findViewById(R.id.monthlyTaskList_01);
                arrayListGroup = new ArrayList<>();
                arrayListGroup.add("01");//Januar, Februar....
                arrayListGroup.add("02");
                arrayListGroup.add("03");
                arrayListGroup.add("04");
                arrayListGroup.add("05");
                arrayListGroup.add("06");
                arrayListGroup.add("07");
                arrayListGroup.add("08");
                arrayListGroup.add("09");
                arrayListGroup.add("10");
                arrayListGroup.add("11");
                arrayListGroup.add("12");
                /*for(int i=1; i<10;i++){
                    arrayListGroup.add("Group" + i);

                }*/
        //Adapter from Project page ******************************


        //SQLite Tutorial
        futureListAdapter = new FutureListAdapter(FuturelogFragment.this, arrayListGroup, getActivity(),getActivity(), task_id, task_description, task_date_ident,
                task_reflection, task_bullet_category,task_deadline, task_project_Ident);
        //recyclerView.setAdapter(futureListAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvGroup.setAdapter(futureListAdapter);
        rvGroup.setLayoutManager(new LinearLayoutManager(getActivity()));




        futureListAdapterMonth = new FutureListAdapterMonth(arrayListGroup,getActivity(),getActivity(), task_id, task_description, task_date_ident,
                task_reflection, task_bullet_category,task_deadline, task_project_Ident,task_time_from,task_time_to);
        recyclerView.setAdapter(futureListAdapterMonth);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));





        return futureView;
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
            }
            // empty_imageview.setVisibility(View.GONE);
            //no_data.setVisibility(View.GONE);
        }

    }








    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.future_top_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.option_01:

                Toast.makeText(getContext(),"option 1 selected",Toast.LENGTH_SHORT).show();
                storeDataInArrays();
                Log.d("reflect", "data was updated and stored in Daily array");
                reflectTask(1);
                return true;
            case R.id.option_02:

                storeDataInArrays();
                Log.d("reflect", "data was updated and stored in Daily array");
                reflectTask(2);
                Toast.makeText(getContext(),"option 2 selected",Toast.LENGTH_SHORT).show();
                return true;

            default:
                break;
        }

        return false;
    }





    public void reflectTask(Integer option){
        for(int i = 0; i< task_id.size(); i++){
            reflection = task_reflection.get(i);
            Integer pagesInt = Integer.parseInt(reflection);

            String dateIdent = task_date_ident.get(i).toString();
            Integer dateIdent_Int = Integer.parseInt(dateIdent);

            String deadline = task_deadline.get(i);

            if(option == 1){
                if(dateIdent_Int == 1 && deadline != null){

                    Log.d("SearchDeeper"," Reflect Futurelog moth______  Reflection: " + pagesInt + "  Name: " + task_description.get(i) + "  Deadline: " + task_deadline.get(i) + "  task_date_ident: " + task_date_ident.get(i));

                    if(pagesInt == 0){//todo alle bilder müssen vorher auf lila gesetzt werden!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        date_ident = "1";
                    }
                    else if(pagesInt == 1){
                        date_ident = "0";
                    }
                    else if(pagesInt == 2){
                        date_ident = "2";
                    }

                    id = task_id.get(i);
                    myDB.updateDateIdent(id, date_ident);
                    Log.d("reflect", "author was updated on pictures title: " + task_description.get(i) + " author: " + date_ident);
                }
                else if(deadline == null){
                    Toast.makeText(getContext(),"Kein Datum für YearlyLog angegeben!",Toast.LENGTH_SHORT).show();
                }
            }
            else if(option == 2){
                if(dateIdent_Int == 2){
                    if(pagesInt == 0){//todo second abfrage if projectIdent == 2
                        date_ident = "2";
                    }
                    else if(pagesInt == 1){
                        date_ident = "1";
                    }
                    else if(pagesInt == 2){
                        date_ident = "0";
                    }

                    id = task_id.get(i);
                    myDB.updateDateIdent(id, date_ident);
                    Log.d("reflect", "author was updated on pictures title: " + task_description.get(i) + " author: " + date_ident);
                }
            }



        }
    }
}
