package com.example.tutorial6;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import java.util.List;


public class LoadCSV extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_csv);

        LineChart lineChart = (LineChart) findViewById(R.id.line_chart);

        Spinner select_csv_spinner = (Spinner) findViewById(R.id.select_csv_spinner);
        ArrayList<String> csv_list = getListDir(getResources().getString(R.string.data_dir_path));

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                                                R.layout.spinner_item, csv_list);

        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        select_csv_spinner.setAdapter(spinnerAdapter);


        Button BackButton = (Button) findViewById(R.id.button_back);
        Button loadCSVButton = (Button) findViewById(R.id.load_csv_button);


        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickBack();
            }
        });

        loadCSVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String csv_name = select_csv_spinner.getSelectedItem().toString();
                String csv_path = getResources().getString(R.string.data_dir_path) + '/' + csv_name;

                LineDataSet lineDataSet1 =  new LineDataSet(new ArrayList<Entry>(),"ACC X");
                LineDataSet lineDataSet2 =  new LineDataSet(new ArrayList<Entry>(),"ACC Y");
                LineDataSet lineDataSet3 =  new LineDataSet(new ArrayList<Entry>(),"ACC Z");

                lineDataSet1.setColor(getResources().getColor(R.color.red));
                lineDataSet2.setColor(getResources().getColor(R.color.green));
                lineDataSet3.setColor(getResources().getColor(R.color.blue));

                ArrayList<String[]> csvData = CsvRead(csv_path);
                for(String[] line: csvData)
                {
                    lineDataSet1.addEntry(new Entry(Float.parseFloat(line[0]), Float.parseFloat(line[1])));
                    lineDataSet2.addEntry(new Entry(Float.parseFloat(line[0]), Float.parseFloat(line[2])));
                    lineDataSet3.addEntry(new Entry(Float.parseFloat(line[0]), Float.parseFloat(line[3])));
                }

                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(lineDataSet1);
                dataSets.add(lineDataSet2);
                dataSets.add(lineDataSet3);
                LineData data = new LineData(dataSets);
                lineChart.setData(data);
                lineChart.invalidate();
            }
        });
    }

    private void ClickBack(){
        finish();

    }

    private ArrayList<String[]> CsvRead(String path){
        int numRedundantLines = 6;
        ArrayList<String[]> CsvData = new ArrayList<>();
        try {
            File file = new File(path);
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] nextLine;
            int lineNum = 0;
            while((nextLine = reader.readNext())!= null)
            {
                if(lineNum >= numRedundantLines){
                    CsvData.add(nextLine);
                }
                lineNum++;

            }

        }catch (Exception e){}
        return CsvData;
    }

    private ArrayList<String> getListDir(String data_dir_path)
    {
        ArrayList<String> csv_list = new ArrayList<String>();
        File dir = new File(data_dir_path);
        File[] files = dir.listFiles();
        for (File file: files)
        {
            String file_name = file.getName();
            Log.d("list_dir", file_name);
            if(file_name.length() > 4)
            {
                String file_extension = file_name.substring(file_name.length() - 4, file_name.length());
                Log.d("list_dir_extension", file_extension);
                if (file_extension.equals(".csv")) {
                    Log.d("list_dir_added", file_name);
                    csv_list.add(file_name);
                }
            }

        }

        return csv_list;
    }


}