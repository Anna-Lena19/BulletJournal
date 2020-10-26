package com.example.bulletjournal;

import android.animation.ArgbEvaluator;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SelfcareFragment extends Fragment {//implements View.OnClickListener

    Button button_timepicker, button_cancle_timepicker;

    Calendar mcurrentTime = Calendar.getInstance();
    final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
    final int minute = mcurrentTime.get(Calendar.MINUTE);

    Switch ballett_Switch,switch_zeichnen;




    //Chanels Notification$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    Button button_channel1, button_channel2;
    private NotificationHelper notificationHelper;
    //Chanels Notification$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

    //Chanels Notification with specific time $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    Button notifyDate;
    //Chanels Notification with specific time $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$


    //Chanels Notification tutorialspoint ########################################

    //Chanels Notification tutorialspoint ########################################






//ActionBarActivity''''''''''''''''''''''''''''''''''''''''''''
    private EditText etPercent;
    private ClipDrawable mImageDrawable;
    private Button okButton ;
    ImageButton addBallett, deleteBallett;

    private int countLevel = 0;

    // a field in your class
    private int mLevel = 0;
    private static int fromLevel = 0;
    private int toLevel = 0;

    public static final int MAX_LEVEL = 10000;
    public static final int LEVEL_DIFF = 100;
    public static final int DELAY = 30;

    private Handler mUpHandler = new Handler();
    private Runnable animateUpImage = new Runnable() {

        @Override
        public void run() {
            doTheUpAnimation(fromLevel, toLevel);
        }
    };

    private Handler mDownHandler = new Handler();
    private Runnable animateDownImage = new Runnable() {

        @Override
        public void run() {
            doTheDownAnimation(fromLevel, toLevel);
        }
    };
    //ActionBarActivity''''''''''''''''''''''''''''''''''''''''''''







    //SwipeViewPager??????????????????????????????????????????????????
    ViewPager viewPager;
    SelfcareAdapter adapter;
    List<String> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    TextView selfcare;
    //SwipeViewPager??????????????????????????????????????????????????



    RecyclerView gridRecycler;

    GoalManager goalManager;
    Button addNewGoal;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View selfcareView =  inflater.inflate(R.layout.fragment_selfcare, container, false);


        //button_timepicker = selfcareView.findViewById(R.id.button_timepicker);
        /*button_timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DialogFragment timepicker = new TimePickerFragment();
                //timepicker.show(getActivity().getSupportFragmentManager(), "time picker");

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, selectedHour);
                        c.set(Calendar.MINUTE,selectedMinute);
                        c.set(Calendar.SECOND, 0);

                        UpdateTimeText(c);
                        startAlarm(c);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        //button_cancle_timepicker = selfcareView.findViewById(R.id.button_cancle_timepicker);
        button_cancle_timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cacelAlarm();
            }
        });


*/
        /*ballett_Switch = selfcareView.findViewById(R.id.switch_ballett);
        ballett_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            Calendar c = Calendar.getInstance();
                            c.set(Calendar.HOUR_OF_DAY, selectedHour);
                            c.set(Calendar.MINUTE,selectedMinute);
                            c.set(Calendar.SECOND, 0);

                            UpdateTimeText(c);
                            startAlarm(c,"Ballett", "Learn Plies",1);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
                else{
                    cacelAlarm(1);
                }
            }
        });



        switch_zeichnen = selfcareView.findViewById(R.id.switch_zeichnen);
        switch_zeichnen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            Calendar c = Calendar.getInstance();
                            c.set(Calendar.HOUR_OF_DAY, selectedHour);
                            c.set(Calendar.MINUTE,selectedMinute);
                            c.set(Calendar.SECOND, 0);

                            UpdateTimeText(c);
                            startAlarm(c,"Zeichnen", "Kopf und Hände", 2);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
                else{
                    cacelAlarm(2);
                }
            }
        });

/*

        //Chanels Notification$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        //button_channel1 = selfcareView.findViewById(R.id.button_channel1);
        //button_channel1.setOnClickListener(this);
        //button_channel2 = selfcareView.findViewById(R.id.button_channel2);
        //button_channel2.setOnClickListener(this);

        notificationHelper = new NotificationHelper(getActivity());
        //Chanels Notification$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

        //Chanels Notification with specific time $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        createNotificationChannel();
        //notifyDate = selfcareView.findViewById(R.id.notifyDate);
        notifyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AlertReceiver.class);
                PendingIntent pendingIntentData = PendingIntent.getBroadcast(getActivity(),0, intent,0);
                AlarmManager alarmManagerData = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                //alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),pendingIntent);
                long tenSecondsInMillis = 1000 * 10;
                long timeAtButtonClick = System.currentTimeMillis();
                alarmManagerData.set(AlarmManager.RTC_WAKEUP,timeAtButtonClick + tenSecondsInMillis,pendingIntentData);
            }
        });
        //Chanels Notification with specific time $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$




        //Chanels Notification tutorialspoint ########################################
        notifyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                Intent intent = new Intent(getActivity().getApplicationContext(),AlertReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,myCalendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            }
        });


        //Chanels Notification tutorialspoint ########################################




*/

        //ActionBarActivity''''''''''''''''''''''''''''''''''''''''''''
        //etPercent = selfcareView.findViewById(R.id.etPercent);
        //okButton = selfcareView.findViewById(R.id.okButton);
        /*okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onClickOk();
            }
        });*/

       /* addBallett = selfcareView.findViewById(R.id.addBallett);
        addBallett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countLevel<=80){
                    countLevel += 20;
                    onClickOk(countLevel);
                }

            }
        });
        deleteBallett = selfcareView.findViewById(R.id.deleteBallett);
        deleteBallett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countLevel>= 20){
                    countLevel -= 20;
                    onClickOk(countLevel);
                }

            }
        });
        */

        //ImageView img = selfcareView.findViewById(R.id.imageView1);
        //mImageDrawable = (ClipDrawable) img.getDrawable();
        //mImageDrawable.setLevel(0);
        //ActionBarActivity''''''''''''''''''''''''''''''''''''''''''''








        //SwipeViewPager??????????????????????????????????????????????????
        models = new ArrayList<>();
        models.add("06:00   Wake Up");
        models.add("07:00   Take bus to Uni");
        models.add("13:00   Take a break");
        adapter = new SelfcareAdapter(models, getActivity());

        viewPager = selfcareView.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.test1),
                getResources().getColor(R.color.test2),
                getResources().getColor(R.color.test3),
                getResources().getColor(R.color.colorPrimaryDark)
        };

        colors = colors_temp;

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount() -1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }

                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        selfcare = selfcareView.findViewById(R.id.selfcare);
        selfcare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateProjectActivity.class);
                startActivity(intent);
            }
        });
        //SwipeViewPager??????????????????????????????????????????????????








        List<String> titles = new ArrayList<>();
        titles.add("Hello World");
        titles.add("Whats up");
        titles.add("To Show");
        titles.add("Good Night");

        goalManager = new GoalManager(getActivity());
        //todo get all goals from goalmanager




        gridRecycler = selfcareView.findViewById(R.id.recyclerview);
        SelfcareAdapterGrid adapter = new SelfcareAdapterGrid(getActivity(),goalManager,getActivity());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        gridRecycler.setLayoutManager(gridLayoutManager);
        gridRecycler.setAdapter(adapter);

        addNewGoal = selfcareView.findViewById(R.id.addNewGoal);
        addNewGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddGoalActivity.class);
                startActivity(intent);
            }
        });



        return selfcareView;
    }



   /* @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND, 0);

        UpdateTimeText(c);
        startAlarm(c);
    }*/

    private void startAlarm(Calendar c, String title, String message, Integer i) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        intent.putExtra("title",title);
        intent.putExtra("message",message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),i, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),pendingIntent);

        /*AlarmManager mgrAlarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        ArrayList<PendingIntent> intentArray = new ArrayList<>();

        for(int i = 0; i < 10; ++i)
        {
            Intent intent = new Intent(getActivity(), AlertReceiver.class);
            // Loop counter `i` is used as a `requestCode`
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), i, intent, 0);
            // Single alarms in 1, 2, ..., 10 minutes (in `i` minutes)
            mgrAlarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 60000 * i,
                    pendingIntent);

            intentArray.add(pendingIntent);
        }*/
    }


    private void UpdateTimeText(Calendar c) {
        String timeText = "alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        Log.d("houre", timeText);
    }

    public void cacelAlarm(Integer i){
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),i, intent, 0);

        alarmManager.cancel(pendingIntent);
        Log.d("houre", "Canceld this alarm");
    }



    /*@Override       //nur weil buttons ausgegraut waren, später wieder freigeben
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_channel1:
                sendOnChannel1("Ballett", "Learn Plies");
                break;
            case R.id.button_channel2:
                sendOnChannel2("Zeichnen", "Kopf und Hände");
                break;
        }
    }*/

    private void sendOnChannel1(String title, String message) {
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification(title,message);
        notificationHelper.getManager().notify(1,nb.build());
    }

    private void sendOnChannel2(String title, String message) {
        NotificationCompat.Builder nb = notificationHelper.getChannel2Notification(title,message);
        notificationHelper.getManager().notify(2,nb.build());
    }




    //Chanels Notification with specific time $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    private  void  createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "ReminderChannel";
            String description = "Channel for Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channelData = new NotificationChannel("notifySubmit",name,importance);
            channelData.setDescription(description);

            NotificationManager notificationManagerData = getActivity().getSystemService(NotificationManager.class);
            notificationManagerData.createNotificationChannel(channelData);
        }
    }
    //Chanels Notification with specific time $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$














    //ActionBarActivity''''''''''''''''''''''''''''''''''''''''''''
    private void doTheUpAnimation(int fromLevel, int toLevel) {
        mLevel += LEVEL_DIFF;
        mImageDrawable.setLevel(mLevel);
        if (mLevel <= toLevel) {
            mUpHandler.postDelayed(animateUpImage, DELAY);
        } else {
            mUpHandler.removeCallbacks(animateUpImage);
            SelfcareFragment.fromLevel = toLevel;
        }
    }

    private void doTheDownAnimation(int fromLevel, int toLevel) {
        mLevel -= LEVEL_DIFF;
        mImageDrawable.setLevel(mLevel);
        if (mLevel >= toLevel) {
            mDownHandler.postDelayed(animateDownImage, DELAY);
        } else {
            mDownHandler.removeCallbacks(animateDownImage);
            SelfcareFragment.fromLevel = toLevel;
        }
    }

    public void onClickOk(Integer height) {
        int temp_level = ( height * MAX_LEVEL) / 100; //(Integer.parseInt(etPercent.getText().toString()))

        if (toLevel == temp_level || temp_level > MAX_LEVEL) {
            return;
        }
        toLevel = (temp_level <= MAX_LEVEL) ? temp_level : toLevel;
        if (toLevel > fromLevel) {
            // cancel previous process first
            mDownHandler.removeCallbacks(animateDownImage);
            SelfcareFragment.fromLevel = toLevel;

            mUpHandler.post(animateUpImage);
        } else {
            // cancel previous process first
            mUpHandler.removeCallbacks(animateUpImage);
            SelfcareFragment.fromLevel = toLevel;

            mDownHandler.post(animateDownImage);
        }
    }
    //ActionBarActivity''''''''''''''''''''''''''''''''''''''''''''
}
