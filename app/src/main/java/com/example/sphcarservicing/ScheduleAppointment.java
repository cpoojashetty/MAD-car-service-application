package com.example.sphcarservicing;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class ScheduleAppointment extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_appointment);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String SP_ID = preferences.getString("SP_ID",null);
        String SP_EMAIL = preferences.getString("SP_EMAIL",null);
        String UEMAIL = preferences.getString("EMAIL",null);
        String SERVICES = preferences.getString("SERVICES",null);
        String SP_NAME = preferences.getString("SP_NAME",null);

        dbh = new DatabaseHelper(this);


        Button buttonBookAppointment = findViewById(R.id.buttonBookAppointment);
        CalendarView calendarView = findViewById(R.id.calendarView);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        final String[] currdate = new String[1];
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
//                currdate[0] = i2 + "/" + (i1+1) + "/" + i; //dd//mm//yyyy

                String selectedDate = String.format("%d-%02d-%02d", i, i1 + 1, i2);
// Get the current date
                Calendar currentDate = Calendar.getInstance();
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                currentDate.set(Calendar.MILLISECOND, 0);

                // Get the selected date
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(i, i1, i2);

                // Compare the selected date with the current date
                if (selectedCalendar.before(currentDate)) {
                    Toast.makeText(ScheduleAppointment.this, "Cannot select a past date", Toast.LENGTH_SHORT).show();
                    // Clear the selected date
                    calendarView.setDate(currentDate.getTimeInMillis());
                    return;
                }

                currdate[0] = selectedDate;

//                currdate[0] = i + "-" + (i1+1) + "-" + i2; //yyyy//mm//dd
                Log.d(TAG, "onSelectedDayChange : "+currdate[0]);

            }
        });


        buttonBookAppointment.setOnClickListener(new View.OnClickListener() {
            String btype;
            boolean isInserted;
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);

//                Toast.makeText(ScheduleAppointment.this,"Booking Date is "+currdate[0]
//                        +
//                        " and Service Type "+
//                        radioButton.getText().toString(),Toast.LENGTH_LONG).show();

                btype = radioButton.getText().toString();


                Log.d(TAG,"SP : "+SP_EMAIL + " UEMAIL : "+UEMAIL
                + " BTYPE : " + radioButton.getText().toString() + " BDATE : "+
                        currdate[0]+ " BSERVICES: "+SERVICES+ " SP_ID : "+ SP_ID);

                isInserted = dbh.addBookingData(SP_EMAIL,UEMAIL,btype,currdate[0],SERVICES,SP_ID);

                if(isInserted){
                    Toast.makeText(ScheduleAppointment.this,"You appointment has been" +
                            "booked!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ScheduleAppointment.this,UserHome.class));
                }
                else {
                    Toast.makeText(ScheduleAppointment.this,"Sorry not booked," +
                            "try again later!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ScheduleAppointment.this,UserHome.class));
                }
            }
        });


    }
}