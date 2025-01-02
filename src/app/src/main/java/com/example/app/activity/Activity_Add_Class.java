package com.example.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.datepicker.MaterialDatePicker;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.ClassDAO;
import com.example.app.adapter.ProgramDAO;
import com.example.app.adapter.StaffDAO;
import com.example.app.adapter.TeacherDAO;
import com.example.app.model.ClassDTO;
import com.example.app.model.ProgramDTO;
import com.example.app.model.StaffDTO;
import com.example.app.model.TeacherDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class Activity_Add_Class extends AppCompatActivity {
    EditText classID, className, idTeacher, staffID;
    TextView startDate, endDate;
    Button exitBtn, doneBtn;
    String[] programIDItem;
    AutoCompleteTextView program;
    ArrayAdapter<String> programAdapter;
    DatePickerDialog.OnDateSetListener startDt, endDt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        String message = getIntent().getStringExtra("classID");

        List<ProgramDTO> listProgramAdapter = ProgramDAO.getInstance(Activity_Add_Class.this)
                .SelectProgram(Activity_Add_Class.this, "STATUS = ?", new String[] {"0"});
        programIDItem = new String[listProgramAdapter.size()];
        for (int i = 0; i < listProgramAdapter.size(); i++) {
            programIDItem[i] = listProgramAdapter.get(i).getNameProgram();
        }

        program = findViewById(R.id.programID);
        programAdapter = new ArrayAdapter<>(this, R.layout.combobox_item, programIDItem);
        program.setAdapter(programAdapter);

        startDate = findViewById(R.id.start_date);
        startDate.setOnClickListener(v -> showDatePicker(true));

        startDt = (view, year, month, dayOfMonth) -> {
            month++; // Tháng bắt đầu từ 0
            startDate.setText(String.format("%02d/%02d/%d", dayOfMonth, month, year));
        };

        endDate = findViewById(R.id.end_date);
        endDate.setOnClickListener(v -> showDatePicker(false));

        endDt = (view, year, month, dayOfMonth) -> {
            month++; // Tháng bắt đầu từ 0
            endDate.setText(String.format("%02d/%02d/%d", dayOfMonth, month, year));
        };

        className = findViewById(R.id.class_name);
        idTeacher = findViewById(R.id.idTeacher);
        staffID = findViewById(R.id.staffID);
        exitBtn = findViewById(R.id.exit_btn);
        doneBtn = findViewById(R.id.done_btn);

        List<ClassDTO> listClass = ClassDAO.getInstance(Activity_Add_Class.this).selectClass(
                Activity_Add_Class.this, "ID_CLASS = ? AND STATUS = ?", new String[] {message, "0"});

        if (!message.equals("")) {
            setupExistingClassData(listClass);
        } else {
            setupNewClassActions();
        }
    }

    private void showDatePicker(boolean isStartDate) {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Chọn ngày");
        MaterialDatePicker<Long> picker = builder.build();

        picker.addOnPositiveButtonClickListener(selection -> {
            LocalDate date = LocalDate.ofEpochDay(selection / (1000 * 60 * 60 * 24));
            String formattedDate = String.format("%02d/%02d/%d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
            if (isStartDate) {
                startDate.setText(formattedDate);
            } else {
                endDate.setText(formattedDate);
            }
        });

        picker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    private void setupExistingClassData(List<ClassDTO> listClass) {
        className.setText(listClass.get(0).getClassName());
        startDate.setText(listClass.get(0).getStartDate());
        endDate.setText(listClass.get(0).getEndDate());

        // Fetch and set other data (program, teacher, staff)
        String idProgramText = listClass.get(0).getIdProgram();
        List<ProgramDTO> programArr = ProgramDAO.getInstance(Activity_Add_Class.this).SelectProgram(
                Activity_Add_Class.this, "ID_PROGRAM = ? AND STATUS = ?", new String[] {idProgramText, "0"});
        String idTeacherText = listClass.get(0).getIdTeacher();
        List<TeacherDTO> teacherArr = TeacherDAO.getInstance(Activity_Add_Class.this).SelectTeacher(
                Activity_Add_Class.this, "ID_TEACHER = ? AND STATUS = ?", new String[] {idTeacherText, "0"});
        String idStaff = listClass.get(0).getIdStaff();
        List<StaffDTO> staffArr = StaffDAO.getInstance(Activity_Add_Class.this).SelectStaffVer2(
                Activity_Add_Class.this, "ID_STAFF = ? AND STATUS = ?", new String[] {idStaff, "0"});

        staffID.setText(staffArr.get(0).getFullName());
        idTeacher.setText(teacherArr.get(0).getFullName());
        program.setText(programArr.get(0).getNameProgram());

        doneBtn.setOnClickListener(v -> {
            if (validateInputs()) {
                updateClass(listClass.get(0).getClassID());
            }
        });

        exitBtn.setOnClickListener(v -> showExitConfirmation());
    }

    private void setupNewClassActions() {
        doneBtn.setOnClickListener(v -> {
            if (validateInputs()) {
                createNewClass();
            }
        });

        exitBtn.setOnClickListener(v -> showExitConfirmation());
    }

    private boolean validateInputs() {
        if (className.getText().toString().isEmpty() || startDate.getText().toString().isEmpty() ||
                endDate.getText().toString().isEmpty() || staffID.getText().toString().isEmpty() ||
                program.getText().toString().isEmpty() || idTeacher.getText().toString().isEmpty()) {
            Toast.makeText(Activity_Add_Class.this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date1 = LocalDate.parse(startDate.getText().toString(), formatter);
        LocalDate date2 = LocalDate.parse(endDate.getText().toString(), formatter);

        if (date1.isAfter(date2)) {
            Toast.makeText(Activity_Add_Class.this, "Ngày kết thúc lớp học phải sau ngày bắt đầu!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void updateClass(String classID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Class.this);
        builder.setTitle("Xác nhận")
                .setMessage("Bạn có chắc chắn muốn sửa thông tin lớp học này không?")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Logic for updating the class
                    List<ProgramDTO> programSelect = ProgramDAO.getInstance(Activity_Add_Class.this).SelectProgram(
                            Activity_Add_Class.this, "NAME = ? AND STATUS = ?", new String[] {program.getText().toString(), "0"});
                    List<TeacherDTO> teacher = TeacherDAO.getInstance(Activity_Add_Class.this).SelectTeacher(
                            Activity_Add_Class.this, "FULLNAME = ? AND STATUS = ?", new String[] {idTeacher.getText().toString(), "0"});
                    List<StaffDTO> staff = StaffDAO.getInstance(Activity_Add_Class.this).SelectStaffVer2(
                            Activity_Add_Class.this, "FULLNAME = ? AND STATUS = ?", new String[] {staffID.getText().toString(), "0"});

                    if (programSelect.isEmpty() || teacher.isEmpty() || staff.isEmpty()) {
                        Toast.makeText(Activity_Add_Class.this, "Thông tin không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ClassDTO classUpdate = new ClassDTO(classID, className.getText().toString(),
                            startDate.getText().toString(), endDate.getText().toString(),
                            programSelect.get(0).getIdProgram(), teacher.get(0).getIdTeacher(),
                            staff.get(0).getIdStaff(), "0", "desiredColor");

                    try {
                        int rowEffect = ClassDAO.getInstance(Activity_Add_Class.this).UpdateClass(
                                Activity_Add_Class.this, classUpdate, "ID_CLASS = ? AND STATUS = ?", new String[] {classID, "0"});
                        if (rowEffect > 0) {
                            Toast.makeText(Activity_Add_Class.this, "Sửa thông tin lớp học thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("Update class failed", "failed");
                        }
                    } catch (Exception e) {
                        Log.d("Update class error: ", e.getMessage());
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void createNewClass() {
        List<ProgramDTO> programSelect = ProgramDAO.getInstance(Activity_Add_Class.this).SelectProgram(
                Activity_Add_Class.this, "NAME = ? AND STATUS = ?", new String[] {program.getText().toString(), "0"});
        List<TeacherDTO> teacher = TeacherDAO.getInstance(Activity_Add_Class.this).SelectTeacher(
                Activity_Add_Class.this, "FULLNAME = ? AND STATUS = ?", new String[] {idTeacher.getText().toString(), "0"});
        List<StaffDTO> staff = StaffDAO.getInstance(Activity_Add_Class.this).SelectStaffVer2(
                Activity_Add_Class.this, "FULLNAME = ? AND STATUS = ?", new String[] {staffID.getText().toString(), "0"});

        if (programSelect.isEmpty() || teacher.isEmpty() || staff.isEmpty()) {
            Toast.makeText(Activity_Add_Class.this, "Thông tin không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        ClassDTO classNew = new ClassDTO(null, className.getText().toString(),
                startDate.getText().toString(), endDate.getText().toString(),
                programSelect.get(0).getIdProgram(), teacher.get(0).getIdTeacher(),
                staff.get(0).getIdStaff(), "0", "desiredColor");

        try {
            int rowEffect = ClassDAO.getInstance(Activity_Add_Class.this).InsertClass(Activity_Add_Class.this, classNew);
            if (rowEffect > 0) {
                Toast.makeText(Activity_Add_Class.this, "Thêm lớp học mới thành công", Toast.LENGTH_SHORT).show();
                resetFields();
            } else {
                Toast.makeText(Activity_Add_Class.this, "Thêm lớp học mới thất bại", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.d("Add new class failed", "failed: " + e.getMessage());
        }
    }

    private void resetFields() {
        className.setText("");
        startDate.setText("");
        endDate.setText("");
        idTeacher.setText("");
        staffID.setText("");
    }

    private void showExitConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Class.this);
        builder.setTitle("Xác nhận")
                .setMessage("Bạn chưa hoàn thành chỉnh sửa, bạn có chắc chắn muốn thoát?")
                .setPositiveButton("OK", (dialog, which) -> finish())
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.normal_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}