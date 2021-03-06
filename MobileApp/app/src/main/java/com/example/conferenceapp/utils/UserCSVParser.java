package com.example.conferenceapp.utils;

import android.annotation.TargetApi;
import android.content.Context;

import com.example.conferenceapp.models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

public class UserCSVParser {

    public static ArrayList<User> users;

    @TargetApi(19)
    public static ArrayList<User> parseCSV(Context context) throws IOException {

        //Path file = FileSystems.getDefault().getPath(".", "paper_details.csv");
        InputStreamReader is = new InputStreamReader(context.getAssets()
                .open("conference_user.csv"));

        users = new ArrayList<User>();

        Reader reader = new BufferedReader(is);
        CsvReader csvReader = new CsvReader();
        csvReader.setContainsHeader(true);

        CsvContainer csv = csvReader.read(reader);
        for (CsvRow row : csv.getRows()) {
            String name = row.getField("Name");
            String email = row.getField("Email");
            String company = row.getField("Company");
            User user = new User(null, name, email, company, null);
            users.add(user);

        }

        return users;

    }


}
