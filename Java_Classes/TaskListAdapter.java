package com.example.bulletjournal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {//old worked     Adapter<TaskViewHolder>

    TaskManager taskManager;

    //SQLite Tutorial
    private Context context;
    private Activity activity;
    private ArrayList task_id, task_description, task_date_ident, task_reflection, task_bullet_category, task_deadline, project_Ident, task_time_start, task_time_end;
    MyDatabaseHelper myDB;

    Integer count;

    String projectIdent;
    Integer project_Int;
    String dateIdent;
    Integer dateIdent_Int;


    TaskListAdapter( Activity activity, Context context, ArrayList task_id, ArrayList task_description, ArrayList task_date_ident,
                  ArrayList task_reflection, ArrayList task_bullet_category, ArrayList task_deadline, ArrayList project_Ident, ArrayList task_time_start, ArrayList task_time_end){
        this.activity = activity;
        this.context = context;
        this.task_id = task_id;
        this.task_description = task_description;
        this.task_date_ident = task_date_ident;
        this.task_reflection = task_reflection;
        this.task_bullet_category = task_bullet_category;
        this.task_deadline = task_deadline;
        this.project_Ident = project_Ident;
        this.task_time_start = task_time_start;
        this.task_time_end = task_time_end;

        myDB = new MyDatabaseHelper(context);
    }
    //Ende

    //Delete?
   /* public TaskListAdapter(TaskManager taskManager){
        this.taskManager = taskManager;
    }*/


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//old worked     TaskViewHolder als return type
        TaskViewHolder  taskViewHolder = new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row,parent,false));//todo testen stadt parent.getContext(), context von oben verwenden.
        NoteViewHolder noteViewHolder = new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row,parent,false));
        EventViewHolder eventViewHolder = new EventViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row,parent,false));

        switch (viewType){
            case 0: return taskViewHolder;
            case 1: return noteViewHolder;
            case 2: return eventViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        Log.d("sizeOf", "size of taskdateident: "+ task_date_ident.size() + " size of projectIdent: "+ project_Ident.size() + " position: " + position);
        Log.d("sizeOf", "gleich 0: " + (project_Ident.get(position) == "0") + " gleich null: " + (project_Ident.get(position) == null));
        if(task_date_ident.size() > 0){
            dateIdent = task_date_ident.get(position).toString();
            dateIdent_Int = Integer.parseInt(dateIdent);
        }


        if(project_Ident.size() > 0|| project_Ident.get(position) != null){
            projectIdent = project_Ident.get(position).toString();
            project_Int = Integer.parseInt(projectIdent);
        }


        if(dateIdent_Int == 0 && (project_Int == 0 || project_Int == 2)){
            Log.d("reflect", "if author = daily is true author:" + dateIdent + " Title: " + task_description.get(position) );
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            //set all images to reflection 0
            myDB.updateImage(String.valueOf(task_id.get(position)),"0");
            

            switch (holder.getItemViewType()){
                case 0: TaskViewHolder taskViewHolder = (TaskViewHolder)holder;

                    Log.d("SearchDeeper"," Show  daily task______   Name: " + task_description.get(position) + "  Deadline: " + task_deadline.get(position) + "  task_date_ident: " + task_date_ident.get(position));

                    //Set Items Text, Image
                    TextView textView = taskViewHolder.linearLayout.findViewById(R.id.task_Description_Display);
                    textView.setText(String.valueOf(task_description.get(position))); // task.getTaskDescription()
                    TextView textView1 = taskViewHolder.linearLayout.findViewById(R.id.task_time_output);

                    TextView textView_Deadline =taskViewHolder.linearLayout.findViewById(R.id.task_deadline);
                    textView_Deadline.setText("Deadline:\n");

                            Log.d("reloader", " taskTimeStrat: "+ task_time_start.get(position));
                    if(task_time_start.size() > 0 && (task_time_start.get(position) != null)){
                        Log.d("reloader", " taskTimeStrat.size: "+ task_time_start.size());

                        SpannableString string = new SpannableString(String.valueOf(task_time_start.get(position)));
                        string.setSpan(new BackgroundColorSpan(Color.parseColor("#66c230ae")), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textView1.setText(string);

                        SpannableString string2 = new SpannableString("" + task_time_end.get(position));
                        string2.setSpan(new BackgroundColorSpan(Color.parseColor("#66c230ae")), 0, string2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textView1.append("\n   -\n");
                        textView1.append(string2);

                    }
                    if(task_deadline.get(position) != null){
                        textView_Deadline.append("" + task_deadline.get(position));//todo **** new Content copy in  note, event down and in all Adapter FutureAdaper...
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
                    int int_id= Integer.parseInt(id);
                    taskViewHolder.itemView.setTag(int_id);

                    //SQLite Tutorial Update To do
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openUpdateActivity(position);
                        }
                    });

                    if(project_Int == 0){
                        taskViewHolder.linearLayout.findViewById(R.id.task_private_uni).setVisibility(View.INVISIBLE);
                    }
                    break;
                case 1: NoteViewHolder noteViewHolder = (NoteViewHolder)holder;

                    Log.d("SearchDeeper"," Show  daily note______   Name: " + task_description.get(position) + "  Deadline: " + task_deadline.get(position) + "  task_date_ident: " + task_date_ident.get(position));

                    //Set Items Text, Image
                    TextView textView_note = noteViewHolder.linearLayout.findViewById(R.id.note_Description_Display);
                    textView_note.setText(String.valueOf(task_description.get(position))); // task.getTaskDescription()
                     TextView deadline_note = noteViewHolder.linearLayout.findViewById(R.id.note_deadline);
                    deadline_note.setText("Deadline:\n");

                    TextView textView_note2 =((NoteViewHolder) holder).task_time_output;

                    Log.d("reloader", " taskTimeStrat: "+ task_time_start.get(position));
                    if(task_time_start.size() > 0 && (task_time_start.get(position) != null)){
                        Log.d("reloader", " taskTimeStrat.size: "+ task_time_start.size());

                        SpannableString string = new SpannableString(String.valueOf(task_time_start.get(position)));
                        string.setSpan(new BackgroundColorSpan(Color.parseColor("#66c230ae")), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textView_note2.setText(string);

                        SpannableString string2 = new SpannableString("" + task_time_end.get(position));
                        string2.setSpan(new BackgroundColorSpan(Color.parseColor("#66c230ae")), 0, string2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textView_note2.append("\n   -\n");
                        textView_note2.append(string2);
                    }
                    if(task_deadline.get(position) != null) {
                        deadline_note.append("" + task_deadline.get(position));
                    }




                    //Change ImageButton Number
                    final ImageButton imageButton_note = noteViewHolder.linearLayout.findViewById(R.id.note_imgButton);
                    showImage(position,imageButton_note);
                    imageButton_note.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            UpdateImage(position,imageButton_note);
                        }
                    });

                    //setTag id to delete by id
                    String id_note = String.valueOf(task_id.get(position));
                    int int_id_note= Integer.parseInt(id_note);
                    noteViewHolder.itemView.setTag(int_id_note);//old worked     holder.itemView

                    //SQLite Tutorial Update To do
                    textView_note.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openUpdateActivity(position);
                        }
                    });
                    break;
                case 2: EventViewHolder eventViewHolder = (EventViewHolder)holder;

                    Log.d("SearchDeeper"," Show  daily event______   Name: " + task_description.get(position) + "  Deadline: " + task_deadline.get(position) + "  task_date_ident: " + task_date_ident.get(position));

                    //Set Items Text, Image
                    TextView textView_event = eventViewHolder.linearLayout.findViewById(R.id.event_Description_Display);
                    textView_event.setText(String.valueOf(task_description.get(position))); // task.getTaskDescription()
                    TextView textView_event2 = eventViewHolder.linearLayout.findViewById(R.id.event_deadline);
                    textView_event2.setText("Deadline:\n");

                    TextView timeOutputEvent = eventViewHolder.linearLayout.findViewById(R.id.event_time_output);

                    Log.d("reloader", " taskTimeStrat: "+ task_time_start.get(position));
                    if(task_time_start.size() > 0 && (task_time_start.get(position) != null)){
                        Log.d("reloader", " taskTimeStrat.size: "+ task_time_start.size());

                        SpannableString string = new SpannableString(String.valueOf(task_time_start.get(position)));
                        string.setSpan(new BackgroundColorSpan(Color.parseColor("#66c230ae")), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        timeOutputEvent.setText(string);

                        SpannableString string2 = new SpannableString("" + task_time_end.get(position));
                        string2.setSpan(new BackgroundColorSpan(Color.parseColor("#66c230ae")), 0, string2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        timeOutputEvent.append("\n   -\n");
                        timeOutputEvent.append(string2);
                    }
                    if(task_deadline.get(position) != null) {
                        textView_event2.append("" + task_deadline.get(position));
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
                    int int_id_event= Integer.parseInt(id_event);
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
        }
        else{
            Log.d("reflect", "if author = daily is false author:" + dateIdent + " Title: " + task_description.get(position) );
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
        }
    }

   /* @Override  old worked
    public void onBindViewHolder(@NonNull TaskViewHolder holder, final int position) {
        //Display Tasks
        //Task task = taskManager.getTaskList().get(position);
        TextView textView = holder.linearLayout.findViewById(R.id.task_Description_Display);
        textView.setText(String.valueOf(book_title.get(position))); // task.getTaskDescription()

        //setTag id to delete by id
        String id = String.valueOf(book_id.get(position));
        int int_id= Integer.parseInt(id);
        holder.itemView.setTag(int_id);

        //SQLite Tutorial
       // holder.book_id_txt.setText(String.valueOf(book_id.get(position)));
        //holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
       // holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
       // holder.book_pages_txt.setText(String.valueOf(book_pages.get(position)));


        //SQLite Tutorial Update To do
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("title",String.valueOf(book_title.get(position)));
                intent.putExtra("id",String.valueOf(book_id.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });
    }*/

    @Override
    public int getItemCount() {

        //taskManager.getTaskCount();
        count=0;
        //SQLite Tutorial
        for(int i = 0; i< task_date_ident.size(); i++){
            String author = task_date_ident.get(i).toString();
            Integer auhthor_int = Integer.parseInt(author);
            if(auhthor_int == 0){
                count ++;
            }

        }
        //Log.d("reflect", " count: " + count + " bokkid.size: " + task_id.size());
        return task_id.size();
    }


    @Override
    public int getItemViewType(int position) {
        String task_category = task_bullet_category.get(position).toString();
        Integer category = Integer.parseInt(task_category);

            Log.d("TaskView", "length of word :" + category);
            if(category == 0){
                return 0;
            }
            if (category == 1){
                return 1;
            }
            if(category ==2){
                return 2;
            }
            else{Log.d("TaskView", "return 0 also Task in else :" + task_bullet_category.get(position) );
                return 0;
            }
    }



    public void openUpdateActivity(int position){
        Intent intent = new Intent(context, UpdateActivity.class);
        intent.putExtra("description",String.valueOf(task_description.get(position)));
        intent.putExtra("id",String.valueOf(task_id.get(position)));
        intent.putExtra("bulletIdent",String.valueOf(task_bullet_category.get(position)));
        intent.putExtra("deadline",String.valueOf(task_deadline.get(position)));
        intent.putExtra("time_start",String.valueOf(task_time_start.get(position)));
        intent.putExtra("time_end",String.valueOf(task_time_end.get(position)));
        activity.startActivityForResult(intent,1);
    }



    public void UpdateImage(int position, ImageButton imgButton){
        storeDataInArrays();

        String bulletNumber = String.valueOf(task_bullet_category.get(position));
        Integer bullet_note_task_event = Integer.parseInt(bulletNumber);

        String imageNumber = String.valueOf(task_reflection.get(position));
        Integer reflection_month_year = Integer.parseInt(imageNumber);
        Toast.makeText(context,"Position: " + position + "Number: " + reflection_month_year,Toast.LENGTH_SHORT).show();

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



    public void showImage(int position, ImageButton imgButton){
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



    void storeDataInArrays(){
        task_reflection.clear();
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            // empty_imageview.setVisibility(View.VISIBLE);
            //no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
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
        TextView book_id_txt, book_title_txt, book_author_txt, book_pages_txt, task_Description_Display, task_time_output;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            //book_id_txt = itemView.findViewById(R.id.book_id_txt);
            //book_title_txt = itemView.findViewById(R.id.book_title_txt);
            //book_author_txt = itemView.findViewById(R.id.book_author_txt);
            //book_pages_txt = itemView.findViewById(R.id.book_pages_txt);
            task_Description_Display = itemView.findViewById(R.id.task_Description_Display);
            task_time_output = itemView.findViewById(R.id.task_time_output);

            linearLayout = itemView.findViewById(R.id.task_row_layout);//ftodo muss hier declariert werden?
        }

    }




        public class NoteViewHolder extends RecyclerView.ViewHolder {

            LinearLayout linearLayout;

            //SQLite Tutorial
            TextView book_id_txt, book_title_txt, book_author_txt, book_pages_txt, task_Description_Display, task_time_output;


            public NoteViewHolder(@NonNull View itemView) {
                super(itemView);

                //book_id_txt = itemView.findViewById(R.id.book_id_txt);
                //book_title_txt = itemView.findViewById(R.id.book_title_txt);
                //book_author_txt = itemView.findViewById(R.id.book_author_txt);
                //book_pages_txt = itemView.findViewById(R.id.book_pages_txt);
                task_Description_Display = itemView.findViewById(R.id.note_Description_Display);
                task_time_output = itemView.findViewById(R.id.note_time_output);

                linearLayout = itemView.findViewById(R.id.note_row_layout);//ftodo muss hier declariert werden?
            }
        }







        public class EventViewHolder extends RecyclerView.ViewHolder {

            LinearLayout linearLayout;

            //SQLite Tutorial
            TextView book_id_txt, book_title_txt, book_author_txt, book_pages_txt, task_Description_Display, task_time_output;


            public EventViewHolder(@NonNull View itemView) {
                super(itemView);

                //book_id_txt = itemView.findViewById(R.id.book_id_txt);
                //book_title_txt = itemView.findViewById(R.id.book_title_txt);
                //book_author_txt = itemView.findViewById(R.id.book_author_txt);
                //book_pages_txt = itemView.findViewById(R.id.book_pages_txt);
                task_Description_Display = itemView.findViewById(R.id.event_Description_Display);
                task_time_output = itemView.findViewById(R.id.event_deadline);

                linearLayout = itemView.findViewById(R.id.event_row_layout);//ftodo muss hier declariert werden?
            }
        }

}
