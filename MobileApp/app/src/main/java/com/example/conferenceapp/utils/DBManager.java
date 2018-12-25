package com.example.conferenceapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.conferenceapp.models.Paper;
import com.example.conferenceapp.models.Session;

import java.util.ArrayList;
import java.util.Arrays;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(Session session) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("title", session.getTitle());
        contentValue.put("schedule", session.getDateTime().toString());
        contentValue.put("id", Integer.toString(session.getID()));
        contentValue.put("clickable", Boolean.toString(session.isClickable()));
        contentValue.put("type", session.getType());
        contentValue.put("icon", session.getIconDrawable());
        contentValue.put("bullet", session.getBulletDrawable());
        contentValue.put("venue", session.getVenue());
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { "title", "schedule", "id", "clickable","type","icon","bullet","venue"};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public void delete(Session session) {
        String title = session.getTitle();
        database.delete(DatabaseHelper.TABLE_NAME, "title" + "= \'" + title+"\'", null);
    }
}
