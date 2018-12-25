package com.example.conferenceapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "PAPERS";

    // Database Information
    static final String DB_NAME = "USER_AGENDA.DB";

    // database version
    static final int DB_VERSION = 3;

    private static final String CREATE_TABLE = "CREATE TABLE `PAPERS` (\n" +
            "\t`title`\tTEXT,\n" +
            "\t`schedule`\tTEXT,\n" +
            "\t`id`\tTEXT,\n" +
            "\t`clickable`\tTEXT,\n" +
            "\t`type`\tTEXT,\n" +
            "\t`icon`\tTEXT,\n" +
            "\t`bullet`\tTEXT,\n" +
            "\t`venue`\tTEXT\n" +
            ")";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
