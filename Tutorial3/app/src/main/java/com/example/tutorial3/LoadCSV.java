package com.example.tutorial3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

        ArrayList<String[]> csvData = DataHandler.CsvRead(getString(R.string.data_path));
        ArrayList<String[]> csvGaussianData = DataHandler.CsvRead(getString(R.string.gaussian_data_path));

        LineDataSet lineDataSet1 =  new LineDataSet(DataValues(csvData),"Data Set 1");
        LineDataSet gaussianDataSet =  new LineDataSet(DataValues(csvGaussianData),"Gaussian data");

        lineDataSet1.setColor(getResources().getColor(R.color.red));
        gaussianDataSet.setColor(getResources().getColor(R.color.green));

        lineDataSet1.setDrawCircles(false);
        gaussianDataSet.setDrawCircles(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(gaussianDataSet);
        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();


        Button LiveDataButton = (Button) findViewById(R.id.open_live_data_button);
        Button StatisticsButton = (Button) findViewById(R.id.open_statistics_button);

        LiveDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenLiveData();
            }
        });

        StatisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenStatistics();
            }
        });
    }


    private ArrayList<Entry> DataValues(ArrayList<String[]> csvData){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        for (int i = 0; i < csvData.size(); i++){

            dataVals.add(new Entry(i,Integer.parseInt(csvData.get(i)[1])));


        }

            return dataVals;
    }

    private void OpenLiveData(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void OpenStatistics(){
        Intent intent = new Intent(this, Statistics.class);
        startActivity(intent);
    }

}