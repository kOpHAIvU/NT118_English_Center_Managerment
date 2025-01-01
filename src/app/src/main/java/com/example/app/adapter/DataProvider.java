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
        try {

            db.execSQL("CREATE TABLE IF NOT EXISTS CERTIFICATE (" +
                    "ID_CERTIFICATE TEXT PRIMARY KEY, " +
                    "NAME TEXT, " +
                    "CONTENT TEXT, " +
                    "STATUS INTEGER)");
            Log.d("CREATE CERTIFICATE", "Database created successfully");
        } catch ( Exception e) {
            Log.d("EXCEPTION CREATE CERTIFICATE",  e.getMessage());
        }

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
        } catch ( Exception e) {
            Log.d("CREATE STAFF",  e.getMessage());
        }

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
        } catch ( Exception e) {
            Log.d("CREATE TEACHERS",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS CLASSROOM (" +
                    "ID_CLASSROOM TEXT PRIMARY KEY, " +
                    "NAME TEXT, " +
                    "STATUS INTEGER, " +
                    "CAPACITY INTEGER)"); // Thêm trường sức chứa
            Log.d("CREATE CLASSROOM", "Database created successfully");
        } catch (Exception e) {
            Log.d("CREATE CLASSROOM", e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS POTENTIAL_STUDENT (" +
                    "ID_STUDENT TEXT PRIMARY KEY, " +
                    "FULLNAME TEXT, " +
                    "ADDRESS TEXT, " +
                    "PHONE_NUMBER TEXT, " +
                    "GENDER TEXT," +
                    "LEVEL TEXT, " +
                    "NUMBER_OF_APPOINTMENTS INTEGER, " +
                    "STATUS INTEGER)");
            Log.d("CREATE POTENTIAL_STUDENTS", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE POTENTIAL_STUDENTS",  e.getMessage());
        }

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
        } catch ( Exception e) {
            Log.d("CREATE OFFICIAL_STUDENTS",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS COLLECTING_TUITION_FEES (" +
                    "ID_BILL TEXT PRIMARY KEY , " +
                    "ID_STUDENT TEXT, " +
                    "COLLECTION_DATE TEXT, " +
                    "TOTAL_MONEY INTEGER, " +
                    "STATUS INTEGER," +
                    "FOREIGN KEY (ID_STUDENT) REFERENCES OFFICAL_STUDENT(ID_STUDENT))");
            Log.d("CREATE COLLECTING_TUITION_FEES", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE COLLECTING_TUITION_FEES",  e.getMessage());
        }

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
                    "COLOR_RES_ID TEXT," +
                    "FOREIGN KEY (ID_PROGRAM) REFERENCES PROGRAM(ID_PROGRAM)," +
                    "FOREIGN KEY (ID_STAFF) REFERENCES STAFF(ID_STAFF)," +
                    "FOREIGN KEY (ID_TEACHER) REFERENCES TEACHER(ID_TEACHER))");
            Log.d("CREATE CLASS", "Database created successfully");
        } catch ( SQLException e) {
            Log.d("CREATE CLASS",  e.getMessage());
        }

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
        } catch( Exception e) {
            Log.d("CREATE SCHEDULE ",  e.getMessage());
        }

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
        } catch ( Exception e) {
            Log.d("CREATE EXAMINATION",  e.getMessage());
        }

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
        } catch ( Exception e) {
            Log.d("CREATE EXAM_SCORE",  e.getMessage());
        }
        //db.execSQL("DELETE FROM TEACHING");
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TEACHING (" +
                    "ID_TEACHING TEXT PRIMARY KEY , " +
                    "ID_STUDENT TEXT , " +
                    "ID_CLASS TEXT, " +
                    "STATUS INTEGER, " +
                    "FOREIGN KEY (ID_STUDENT) REFERENCES OFFICAL_STUDENT(ID_STUDENT)," +
                    "FOREIGN KEY (ID_CLASS) REFERENCES CLASS(ID_CLASS))");
            Log.d("CREATE EXAM_SCORE", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE EXAM_SCORE",  e.getMessage());
        }

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
        } catch ( Exception e) {
            Log.d("CREATE PROGRAM",  e.getMessage());
        }

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
        } catch ( Exception e) {
            Log.d("CREATE ACCOUNT",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS NOTIFICATION (" +
                    "ID_NOTIFICATION TEXT PRIMARY KEY, " +
                    "ID_ACCOUNT TEXT," +
                    "TITLE TEXT," +
                    "CONTENT TEXT," +
                    "STATUS TEXT," +
                    " FOREIGN KEY (ID_ACCOUNT) REFERENCES ACCOUNT(ID_ACCOUNT))");
            Log.d("CREATE NOTIFICATION: ", "Success");
        } catch (Exception e ) {
            Log.d("CREATE NOTIFICATION: ", e.getMessage());
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

    private void insertClassroom(SQLiteDatabase db, String name, int status, int index, int capacity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_CLASSROOM", "CLA" + index);  // ID with "CLA" + index
        contentValues.put("NAME", name);
        contentValues.put("STATUS", status);
        contentValues.put("CAPACITY", capacity); // Thêm sức chứa vào ContentValues

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
