package com.example.tutorial3;

import android.util.Log;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class DataHandler
{
    public static ArrayList<String[]> CsvRead(String path){
        ArrayList<String[]> CsvData = new ArrayList<>();
        try {
            File file = new File(path);
            CSVReader reader = new CSVReader(new FileReader(file));
            String[]nextline;
            while((nextline = reader.readNext())!= null){
                if(nextline != null){
                    CsvData.add(nextline);

                }
            }

        }catch (Exception e)
        {
            Log.d("Read", e.getMessage());

        }
        return CsvData;
    }

    public static ArrayList<String[]> TransposeArray(ArrayList<String[]> array){
        ArrayList<String[]> new_array = new ArrayList<String[]>();
        String[] first_col = new String[array.size()];
        String[] second_col = new String[array.size()];

        for(int i = 0; i < array.size(); i++)
        {
            String[] row = array.get(i);
            first_col[i] = row[0];
            second_col[i] = row[1];
        }

        new_array.add(first_col);
        new_array.add(second_col);

        return new_array;
    }
}
