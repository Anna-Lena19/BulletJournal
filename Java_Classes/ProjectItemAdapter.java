package com.example.bulletjournal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProjectItemAdapter extends RecyclerView.Adapter<ProjectItemAdapter.ViewHolder> {

    ArrayList<String> arrayListItem;
    String header;

    private Context context;
    private Activity activity;
    private ArrayList task_id, task_description, task_date_ident, task_reflection, task_bullet_category, task_deadline, project_identification, project_deadline, project_headings;
    MyDatabaseHelper myDB;

    Integer count;


    public ProjectItemAdapter(ArrayList<String> arrayListItem) {
        this.arrayListItem = arrayListItem;
    }

    public ProjectItemAdapter(String arrayListItem, Activity activity, Context context, ArrayList task_id, ArrayList task_description, ArrayList task_date_ident,
                              ArrayList task_reflection, ArrayList task_bullet_category, ArrayList task_deadline, ArrayList project_identification, ArrayList project_deadline, ArrayList project_headings){
        this.activity = activity;
        this.context = context;
        this.task_id = task_id;
        this.task_description = task_description;
        this.task_date_ident = task_date_ident;
        this.task_reflection = task_reflection;
        this.task_bullet_category = task_bullet_category;
        this.task_deadline = task_deadline;
        this.project_identification = project_identification;
        this.project_deadline = project_deadline;
        this.project_headings = project_headings;

        myDB = new MyDatabaseHelper(context);

        this.header = arrayListItem;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row,parent,false);
        return new ProjectItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //holder.title.setText(arrayListItem.get(position));




        String projectIdent = project_identification.get(position).toString();
        Integer projectIdent_Int = Integer.parseInt(projectIdent);
        Log.d("reflect", "dateident " + projectIdent + " Title: " + task_description.get(position) );
        Log.d("contains", "project_headings == header ?  Project_headings: " + project_headings.get(position) + " header: " + header);
        Log.d("contains", "Project_headings Size: " + project_headings.size());
        String checkOnHeader = "";
        if(project_headings.get(position) != null){
            checkOnHeader = project_headings.get(position).toString();
        }
        Log.d("contains", "Header is null or not " + checkOnHeader);
        Log.d("contains", "equal header headings " + (checkOnHeader.equals(header)));

        if(projectIdent_Int >= 1 &&  checkOnHeader.equals(header)){//todo check everywhere if there´s a else for 3, beim zurück schieben wieder auf 1 aetzen
            //Log.d("reflect", "dateident == 3" + dateIdent + " Title: " + task_description.get(position) );
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            ProjectItemAdapter.ViewHolder projectViewHolder = (ProjectItemAdapter.ViewHolder)holder;

                    //Set Items Text, Image

                    //TextView textView = taskViewHolder.linearLayout.findViewById(R.id.task_Description_Display);
                    holder.taskDescription_output.setText(String.valueOf(task_description.get(position))); // task.getTaskDescription()


                    if(task_deadline.get(position) != null){
                        holder.task_time_output.append("\n" + task_deadline.get(position));
                    }


                    //Change ImageButton Number
                    final ImageButton imageButton = holder.imageButton;
                    showImage(position, imageButton);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            UpdateImage(position, imageButton);
                        }
                    });

                    //setTag id to delete by id
                    String id = String.valueOf(task_id.get(position));
                    int int_id= Integer.parseInt(id);
                    projectViewHolder.itemView.setTag(int_id);

                    //SQLite Tutorial Update To do
                    holder.taskDescription_output.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openUpdateActivity(position);
                        }
                    });

        }
        else{
            //Log.d("reflect", "dateIdent not 3 Project" + dateIdent + " Title: " + task_description.get(position) );
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
        }


    }








    public void UpdateImage(int position, ImageButton imgButton){
        storeDataInArrays();
        String imageNumber = String.valueOf(task_reflection.get(position));
        Integer reflection_month_year = Integer.parseInt(imageNumber);
        Toast.makeText(context,"Position: " + position + "Number: " + reflection_month_year,Toast.LENGTH_SHORT).show();

        if(reflection_month_year == 0){
            myDB.updateImage(String.valueOf(task_id.get(position)),"1");
            myDB.updateProjectIdent(String.valueOf(task_id.get(position)), "2");
            imgButton.setImageResource(R.drawable.ic_task_migrated);
            //imgButton.setBackgroundColor(Color.parseColor("#c230ae"));
        }
        else if(reflection_month_year == 1){
            myDB.updateImage(String.valueOf(task_id.get(position)),"2");
            myDB.updateProjectIdent(String.valueOf(task_id.get(position)), "2");
            imgButton.setImageResource(R.drawable.ic_task_schedulet);
            //imgButton.setBackgroundColor(Color.parseColor("#3fc7d1"));
        }
        else if(reflection_month_year == 2){
            myDB.updateImage(String.valueOf(task_id.get(position)),"3");
            myDB.updateProjectIdent(String.valueOf(task_id.get(position)), "2");
            imgButton.setImageResource(R.drawable.ic_task_complete);
            //imgButton.setBackgroundColor(Color.parseColor("#3832a8"));
        }
        else if(reflection_month_year == 3){
            myDB.updateImage(String.valueOf(task_id.get(position)),"4");
            myDB.updateProjectIdent(String.valueOf(task_id.get(position)), "2");
            imgButton.setImageResource(R.drawable.ic_task_irrelevant);
            //imgButton.setBackgroundColor(Color.parseColor("#3832a8"));
        }
        else if(reflection_month_year == 4){
            myDB.updateImage(String.valueOf(task_id.get(position)),"0");
            myDB.updateProjectIdent(String.valueOf(task_id.get(position)), "2");
            imgButton.setImageResource(R.drawable.ic_task);
            //imgButton.setBackgroundColor(Color.parseColor("#3832a8"));
        }
    }



    public void showImage(int position, ImageButton imgButton){
        String imageNumber = String.valueOf(task_reflection.get(position));
        Integer imageNumber_Int = Integer.parseInt(imageNumber);
        //Toast.makeText(context,"Position: " + position + "Number: " + imageNumber_Int,Toast.LENGTH_SHORT).show();

        if(imageNumber_Int == 0){
            imgButton.setImageResource(R.drawable.ic_task);
            //imgButton.setBackgroundColor(Color.parseColor("#3832a8"));
        }
        else if(imageNumber_Int == 1){
            imgButton.setImageResource(R.drawable.ic_task_migrated);
            //imgButton.setBackgroundColor(Color.parseColor("#c230ae"));
        }
        else if(imageNumber_Int == 2){
            imgButton.setImageResource(R.drawable.ic_task_schedulet);
            //imgButton.setBackgroundColor(Color.parseColor("#3fc7d1"));
        }
        else if(imageNumber_Int == 3){
            imgButton.setImageResource(R.drawable.ic_task_complete);
            //imgButton.setBackgroundColor(Color.parseColor("#3fc7d1"));
        }
        else if(imageNumber_Int == 4){
            imgButton.setImageResource(R.drawable.ic_task_irrelevant);
            //imgButton.setBackgroundColor(Color.parseColor("#3fc7d1"));
        }
    }


    void storeDataInArrays(){
        task_reflection.clear();
        project_identification.clear();
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            // empty_imageview.setVisibility(View.VISIBLE);
            //no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                task_reflection.add(cursor.getString(3));
                project_identification.add(cursor.getString(6));
            }
            // empty_imageview.setVisibility(View.GONE);
            //no_data.setVisibility(View.GONE);
        }

    }



    public void openUpdateActivity(int position){
        Intent intent = new Intent(context, UpdateActivity.class);
        intent.putExtra("description",String.valueOf(task_description.get(position)));
        intent.putExtra("id",String.valueOf(task_id.get(position)));
        intent.putExtra("bulletIdent",String.valueOf(task_bullet_category.get(position)));
        intent.putExtra("deadline",String.valueOf(task_deadline.get(position)));
        activity.startActivityForResult(intent,1);
    }



















    @Override
    public int getItemCount() {
        return project_headings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, taskDescription_output, task_time_output;
        final ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //title = itemView.findViewById(R.id.task_Description_Display);
            taskDescription_output = itemView.findViewById(R.id.task_Description_Display);
            imageButton = itemView.findViewById(R.id.task_imgButton);
            task_time_output = itemView.findViewById(R.id.task_deadline);
        }
    }
}
