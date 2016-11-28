package com.mayankattri.mc_project_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayank on 1/10/16.
 */
public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "TAO";

    // Email table name
    private static final String TABLE_EMAIL = "EMAIL_TABLE";

    // Email Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_BODY = "body";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " +
                TABLE_EMAIL + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_EMAIL + " TEXT," +
                KEY_SUBJECT + " TEXT," +
                KEY_BODY + " TEXT," +
                KEY_DATE + " TEXT," +
                KEY_TIME + " TEXT" + ")";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMAIL);
        // Creating tables again
        onCreate(db);
    }

    // Adding new note
    public void addEmailTask(EmailTask task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_ID, note.getID());
        values.put(KEY_EMAIL, task.getEmail());
        values.put(KEY_SUBJECT, task.getSubject());
        values.put(KEY_BODY, task.getBody());
        values.put(KEY_DATE, task.getDate());
        values.put(KEY_TIME, task.getTime());

        // Inserting Row
        db.insert(TABLE_EMAIL, null, values);
        db.close(); // Closing database connection
    }

    // Getting one Note
    public EmailTask getEmailTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EMAIL,
                new String[]{KEY_ID, KEY_EMAIL, KEY_SUBJECT, KEY_BODY, KEY_DATE, KEY_TIME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        EmailTask task = new EmailTask(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));

        return task;
    }

    // Getting All EmailTasks
    public List<EmailTask> getAllEmailTask() {
        List<EmailTask> taskList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EMAIL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EmailTask task = new EmailTask();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setEmail(cursor.getString(1));
                task.setSubject(cursor.getString(2));
                task.setBody(cursor.getString(3));
                task.setDate(cursor.getString(4));
                task.setTime(cursor.getString(5));
                // Adding item to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        // return TO_DO_LIST
        return taskList;
    }

    // Getting notes Count
    public int getEmailTaskCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EMAIL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating a note
    public int updateEmailTask(EmailTask task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, task.getId());
        values.put(KEY_EMAIL, task.getEmail());
        values.put(KEY_SUBJECT, task.getSubject());
        values.put(KEY_BODY, task.getBody());
        values.put(KEY_DATE, task.getDate());
        values.put(KEY_TIME, task.getTime());

        // updating row
        return db.update(TABLE_EMAIL, values, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    // Deleting a note
    public void deleteEmailTask(EmailTask task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMAIL, KEY_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
        db.close();
    }
}
