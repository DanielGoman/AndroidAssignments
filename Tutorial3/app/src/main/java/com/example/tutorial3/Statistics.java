package com.example.tutorial3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Button openLiveDataButton = (Button) findViewById(R.id.open_live_data_button);
        Button openLoadCSVButton = (Button) findViewById(R.id.open_load_csv_button);

        openLiveDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenLiveData();
            }
        });

        openLoadCSVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenLoadCSV();
            }
        });

        BarChart barChart = (BarChart) findViewById(R.id.bar_chart);
        setupBarChart(barChart, getString(R.string.data_path), getString(R.string.gaussian_data_path));
    }

    private void setupBarChart(BarChart barChart, String first_csv_path , String second_csv_path)
    {
        ArrayList<String[]> first_csv = DataHandler.CsvRead(first_csv_path);
        ArrayList<String[]> second_csv = DataHandler.CsvRead(second_csv_path);

        first_csv = DataHandler.TransposeArray(first_csv);
        second_csv = DataHandler.TransposeArray(second_csv);

        double random_mean = CalcMean(first_csv.get(0));
        double random_std = CalcStd(first_csv.get(1), random_mean);

        double gaussian_mean = CalcMean(second_csv.get(0));
        double gaussian_std = CalcStd(second_csv.get(1), gaussian_mean);

        ArrayList<BarEntry> random_statistics = new ArrayList<BarEntry>();
        ArrayList<BarEntry> gaussian_statistics = new ArrayList<BarEntry>();

        random_statistics.add(new BarEntry(0f, (float) random_mean));
        random_statistics.add(new BarEntry(1f, (float) random_std));

        gaussian_statistics.add(new BarEntry(0f, (float) gaussian_mean));
        gaussian_statistics.add(new BarEntry(1f, (float) gaussian_std));

        BarDataSet randomDataSet = new BarDataSet(random_statistics, "Random dataset stats");
        BarDataSet gaussianDataSet = new BarDataSet(gaussian_statistics, "Gaussian dataset stats");

        randomDataSet.setColor(getResources().getColor(R.color.red));
        gaussianDataSet.setColor(getResources().getColor(R.color.green));

        Log.d("stats", random_statistics.toString());
        Log.d("stats", gaussian_statistics.toString());

        ArrayList<IBarDataSet> datasets = new ArrayList<IBarDataSet>();
        datasets.add(randomDataSet);
        datasets.add(gaussianDataSet);

        BarData data = new BarData(datasets);

        float groupSpace = 0.4f;
        float barSpace = 0f;
        data.setBarWidth(0.2f);
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.groupBars(0f, groupSpace, barSpace);
        barChart.invalidate();
    }

    private double CalcMean(String[] dataList){
        double sum = 0;
        for (String item : dataList) {
            sum += Integer.parseInt(item);
        }
        return sum / dataList.length;
    }

    private double CalcStd(String[] dataList, double mean){
        double sum = 0;
        for (String item : dataList) {
            sum += Math.pow(Integer.parseInt(item) - mean, 2);
        }
        return Math.sqrt(sum / dataList.length);
    }

    private void OpenLiveData(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void OpenLoadCSV(){
        Intent intent = new Intent(this, LoadCSV.class);
        startActivity(intent);
    }
}