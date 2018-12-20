package com.example.conferenceapp.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.widget.Toast;

import com.example.conferenceapp.models.About;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.Food;
import com.example.conferenceapp.models.Partner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

public class ConferenceCSVParser {

    @TargetApi(19)
    public static Conference parseCSV(Context context) throws IOException {

        //Path file = FileSystems.getDefault().getPath(".", "paper_details.csv");
        InputStreamReader is = new InputStreamReader(context.getAssets()
                .open("conference_data.csv"));

        Reader reader = new BufferedReader(is);
        CsvReader csvReader = new CsvReader();
        Conference conference = null;
        CsvContainer csv = csvReader.read(reader);
        for (CsvRow row : csv.getRows()) {
            String name = row.getField(0);
            String venue = row.getField(1);
            String start_day = row.getField(2);
            String end_day = row.getField(3);
            About about = new About(row.getField(4), row.getField(5), row.getField(6),row.getField(7), row.getField(8));

            String partners[] = row.getField(9).replace("[","").replace("]","").replace("\"","").split(",");
            Partner partners_list[] = new Partner[partners.length/3];
            for (int i=0; i< partners.length/3; i++) {
                String partner_name = partners[3 * i];
                String partner_type = partners[3 * i + 1];
                String partner_website = partners[3 * i + 2];
                Partner partner = new Partner(i, partner_name, partner_type, partner_website, 0);
                partners_list[i] = partner;
            }
            String id = row.getField(11);
            conference = new Conference(id, name, venue, about,start_day, end_day, partners_list, null);

        }

        return conference;
    }
}