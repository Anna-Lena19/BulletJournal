package com.example.bulletjournal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProjectsFragment extends Fragment {

    RecyclerView rvGroup;
    ArrayList<String> arrayListGroup;
    LinearLayoutManager layoutManagerGroup;
    ProjectAdapter projectAdapter;


    //Button
    FloatingActionButton addNoteButton;

    MyDatabaseHelper myDB;
    ArrayList<String> task_id, task_description, task_date_ident, task_reflection, task_bullet_category, task_deadline, project_identification, project_deadline, project_headings ;
    String date_ident, id, reflection, project_Ident;
    ArrayList<String> project_header, project_deadlines;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View projectView = inflater.inflate(R.layout.fragment_projects, container, false);


        rvGroup = projectView.findViewById(R.id.projectTaskList);
        arrayListGroup = new ArrayList<>();
        for(int i=1; i<10;i++){
            arrayListGroup.add("Group" + i);

        }//todo abfrage, ob project mit title vorligt, anhand letzter zeile sql, nur todos mit diesem titel übergeben




        myDB = new MyDatabaseHelper(getActivity());
        task_id = new ArrayList<>();
        task_description = new ArrayList<>();
        task_date_ident = new ArrayList<>();
        task_reflection = new ArrayList<>();
        task_bullet_category = new ArrayList<>();
        task_deadline = new ArrayList<>();
        project_identification = new ArrayList<>();
        project_deadline = new ArrayList<>();
        project_headings = new ArrayList<>();

        project_header = new ArrayList<>();
        project_deadlines = new ArrayList<>();
        Log.d("delete", "in Daily nach initialisation is my DB null: " + (myDB == null));

        storeDataInArrays();

        selectProjectHeading();
        selectProjectDeadline();



        projectAdapter = new ProjectAdapter(ProjectsFragment.this, project_header, getActivity(),getActivity(), task_id, task_description, task_date_ident,
                task_reflection, task_bullet_category,task_deadline, project_identification, project_deadlines, project_headings);
        layoutManagerGroup = new LinearLayoutManager(getContext());
        rvGroup.setLayoutManager(layoutManagerGroup);
        rvGroup.setAdapter(projectAdapter );


        setHasOptionsMenu(true);


        //Add Functionality to AddButton
        addNoteButton = projectView.findViewById(R.id.addProjectButton);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddProjectActivity.class);
                startActivity(intent);
            }
        });


        return projectView;
    }





    private void selectProjectHeading() {

        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            // empty_imageview.setVisibility(View.VISIBLE);
            //no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                String header =  cursor.getString(8);
                //if(!project_header.isEmpty()){
                    if(project_header.contains(cursor.getString(8))){
                        Log.d("contains", " is already inside: " + cursor.getString(8));
                    }
                    else if(cursor.isNull(8)){
                        Log.d("contains", " is.Null: " + cursor.getString(8));
                    }
                    else{
                        project_header.add(cursor.getString(8));
                        Log.d("contains", " is not inside " + cursor.getString(8));
                    }
                //}


            }
            // empty_imageview.setVisibility(View.GONE);
            //no_data.setVisibility(View.GONE);
        }

    }







    private void selectProjectDeadline() {

        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            // empty_imageview.setVisibility(View.VISIBLE);
            //no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                String header =  cursor.getString(7);
                //if(!project_header.isEmpty()){
                if(project_deadlines.contains(cursor.getString(7))){
                    Log.d("contains", " is already inside: " + cursor.getString(7));
                }
                else if(cursor.isNull(7)){
                    Log.d("contains", " is.Null: " + cursor.getString(7));
                }
                else{
                    project_deadlines.add(cursor.getString(7));
                    Log.d("contains", " is not inside " + cursor.getString(7));
                }
                //}


            }
            // empty_imageview.setVisibility(View.GONE);
            //no_data.setVisibility(View.GONE);
        }

    }






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
                project_identification.add(cursor.getString(6));
                project_deadline.add(cursor.getString(7));
                project_headings.add(cursor.getString(8));
            }
            // empty_imageview.setVisibility(View.GONE);
            //no_data.setVisibility(View.GONE);
        }

    }









    //Menu bar for send data to Future...


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.project_top_menu, menu);
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




    public void reflectTask(){
        for(int i = 0; i< task_id.size(); i++){
            reflection = task_reflection.get(i);
            Integer reflection_Int = Integer.parseInt(reflection);

            project_Ident = project_identification.get(i);
            Integer project_IdentInt = Integer.parseInt(project_Ident);

            String deadline = task_deadline.get(i);

            if(project_IdentInt >=1 && (deadline != null)){//todo (deadline != null & reflection zu jahr bei allen daily month

                Log.d("SearchDeeper"," Reflect Project______  Reflection: " + reflection_Int + "  Name: " + task_description.get(i) + "  Deadline: " + task_deadline.get(i));


                if(reflection_Int == 0){
                    date_ident = "0";
                }
                else if(reflection_Int == 1){
                    date_ident = "1";
                }
                else if(reflection_Int == 2){
                    date_ident = "2";
                }

                id = task_id.get(i);
                myDB.updateDateIdent(id, date_ident);
                //Log.d("reflect", "author was updated on pictures title: " + task_description.get(i) + " author: " + date_ident);
            }
            else if(deadline == null) {
                Toast.makeText(getContext(), "Kein Datum für YearlyLog angegeben!", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
