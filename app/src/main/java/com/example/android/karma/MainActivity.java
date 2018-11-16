package com.example.android.karma;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity".getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setSelectionColor(getResources().getColor(R.color.colorPrimaryDark));
        try {
            Log.v(LOG_TAG, "onCreate: setElevation of the action bar");
            getSupportActionBar().setElevation(0f);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        List<EventDay> events = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH, 20);

        events.add(new EventDay(calendar, R.drawable.test_event));
        events.add(new EventDay(calendar1, R.drawable.test_event));

        Log.v(LOG_TAG, "onCreate: number of events" + events.size());
        calendarView.setEvents(events);

    }

}