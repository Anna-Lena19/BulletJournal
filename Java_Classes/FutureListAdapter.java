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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FutureListAdapter extends RecyclerView.Adapter<FutureListAdapter.ViewHolder2> {//old worked     Adapter<TaskViewHolder>

    TaskManager taskManager;

    //SQLite Tutorial
    private Context context;
    private Activity activity;
    private ArrayList task_id, task_description, task_date_ident, task_reflection, task_bullet_category, task_deadline, project_Ident, task_beginning, task_ending;
    MyDatabaseHelper myDB;

    Integer count;

    private Fragment fragment;
    ArrayList<String> arrayListGroup;
    String header;


    FutureListAdapter(Fragment fragment, ArrayList<String> arrayListGroup, Activity activity, Context context, ArrayList book_id, ArrayList book_title, ArrayList book_author,
                      ArrayList book_pages, ArrayList task_bullet_category, ArrayList task_deadline, ArrayList project_Ident){
        this.activity = activity;
        this.context = context;
        this.task_id = book_id;
        this.task_description = book_title;
        this.task_date_ident = book_author;
        this.task_reflection = book_pages;
        this.task_bullet_category = task_bullet_category;
        this.task_deadline = task_deadline;
        this.project_Ident = project_Ident;

        this.fragment = fragment;
        this.arrayListGroup = arrayListGroup;

        myDB = new MyDatabaseHelper(context);
    }
    //Ende

    //Delete?
   /* public TaskListAdapter(TaskManager taskManager){
        this.taskManager = taskManager;
    }*/


    @NonNull
    @Override
    public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//old worked     TaskViewHolder als return type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.futurelog_row_group,parent,false);
        return new FutureListAdapter.ViewHolder2(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder2 holder, final int position) {

        holder.title.setText(arrayListGroup.get(position));

        header = arrayListGroup.get(position);


        FutureListItemAdapter futureListItemAdapter = new FutureListItemAdapter(activity,context, task_id, task_description, task_date_ident,
                task_reflection, task_bullet_category,task_deadline, project_Ident, task_beginning, task_ending,header);

        holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayoutManager linearLayout = new LinearLayoutManager(fragment.getContext());
        holder.rvProjects.setLayoutManager(linearLayout);
        holder.rvProjects.setAdapter(futureListItemAdapter);
    }



    @Override
    public int getItemCount() {

        return arrayListGroup.size();
    }




    public class ViewHolder2 extends RecyclerView.ViewHolder {

        RecyclerView rvProjects;
        TextView title, showProjectDeadline;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);

            rvProjects = itemView.findViewById(R.id.futurelog_Tasklist_child);
            title = itemView.findViewById(R.id._title);
            //showProjectDeadline = itemView.findViewById(R.id.showFutureDeadline);
        }
    }




}
