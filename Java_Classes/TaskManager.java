package com.example.bulletjournal;

import android.content.Context;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

public class TaskManager {
    ArrayList<Task> taskList;

    public TaskManager(Context context){
        //Hawk.init(context).build();
        loadTaskList();
    }

    public void addTask(Task task){
        taskList.add(task);
        saveTaskList();
    }

    public void removeTask(Task task){
        taskList.remove(task);
        saveTaskList();
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public  int getTaskCount(){//Test--------------

       /*Task task;
        if(true){
            return 1;
        }
        else{
            return taskList.size();
        }*/
       return 0;

    }

    protected void saveTaskList(){
       // Hawk.put("taskList",taskList);
    }

    protected void loadTaskList(){
        //taskList = Hawk.get("taskList", new ArrayList<Task>());
    }
}
