package com.example.conferenceapp.utils;

import android.annotation.TargetApi;
import android.content.Context;

import com.example.conferenceapp.models.CustomTime;
import com.example.conferenceapp.models.Paper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvParser;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

public class PaperCSVParser {

    public static ArrayList<Paper> papers;



    @TargetApi(19)
    public static void parseCSV(Context context) throws IOException{

        //Path file = FileSystems.getDefault().getPath(".", "paper_details.csv");
        InputStreamReader is = new InputStreamReader(context.getAssets()
                .open("paper_details.csv"));

        papers = new ArrayList<Paper>();

        Reader reader = new BufferedReader(is);
        CsvReader csvReader = new CsvReader();
        csvReader.setContainsHeader(true);

        CsvContainer csv = csvReader.read(reader);
        for (CsvRow row : csv.getRows()) {
            //System.out.println("First column of line: " + row.getField("name"));
            String title = row.getField("title");
            String authors[] = row.getField("authors").split(",");
            String topics[] = row.getField("topics").split("\n");
            String venue = row.getField("venue");
            String time[] = row.getField("time").split(",");
            String day = time[0];
            String date = time[1];
            String confTime[] = time[2].split("-");
            String startTime = confTime[0];
            String endTime = confTime[1];
            String abs = row.getField("abstract");
            CustomTime paper_schedule = new CustomTime(date, startTime, endTime, day);
            Paper paper = new Paper(title, venue, paper_schedule, authors, topics, abs);
            papers.add(paper);

        }



    }
}
