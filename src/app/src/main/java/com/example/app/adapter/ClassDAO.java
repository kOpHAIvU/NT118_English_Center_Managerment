package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.app.model.ClassDTO;
import com.example.app.model.TeachingDTO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.example.app.R;
import android.util.Pair;

public class ClassDAO {

    public static ClassDAO instance;

    private ClassDAO(Context context) {}

    public static synchronized ClassDAO getInstance(Context context) {
        if (instance == null) {
            instance = new ClassDAO(context);
        }
        return instance;
    }

    public int InsertClass(Context context, ClassDTO classDTO) {
        int rowEffect = -1;
        int maxId = DataProvider.getInstance(context).getMaxId("CLASS", "ID_CLASS");

        ContentValues values = new ContentValues();
        values.put("ID_CLASS", "CLS" + (maxId + 1));
        values.put("NAME", classDTO.getClassName());
        values.put("START_DATE", classDTO.getStartDate());
        values.put("END_DATE", classDTO.getEndDate());
        values.put("ID_PROGRAM", classDTO.getIdProgram());
        values.put("ID_TEACHER", classDTO.getIdTeacher());
        values.put("ID_STAFF", classDTO.getIdStaff());
        values.put("STATUS", "0");
        values.put("COLOR_RES_ID", classDTO.getColorResId()); // Thêm mã màu vào giá trị

        try {
            rowEffect = DataProvider.getInstance(context).insertData("CLASS", values);
            Log.d("Insert Class", rowEffect > 0 ? "success" : "Fail");
        } catch (SQLException e) {
            Log.d("Insert Class Error", e.getMessage());
        }

        return rowEffect;
    }

    public int UpdateClass(Context context, ClassDTO classDTO, String whereClause, String[] whereArgs) {
        int rowEffect = -1;

        ContentValues values = new ContentValues();
        values.put("NAME", classDTO.getClassName());
        values.put("START_DATE", classDTO.getStartDate());
        values.put("END_DATE", classDTO.getEndDate());
        values.put("ID_PROGRAM", classDTO.getIdProgram());
        values.put("ID_TEACHER", classDTO.getIdTeacher());
        values.put("ID_STAFF", classDTO.getIdStaff());
        values.put("STATUS", "0");
        values.put("COLOR_RES_ID", classDTO.getColorResId()); // Cập nhật mã màu

        try {
            rowEffect = DataProvider.getInstance(context).updateData("CLASS", values, whereClause, whereArgs);
            Log.d("Update Class", rowEffect > 0 ? "success" : "Fail");
        } catch (SQLException e) {
            Log.d("Update Class Error", e.getMessage());
        }

        return rowEffect;
    }

    public Pair<String, Integer> calculateStatusDisplay(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate now = LocalDate.now();

        try {
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);

            if (now.isBefore(start)) {
                return new Pair<>("Chưa bắt đầu", R.color.yellow); // Màu xanh
            } else if (now.isAfter(end)) {
                return new Pair<>("Đã kết thúc", R.color.red); // Màu đỏ
            } else {
                return new Pair<>("Đang diễn ra", R.color.green); // Màu vàng
            }
        } catch (DateTimeParseException e) {
            return new Pair<>("Không xác định", R.color.gray); // Màu xám
        }
    }

    public List<ClassDTO> selectClass(Context context, String whereClause, String[] whereArgs) {
        List<ClassDTO> listClass = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("CLASS", new String[]{"*"}, whereClause, whereArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    listClass.add(getClassFromCursor(cursor));
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.d("Select Class Error", e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listClass;
    }

    private ClassDTO getClassFromCursor(Cursor cursor) {
        if (cursor == null || cursor.isBeforeFirst() || cursor.isAfterLast()) {
            return null; // Hoặc xử lý theo cách khác nếu không có dữ liệu
        }

        String id = "", name = "", start = "", end = "", idProgram = "", idTeacher = "", idStaff = "";
        String colorResId = ""; // Thêm biến cho mã màu

        int idIndex = cursor.getColumnIndex("ID_CLASS");
        if (idIndex != -1) {
            id = cursor.getString(idIndex);
        }
        int nameIndex = cursor.getColumnIndex("NAME");
        if (nameIndex != -1) {
            name = cursor.getString(nameIndex);
        }
        int startIndex = cursor.getColumnIndex("START_DATE");
        if (startIndex != -1) {
            start = cursor.getString(startIndex);
        }
        int endIndex = cursor.getColumnIndex("END_DATE");
        if (endIndex != -1) {
            end = cursor.getString(endIndex);
        }
        int programIndex = cursor.getColumnIndex("ID_PROGRAM");
        if (programIndex != -1) {
            idProgram = cursor.getString(programIndex);
        }
        int teacherIndex = cursor.getColumnIndex("ID_TEACHER");
        if (teacherIndex != -1) {
            idTeacher = cursor.getString(teacherIndex);
        }
        int staffIndex = cursor.getColumnIndex("ID_STAFF");
        if (staffIndex != -1) {
            idStaff = cursor.getString(staffIndex);
        }
        int colorIndex = cursor.getColumnIndex("COLOR_RES_ID"); // Lấy chỉ số cột mã màu
        if (colorIndex != -1) {
            colorResId = cursor.getString(colorIndex);
        }

        // Tính trạng thái dựa trên ngày bắt đầu và kết thúc
        Pair<String, Integer> result = calculateStatusDisplay(start, end);
        String status = result.first; // Trạng thái

        // Trả về một đối tượng ClassDTO
        return new ClassDTO(id, name, start, end, idProgram, idTeacher, idStaff, status, colorResId); // Thêm colorResId vào ClassDTO
    }

    public List<ClassDTO> SelectClassByIdUser(Context context, String idUser, int type) {
        List<ClassDTO> listClass = new ArrayList<>();
        Set<String> idClass = new HashSet<>();

        if (type == 1) {
            List<TeachingDTO> teaching = TeachingDAO.getInstance(context).SelectTeaching(
                    context, "ID_STUDENT = ? and STATUS = ?", new String[]{idUser, "0"});
            for (TeachingDTO teach : teaching) {
                idClass.add(teach.getIdClass());
            }
            for (String id : idClass) {
                listClass.addAll(selectClass(context, "ID_CLASS = ? AND STATUS = ?", new String[]{id, "0"}));
            }
        } else {
            listClass.addAll(selectClass(context, "STATUS = ?", new String[]{"0"}));
        }

        return listClass;
    }

    public int DeleteClass(Context context, ClassDTO classDelete, String whereClause, String[] whereArgs) {
        int rowEffect = -1;
        ContentValues values = new ContentValues();
        values.put("STATUS", 1);

        try {
            rowEffect = DataProvider.getInstance(context).updateData("CLASS", values, whereClause, whereArgs);
            Log.d("Delete Class", rowEffect > 0 ? "success" : "Fail");
        } catch (SQLException e) {
            Log.d("Delete Class Error", e.getMessage());
        }

        return rowEffect;
    }

    public List<ClassDTO> selectClassByYear(Context context, int year) {
        List<ClassDTO> listClass = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("CLASS", new String[]{"*"},
                    "STATUS = ?", new String[] {"0"}, null);
        } catch (SQLException e) {
            Log.d("Select Class: ", e.getMessage());
        }

        String id = "", name = "", start = "", end = "", idProgram = "", idTeacher = "", idStaff = "";

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex("ID_CLASS");
                if (idIndex != -1) {
                    id = cursor.getString(idIndex);
                }
                int nameIndex = cursor.getColumnIndex("NAME");
                if (nameIndex != -1) {
                    name = cursor.getString(nameIndex);
                }
                int endIndex = cursor.getColumnIndex("END_DATE");
                if (endIndex != -1) {
                    end = cursor.getString(endIndex);
                }
                int programIndex = cursor.getColumnIndex("ID_PROGRAM");
                if (programIndex != -1) {
                    idProgram = cursor.getString(programIndex);
                }
                int teacherIndex = cursor.getColumnIndex("ID_TEACHER");
                if (teacherIndex != -1) {
                    idTeacher = cursor.getString(teacherIndex);
                }
                int staffIndex = cursor.getColumnIndex("ID_STAFF");
                if (staffIndex != -1) {
                    idStaff = cursor.getString(staffIndex);
                }
                int startIndex = cursor.getColumnIndex("START_DATE");
                if (startIndex != -1) {
                    start = cursor.getString(startIndex);
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                try {
                    LocalDate dateTime = LocalDate.parse(start, formatter);
                    Log.d("Get date time in class DAO: ", String.valueOf(dateTime.getYear()));
                    if (dateTime.getYear() == year) {
                        listClass.add(new ClassDTO(id, name, start, end, idProgram, idTeacher, idStaff, "0", "")); // Thêm colorResId nếu cần
                    }
                } catch (DateTimeParseException e) {
                    Log.d("Parse date exception: ", e.getMessage());
                }

            } while (cursor.moveToNext());
        }
        return listClass;
    }
}