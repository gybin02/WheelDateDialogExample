package com.seeker.datedialog.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.seeker.demo.datedialogexample.R;
import com.seeker.datedialog.widget.HomeDateDialog;
import com.seeker.datedialog.widget.HomeDateDialogBuilder;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private HomeDateDialog dateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_show= (Button) findViewById(R.id.btn_show);
        assert btn_show != null;
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }


    private void showDialog() {
        try {
            Calendar calendarToday = Calendar.getInstance();
            Calendar calendarStart = (Calendar) calendarToday.clone();
            calendarStart.add(Calendar.DAY_OF_YEAR,-365);
            Calendar calendarEnd = (Calendar) calendarToday.clone();
            calendarEnd.add(Calendar.DAY_OF_YEAR,365);
            
            dateDialog = new HomeDateDialogBuilder().setContext(this)
                    .setTitle("选择日期")
                    .setStartCalendar(calendarStart)
                    .setEndCalendar(calendarEnd)
                    .setSelectCalendar(calendarToday)
                    .setState(HomeDateDialog.STATE_TODAY).setOnDialogResult(new HomeDateDialog.OnDialogResult() {
                        @Override
                        public void onSelectedResult(boolean bOk, Calendar calendar) {
                            if (bOk) {
                                //TODO
                            }
                        }

                        @Override
                        public void onScrollFinish(Calendar calendar) {

                        }
                    }).createHomeDateDialog();
            dateDialog.show();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
