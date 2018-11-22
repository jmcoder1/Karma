package com.example.android.karma;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private static final String LOG_TAG = "MainActivity".getClass().getSimpleName();

    private ActionBar mActionBar;
    private CalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCalendarView();
        initActionBar();

        List<EventDay> events = new ArrayList<>();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH, 12);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH, 16);

        events.add(new EventDay(calendar1, R.drawable.test_event));
        events.add(new EventDay(calendar2, R.drawable.test_event));

        mCalendarView.setEvents(events);
        Log.v(LOG_TAG, "onCreate: number of events added" + events.size());

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

    private void initCalendarView() {
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);

        mCalendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                Log.v(LOG_TAG, "initCalendarView: calendarView moved backwards");
                mActionBar.setTitle(mCalendarView.getCalendarTitleDate());
            }
        });

        mCalendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                Log.v(LOG_TAG, "initCalendarView: calendarView moved forward");
                mActionBar.setTitle(mCalendarView.getCalendarTitleDate());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_calendar_today:
                setCalendarToday();
                Log.v(LOG_TAG, "onOptionsItemSelected: clicked the calendar today icon.");
                return true;
            case R.id.action_hide_events:
                // TODO: open the hide events tab
                Log.v(LOG_TAG, "onOptionsItemSelected: clicked the hide events tab.");
                return true;
            case R.id.action_settings:
                // TODO: open the settings tab or shared preference tab
                Log.v(LOG_TAG, "onOptionsItemSelected: clicked the settings.");
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setCalendarToday() {
        Calendar cal = Calendar.getInstance();

        try {
            mCalendarView.setDate(cal);
        } catch(OutOfDateRangeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }
}