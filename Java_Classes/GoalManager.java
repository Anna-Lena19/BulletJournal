package com.example.bulletjournal;

import android.content.Context;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

public class GoalManager {
    ArrayList<Goal> goalList;

    public GoalManager(Context context){
        Hawk.init(context).build();
        loadGoalList();
    }

    public void addGoal(Goal goal){
        goalList.add(goal);
        saveGoalList();
    }

    public void removeGoal(Goal goal){
        goalList.remove(goal);
        saveGoalList();
    }

    public ArrayList<Goal> getGoalList() {
        return goalList;
    }

    public  int getGoalCount(){
       return goalList.size();
    }

    protected void saveGoalList(){
        Hawk.put("goalList",goalList);
    }

    protected void loadGoalList(){
        goalList = Hawk.get("goalList", new ArrayList<Goal>());
    }
}
