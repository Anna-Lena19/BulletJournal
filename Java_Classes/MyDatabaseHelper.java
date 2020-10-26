package com.example.bulletjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper{

    private Context context;
    private static final  String DATABASE_NAME = "Bulletjournal.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_journal";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DESCRIPTION = "task_description";
    private static final String COLUMN_DATE_IDENTIFICATION = "date_identification";
    private static final String COLUMN_TASK_REFLECTION = "task_reflection";
    private static final String COLUMN_BULLETS_IDENTIFICATION = "bullets_identification";
    private static final String COLUMN_TASK_DEADLINE = "task_deadline";
    private static final String COLUMN_PROJECT_IDENTIFICATION = "project_identification";
    private static final String COLUMN_PROJECT_DEADLINE = "project_deadline";
    private static final String COLUMN_SONSTIGES = "sonstiges";
    private static final String COLUMN_TIME_FROM = "task_beginning";
    private static final String COLUMN_TIME_TO = "tak_ending";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE_IDENTIFICATION + " TEXT, " +
                COLUMN_TASK_REFLECTION + " INTEGER," +
                COLUMN_BULLETS_IDENTIFICATION + " TEXT," +
                COLUMN_TASK_DEADLINE + " TEXT," +
                COLUMN_PROJECT_IDENTIFICATION + " TEXT," +
                COLUMN_PROJECT_DEADLINE + " TEXT," +
                COLUMN_SONSTIGES + " TEXT," +
                COLUMN_TIME_FROM + " TEXT," +
                COLUMN_TIME_TO + " TEXT);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addBook(String taskDescription, String date_ident, int task_reflction, String bullet_category, String task_deadline, String project_identification){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DESCRIPTION, taskDescription);
        cv.put(COLUMN_DATE_IDENTIFICATION, date_ident);
        cv.put(COLUMN_TASK_REFLECTION, task_reflction);
        cv.put(COLUMN_BULLETS_IDENTIFICATION, bullet_category);
        cv.put(COLUMN_TASK_DEADLINE,task_deadline);
        cv.put(COLUMN_PROJECT_IDENTIFICATION,project_identification);


        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            Log.d("noData", "faild add task");
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            Log.d("noData", "Successfully add task");
        }
    }




    void addProject(String taskDescription, String date_ident, int task_reflction, String bullet_category, String task_deadline, String project_identification, String task_heading, String project_deadline){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DESCRIPTION, taskDescription);
        cv.put(COLUMN_DATE_IDENTIFICATION, date_ident);
        cv.put(COLUMN_TASK_REFLECTION, task_reflction);
        cv.put(COLUMN_BULLETS_IDENTIFICATION, bullet_category);
        cv.put(COLUMN_TASK_DEADLINE,task_deadline);
        cv.put(COLUMN_PROJECT_IDENTIFICATION,project_identification);
        cv.put(COLUMN_SONSTIGES,task_heading);
        cv.put(COLUMN_PROJECT_DEADLINE,project_deadline);

        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            Log.d("noData", "faild add task");
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            Log.d("noData", "Successfully add task");
        }
    }



    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String task_description, String bullet_category, String task_deadline, String task_beginning, String task_ending){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DESCRIPTION, task_description);
        cv.put(COLUMN_BULLETS_IDENTIFICATION, bullet_category);
        cv.put(COLUMN_TASK_DEADLINE, task_deadline);
        cv.put(COLUMN_TIME_FROM, task_beginning);
        cv.put(COLUMN_TIME_TO, task_ending);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }


    // Test Update only image not sure if work
    void updateImage(String row_id, String task_reflection){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put(COLUMN_TITLE, title);
        //cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_TASK_REFLECTION, task_reflection);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            //Toast.makeText(context, "Image Update Successfully!", Toast.LENGTH_SHORT).show();
        }

    }



    void updateProjectIdent(String row_id, String project_Ident){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put(COLUMN_TITLE, title);
        //cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PROJECT_IDENTIFICATION, project_Ident);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            //Toast.makeText(context, "Image Update Successfully!", Toast.LENGTH_SHORT).show();
        }

    }



    // Test Update only author not sure if work
    void updateDateIdent(String row_id, String date_ident){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DATE_IDENTIFICATION, date_ident);
        //cv.put(COLUMN_PAGES, pages);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            //Toast.makeText(context, "Image Update Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void updateProjectDeadline(String row_id, String deadline){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_PROJECT_DEADLINE, deadline);
        //cv.put(COLUMN_PAGES, pages);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            //Toast.makeText(context, "Image Update Successfully!", Toast.LENGTH_SHORT).show();
        }
    }


    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }




    //Test get id to delete row
    void deleteRowAtPosition(int position){
        Cursor cursor = readAllData();
        if(cursor.getCount() == 0){

        }else{
            cursor.moveToPosition(position);
            String id =  cursor.getString(0);
            //int int_id = Integer.parseInt(id);
            deleteOneRow(id);
            String title = cursor.getString(1);
            Toast.makeText(context, "Delete: "+ title, Toast.LENGTH_SHORT).show();

        }

    }



}
