package com.example.conferenceapp.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.conferenceapp.models.About;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.CustomTime;
import com.example.conferenceapp.models.Food;
import com.example.conferenceapp.models.Partner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

public class ConferenceCSVParser {

    @TargetApi(19)
    public static Conference parseCSV(Context context) throws IOException {

        CsvReader csvReader = new CsvReader();
        csvReader.setContainsHeader(true);


        InputStreamReader is = new InputStreamReader(context.getAssets()
                .open("conference_sponsors.csv"));
        Reader reader = new BufferedReader(is);
        CsvContainer csv = csvReader.read(reader);

        ArrayList<Partner> partners = new ArrayList<>();
        for (CsvRow row : csv.getRows()) {
            String name = row.getField("company");
            String type = row.getField("type");
            String website = row.getField("website");
            String imageID = row.getField("image");
            partners.add(new Partner(0, name, type, website, imageID));
        }

        Partner[] partners_list = partners.toArray(new Partner[partners.size()]);

        is = new InputStreamReader(context.getAssets().open("conference_food_events.csv"));
        reader = new BufferedReader(is);
        csv = csvReader.read(reader);

        ArrayList<Food> food_events = new ArrayList<>();
        for (CsvRow row : csv.getRows()) {
            String name = row.getField("event-name");
            String location = row.getField("location");
            String details = row.getField("details");
            String start_time = row.getField("start-time");
            String end_time = row.getField("end-time");
            CustomTime time_range = new CustomTime(start_time.split("T")[0], start_time.split("T")[1], end_time.split("T")[1]);

            food_events.add(new Food(time_range, location, name, details));
        }
        Food[] food_guide = food_events.toArray(new Food[food_events.size()]);

        is = new InputStreamReader(context.getAssets().open("conference_data.csv"));
        reader = new BufferedReader(is);
        csv = csvReader.read(reader);
        Conference conference = null;

        for (CsvRow row : csv.getRows()) {
            String name = row.getField("conference-name");
            String venue = row.getField("conference-venue");
            String start_day = row.getField("start-date");
            String end_day = row.getField("end-date");
            About about = new About(row.getField("about"), row.getField("website"), row.getField("facebook"),row.getField("twitter"), row.getField("contact"));
            String id = row.getField("id");
            conference = new Conference(id, name, venue, about,start_day, end_day, partners_list, food_guide);
        }
        return conference;
    }
}
