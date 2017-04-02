package hackprinceton.checks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import hackprinceton.checks.contract.TaskContract;

import static hackprinceton.checks.contract.HeaderContract.HeaderEntry.*;

/**
 * Created by Kentaro on 4/1/2017.
 */

public class ChecksDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    private String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_NAME_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME_NAME + " TEXT,"
            + COLUMN_NAME_EMAIL + " TEXT," + COLUMN_NAME_INTERVAL + " INTEGER," +
            COLUMN_NAME_DATA + " INTEGER," + COLUMN_NAME_NEXT + " INTEGER"+ ")";

    private String SQL_CREATE_TASK = "CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME;
    private String SQL_CREATE_TASK_2 = "("
            + TaskContract.TaskEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
            TaskContract.TaskEntry.COLUMN_NAME_NAME + " INTEGER,"
            + TaskContract.TaskEntry.COLUMN_NAME_DAYS + " TEXT" + ")";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String SQL_DELETE_TASKS =
            "DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_NAME;

    public ChecksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    long addHeader(HeaderRow header) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, header.getName());
        values.put(COLUMN_NAME_EMAIL, header.getEmail());
        values.put(COLUMN_NAME_INTERVAL, header.getInterval());
        values.put(COLUMN_NAME_DATA, header.getDb());
        values.put(COLUMN_NAME_NEXT, header.getNext());

        // Inserting Row
        long a = db.insert(TABLE_NAME, null, values);
        db.execSQL(SQL_CREATE_TASK + Integer.toString(header.getDb()) + SQL_CREATE_TASK_2);
        db.close(); // Closing database connection
        return a;
    }

    void addTask(int table, TaskRow task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_NAME, task.getName());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DAYS, task.getDays());

        // Inserting Row
        db.insert(TaskContract.TaskEntry.TABLE_NAME + Integer.toString(table), null, values);
        db.close(); // Closing database connection
    }

    public List<TaskRow> getTasks(int table) {
        List<TaskRow> taskList = new ArrayList<TaskRow>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TaskContract.TaskEntry.TABLE_NAME + Integer.toString(table);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TaskRow row = new TaskRow();
                row.setID(Integer.parseInt(cursor.getString(0)));
                row.setName(cursor.getString(1));
                row.setDays(cursor.getString(2));

                taskList.add(row);
            } while (cursor.moveToNext());
        }

        return taskList;
    }

    public TaskRow getTask(int table, int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE_NAME + Integer.toString(table), new String[] { TaskContract.TaskEntry.COLUMN_NAME_ID,
                        TaskContract.TaskEntry.COLUMN_NAME_NAME, TaskContract.TaskEntry.COLUMN_NAME_DAYS},
                COLUMN_NAME_ID + "=?",new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        TaskRow row = new TaskRow();
        row.setID(Integer.parseInt(cursor.getString(0)));
        row.setName(cursor.getString(1));
        row.setDays(cursor.getString(2));

        // return contact
        return row;
    }

    public HeaderRow getHeader(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_NAME_ID,
                        COLUMN_NAME_NAME, COLUMN_NAME_EMAIL, COLUMN_NAME_INTERVAL, COLUMN_NAME_DATA, COLUMN_NAME_NEXT },
                COLUMN_NAME_ID + "=?",new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        HeaderRow row = new HeaderRow();
        row.setID(Integer.parseInt(cursor.getString(0)));
        row.setName(cursor.getString(1));
        row.setEmail(cursor.getString(2));
        row.setInterval(Integer.parseInt(cursor.getString(3)));
        row.setDb(Integer.parseInt(cursor.getString(4)));
        row.setNext(Long.parseLong(cursor.getString(5)));

        // return contact
        return row;
    }


    public List<HeaderRow> getAllHeaders() {
        List<HeaderRow> headerList = new ArrayList<HeaderRow>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HeaderRow row = new HeaderRow();
                row.setID(Integer.parseInt(cursor.getString(0)));
                row.setName(cursor.getString(1));
                row.setEmail(cursor.getString(2));
                row.setInterval(Integer.parseInt(cursor.getString(3)));
                row.setDb(Integer.parseInt(cursor.getString(4)));
                row.setNext(Long.parseLong(cursor.getString(5)));

                headerList.add(row);
            } while (cursor.moveToNext());
        }

        return headerList;
    }

    public int updateHeader(HeaderRow header) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, header.getName());
        values.put(COLUMN_NAME_EMAIL, header.getEmail());
        values.put(COLUMN_NAME_INTERVAL, header.getInterval());
        values.put(COLUMN_NAME_DATA, header.getDb());
        values.put(COLUMN_NAME_NEXT, header.getNext());

        // updating row
        return db.update(TABLE_NAME, values, COLUMN_NAME_ID + " = ?",
                new String[] { String.valueOf(header.getID()) });
    }

    public int updateTask(int table, TaskRow task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_NAME, task.getName());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DAYS, task.getDays());

        // updating row
        return db.update(TaskContract.TaskEntry.TABLE_NAME + Integer.toString(table), values,
                TaskContract.TaskEntry.COLUMN_NAME_ID + " = ?",
                new String[] { String.valueOf(task.getID()) });
    }

    public void deleteHeader(HeaderRow headerRow) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL_DELETE_TASKS + Integer.toString(headerRow.getDb()));
        db.delete(TABLE_NAME, COLUMN_NAME_ID + " = ?",
                new String[] { String.valueOf(headerRow.getID()) });
        db.close();
    }


    public void deleteTask(int table, TaskRow task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE_NAME + Integer.toString(table),
                TaskContract.TaskEntry.COLUMN_NAME_ID + " = ?", new String[] { String.valueOf(task.getID()) });
        db.close();
    }


}
