package com.example.android.karma;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CollapsibleCalendar collapsibleCalendar = findViewById(R.id.collapsibleCalendarView);
        Calendar today = new GregorianCalendar();

        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH));
        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH), Color.BLUE);

        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect() {
            }

            @Override
            public void onItemClick(View v) {

            }

            @Override
            public void onDataUpdate() {

            }

            @Override
            public void onMonthChange() {
                collapsibleCalendar.setSelectedItemBackgroundColor(getResources().getColor(R.color.colorAccent ));

            }

            @Override
            public void onWeekChange(int position) {

            }
        });
    }
}