package com.example.bulletjournal;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListTouchCallback extends ItemTouchHelper.SimpleCallback {

    RecyclerView recyclerView;
    MyDatabaseHelper myDB;

    public TaskListTouchCallback(RecyclerView recyclerView, MyDatabaseHelper myDB) {
        super(ItemTouchHelper.UP |ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT);

        this.recyclerView = recyclerView;
        this.myDB = myDB;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int oldPosition = viewHolder.getAdapterPosition();
        int newPosition = target.getAdapterPosition();
        //todo Task an oldPositon löschen und Task an newPosition in Datenbank einfügen
        recyclerView.getAdapter().notifyItemMoved(oldPosition,newPosition);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        //todo Vorerst gelöscht vielleicht später noch implementieren

        /*int position = viewHolder.getAdapterPosition();
        //todo delete Task an position
        if(myDB == null){
            Log.d("delete", "myDB is null");
        }
        else{
           // myDB.deleteRowAtPosition(position);
            String id = String.valueOf(viewHolder.itemView.getTag());
            myDB.deleteOneRow(id);
        }

        recyclerView.getAdapter().notifyItemRemoved(position);//*/
    }
}
