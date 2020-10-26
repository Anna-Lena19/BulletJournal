package com.example.bulletjournal;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class CalendarFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View calendarView = inflater.inflate(R.layout.fragment_calendar, container, false);

        TextView textView = calendarView.findViewById(R.id.testShadow);
        //textView.setOutlineSpotShadowColor(Color.RED);


        return calendarView;
    }
}
