package com.example.tutorial3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    LineChart mpLineChart;
    int counter = 1;
    int val = 40;

    int mean = 50;
    int std = 50;
    private Handler mHandler = new Handler();  //Handler is used for delay definition in the loop

    Random random = new Random();

    public MainActivity() throws FileNotFoundException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }

        mpLineChart = (LineChart) findViewById(R.id.line_chart);
        LineDataSet lineDataSet1 =  new LineDataSet(dataValues1(), "Data Set 1");
        LineDataSet gaussianDataSet = new LineDataSet(dataValues1(), "Gaussian Data Set");

        lineDataSet1.setColor(getResources().getColor(R.color.red));
        gaussianDataSet.setColor(getResources().getColor(R.color.green));

        lineDataSet1.setDrawCircles(false);
        gaussianDataSet.setDrawCircles(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        dataSets.add(lineDataSet1);
        dataSets.add(gaussianDataSet);
        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();

        Button buttonClear = (Button) findViewById(R.id.clear_button);
        Button buttonLoadCSV = (Button) findViewById(R.id.open_load_csv_button);
        Button buttonOpenStatistics = (Button) findViewById(R.id.open_statistics_button);

        buttonLoadCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenLoadCSV();
            }
        });

        buttonOpenStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenStatistics();
            }
        });

        LineDataSet finalLineDataSet = lineDataSet1;

        Runnable DataUpdate = new Runnable(){
            @Override
            public void run() {
                int val = (int) (Math.random() * 80);
                int normVal = (int) generateGaussianVal();
                data.addEntry(new Entry(counter,val),0);
                data.addEntry(new Entry(counter, normVal), 1);

                finalLineDataSet.notifyDataSetChanged(); // let the data know a dataSet changed
                mpLineChart.notifyDataSetChanged(); // let the chart know it's data changed
                mpLineChart.invalidate(); // refresh

                saveToCsv("sdcard/csv_dir", "data.csv", String.valueOf(counter),String.valueOf(val));
                saveToCsv("sdcard/csv_dir", "gaussian_data.csv", String.valueOf(counter),String.valueOf(normVal));

                counter += 1;
                mHandler.postDelayed(this,500);
            }
        };

        buttonClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Clear",Toast.LENGTH_SHORT).show();
                LineData data = mpLineChart.getData();
                ILineDataSet set = data.getDataSetByIndex(0);
                ILineDataSet gaussianData = data.getDataSetByIndex(1);

                while(set.removeLast() && gaussianData.removeLast()){}
                counter = 1;

            }
        });

        
        mHandler.postDelayed(DataUpdate,500);
    }


    private double generateGaussianVal()
    {
        return random.nextGaussian() * std + mean;
    }

    private ArrayList<Entry> dataValues1()
    {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0,0));
        return dataVals;
    }

    private void saveToCsv(String outDirPath, String csvName, String str1, String str2){
        try{
            File file = new File(outDirPath);
            file.mkdirs();
            String csvPath = outDirPath + '/' + csvName;
            Log.d("Write", "Writing into " + csvPath);
            CSVWriter csvWriter = new CSVWriter(new FileWriter(csvPath,true));
            String row[]= new String[]{str1,str2};
            csvWriter.writeNext(row);
            csvWriter.close();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this,"ERROR",Toast.LENGTH_LONG).show();

            Log.d("Write", e.getMessage());
            e.printStackTrace();
        }
    }

   private void OpenLoadCSV(){
        Intent intent = new Intent(this,LoadCSV.class);
        startActivity(intent);
   }

   private void OpenStatistics(){
       Intent intent = new Intent(this, Statistics.class);
       startActivity(intent);
   }
}
