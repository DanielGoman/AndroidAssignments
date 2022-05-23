package com.example.tutorial6;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.slider.Slider;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class SaveCSVFragment extends Fragment {

    public SaveCSVFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.save_csv_fragment, container, false);

        Button save_button = (Button) inflatedView.findViewById(R.id.save_button);
        Button cancel_save_button = (Button) inflatedView.findViewById(R.id.cancel_save_button);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText csv_name_et = (EditText) inflatedView.findViewById(R.id.csv_name_et);
                EditText steps_made_et = (EditText) inflatedView.findViewById(R.id.step_made_et);
                Spinner mode_spinner = (Spinner) inflatedView.findViewById(R.id.mode_spinner);

                Editable a = csv_name_et.getText();
                String csv_name = a.toString();
                String steps_made = steps_made_et.getText().toString();
                String estimated_steps_made = String.valueOf(TerminalFragment.currEstimatedSteps);
                String mode = mode_spinner.getSelectedItem().toString();

                //saving data to csv

                try {
                    //create new csv unless file already exists
                    String data_dir_path = getString(R.string.data_dir_path);
                    File file = new File(data_dir_path);
                    file.mkdirs();
                    String full_csv_name = csv_name + ".csv";
                    String csv = data_dir_path + full_csv_name;
                    CSVWriter csvWriter = new CSVWriter(new FileWriter(csv,false));

                    String current_datetime = getCurrentDatetime();

                    String file_name_row[] = new String[]{"NAME:", full_csv_name};
                    String experiment_time_row[] = new String[]{"EXPERIMENT TIME:", current_datetime};
                    String activity_type_row[] = new String[]{"ACTIVITY TYPE:", mode};
                    String steps_made_row[] = new String[]{"COUNT OF ACTUAL STEPS:", steps_made};
                    String estimated_steps_made_row[] = new String[]{"ESTIMATED NUMBER OF STEPS:",
                                                                         estimated_steps_made};
                    String empty_row[] = new String[]{"", ""};
                    String table_header[] = new String[]{"Time [sec]", "ACC X", "ACC Y", "ACC Z"};

                    csvWriter.writeNext(file_name_row);
                    csvWriter.writeNext(experiment_time_row);
                    csvWriter.writeNext(activity_type_row);
                    csvWriter.writeNext(steps_made_row);
                    csvWriter.writeNext(estimated_steps_made_row);
                    csvWriter.writeNext(empty_row);
                    csvWriter.writeNext(table_header);

                    TerminalFragment.isSaveNeeded = true;
                    TerminalFragment.file_name = full_csv_name;
                    csvWriter.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

        cancel_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

        return inflatedView;
    }

    private String getCurrentDatetime(){
        Date now = new Date();
        return now.toLocaleString();
    }
}