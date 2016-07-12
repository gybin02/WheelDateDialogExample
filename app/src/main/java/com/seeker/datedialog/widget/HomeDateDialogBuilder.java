package com.seeker.datedialog.widget;

import android.app.Activity;

import java.util.Calendar;

public class HomeDateDialogBuilder {
    private Activity context;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private Calendar selectCalendar;
    private String title;
    private int state;
    private HomeDateDialog.OnDialogResult onDialogResult;
    HomeDateDialog homeDateDialog;

    public HomeDateDialogBuilder setContext(Activity context) {
        this.context = context;
        return this;
    }

    public HomeDateDialogBuilder setStartCalendar(Calendar startCalendar) {
        this.startCalendar = startCalendar;
        return this;
    }

    public HomeDateDialogBuilder setEndCalendar(Calendar endCalendar) {
        this.endCalendar = endCalendar;
        return this;
    }

    public HomeDateDialogBuilder setSelectCalendar(Calendar selectCalendar) {
        this.selectCalendar = selectCalendar;
        return this;
    }

    public HomeDateDialogBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public HomeDateDialogBuilder setState(int state) {
        this.state = state;
        return this;
    }

    public HomeDateDialogBuilder setOnDialogResult(HomeDateDialog.OnDialogResult onDialogResult) {
        this.onDialogResult = onDialogResult;
        return this;
    }

    public HomeDateDialog createHomeDateDialog() {
        if(homeDateDialog==null){
            homeDateDialog = new HomeDateDialog(context, startCalendar, endCalendar, selectCalendar, title, state, onDialogResult);
        }
        return homeDateDialog;
    }
}