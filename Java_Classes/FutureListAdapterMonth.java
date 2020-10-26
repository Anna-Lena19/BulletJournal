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

public class FutureListAdapterMonth extends RecyclerView.Adapter<RecyclerView.ViewHolder> {//old worked     Adapter<TaskViewHolder>

    TaskManager taskManager;

    //SQLite Tutorial
    private Context context;
    private Activity activity;
    private ArrayList task_id, task_description, task_date_ident, task_reflection, task_bullet_category, task_deadline, project_Ident, task_time_start, task_time_end;
    MyDatabaseHelper myDB;

    Integer count;

    private Fragment fragment;
    ArrayList<String> arrayListGroup;


    String projectIdent;
    Integer project_Int;
    String dateIdent;
    Integer dateIdent_Int;


    FutureListAdapterMonth( ArrayList<String> arrayListGroup, Activity activity, Context context, ArrayList book_id, ArrayList book_title, ArrayList book_author,
                           ArrayList book_pages, ArrayList task_bullet_category, ArrayList task_deadline, ArrayList project_Ident, ArrayList task_time_start, ArrayList task_time_end) {
        this.activity = activity;
        this.context = context;
        this.task_id = book_id;
        this.task_description = book_title;
        this.task_date_ident = book_author;
        this.task_reflection = book_pages;
        this.task_bullet_category = task_bullet_category;
        this.task_deadline = task_deadline;
        this.project_Ident = project_Ident;
        this.task_time_start = task_time_start;
        this.task_time_end = task_time_end;

        this.fragment = fragment;
        this.arrayListGroup = arrayListGroup;

        myDB = new MyDatabaseHelper(context);

        //set all images to reflection 0
    }
    //Ende

    //Delete?
   /* public TaskListAdapter(TaskManager taskManager){
        this.taskManager = taskManager;
    }*/


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//old worked     TaskViewHolder als return type
        TaskViewHolder taskViewHolder = new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false));//todo testen stadt parent.getContext(), context von oben verwenden.
        NoteViewHolder noteViewHolder = new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row, parent, false));
        EventViewHolder eventViewHolder = new EventViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row, parent, false));

        switch (viewType) {
            case 0:
                return taskViewHolder;
            case 1:
                return noteViewHolder;
            case 2:
                return eventViewHolder;
        }

        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        String dateIdent = task_date_ident.get(position).toString();
        Integer date_Ident_Int = Integer.parseInt(dateIdent);

        String projectIdent = project_Ident.get(position).toString();
        Integer project_Ident_Int = Integer.parseInt(projectIdent);

        if (date_Ident_Int == 1 && (project_Ident_Int == 0 || project_Ident_Int == 2)) {
            Log.d("reflect", "if author = daily is true author:" + dateIdent + " Title: " + task_description.get(position));
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            //set all images to reflection 0
            myDB.updateImage(String.valueOf(task_id.get(position)),"0");



            switch (holder.getItemViewType()) {
                case 0:
                    FutureListAdapterMonth.TaskViewHolder taskViewHolder = (FutureListAdapterMonth.TaskViewHolder) holder;

                    Log.d("SearchDeeper"," Show Futurelog moth task______   Name: " + task_description.get(position) + "  Deadline: " + task_deadline.get(position) + "  task_date_ident: " + task_date_ident.get(position));


                    //Set Items Text, Image
                    TextView textView = taskViewHolder.linearLayout.findViewById(R.id.task_Description_Display);
                    textView.setText(String.valueOf(task_description.get(position))); // task.getTaskDescription()

                    TextView taskDeadline = taskViewHolder.linearLayout.findViewById(R.id.task_deadline);
                    taskDeadline.setText("Deadline:\n");

                    if(task_deadline.get(position) != null){
                        taskDeadline.append("" +  task_deadline.get(position));
                    }


                    //Change ImageButton Number
                    final ImageButton imageButton = taskViewHolder.linearLayout.findViewById(R.id.task_imgButton);
                    showImage(position, imageButton);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            UpdateImage(position, imageButton);
                        }
                    });

                    //setTag id to delete by id
                    String id = String.valueOf(task_id.get(position));
                    int int_id = Integer.parseInt(id);
                    taskViewHolder.itemView.setTag(int_id);

                    //SQLite Tutorial Update To do
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openUpdateActivity(position);
                        }
                    });

                    if(project_Ident_Int == 0){
                        taskViewHolder.linearLayout.findViewById(R.id.task_private_uni).setVisibility(View.INVISIBLE);
                    }
                    break;
                case 1:
                    FutureListAdapterMonth.NoteViewHolder noteViewHolder = (FutureListAdapterMonth.NoteViewHolder) holder;

                    Log.d("SearchDeeper"," Show Futurelog moth note______   Name: " + task_description.get(position) + "  Deadline: " + task_deadline.get(position) + "  task_date_ident: " + task_date_ident.get(position));

                    //Set Items Text, Image
                    TextView textView_note = noteViewHolder.linearLayout.findViewById(R.id.note_Description_Display);
                    textView_note.setText(String.valueOf(task_description.get(position))); // task.getTaskDescription()

                    TextView noteDeadline = noteViewHolder.linearLayout.findViewById(R.id.note_deadline);
                     noteDeadline.setText("Deadline:\n");

                    if(task_deadline.get(position) != null){
                        noteDeadline.append("" + task_deadline.get(position));
                    }


                    //Change ImageButton Number
                    final ImageButton imageButton_note = noteViewHolder.linearLayout.findViewById(R.id.note_imgButton);
                    showImage(position, imageButton_note);
                    imageButton_note.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            UpdateImage(position, imageButton_note);
                        }
                    });

                    //setTag id to delete by id
                    String id_note = String.valueOf(task_id.get(position));
                    int int_id_note = Integer.parseInt(id_note);
                    noteViewHolder.itemView.setTag(int_id_note);//old worked     holder.itemView

                    //SQLite Tutorial Update To do
                    textView_note.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openUpdateActivity(position);
                        }
                    });
                    break;
                case 2:
                    FutureListAdapterMonth.EventViewHolder eventViewHolder = (FutureListAdapterMonth.EventViewHolder) holder;

                    Log.d("SearchDeeper"," Show Futurelog moth event______Reflection: " + task_reflection.get(position) + "  Name: " + task_description.get(position) + "  Deadline: " + task_deadline.get(position) + "  task_date_ident: " + task_date_ident.get(position));

                    //Set Items Text, Image
                    TextView textView_event = eventViewHolder.linearLayout.findViewById(R.id.event_Description_Display);
                    textView_event.setText(String.valueOf(task_description.get(position))); // task.getTaskDescription()

                    TextView eventDeadline = eventViewHolder.linearLayout.findViewById(R.id.event_deadline);
                    eventDeadline.setText("Deadline:\n");

                    if(task_deadline.get(position) != null){
                        eventDeadline.append("" + task_deadline.get(position));
                    }


                    //Change ImageButton Number
                    final ImageButton imageButton_event = eventViewHolder.linearLayout.findViewById(R.id.event_imgButton);
                    showImage(position, imageButton_event);
                    imageButton_event.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            UpdateImage(position, imageButton_event);
                        }
                    });

                    //setTag id to delete by id
                    String id_event = String.valueOf(task_id.get(position));
                    int int_id_event = Integer.parseInt(id_event);
                    eventViewHolder.itemView.setTag(int_id_event);

                    //SQLite Tutorial Update To do
                    textView_event.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openUpdateActivity(position);
                        }
                    });
                    break;
            }
        } else {
            Log.d("reflect", "if author = daily is false author:" + dateIdent + " Title: " + task_description.get(position));
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
    }


    @Override
    public int getItemCount() {

        return task_id.size();
    }


    @Override
    public int getItemViewType(int position) {
        String task_category = task_bullet_category.get(position).toString();
        Integer category = Integer.parseInt(task_category);

        Log.d("TaskView", "length of word :" + category);
        if (category == 0) {
            return 0;
        }
        if (category == 1) {
            return 1;
        }
        if (category == 2) {
            return 2;
        } else {
            Log.d("TaskView", "return 0 also Task in else :" + task_bullet_category.get(position));
            return 0;
        }
    }


    public void openUpdateActivity(int position) {
        Intent intent = new Intent(context, UpdateActivity.class);
        intent.putExtra("description", String.valueOf(task_description.get(position)));
        intent.putExtra("id", String.valueOf(task_id.get(position)));
        intent.putExtra("bulletIdent", String.valueOf(task_bullet_category.get(position)));
        intent.putExtra("deadline", String.valueOf(task_deadline.get(position)));
        activity.startActivityForResult(intent, 1);
    }


    public void UpdateImage(int position, ImageButton imgButton) {
        storeDataInArrays();

        String bulletNumber = String.valueOf(task_bullet_category.get(position));
        Integer bullet_note_task_event = Integer.parseInt(bulletNumber);

        String imageNumber = String.valueOf(task_reflection.get(position));
        Integer reflection_month_year = Integer.parseInt(imageNumber);
        Toast.makeText(context, "Position: " + position + "Number: " + reflection_month_year, Toast.LENGTH_SHORT).show();

        if(reflection_month_year == 0 && bullet_note_task_event == 0){
            myDB.updateImage(String.valueOf(task_id.get(position)),"1");
            imgButton.setImageResource(R.drawable.ic_task_migrated);
            //imgButton.setBackgroundColor(Color.parseColor("#272D33"));
        }
        else if(reflection_month_year == 0 && bullet_note_task_event == 1){
            myDB.updateImage(String.valueOf(task_id.get(position)),"1");
            imgButton.setImageResource(R.drawable.ic_event_migradet);
            //imgButton.setBackgroundColor(Color.parseColor("#c230ae"));
        }
        else if(reflection_month_year == 0 && bullet_note_task_event == 2){
            myDB.updateImage(String.valueOf(task_id.get(position)),"1");
            imgButton.setImageResource(R.drawable.ic_arrow_back);
            //imgButton.setBackgroundColor(Color.parseColor("#c230ae"));
        }
        else if(reflection_month_year == 1 && bullet_note_task_event == 0){
            myDB.updateImage(String.valueOf(task_id.get(position)),"2");
            imgButton.setImageResource(R.drawable.ic_task_schedulet);
            //imgButton.setBackgroundColor(Color.parseColor("#3fc7d1"));
        }
        else if(reflection_month_year == 1 && bullet_note_task_event == 1){
            myDB.updateImage(String.valueOf(task_id.get(position)),"2");
            imgButton.setImageResource(R.drawable.ic_event_schedulet);
            //imgButton.setBackgroundColor(Color.parseColor("#3fc7d1"));
        }
        else if(reflection_month_year == 1 && bullet_note_task_event == 2){
            myDB.updateImage(String.valueOf(task_id.get(position)),"2");
            imgButton.setImageResource(R.drawable.ic_arrow_back);
            //imgButton.setBackgroundColor(Color.parseColor("#3fc7d1"));
        }
        else if(reflection_month_year == 2 && bullet_note_task_event == 0){
            myDB.updateImage(String.valueOf(task_id.get(position)),"3");
            imgButton.setImageResource(R.drawable.ic_task_complete);
            //imgButton.setBackgroundColor(Color.parseColor("#3832a8"));
        }
        else if(reflection_month_year == 2 && bullet_note_task_event == 1){
            myDB.updateImage(String.valueOf(task_id.get(position)),"3");
            imgButton.setImageResource(R.drawable.ic_event_complete);
            //imgButton.setBackgroundColor(Color.parseColor("#3832a8"));
        }
        else if(reflection_month_year == 2 && bullet_note_task_event == 2){
            myDB.updateImage(String.valueOf(task_id.get(position)),"3");
            imgButton.setImageResource(R.drawable.ic_arrow_back);
            //imgButton.setBackgroundColor(Color.parseColor("#3832a8"));
        }
        else if(reflection_month_year == 3 && bullet_note_task_event == 0){
            myDB.updateImage(String.valueOf(task_id.get(position)),"4");
            imgButton.setImageResource(R.drawable.ic_task_irrelevant);
            //imgButton.setBackgroundColor(Color.parseColor("#3832a8"));
        }
        else if(reflection_month_year == 3 && bullet_note_task_event == 1){
            myDB.updateImage(String.valueOf(task_id.get(position)),"4");
            imgButton.setImageResource(R.drawable.ic_event_irrelevant);
            //imgButton.setBackgroundColor(Color.parseColor("#3832a8"));
        }
        else if(reflection_month_year == 3 && bullet_note_task_event == 2){
            myDB.updateImage(String.valueOf(task_id.get(position)),"4");
            imgButton.setImageResource(R.drawable.ic_arrow_back);
            //imgButton.setBackgroundColor(Color.parseColor("#3832a8"));
        }
        else if(reflection_month_year == 4 && bullet_note_task_event == 0){
            myDB.updateImage(String.valueOf(task_id.get(position)),"0");
            imgButton.setImageResource(R.drawable.ic_task);
            //imgButton.setBackgroundColor(Color.parseColor("#3832a8"));
        }
        else if(reflection_month_year == 4 && bullet_note_task_event == 1){
            myDB.updateImage(String.valueOf(task_id.get(position)),"0");
            imgButton.setImageResource(R.drawable.ic_event);
            //imgButton.setBackgroundColor(Color.parseColor("#3832a8"));
        }
        else if(reflection_month_year == 4 && bullet_note_task_event == 2){
            myDB.updateImage(String.valueOf(task_id.get(position)),"0");
            imgButton.setImageResource(R.drawable.ic_arrow_back);
            //imgButton.setBackgroundColor(Color.parseColor("#3832a8"));
        }
    }


    public void showImage(int position, ImageButton imgButton) {
        String imageNumber = String.valueOf(task_reflection.get(position));
        Integer reflection_month_year = Integer.parseInt(imageNumber);
        //Toast.makeText(context,"Position: " + position + "Number: " + imageNumber_Int,Toast.LENGTH_SHORT).show();

        String bulletNumber = String.valueOf(task_bullet_category.get(position));
        Integer bullet_note_task_event = Integer.parseInt(bulletNumber);

        if(reflection_month_year == 0 && bullet_note_task_event == 0){
            imgButton.setImageResource(R.drawable.ic_task);
            //imgButton.setBackgroundColor(Color.parseColor("#272D33"));
        }
        else if(reflection_month_year == 0 && bullet_note_task_event == 1){
            imgButton.setImageResource(R.drawable.ic_event);
            //imgButton.setBackgroundColor(Color.parseColor("#3832a8"));
        }
        else if(reflection_month_year == 0 && bullet_note_task_event == 2){
            imgButton.setImageResource(R.drawable.ic_arrow_back);
            //imgButton.setBackgroundColor(Color.parseColor("#3832a8"));
        }
        else if(reflection_month_year == 1 && bullet_note_task_event == 0){
            imgButton.setImageResource(R.drawable.ic_task_migrated);
            //imgButton.setBackgroundColor(Color.parseColor("#c230ae"));
        }
        else if(reflection_month_year == 1 && bullet_note_task_event == 1){
            imgButton.setImageResource(R.drawable.ic_event_migradet);
            //imgButton.setBackgroundColor(Color.parseColor("#c230ae"));
        }
        else if(reflection_month_year == 1 && bullet_note_task_event == 2){
            imgButton.setImageResource(R.drawable.ic_arrow_back);
            //imgButton.setBackgroundColor(Color.parseColor("#c230ae"));
        }
        else if(reflection_month_year == 2 && bullet_note_task_event == 0){
            imgButton.setImageResource(R.drawable.ic_task_schedulet);
            //imgButton.setBackgroundColor(Color.parseColor("#3fc7d1"));
        }
        else if(reflection_month_year == 2 && bullet_note_task_event == 1){
            imgButton.setImageResource(R.drawable.ic_event_schedulet);
            //imgButton.setBackgroundColor(Color.parseColor("#3fc7d1"));
        }
        else if(reflection_month_year == 2 && bullet_note_task_event == 2){
            imgButton.setImageResource(R.drawable.ic_arrow_back);
            //imgButton.setBackgroundColor(Color.parseColor("#3fc7d1"));
        }
        else if(reflection_month_year == 3 && bullet_note_task_event == 0){
            imgButton.setImageResource(R.drawable.ic_task_complete);
            //imgButton.setBackgroundColor(Color.parseColor("#3fc7d1"));
        }
        else if(reflection_month_year == 3 && bullet_note_task_event == 1){
            imgButton.setImageResource(R.drawable.ic_event_complete);
            //imgButton.setBackgroundColor(Color.parseColor("#3fc7d1"));
        }
        else if(reflection_month_year == 3 && bullet_note_task_event == 2){
            imgButton.setImageResource(R.drawable.ic_arrow_back);
            //imgButton.setBackgroundColor(Color.parseColor("#3fc7d1"));
        }
        else if(reflection_month_year == 4 && bullet_note_task_event == 0){
            imgButton.setImageResource(R.drawable.ic_task_irrelevant);
            //imgButton.setBackgroundColor(Color.parseColor("#3fc7d1"));
        }
        else if(reflection_month_year == 4 && bullet_note_task_event == 1){
            imgButton.setImageResource(R.drawable.ic_event_irrelevant);
            //imgButton.setBackgroundColor(Color.parseColor("#3fc7d1"));
        }
        else if(reflection_month_year == 4 && bullet_note_task_event == 2){
            imgButton.setImageResource(R.drawable.ic_arrow_back);
            //imgButton.setBackgroundColor(Color.parseColor("#3fc7d1"));
        }
    }


    void storeDataInArrays() {
        task_reflection.clear();
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            // empty_imageview.setVisibility(View.VISIBLE);
            //no_data.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                task_reflection.add(cursor.getString(3));
            }
            // empty_imageview.setVisibility(View.GONE);
            //no_data.setVisibility(View.GONE);
        }

    }


    //TaskViewHolder
    public class TaskViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;

        //SQLite Tutorial
        TextView book_id_txt, book_title_txt, book_author_txt, book_pages_txt, task_Description_Display;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            //book_id_txt = itemView.findViewById(R.id.book_id_txt);
            //book_title_txt = itemView.findViewById(R.id.book_title_txt);
            //book_author_txt = itemView.findViewById(R.id.book_author_txt);
            //book_pages_txt = itemView.findViewById(R.id.book_pages_txt);
            task_Description_Display = itemView.findViewById(R.id.task_Description_Display);

            linearLayout = itemView.findViewById(R.id.task_row_layout);//ftodo muss hier declariert werden?
        }

    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;

        //SQLite Tutorial
        TextView book_id_txt, book_title_txt, book_author_txt, book_pages_txt, task_Description_Display;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            //book_id_txt = itemView.findViewById(R.id.book_id_txt);
            //book_title_txt = itemView.findViewById(R.id.book_title_txt);
            //book_author_txt = itemView.findViewById(R.id.book_author_txt);
            //book_pages_txt = itemView.findViewById(R.id.book_pages_txt);
            task_Description_Display = itemView.findViewById(R.id.note_Description_Display);

            linearLayout = itemView.findViewById(R.id.note_row_layout);//ftodo muss hier declariert werden?
        }
    }


    public class EventViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;

        //SQLite Tutorial
        TextView book_id_txt, book_title_txt, book_author_txt, book_pages_txt, task_Description_Display;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            //book_id_txt = itemView.findViewById(R.id.book_id_txt);
            //book_title_txt = itemView.findViewById(R.id.book_title_txt);
            //book_author_txt = itemView.findViewById(R.id.book_author_txt);
            //book_pages_txt = itemView.findViewById(R.id.book_pages_txt);
            task_Description_Display = itemView.findViewById(R.id.event_Description_Display);

            linearLayout = itemView.findViewById(R.id.event_row_layout);//ftodo muss hier declariert werden?
        }
    }
}