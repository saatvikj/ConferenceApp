package com.example.conferenceapp.utils;

import android.annotation.TargetApi;
import android.content.Context;

import com.example.conferenceapp.models.CustomTime;
import com.example.conferenceapp.models.Paper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

public class PaperCSVParser {

    public static ArrayList<Paper> papers;



    @TargetApi(19)
    public static void parseCSV(Context context) throws IOException{

        InputStreamReader is = new InputStreamReader(context.getAssets()
                .open("conference_schedule.csv"));

        papers = new ArrayList<Paper>();

        Reader reader = new BufferedReader(is);
        CsvReader csvReader = new CsvReader();
        csvReader.setContainsHeader(true);

        CsvContainer csv = csvReader.read(reader);
        for (CsvRow row : csv.getRows()) {
            String title = row.getField("title");
            String[] authors = row.getField("authors").split(",");
            String[] topics = row.getField("topics").split("\n");
            String venue = row.getField("venue");
            String[] time = row.getField("time").split(",");
            String date = time[0];
            String[] confTime = time[1].split("-");
            String startTime = confTime[0];
            String endTime = confTime[1];
            String abs = row.getField("abstract");
            CustomTime paper_schedule = new CustomTime(date, startTime, endTime);
            Paper paper = new Paper(title, venue, paper_schedule, authors, topics, abs);
            papers.add(paper);

        }



    }

    public static Paper[] get_papers_for_day(String date) {

        ArrayList<Paper> papers_for_day = new ArrayList<>();

        for (int i = 0; i < papers.size(); i++) {
            Paper paper = papers.get(i);

            if (paper.getTime().date.equalsIgnoreCase(date)) {
                papers_for_day.add(paper);
            }
        }

        return papers_for_day.toArray(new Paper[papers_for_day.size()]);
    }
}
