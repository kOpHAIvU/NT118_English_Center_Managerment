package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.app.model.ClassroomDTO;

import java.util.ArrayList;
import java.util.List;

public class ClassroomDAO {
    public static ClassroomDAO instance;
    private ClassroomDAO(Context context) {}

    public static synchronized ClassroomDAO getInstance(Context context) {
        if (instance == null) {
            instance = new ClassroomDAO(context);
        }
        return instance;
    }

    public int InsertNewClassroom(Context context, ClassroomDTO classroom) {
        int rowEffect = -1;

        ContentValues values = new ContentValues();
        int maxId = DataProvider.getInstance(context).getMaxId("CLASSROOM", "ID_CLASSROOM");

        values.put("ID_CLASSROOM", "CLA" + String.valueOf(maxId + 1));
        values.put("NAME", classroom.getName());
        values.put("STATUS", 0);
        values.put("CAPACITY", classroom.getCapacity()); // Thêm sức chứa

        try {
            rowEffect = DataProvider.getInstance(context).insertData("CLASSROOM", values);
            if (rowEffect > 0) {
                Log.d("Insert New Classroom: ", "success");
            } else {
                Log.d("Insert New Classroom: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert New Classroom: ", e.getMessage());
        }

        return rowEffect;
    }

    public int UpdateClassroom(Context context, ClassroomDTO classroom, String whereClauses, String[] whereArgs) {
        int rowEffect = -1;

        ContentValues values = new ContentValues();
        values.put("NAME", classroom.getName());
        values.put("CAPACITY", classroom.getCapacity()); // Cập nhật sức chứa

        try {
            rowEffect = DataProvider.getInstance(context).updateData("CLASSROOM", values, whereClauses, whereArgs);
            return rowEffect;
        } catch (SQLException e) {
            Log.e("Update Classroom: ", e.getMessage());
        }

        return rowEffect;
    }

    public List<ClassroomDTO> SelectClassroom(Context context, String whereClause, String[] whereArgs) {
        List<ClassroomDTO> listClassroom = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("CLASSROOM", new String[]{"*"}, whereClause, whereArgs, null);
        } catch (SQLException e) {
            Log.d("Select Classroom: ", e.getMessage());
        }

        String id = "", name = "";
        int capacity = 0; // Biến để lưu sức chứa

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex("ID_CLASSROOM");
                if (idIndex != -1) {
                    id = cursor.getString(idIndex);
                }
                int nameIndex = cursor.getColumnIndex("NAME");
                if (nameIndex != -1) {
                    name = cursor.getString(nameIndex);
                }
                int capacityIndex = cursor.getColumnIndex("CAPACITY"); // Lấy chỉ số sức chứa
                if (capacityIndex != -1) {
                    capacity = cursor.getInt(capacityIndex); // Lấy sức chứa
                }
                listClassroom.add(new ClassroomDTO(id, name, capacity)); // Thêm sức chứa vào ClassroomDTO
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close(); // Đảm bảo đóng cursor
        }

        return listClassroom;
    }
}