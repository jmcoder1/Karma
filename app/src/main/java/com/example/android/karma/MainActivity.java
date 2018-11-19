package com.example.android.karma;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnCalendarPageChangeListener {

    private static final String LOG_TAG = "MainActivity".getClass().getSimpleName();

    private ActionBar mActionBar;
    private CalendarView mCalendarView;

    @Override
    public void onChange() {
        Toast.makeText(getApplicationContext(), "Title change", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        //calendarView.setWeekDayBarColor(getResources().getColor(R.color.colorTest));


        List<EventDay> events = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH, 20);

        events.add(new EventDay(calendar, R.drawable.test_event));
        events.add(new EventDay(calendar1, R.drawable.test_event));

        Log.v(LOG_TAG, "onCreate: number of events" + events.size());
        mCalendarView.setEvents(events);

        initActionBar();

        mCalendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                mActionBar.setTitle(mCalendarView.getCalendarTitleDate());
            }
        });

        mCalendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                mActionBar.setTitle(mCalendarView.getCalendarTitleDate());
            }
        });


    }

    private void initActionBar() {
        mActionBar = getSupportActionBar();
        if(mActionBar != null) {
            Log.v(LOG_TAG, "initActionBar: initialising the Calendar activity action bar");
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

            try {
                mActionBar.setElevation(0f);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            mActionBar.setTitle(mCalendarView.getCalendarTitleDate());
        }
    }

}