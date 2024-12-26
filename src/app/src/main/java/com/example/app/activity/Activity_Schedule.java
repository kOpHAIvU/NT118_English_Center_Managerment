package com.example.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app.R;
import com.example.app.adapter.AccountDAO;
import com.example.app.adapter.ScheduleDAO;
import com.example.app.model.List_Adapter;
import com.example.app.model.ScheduleDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Activity_Schedule extends AppCompatActivity {

    private ListView classListView;
    private ArrayList<Object> dataArrayList;
    private List_Adapter listAdapter;
    TextView noInfoTextView;
    TextView dateTextView;
    private Button selectedButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_schedule);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        noInfoTextView = findViewById(R.id.no_info_text_view);
        dateTextView = findViewById(R.id.text_view_date);
        classListView = findViewById(R.id.class_list_view);
        dataArrayList = new ArrayList<>();

        setupButtonListeners();
        selectDefaultButton();
    }

    private void selectDefaultButton() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        String dateString = "Thứ " + dayOfWeek +  ", Ngày "+ day + " Tháng " + month + " Năm " + year;
        dateTextView.setText(dateString);

        switch (dayOfWeek) {
            case Calendar.MONDAY:
                selectButton(findViewById(R.id.button_monday));
                displayClasses("2");
                break;
            case Calendar.TUESDAY:
                selectButton(findViewById(R.id.button_tuesday));
                displayClasses("3");
                break;
            case Calendar.WEDNESDAY:
                selectButton(findViewById(R.id.button_wednesday));
                displayClasses("4");
                break;
            case Calendar.THURSDAY:
                selectButton(findViewById(R.id.button_thursday));
                displayClasses("5");
                break;
            case Calendar.FRIDAY:
                selectButton(findViewById(R.id.button_friday));
                displayClasses("6");
                break;
            case Calendar.SATURDAY:
                selectButton(findViewById(R.id.button_saturday));
                displayClasses("7");
                break;
            case Calendar.SUNDAY:
                selectButton(findViewById(R.id.button_sunday));
                displayClasses("8");
                break;
        }
    }
    private void selectButton(Button button) {
        if (selectedButton != null) {
            selectedButton.setBackgroundResource(R.drawable.button_selector);
            selectedButton.setTextColor(getResources().getColor(android.R.color.black));
        }

        selectedButton = button;

        button.setBackgroundResource(R.drawable.button_background_pressed);
        button.setTextColor(getResources().getColor(android.R.color.white));
    }
    private void setupButtonListeners() {
        Button buttonMonday = findViewById(R.id.button_monday);
        Button buttonTuesday = findViewById(R.id.button_tuesday);
        Button buttonWednesday = findViewById(R.id.button_wednesday);
        Button buttonThursday = findViewById(R.id.button_thursday);
        Button buttonFriday = findViewById(R.id.button_friday);
        Button buttonSaturday = findViewById(R.id.button_saturday);
        Button buttonSunday = findViewById(R.id.button_sunday);

        buttonMonday.setOnClickListener(v -> {
            selectButton(buttonMonday);
            displayClasses("2");
        });
        buttonTuesday.setOnClickListener(v -> {
            selectButton(buttonTuesday);
            displayClasses("3");
        });
        buttonWednesday.setOnClickListener(v -> {
            selectButton(buttonWednesday);
            displayClasses("4");
        });
        buttonThursday.setOnClickListener(v -> {
            selectButton(buttonThursday);
            displayClasses("5");
        });
        buttonFriday.setOnClickListener(v -> {
            selectButton(buttonFriday);
            displayClasses("6");
        });
        buttonSaturday.setOnClickListener(v -> {
            selectButton(buttonSaturday);
            displayClasses("7");
        });
        buttonSunday.setOnClickListener(v -> {
            selectButton(buttonSunday);
            displayClasses("8");
        });
    }

    private void displayClasses(String day) {
        dataArrayList.clear();

        int type = AccountDAO.getInstance(Activity_Schedule.this).GetObjectLogin(Activity_Schedule.this,
                Activity_Login.username, Activity_Login.password);

        List<ScheduleDTO> listSchedule = ScheduleDAO.getInstance(Activity_Schedule.this)
                .SelectScheduleByIdStudent(Activity_Schedule.this, Activity_Login.idUser , type);

        for (ScheduleDTO schedule : listSchedule) {
            if (schedule.getDayOfWeek().equalsIgnoreCase(day)) {
                dataArrayList.add(schedule);
            }
        }

        if (dataArrayList.isEmpty()) {
            classListView.setVisibility(View.GONE);
            noInfoTextView.setVisibility(View.VISIBLE);
        } else {
            classListView.setVisibility(View.VISIBLE);
            noInfoTextView.setVisibility(View.GONE);
            listAdapter = new List_Adapter(Activity_Schedule.this, R.layout.list_schedule_item, dataArrayList);
            classListView.setAdapter(listAdapter);
        }
    }
}