package com.example.conferenceapp.utils;

import android.annotation.TargetApi;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import com.example.conferenceapp.models.*;

import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

public class ProgramCSVParser {

    public static ArrayList<Session> sessions;

    @TargetApi(19)
    public static ArrayList<Session> parseCSV(Context context) throws IOException {

        InputStreamReader is = new InputStreamReader(context.getAssets()
                .open("session_program.csv"));

        Reader reader = new BufferedReader(is);
        CsvReader csvReader = new CsvReader();
        Conference conference = null;
        CsvContainer csv = csvReader.read(reader);
        sessions = new ArrayList<Session>();

        for (CsvRow row : csv.getRows()) {
            int id = Integer.parseInt(row.getField(0));
            String title = row.getField(1);
            String datetime[] = row.getField(2).split(",");
            String date = datetime[0];
            String confTime[] = datetime[1].split("-");
            String startTime = confTime[0];
            String endTime = confTime[1];
            CustomTime time = new CustomTime(date,startTime,endTime);
            String type = row.getField(3);
            String click = row.getField(4);
            Boolean clickable = false;

            if(click.equals("TRUE")){
                clickable = true;
            }

            Session session = new Session(id, title, time, type, clickable);
            sessions.add(session);

        }

        return sessions;
    }

}
