package com.shrikanthravi.collapsiblecalendarview.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.shrikanthravi.collapsiblecalendarview.R;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.listener.OnSwipeTouchListener;
import com.shrikanthravi.collapsiblecalendarview.view.LockScrollView;


public abstract class UICalendar extends LinearLayout {


    // Day of Week
    public static final int SUNDAY    = 0;
    public static final int MONDAY    = 1;
    public static final int TUESDAY   = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY  = 4;
    public static final int FRIDAY    = 5;
    public static final int SATURDAY  = 6;
    // State
    public static final int STATE_EXPANDED   = 0;
    public static final int STATE_COLLAPSED  = 1;
    public static final int STATE_PROCESSING = 2;
    public static final int EVENT_DOT_BIG = 0;
    public static final int EVENT_DOT_SMALL = 1;

    protected Context mContext;
    protected LayoutInflater mInflater;

    // UI
    protected LinearLayout mLayoutRoot;
    protected TextView mTxtTitle;
    protected TableLayout mTableHead;
    protected LockScrollView mScrollViewBody;
    protected TableLayout mTableBody;

    // Attributes
    private boolean mShowWeek = true;
    private int mFirstDayOfWeek = SUNDAY;
    private int mState = STATE_COLLAPSED;

    private int mTextColor = Color.BLACK;
    private int mPrimaryColor = Color.WHITE;

    private int mTodayItemTextColor = Color.BLACK;

    private Drawable mTodayItemBackgroundDrawable =
            getResources().getDrawable(R.drawable.circle_black_stroke_background);

    private int mSelectedItemTextColor = Color.WHITE;
    private Drawable mSelectedItemBackgroundDrawable =
            getResources().getDrawable(R.drawable.selected_bg);

    private Day mSelectedItem = null;

    private int mEventColor = Color.BLACK;
    private int mEventDotSize = EVENT_DOT_BIG;

    public UICalendar(Context context) {
        this(context, null);
    }

    public UICalendar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UICalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.UICalendar, defStyleAttr, 0);
        setAttributes(attributes);
        attributes.recycle();
    }

    protected abstract void redraw();
    protected abstract void reload();

    protected void init(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);

        // load rootView from xml
        View rootView = mInflater.inflate(R.layout.widget_collapsible_calendarview, this, true);

        // init UI
        mLayoutRoot          = rootView.findViewById(R.id.layout_root);
        mTxtTitle            = rootView.findViewById(R.id.txt_title);

        mTableHead           = rootView.findViewById(R.id.table_head);
        mScrollViewBody      = rootView.findViewById(R.id.scroll_view_body);
        mTableBody           = rootView.findViewById(R.id.table_body);
    }

    protected void setAttributes(TypedArray attrs) {
        // set attributes by the values from XML
        //setStyle(attrs.getInt(R.styleable.UICalendar_style, mStyle));
        setShowWeek(attrs.getBoolean(R.styleable.UICalendar_showWeek, mShowWeek));
        setFirstDayOfWeek(attrs.getInt(R.styleable.UICalendar_firstDayOfWeek, mFirstDayOfWeek));
        setState(attrs.getInt(R.styleable.UICalendar_state, mState));

        setTextColor(attrs.getColor(R.styleable.UICalendar_textColor, mTextColor));
        setPrimaryColor(attrs.getColor(R.styleable.UICalendar_primaryColor, mPrimaryColor));

        setEventColor(attrs.getColor(R.styleable.UICalendar_eventColor, mEventColor));
        setEventDotSize(attrs.getInt(R.styleable.UICalendar_eventDotSize, mEventDotSize));

        setTodayItemTextColor(attrs.getColor(
                R.styleable.UICalendar_todayItem_textColor, mTodayItemTextColor));

        setSelectedItemTextColor(attrs.getColor(
                R.styleable.UICalendar_selectedItem_textColor, mSelectedItemTextColor));
        Drawable selectedItemBackgroundDrawable =
                attrs.getDrawable(R.styleable.UICalendar_selectedItem_background);
        if (selectedItemBackgroundDrawable != null) {
            setSelectedItemBackgroundDrawable(selectedItemBackgroundDrawable);
        } else {
            setSelectedItemBackgroundDrawable(mSelectedItemBackgroundDrawable);
        }
        Day selectedItem = null;
    }

    public boolean isShowWeek() {
        return mShowWeek;
    }

    public void setShowWeek(boolean showWeek) {
        this.mShowWeek = showWeek;

        if (showWeek) {
            mTableHead.setVisibility(VISIBLE);
        } else {
            mTableHead.setVisibility(GONE);
        }
    }

    public int getFirstDayOfWeek() {
        return mFirstDayOfWeek;
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) {
        this.mFirstDayOfWeek = firstDayOfWeek;
        reload();
    }

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
        redraw();

        mTxtTitle.setTextColor(mTextColor);
    }

    public int getPrimaryColor() {
        return mPrimaryColor;
    }

    public void setPrimaryColor(int primaryColor) {
        this.mPrimaryColor = primaryColor;
        redraw();

        mLayoutRoot.setBackgroundColor(mPrimaryColor);
    }

    private void setEventColor(int eventColor) {
        this.mEventColor = eventColor;
        redraw();

    }

    public void setSelectedItemBackgroundColor(int backgroundColor) {
        mSelectedItemBackgroundDrawable.mutate();
        mSelectedItemTextColor = backgroundColor;
        DrawableCompat.setTint(mSelectedItemBackgroundDrawable, backgroundColor);
        redraw();
    }

    private void setEventDotSize(int eventDotSize) {
        this.mEventDotSize = eventDotSize;
        redraw();

    }

    public int getEventDotSize() {
        return mEventDotSize;

    }

    public int getEventColor() {
        return mEventColor;
    }

    public int getTodayItemTextColor() {
        return mTodayItemTextColor;
    }

    public void setTodayItemTextColor(int todayItemTextColor) {
        this.mTodayItemTextColor = todayItemTextColor;
        redraw();
    }

    public Drawable getTodayItemBackgroundDrawable() {
        return mTodayItemBackgroundDrawable;
    }

    public void setTodayItemBackgroundDrawable(Drawable todayItemBackgroundDrawable) {
        this.mTodayItemBackgroundDrawable = todayItemBackgroundDrawable;
        redraw();
    }

    public int getSelectedItemTextColor() {
        return mSelectedItemTextColor;
    }

    public void setSelectedItemTextColor(int selectedItemTextColor) {
        this.mSelectedItemTextColor = selectedItemTextColor;
        redraw();
    }

    public Drawable getSelectedItemBackgroundDrawable() {
        return mSelectedItemBackgroundDrawable;
    }

    public void setSelectedItemBackgroundDrawable(Drawable selectedItemBackground) {
        this.mSelectedItemBackgroundDrawable = selectedItemBackground;
        redraw();
    }

    public Day getSelectedItem() {
        return mSelectedItem;
    }

    public void setSelectedItem(Day selectedItem) {
        this.mSelectedItem = selectedItem;
    }

}
