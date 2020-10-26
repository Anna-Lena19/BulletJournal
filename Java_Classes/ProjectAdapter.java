package com.example.bulletjournal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private Fragment fragment;
    ArrayList<String> arrayListGroup;

    ArrayList<String> task_id, task_description, task_date_ident, task_reflection, task_bullet_category, task_deadline, project_identification, project_deadline, project_headings;
    private Context context;
    private Activity activity;

    String header;
    ArrayList<String> ids_forDelete;

    ProjectAdapter(Fragment fragment, ArrayList<String> arrayListGroup, Activity activity, Context context, ArrayList task_id, ArrayList task_description, ArrayList task_date_ident,
                   ArrayList task_reflection, ArrayList task_bullet_category, ArrayList task_deadline, ArrayList project_identification, ArrayList project_deadline, ArrayList project_headings){
        this.fragment = fragment;
        this.arrayListGroup = arrayListGroup;

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
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_row_group,parent,false);
        return new ProjectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.title.setText(arrayListGroup.get(position));
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUpdateActivity(position);
            }
        });
        if(!(project_deadline.size() <= position)) {
            holder.showProjectDeadline.setText(String.valueOf(project_deadline.get(position)));//todo project ohne subproject erstellt OHNE DATUM dann noch ein project erstellt und abgestürtzt wegen index outof Boundsexeption
            Log.d("DeadlineTest", "showDeadline position: " + position + " deadline: " + project_deadline.get(position));
        }
        ArrayList<String> arrayListItem = new ArrayList<>();
        /*for(int i = 1; i<=task_id.size(); i++){
            arrayListItem.add("Member" + i);
        }*/
        header = arrayListGroup.get(position);


        ProjectItemAdapter projectAdapter = new ProjectItemAdapter(header,activity,context, task_id, task_description, task_date_ident,
                task_reflection, task_bullet_category,task_deadline, project_identification, project_deadline, project_headings);

        holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayoutManager linearLayout = new LinearLayoutManager(fragment.getContext());
        holder.rvProjects.setLayoutManager(linearLayout);
        holder.rvProjects.setAdapter(projectAdapter);
    }



    public void openUpdateActivity(int position){
        header = arrayListGroup.get(position);
        Log.d("StringTest1", " bei update open ist aktueller header: " + header + " position: " + position);
        ids_forDelete = new ArrayList<>();
        String checkOnHeader = "";
        for(int i =0 ; i<project_headings.size();i++){
            if(project_headings.get(i) != null){
                checkOnHeader = project_headings.get(i).toString();
            }
            if(checkOnHeader.equals(header)){
                ids_forDelete.add(task_id.get(i));
            }
        }


        Bundle extra = new Bundle();
        extra.putSerializable("objects", ids_forDelete);


        Intent intent = new Intent(context, UpdateProjectActivity.class);
        intent.putExtra("description",header);
        intent.putExtra("id",extra);
        intent.putExtra("deadline",project_deadline.get(position));
        activity.startActivityForResult(intent,1);


    }




    @Override
    public int getItemCount() {
        return arrayListGroup.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView rvProjects;
        TextView title, showProjectDeadline;

         public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rvProjects = itemView.findViewById(R.id.project_Tasklist_child);
            title = itemView.findViewById(R.id.title_title);
             showProjectDeadline = itemView.findViewById(R.id.showProjectDeadline);
        }
    }
}
