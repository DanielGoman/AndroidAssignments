package com.example.FinalProject;

import android.util.Log;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class Calculations
{
    private static int numEstimatedSteps = 0;
    private static float lastPeakTime = 0;
    private static ArrayList<Entry> magList = new ArrayList<Entry>();

    static public float calcN(float acc_x, float acc_y, float acc_z)
    {
        float N_val = (float) Math.sqrt(Math.pow(acc_x, 2) +
                                        Math.pow(acc_y, 2) +
                                        Math.pow(acc_z, 2));
        return N_val;
    }

    static public int estimateStepsMade(Entry newEntry)
    {
        //        if (!Python.isStarted()){
        //            Python.start(new AndroidPlatform(requireActivity()));
        //        }
        //
        //        Python py = Python.getInstance();
        //        PyObject pyobj = py.getModule("steps_calculator");
        //        PyObject obj = pyobj.callAttr("calculate_steps");
        //
        //        return obj.asList().get(0).toInt();
        magList.add(newEntry);
        int numEntries = magList.size();
        if (numEntries > 2)
        {
            float prevDiff = magList.get(numEntries - 2).getY() - magList.get(numEntries - 3).getY();
            float currDiff = magList.get(numEntries - 1).getY() - magList.get(numEntries - 2).getY();
            Log.d("prev diff", String.valueOf(prevDiff));
            Log.d("curr diff", String.valueOf(currDiff));

            if(prevDiff > 0 && currDiff < 0)
            {
                numEstimatedSteps++;
            }
        }

        return numEstimatedSteps;
    }
}
