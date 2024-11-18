package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class DataProvider extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ENGLISH_CENTER_MANAGEMENT.db";
    private static DataProvider instance;
    private static final int DATABASE_VERSION = 1;

    private DataProvider(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static synchronized DataProvider getInstance(Context context) {
        if (instance == null) {
            instance = new DataProvider(context);
        }
        return instance;
    }

    public void createTableAndDb(SQLiteDatabase db) {
        if (!isTableExists(db, "CERTIFICATE")) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS CERTIFICATE (" +
                        "ID_CERTIFICATE TEXT PRIMARY KEY, " +
                        "NAME TEXT, " +
                        "CONTENT TEXT, " +
                        "STATUS INTEGER)");
                Log.d("CREATE CERTIFICATE", "Database created successfully");

                insertCertificate(db, "Ielts Academic", "Ielts Academic được công nhận rộng rãi như là yêu cầu ngôn ngữ đầu vào cho tất cả các khóa học Đại học và Sau Đại học", 1, 0);
                insertCertificate(db, "Ielts General", "Ielts General thích hợp cho tất cả những ai chuẩn bị tới các nước nói tiếng Anh để hoàn tất chương trình trung học, các chương trình đào tạo hoặc với mục đích nhập cư.", 1, 1);
                insertCertificate(db, "Toeic", "TOEIC là một chứng chỉ tiếng Anh quốc tế, đánh giá khả năng sử dụng tiếng Anh trong môi trường làm việc toàn cầu", 1, 2);

                Log.d("INSERT CERTIFICATE", "Sample certificate data inserted successfully");
            } catch ( Exception e) {
                Log.d("EXCEPTION CREATE CERTIFICATE",  e.getMessage());
            }
        }

        if (!isTableExists(db, "STAFF")) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS STAFF (" +
                        "ID_STAFF TEXT PRIMARY KEY, " +
                        "FULLNAME TEXT, " +
                        "ADDRESS TEXT, " +
                        "PHONE_NUMBER TEXT, " +
                        "GENDER TEXT, " +
                        "BIRTHDAY TEXT," +
                        "SALARY INTEGER, " +
                        "TYPE TEXT," +
                        "STATUS INTEGER)");
                Log.d("CREATE STAFF", "Database created successfully");

                insertStaff(db, "Nguyen Thi C", "TP HCM", "0343333333", "Nữ", "12/6/2022", 10000000, "1", 0, 0);
                insertStaff(db, "Nguyen Thi D", "Đồng Nai", "03466555333", "Nữ", "14/2/2022", 2000000, "2", 0, 1);
                insertStaff(db, "Nguyen Thi E", "Bình Định", "03435555333", "Nữ", "27/2/2022", 15000000, "3", 0, 2);

                Log.d("INSERT STAFF", "Sample staff data inserted successfully");
            } catch ( Exception e) {
                Log.d("CREATE STAFF",  e.getMessage());
            }
        }

        if (!isTableExists(db, "TEACHERS")) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS TEACHERS (" +
                        "ID_TEACHER TEXT PRIMARY KEY , " +
                        "FULLNAME TEXT, " +
                        "ADDRESS TEXT, " +
                        "PHONE_NUMBER TEXT, " +
                        "GENDER TEXT, " +
                        "BIRTHDAY TEXT," +
                        "SALARY INTEGER, " +
                        "STATUS INTEGER)");
                Log.d("CREATE TEACHERS", "Database created successfully");

                insertTeacher(db, "Nguyen Thi A", "TP HCM", "0343333333", "Nữ", "12/6/1985", 12000000, 0, 0);
                insertTeacher(db, "Nguyen Thi B", "Đồng Nai", "03466555333", "Nam", "14/2/1990", 15000000, 0, 1);
                insertTeacher(db, "Nguyen Thi C", "Bình Định", "03435555333", "Nữ", "27/2/1980", 18000000, 1, 2);

                Log.d("INSERT TEACHERS", "Sample teacher data inserted successfully");
            } catch ( Exception e) {
                Log.d("CREATE TEACHERS",  e.getMessage());
            }
        }

        if (!isTableExists(db, "CLASSROOM")) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS CLASSROOM (" +
                        "ID_CLASSROOM TEXT PRIMARY KEY, " +
                        "NAME TEXT, " +
                        "STATUS INTEGER)");
                Log.d("CREATE EXAMINATION", "Database created successfully");

                insertClassroom(db, "B1.11", 0, 0);
                insertClassroom(db, "B1.12", 1, 1);
                insertClassroom(db, "B1.13", 0, 2);
                insertClassroom(db, "B1.14", 1, 3);
                insertClassroom(db, "B1.15", 0, 4);

                Log.d("INSERT CLASSROOMS", "Sample classroom data inserted successfully");
            } catch ( Exception e) {
                Log.d("CREATE EXAMINATION",  e.getMessage());
            }
        }

        if (!isTableExists(db, "OFFICIAL_STUDENT")) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS OFFICIAL_STUDENT (" +
                        "ID_STUDENT TEXT PRIMARY KEY , " +
                        "FULLNAME TEXT, " +
                        "ADDRESS TEXT, " +
                        "PHONE_NUMBER TEXT, " +
                        "GENDER TEXT," +
                        "BIRTHDAY TEXT," +
                        "STATUS INTEGER)");
                Log.d("CREATE OFFICIAL_STUDENTS", "Database created successfully");

                insertOfficialStudent(db, "Nguyen Van A", "Binh Dinh", "0312345678", "Nam", "22/2/2022", 0, 0);
                insertOfficialStudent(db, "Le Thi B", "Binh Duong", "0332323222", "Nữ", "22/2/2022", 0, 1);
                insertOfficialStudent(db, "Ho Thi C", "TP Hồ Chí Minh", "036723222", "Nam", "22/2/2022", 0, 2);
                insertOfficialStudent(db, "Nguyen Thi D", "Hà Nội", "0332323222", "Nữ", "22/2/2022", 0, 3);
                insertOfficialStudent(db, "Le Thi E", "Phú Yên", "0345678901", "Nam", "22/2/2022", 0, 4);
                insertOfficialStudent(db, "Hoang Thi F", "Đồng Nai", "0345623777", "Nữ", "22/2/2022", 0, 5);

                Log.d("INSERT OFFICIAL STUDENTS", "Sample official student data inserted successfully");
            } catch ( Exception e) {
                Log.d("CREATE OFFICIAL_STUDENTS",  e.getMessage());
            }
        }

        if (!isTableExists(db, "COLLECTING_TUITION_FEES")) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS COLLECTING_TUITION_FEES (" +
                        "ID_BILL TEXT PRIMARY KEY , " +
                        "ID_STUDENT TEXT, " +
                        "COLLECTION_DATE TEXT, " +
                        "TOTAL_MONEY INTEGER, " +
                        "STATUS INTEGER," +
                        "FOREIGN KEY (ID_STUDENT) REFERENCES OFFICAL_STUDENT(ID_STUDENT))");
                Log.d("CREATE COLLECTING_TUITION_FEES", "Database created successfully");

                insertCollectionTuitionFee(db, "TEC1", "23/1/2024 15:23:22", 10000000, 0, 0);
                insertCollectionTuitionFee(db, "TEC2", "23/2/2024 15:23:22", 20000000, 0, 1);
                insertCollectionTuitionFee(db, "TEC3", "23/3/2024 15:23:22", 10000000, 0, 2);
                insertCollectionTuitionFee(db, "TEC4", "23/4/2024 15:23:22", 40000000, 0, 3);
                insertCollectionTuitionFee(db, "TEC5", "23/5/2024 15:23:22", 60000000, 0, 4);
                insertCollectionTuitionFee(db, "TEC6", "23/6/2024 15:23:22", 40000000, 0, 5);
                insertCollectionTuitionFee(db, "TEC7", "23/7/2024 15:23:22", 90000000, 0, 6);
                insertCollectionTuitionFee(db, "TEC8", "23/8/2024 15:23:22", 10000000, 0, 7);
                insertCollectionTuitionFee(db, "TEC9", "23/9/2024 15:23:22", 10000000, 0, 8);
                insertCollectionTuitionFee(db, "TEC10", "23/10/2024 15:23:22", 50000000, 0, 9);
                insertCollectionTuitionFee(db, "TEC11", "23/11/2024 15:23:22", 40000000, 0, 10);
                insertCollectionTuitionFee(db, "TEC12", "23/12/2024 15:23:22", 10000000, 0, 11);

                Log.d("INSERT COLLECTION_TUITION_FEES", "Sample tuition fee data inserted successfully");
            } catch ( Exception e) {
                Log.d("CREATE COLLECTING_TUITION_FEES",  e.getMessage());
            }
        }

        if (!isTableExists(db, "CLASS")) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS CLASS (" +
                        "ID_CLASS TEXT PRIMARY KEY , " +
                        "NAME TEXT, " +
                        "START_DATE TEXT, " +
                        "END_DATE TEXT, " +
                        "ID_PROGRAM TEXT, " +
                        "ID_TEACHER TEXT, " +
                        "ID_STAFF TEXT, " +
                        "STATUS INTEGER," +
                        "FOREIGN KEY (ID_PROGRAM) REFERENCES PROGRAM(ID_PROGRAM)," +
                        "FOREIGN KEY (ID_STAFF) REFERENCES STAFF(ID_STAFF)," +
                        "FOREIGN KEY (ID_TEACHER) REFERENCES TEACHER(ID_TEACHER))");
                Log.d("CREATE CLASS", "Database created successfully");

                insertClass(db, "Lớp ielts từ 5-6.5 giúp cải thiện kĩ năng nghe", "22/01/2024", "22/09/2024", "PRO1", "TEA1", "STA2", "0", 0);
                insertClass(db, "Lớp ielts từ 5-6.5 giúp cải thiện kĩ năng đọc", "22/01/2024", "22/10/2024", "PRO2", "TEA2", "STA1", "0", 1);
                insertClass(db, "Lớp ielts từ 6-7.5 giúp cải thiện kĩ năng nói", "22/02/2024", "22/12/2024", "PRO3", "TEA1", "STA2", "0", 2);
                insertClass(db, "Lớp ielts từ 5.5-6.5 giúp cải thiện kĩ năng đọc", "22/03/2024", "22/11/2024", "PRO4", "TEA1", "STA2", "0", 3);
                insertClass(db, "Lớp toeic cải thiện kĩ năng nghe 700-990", "22/04/2024", "22/12/2024", "PRO5", "TEA2", "STA1", "0", 4);
                insertClass(db, "Lớp toeic cải thiện kĩ năng đọc 600-990", "22/04/2024", "22/12/2024", "PRO6", "TEA2", "STA1", "0", 5);

                Log.d("INSERT CLASS", "Sample class data inserted successfully");
            } catch ( SQLException e) {
                Log.d("CREATE CLASS",  e.getMessage());
            }
        }

        if (!isTableExists(db, "SCHEDULE")) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS SCHEDULE (" +
                        "ID_SCHEDULE TEXT PRIMARY KEY," +
                        "DAY_OF_WEEK INTEGER, " +
                        "START_TIME TEXT," +
                        "END_TIME TEXT," +
                        "ID_CLASS TEXT, " +
                        "ID_CLASSROOM TEXT, " +
                        "STATUS INTEGER, " +
                        "FOREIGN KEY (ID_CLASS) REFERENCES CLASS(ID_CLASS)," +
                        "FOREIGN KEY (ID_CLASSROOM) REFERENCES CLASSROOM(ID_CLASSROOM))");

                insertSchedule(db, "2", "9", "11", "CLS1", "CLA1", 0, 0);
                insertSchedule(db, "3", "7", "9", "CLS1", "CLA2", 0, 1);
                insertSchedule(db, "4", "13", "15", "CLS2", "CLA3", 0, 2);
                insertSchedule(db, "5", "15", "17", "CLS2", "CLA4", 0, 3);
                insertSchedule(db, "6", "7", "9", "CLS3", "CLA4", 0, 4);
                insertSchedule(db, "7", "9", "11", "CLS3", "CLA5", 0, 5);
                insertSchedule(db, "7", "9", "11", "CLS4", "CLA2", 0, 6);
                insertSchedule(db, "7", "9", "11", "CLS5", "CLA3", 0, 7);
                insertSchedule(db, "8", "13", "15", "CLS4", "CLA4", 0, 8);
                insertSchedule(db, "8", "9", "11", "CLS2", "CLA5", 0, 9);

                Log.d("INSERT SCHEDULE", "Sample schedule data inserted successfully");
            } catch( Exception e) {
                Log.d("CREATE SCHEDULE ",  e.getMessage());
            }
        }

        if (!isTableExists(db, "EXAMINATION")) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS EXAMINATION (" +
                        "ID_EXAM TEXT PRIMARY KEY, " +
                        "NAME TEXT, " +
                        "FORMAT TEXT, " +
                        "EXAM_DATE TEXT, " +
                        "ID_CLASS TEXT, " +
                        "ID_CLASSROOM TEXT, " +
                        "STATUS INTEGER," +
                        "FOREIGN KEY (ID_CLASS) REFERENCES CLASS(ID_CLASS)," +
                        "FOREIGN KEY (ID_CLASSROOM) REFERENCES CLASSROOM(ID_CLASSROOM))");
                Log.d("CREATE EXAMINATION", "Database created successfully");

                insertExamination(db, "Kiểm tra cuối khóa", "Format Ielts", "22/09/2024", "CLS1", "CLA1", 0, 0);
                insertExamination(db, "Kiểm tra cuối khóa", "Format Ielts", "22/10/2024", "CLS2", "CLA1", 0, 1);
                insertExamination(db, "Kiểm tra cuối khóa", "Format Ielts", "22/12/2024", "CLS3", "CLA1", 0, 2);
                insertExamination(db, "Kiểm tra cuối khóa", "Format Ielts", "22/11/2024", "CLS4", "CLA1", 0, 3);
                insertExamination(db, "Kiểm tra cuối khóa", "Format Toeic", "22/12/2024", "CLS5", "CLA1", 0, 4);
                insertExamination(db, "Kiểm tra cuối khóa", "Format Toeic", "22/12/2024", "CLS6", "CLA1", 0, 5);

                Log.d("INSERT EXAMINATION", "Sample examination data inserted successfully");
            } catch ( Exception e) {
                Log.d("CREATE EXAMINATION",  e.getMessage());
            }
        }

        if (!isTableExists(db, "EXAM_SCORE")) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS EXAM_SCORE (" +
                        "ID_EXAM_SCORE TEXT PRIMARY KEY, " +
                        "ID_EXAM TEXT , " +
                        "ID_STUDENT TEXT, " +
                        "SPEAKING_SCORE REAL, " +
                        "WRITING_SCORE REAL, " +
                        "LISTENING_SCORE REAL, " +
                        "READING_SCORE REAL," +
                        "STATUS INTEGER," +
                        "FOREIGN KEY (ID_EXAM) REFERENCES EXAMINATION(ID_EXAM)," +
                        "FOREIGN KEY (ID_STUDENT) REFERENCES OFFICAL_STUDENT(ID_STUDENT) )");
                Log.d("CREATE EXAM_SCORE", "Database created successfully");

                insertExamScore(db, "EXA1", "STU1", 6, 4, 6, 6, 0, 0);
                insertExamScore(db, "EXA2", "STU2", 6, 66, 7, 6, 0, 1);
                insertExamScore(db, "EXA3", "STU3", 6, 7, 9, 6, 0, 2);
                insertExamScore(db, "EXA4", "STU4", 7, 9, 9, 8, 0, 3);
                insertExamScore(db, "EXA5", "STU5", 500, 600, 600, 400, 0, 4);
                insertExamScore(db, "EXA6", "STU6", 600, 800, 600, 600, 0, 5);
                insertExamScore(db, "EXA6", "STU1", 9, 8, 8, 8, 0, 6);
                insertExamScore(db, "EXA5", "STU2", 6, 7, 6, 6, 0, 7);
                insertExamScore(db, "EXA4", "STU3", 5, 5, 5, 5, 0, 8);
                insertExamScore(db, "EXA3", "STU4", 6, 7, 6, 6, 0, 9);
                insertExamScore(db, "EXA2", "STU5", 600, 700, 600, 650, 0, 10);
                insertExamScore(db, "EXA1", "STU6", 660, 700, 600, 600, 0, 11);

                Log.d("INSERT EXAM_SCORE", "Sample exam score data inserted successfully");
            } catch ( Exception e) {
                Log.d("CREATE EXAM_SCORE",  e.getMessage());
            }
        }

        if (!isTableExists(db, "TEACHING")) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS TEACHING (" +
                        "ID_TEACHING TEXT PRIMARY KEY , " +
                        "ID_STUDENT TEXT , " +
                        "ID_CLASS TEXT, " +
                        "STATUS INTEGER, " +
                        "FOREIGN KEY (ID_STUDENT) REFERENCES OFFICAL_STUDENT(ID_STUDENT)," +
                        "FOREIGN KEY (ID_CLASS) REFERENCES CLASS(ID_CLASS))");
                Log.d("CREATE EXAM_SCORE", "Database created successfully");

                insertTeaching(db, "STU1", "CLS1", 0);
                insertTeaching(db, "STU2", "CLS2", 1);
                insertTeaching(db, "STU3", "CLS3", 2);
                insertTeaching(db, "STU4", "CLS4", 3);
                insertTeaching(db, "STU5", "CLS5", 4);
                insertTeaching(db, "STU6", "CLS6", 5);
                insertTeaching(db, "STU1", "CLS6", 6);
                insertTeaching(db, "STU2", "CLS5", 7);
                insertTeaching(db, "STU3", "CLS4", 8);
                insertTeaching(db, "STU4", "CLS3", 9);
                insertTeaching(db, "STU5", "CLS2", 10);
                insertTeaching(db, "STU6", "CLS1", 11);

                Log.d("INSERT TEACHING", "Sample teaching data inserted successfully");
            } catch ( Exception e) {
                Log.d("CREATE EXAM_SCORE",  e.getMessage());
            }
        }

        if (!isTableExists(db, "PROGRAM")) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS PROGRAM (" +
                        "ID_PROGRAM TEXT PRIMARY KEY, " +
                        "NAME TEXT, " +
                        "INPUT_SCORE REAL, " +
                        "OUTPUT_SCORE REAL, " +
                        "CONTENT TEXT, " +
                        "SPEAKING_SCORE REAL, " +
                        "WRITING_SCORE REAL, " +
                        "LISTENING_SCORE REAL, " +
                        "READING_SCORE REAL, " +
                        "TUITION_FEES INTEGER, " +
                        "STUDY_PERIOD INTEGER, " + // Lộ trình học kéo dài 6 tháng, 12 tháng,....
                        "ID_CERTIFICATE TEXT, " +
                        "STATUS INTEGER, " +
                        "FOREIGN KEY (ID_CERTIFICATE) REFERENCES CERTIFICATE(ID_CERTIFICATE))");
                Log.d("CREATE PROGRAM", "Database created successfully");

                insertProgram(db, "Chứng chỉ ielts academic", 5.0, 6.5, "Cải thiện khả năng nghe", 5.5, 6.5, 7.0, 7.0, 3000000, "6 tháng", "CER1", 0, 0);
                insertProgram(db, "Chứng chỉ ielts academic", 5.0, 6.5, "Cải thiện khả năng nói", 6.5, 6.5, 7.0, 7.0, 10000000, "6 tháng", "CER1", 0, 1);
                insertProgram(db, "Chứng chỉ ielts general", 5.0, 6.5, "Cải thiện khả năng nghe", 5.5, 6.5, 7.0, 7.0, 5000000, "6 tháng", "CER2", 0, 2);
                insertProgram(db, "Chứng chỉ ielts general", 5.0, 6.5, "Cải thiện khả năng nói", 6.5, 6.5, 7.0, 7.0, 10000000, "6 tháng", "CER2", 0, 3);
                insertProgram(db, "Chứng chỉ toeic 500-880", 5.0, 6.5, "Cải thiện khả năng nghe", 5.5, 6.5, 7.0, 7.0, 5000000, "6 tháng", "CER3", 0, 4);
                insertProgram(db, "Chứng chỉ toeic 500-700", 5.0, 6.5, "Cải thiện khả năng nói", 6.5, 6.5, 7.0, 7.0, 10000000, "6 tháng", "CER3", 0, 5);

                Log.d("INSERT PROGRAM", "Sample program data inserted successfully");
            } catch ( Exception e) {
                Log.d("CREATE PROGRAM",  e.getMessage());
            }
        }

        if (!isTableExists(db, "ACCOUNT")) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS ACCOUNT (" +
                        "ID_ACCOUNT TEXT PRIMARY KEY, " +
                        "ID_USER TEXT," +
                        "USERNAME TEXT, " +
                        "PASSWORD TEXT," +
                        "STATUS INTEGER," +
                        "FOREIGN KEY (ID_USER) REFERENCES OFFICAL_STUDENT(ID_STUDENT)," +
                        "FOREIGN KEY (ID_USER) REFERENCES TEACHERS(ID_TEACHER)," +
                        "FOREIGN KEY (ID_USER) REFERENCES STAFF(ID_STAFF) )");
                Log.d("CREATE ACCOUNT", "Database created successfully");

                insertAccount(db, "STU1", "nguyenthia", "thia123", 1, 0);
                insertAccount(db, "STA1", "nguyenthic", "thic123", 0, 1);
                insertAccount(db, "STA2", "nguyenthid", "thid123", 1, 2);
                insertAccount(db, "STA3", "nguyenthie", "thie123", 0, 3);

                Log.d("INSERT ACCOUNT", "Sample account data inserted successfully");
            } catch ( Exception e) {
                Log.d("CREATE ACCOUNT",  e.getMessage());
            }
        }

        if (!isTableExists(db, "NOTIFICATION")) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS NOTIFICATION (" +
                        "ID_NOTIFICATION TEXT PRIMARY KEY, " +
                        "ID_ACCOUNT TEXT," +
                        "TITLE TEXT," +
                        "CONTENT TEXT," +
                        "STATUS TEXT," +
                        " FOREIGN KEY (ID_ACCOUNT) REFERENCES ACCOUNT(ID_ACCOUNT))");
                Log.d("CREATE NOTIFICATION: ", "Success");

                insertNotification(db, "ACC3", "Thông báo nghi học", "Nghỉ học từ ngày 13/4/2024 đến hết ngày 30/5/2024", "Pending");
                insertNotification(db, "ACC2", "Thông báo nghỉ học", "Lớp CLS1 nghỉ học từ ngày 13/4/2024 đến hết ngày 30/5/2024", "Sent");
                insertNotification(db, "ACC4", "Thông báo học bù", "Lớp CLS2 thi tổng kết khóa vào ngày 22/9/2024", "Pending");
                insertNotification(db, "ACC2", "Thông báo học bù", "Lớp CLS3 học bù từ ngày 13/4/2024 đến hết ngày 16/4/2024", "Sent");

                Log.d("INSERT NOTIFICATION", "Sample notification data inserted successfully");
            } catch (Exception e ) {
                Log.d("CREATE NOTIFICATION: ", e.getMessage());
            }
        }





    }

    private boolean isTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{tableName});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableAndDb(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DELETE FROM STAFF");
        db.execSQL("DELETE FROM OFFICIAL_STUDENT");
        db.execSQL("DELETE FROM POTENTIAL_STUDENT");
        db.execSQL("DELETE FROM CLASS");
        db.execSQL("DELETE FROM CLASSROOM");
        db.execSQL("DELETE FROM COLLECTING_TUITION_FEES");
        db.execSQL("DELETE FROM SCHEDULE");
        db.execSQL("DELETE FROM EXAMINATION");
        db.execSQL("DELETE FROM EXAM_SCORE");
        db.execSQL("DELETE FROM ACCOUNT");
        db.execSQL("DELETE FROM CERTIFICATE");
        db.execSQL("DELETE FROM TEACHERS");
        db.execSQL("DELETE FROM TEACHING");
        db.execSQL("DELETE FROM NOTIFICATION");
        db.execSQL("DELETE FROM PROGRAM");

        createTableAndDb(db);
    }

    public void recreateDatabase(Context context) {
        File dbFile = new File(getWritableDatabase().getPath());
        Log.d("Database path: ", dbFile.toString());
        if (!dbFile.exists()) {
            // Nếu file không tồn tại, khởi tạo lại cơ sở dữ liệu
            SQLiteDatabase db = getWritableDatabase();
            onCreate(db);
        }
    }

    public int insertData(String tableName, ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        int newRowId = (int)db.insert(tableName, null, contentValues);
        db.close();

        return newRowId;
    }

    public int deleteData(String tableName, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(tableName, whereClause, whereArgs);
        db.close();

        return rowsDeleted;
    }

    public int updateData(String tableName, ContentValues contentValues, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsUpdated = db.update(tableName, contentValues, whereClause, whereArgs);
        db.close();
        return rowsUpdated;
    }

    public Cursor selectData (String tableName, String[] columns, String whereClause, String[] whereArgs, String groupBy) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName, columns, whereClause, whereArgs, groupBy, null, null);
        return cursor;
    }

    public int getMaxId(String tableName, String column) {
        int maxId = 0;

        // Xác minh tableName và column để tránh SQL Injection
        if (tableName!= null &&!tableName.isEmpty() && column!= null &&!column.isEmpty()) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();
                String selection = "1"; // Điều kiện lọc mặc định để lấy tất cả các bản ghi
                String orderByClause = "ORDER BY abs(cast(substr(" + column + ", 4, length(" + column + ") - 3) as integer)) DESC";

                Cursor cursor = db.rawQuery("SELECT " + column + " FROM " + tableName + " WHERE " + selection + " " + orderByClause, null);

                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(column);
                    if (columnIndex!= -1) {
                        String maxIdString = cursor.getString(columnIndex);
                        String substring = maxIdString.substring(3); // Giả định rằng chuỗi bắt đầu bằng 'ID_' và số sau đó là ID thực tế
                        maxId = Integer.parseInt(substring);
                        Log.d("Get max id in DataProvider: ", String.valueOf(maxId));
                    }
                }
            } catch (NumberFormatException | SQLException e) {
                Log.d("Select max id: ", e.getMessage());
            }
        }else {
            return 0;
        }

        return maxId;
    }

    private void insertCertificate(SQLiteDatabase db, String name, String content, int status, int index) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_CERTIFICATE", "CER" + index);  // ID with "CER" + index
        contentValues.put("NAME", name);
        contentValues.put("CONTENT", content);
        contentValues.put("STATUS", status);

        // Inserting the certificate data
        db.insert("CERTIFICATE", null, contentValues);
    }

    private void insertStaff(SQLiteDatabase db, String fullname, String address, String phoneNumber, String gender,
                             String birthday, int salary, String type, int status, int index) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_STAFF", "STA" + index);  // ID with "STA" + index
        contentValues.put("FULLNAME", fullname);
        contentValues.put("ADDRESS", address);
        contentValues.put("PHONE_NUMBER", phoneNumber);
        contentValues.put("GENDER", gender);
        contentValues.put("BIRTHDAY", birthday);
        contentValues.put("SALARY", salary);
        contentValues.put("TYPE", type);
        contentValues.put("STATUS", status);

        // Inserting the staff data
        db.insert("STAFF", null, contentValues);
    }

    private void insertTeacher(SQLiteDatabase db, String fullname, String address, String phoneNumber, String gender,
                               String birthday, int salary, int status, int index) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_TEACHER", "TEA" + index);  // ID with "TEA" + index
        contentValues.put("FULLNAME", fullname);
        contentValues.put("ADDRESS", address);
        contentValues.put("PHONE_NUMBER", phoneNumber);
        contentValues.put("GENDER", gender);
        contentValues.put("BIRTHDAY", birthday);
        contentValues.put("SALARY", salary);
        contentValues.put("STATUS", status);

        // Inserting the teacher data
        db.insert("TEACHERS", null, contentValues);
    }

    private void insertClassroom(SQLiteDatabase db, String name, int status, int index) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_CLASSROOM", "CLA" + index);  // ID with "CLA" + index
        contentValues.put("NAME", name);
        contentValues.put("STATUS", status);

        // Inserting the classroom data
        db.insert("CLASSROOM", null, contentValues);
    }

    private void insertOfficialStudent(SQLiteDatabase db, String fullname, String address, String phoneNumber,
                                       String gender, String birthday, int status, int index) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_STUDENT", "STU" + index);  // ID with "STU" + index
        contentValues.put("FULLNAME", fullname);
        contentValues.put("ADDRESS", address);
        contentValues.put("PHONE_NUMBER", phoneNumber);
        contentValues.put("GENDER", gender);
        contentValues.put("BIRTHDAY", birthday);
        contentValues.put("STATUS", status);

        // Inserting the student data
        db.insert("OFFICIAL_STUDENT", null, contentValues);
    }

    private void insertCollectionTuitionFee(SQLiteDatabase db, String idStudent, String collectionDate, int totalMoney,
                                            int status, int index) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_BILL", "CTF" + (index + 1));  // ID with "CTF" + index
        contentValues.put("ID_STUDENT", idStudent);  // Assuming idStudent is the student ID like TEC1, TEC2...
        contentValues.put("COLLECTION_DATE", collectionDate);
        contentValues.put("TOTAL_MONEY", totalMoney);
        contentValues.put("STATUS", status);

        // Inserting the collection tuition fee data
        db.insert("COLLECTING_TUITION_FEES", null, contentValues);
    }

    private void insertClass(SQLiteDatabase db, String name, String startDate, String endDate, String programId,
                             String teacherId, String staffId, String status, int index) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_CLASS", "CLS" + index);  // ID with "CLS" + index
        contentValues.put("NAME", name);
        contentValues.put("START_DATE", startDate);
        contentValues.put("END_DATE", endDate);
        contentValues.put("ID_PROGRAM", programId);
        contentValues.put("ID_TEACHER", teacherId);
        contentValues.put("ID_STAFF", staffId);
        contentValues.put("STATUS", status);

        // Inserting the class data
        db.insert("CLASS", null, contentValues);
    }

    private void insertSchedule(SQLiteDatabase db, String dayOfWeek, String startTime, String endTime,
                                String classId, String classroomId, int status, int index) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_SCHEDULE", "SCH" + index);  // ID with "SCH" + index
        contentValues.put("DAY_OF_WEEK", dayOfWeek);
        contentValues.put("START_TIME", startTime);
        contentValues.put("END_TIME", endTime);
        contentValues.put("ID_CLASS", classId);
        contentValues.put("ID_CLASSROOM", classroomId);
        contentValues.put("STATUS", status);

        // Inserting the schedule data
        db.insert("SCHEDULE", null, contentValues);
    }

    private void insertExamination(SQLiteDatabase db, String name, String format, String examDate,
                                   String classId, String classroomId, int status, int index) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_EXAM", "EXA" + index);  // ID with "EXA" + index
        contentValues.put("NAME", name);
        contentValues.put("FORMAT", format);
        contentValues.put("EXAM_DATE", examDate);
        contentValues.put("ID_CLASS", classId);
        contentValues.put("ID_CLASSROOM", classroomId);
        contentValues.put("STATUS", status);

        // Inserting the examination data
        db.insert("EXAMINATION", null, contentValues);
    }

    private void insertExamScore(SQLiteDatabase db, String examId, String studentId,
                                 double speakingScore, double writingScore,
                                 double listeningScore, double readingScore,
                                 int status, int index) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_EXAM_SCORE", "ESC" + index);  // ID with "ESC" + index
        contentValues.put("ID_EXAM", examId);
        contentValues.put("ID_STUDENT", studentId);
        contentValues.put("SPEAKING_SCORE", speakingScore);
        contentValues.put("WRITING_SCORE", writingScore);
        contentValues.put("LISTENING_SCORE", listeningScore);
        contentValues.put("READING_SCORE", readingScore);
        contentValues.put("STATUS", status);

        // Inserting the exam score data
        db.insert("EXAM_SCORE", null, contentValues);
    }

    private void insertTeaching(SQLiteDatabase db, String idStudent, String idClass, int index) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_TEACHING", "TEC" + index);  // ID with "TEC" + last character of ID_STUDENT and ID_CLASS
        contentValues.put("ID_STUDENT", idStudent);
        contentValues.put("ID_CLASS", idClass);
        contentValues.put("STATUS", 1);  // Assuming "1" means active, you can adjust this as needed

        // Inserting the teaching data
        db.insert("TEACHING", null, contentValues);
    }

    private void insertProgram(SQLiteDatabase db, String name, double inputScore, double outputScore, String content,
                               double speakingScore, double writingScore, double listeningScore, double readingScore,
                               int tuitionFees, String studyPeriod, String certificateId, int status, int index) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_PROGRAM", "PRO" + index);  // ID with "PRO" + index
        contentValues.put("NAME", name);
        contentValues.put("INPUT_SCORE", inputScore);
        contentValues.put("OUTPUT_SCORE", outputScore);
        contentValues.put("CONTENT", content);
        contentValues.put("SPEAKING_SCORE", speakingScore);
        contentValues.put("WRITING_SCORE", writingScore);
        contentValues.put("LISTENING_SCORE", listeningScore);
        contentValues.put("READING_SCORE", readingScore);
        contentValues.put("TUITION_FEES", tuitionFees);
        contentValues.put("STUDY_PERIOD", studyPeriod);
        contentValues.put("ID_CERTIFICATE", certificateId);
        contentValues.put("STATUS", status);

        // Inserting the program data
        db.insert("PROGRAM", null, contentValues);
    }

    private void insertAccount(SQLiteDatabase db, String idUser, String username, String password, int status, int index) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_ACCOUNT", "ACC" +index);  // ID with "ACC" + last character of ID_USER
        contentValues.put("ID_USER", idUser);
        contentValues.put("USERNAME", username);
        contentValues.put("PASSWORD", password);
        contentValues.put("STATUS", status);

        // Inserting the account data
        db.insert("ACCOUNT", null, contentValues);
    }

    private void insertNotification(SQLiteDatabase db, String idAccount, String title, String content, String status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_NOTIFICATION", "NOT" + idAccount.substring(idAccount.length() - 1));  // ID with "NOT" + last character of ID_ACCOUNT
        contentValues.put("ID_ACCOUNT", idAccount);
        contentValues.put("TITLE", title);
        contentValues.put("CONTENT", content);
        contentValues.put("STATUS", status);

        // Inserting the notification data
        db.insert("NOTIFICATION", null, contentValues);
    }
}
