package com.example.bulletjournal;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
