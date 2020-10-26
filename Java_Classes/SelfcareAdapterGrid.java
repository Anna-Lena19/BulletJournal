package com.example.bulletjournal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SelfcareAdapterGrid extends RecyclerView.Adapter<SelfcareAdapterGrid.ViewHolder>{
    List<String> titles;
    List<Integer> images;
    LayoutInflater inflater;
    GoalManager goalManager;
    Context context;
    Activity activity;

    public SelfcareAdapterGrid(Context context, GoalManager goalManager, Activity activity){
        this.titles = titles;
        this.goalManager = goalManager;
        //this.images = images;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.goal_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Goal goal = goalManager.getGoalList().get(position);
        holder.title.setText(goal.getYearlyGoal());
        //holder.gridIcon.setImageResource(images.get(position));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"clicked on " + position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,GoalDetails.class);
                intent.putExtra("yearlyGoal",goal.getYearlyGoal());
                intent.putExtra("monthlyGoal",goal.getMonthlyGoal());
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo open galerie to add image
            }
        });
    }

    @Override
    public int getItemCount() {
        return goalManager.getGoalCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        CardView cardView;
        ImageButton addImage;
        //ImageView gridIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.setText);
            cardView = itemView.findViewById(R.id.cardViewGoal);
            addImage = itemView.findViewById(R.id.addImage);
            //gridIcon = itemView.findViewById(R.id.imageView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked -> " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
