package com.example.app.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app.R;
import com.example.app.adapter.ButtonsAdapter;
import java.util.Arrays;
import java.util.List;

public class Activity_Pick_Role extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_role);

        RecyclerView recyclerView = findViewById(R.id.buttons_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> buttonTitles = Arrays.asList(
                "Đăng nhập với tư cách khách",
                "Đăng nhập với tài khoản"
        );

        List<Integer> buttonIcons = Arrays.asList(
                R.drawable.icon_guest,
                R.drawable.icon_account
        );

        ButtonsAdapter adapter = new ButtonsAdapter(buttonTitles, buttonIcons, position -> {
            if (position == 0) {
                Intent intent = new Intent(Activity_Pick_Role.this, Activity_Notifications.class);
                intent.putExtra("message", "Xem chứng chỉ");
                startActivity(intent);
            } else if (position == 1) {
                Intent intent = new Intent(Activity_Pick_Role.this, Activity_Login.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}