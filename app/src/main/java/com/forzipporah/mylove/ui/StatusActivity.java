package com.forzipporah.mylove.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.forzipporah.mylove.R;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy");
        try {
            Date relationShipStart = simpleDateFormat.parse("04/11/2012");
            Date current = new Date();
            String duration = printDifference(relationShipStart, current);
            TextView together = (TextView) findViewById(R.id.togetherForTV);
            together.setText(duration);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public String printDifference(Date startDate, Date endDate) {
        Period period = new Period(startDate.getTime(), endDate.getTime(), PeriodType.yearMonthDayTime());

        return period.getYears() + " years  " + period.getMonths() + " months " + period.getDays() + " days " + period.getHours() + " hours ";
    }
}
