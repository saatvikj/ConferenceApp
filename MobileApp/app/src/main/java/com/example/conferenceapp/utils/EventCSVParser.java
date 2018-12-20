package com.example.conferenceapp.utils;

import android.annotation.TargetApi;
import android.content.Context;

import com.example.conferenceapp.models.CustomTime;
import com.example.conferenceapp.models.Event;
import com.example.conferenceapp.models.Paper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

public class EventCSVParser {

    public static ArrayList<Event> events;

    @TargetApi(19)
    public static ArrayList<Event> parseCSV(Context context, int id) throws IOException{

        String file_name = Integer.toString(id).concat(".csv");

        InputStreamReader is = new InputStreamReader(context.getAssets()
                .open(file_name));

        events = new ArrayList<Event>();

        Reader reader = new BufferedReader(is);
        CsvReader csvReader = new CsvReader();
        csvReader.setContainsHeader(true);

        CsvContainer csv = csvReader.read(reader);
        for (CsvRow row : csv.getRows()) {
            String title = row.getField("Title");
            String _abstract = row.getField("Abstract");
            String authors[] = row.getField("Authors").split(",");

            Event event = new Event(title, _abstract, authors);
            events.add(event);
        }
        return events;
    }
}
