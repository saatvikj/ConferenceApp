package com.example.conferenceapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.conferenceapp.models.Paper;

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

    public void insert(Paper paper) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("title", paper.getTitle());
        contentValue.put("authors", Arrays.toString(paper.getAuthors()));
        contentValue.put("topics", Arrays.toString(paper.getTopics()));
        contentValue.put("venue", paper.getVenue());
        contentValue.put("schedule", paper.getTime().toString());
        contentValue.put("abstract", paper.getPaper_abstract());
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { "title", "authors", "topics", "venue", "schedule", "abstract"};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public void delete(Paper paper) {
        String title = paper.getTitle();
        database.delete(DatabaseHelper.TABLE_NAME, "title" + "= \'" + title+"\'", null);
    }
}
