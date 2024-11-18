package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.AccountDAO;
import com.example.app.adapter.CertificateDAO;
import com.example.app.adapter.ClassDAO;
import com.example.app.adapter.ClassroomDAO;
import com.example.app.adapter.CollectionTuitionFeesDAO;
import com.example.app.adapter.DataProvider;
import com.example.app.adapter.ExamScoreDAO;
import com.example.app.adapter.ExaminationDAO;
import com.example.app.adapter.NotificationDAO;
import com.example.app.adapter.OfficialStudentDAO;
import com.example.app.adapter.PotentialStudentDAO;
import com.example.app.adapter.ProgramDAO;
import com.example.app.adapter.ScheduleDAO;
import com.example.app.adapter.StaffDAO;
import com.example.app.adapter.TeacherDAO;
import com.example.app.adapter.TeachingDAO;
import com.example.app.model.AccountDTO;
import com.example.app.model.CertificateDTO;
import com.example.app.model.ClassDTO;
import com.example.app.model.ClassroomDTO;
import com.example.app.model.CollectionTuitionFeesDTO;
import com.example.app.model.ExamScoreDTO;
import com.example.app.model.ExaminationDTO;
import com.example.app.model.NotificationDTO;
import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.PotentialStudentDTO;
import com.example.app.model.ProgramDTO;
import com.example.app.model.ScheduleDTO;
import com.example.app.model.StaffDTO;
import com.example.app.model.TeacherDTO;
import com.example.app.model.TeachingDTO;

public class Activity_Login extends AppCompatActivity {
    EditText usernameInput, passwordInput;
    TextView fogotPassBtn;
    Button loginBtn;
    public static String idUser;
    public static String password;
    public static String username;
    public static String idAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.input_username);
        passwordInput = findViewById(R.id.input_password);
        loginBtn = findViewById(R.id.login_btn);
        fogotPassBtn = findViewById(R.id.fogotPassBtn);

        fogotPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Login.this, Activity_Forgot_Password.class));
            }
        });

        // Initialize database

        DataProvider.getInstance(Activity_Login.this).recreateDatabase(Activity_Login.this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // handle login event
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Activity_Login.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {

                    String whereClause = "USERNAME = ? AND PASSWORD = ?";
                    String[] whereArgs = new String[]{username, password};
                    Cursor cursor = AccountDAO.getInstance(Activity_Login.this).selectAccount(Activity_Login.this, whereClause, whereArgs);

                    if (cursor != null && cursor.getCount() > 0) {
                        Intent intent = new Intent(Activity_Login.this, Activity_Main_Screen.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Activity_Login.this, "Hãy nhập đúng " +
                                "tên đăng nhập và mật khẩu!", Toast.LENGTH_SHORT).show();
                    }

                    if (cursor.moveToFirst()) {
                        do {
                            int idIndex = cursor.getColumnIndex("ID_USER");
                            if (idIndex != -1) {
                                idUser = cursor.getString(idIndex);
                            }
                            int idAccIndex = cursor.getColumnIndex("ID_ACCOUNT");
                            if (idAccIndex != -1) {
                                idAccount = cursor.getString(idAccIndex);
                            }

                        } while (cursor.moveToNext());
                    }

                    cursor.close();
                }

            }
        });
    }

    @Override
    protected  void onStart()  {
        super.onStart();
        DataProvider.getInstance(Activity_Login.this).recreateDatabase(Activity_Login.this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // handle login event
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Activity_Login.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {

                    String whereClause = "USERNAME = ? AND PASSWORD = ?";
                    String[] whereArgs = new String[]{username, password};
                    Cursor cursor = AccountDAO.getInstance(Activity_Login.this).selectAccount(Activity_Login.this, whereClause, whereArgs);

                    if (cursor != null && cursor.getCount() > 0) {
                        Intent intent = new Intent(Activity_Login.this, Activity_Main_Screen.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Activity_Login.this, "Hãy nhập đúng tên đăng " +
                                "nhập và mật khẩu!", Toast.LENGTH_SHORT).show();
                    }

                    if (cursor.moveToFirst()) {
                        do {
                            int idIndex = cursor.getColumnIndex("ID_USER");
                            if (idIndex != -1) {
                                idUser = cursor.getString(idIndex);
                            }
                            int idAccIndex = cursor.getColumnIndex("ID_ACCOUNT");
                            if (idAccIndex != -1) {
                                idAccount = cursor.getString(idAccIndex);
                            }

                        } while (cursor.moveToNext());
                    }

                    cursor.close();
                }

            }
        });

    }
}