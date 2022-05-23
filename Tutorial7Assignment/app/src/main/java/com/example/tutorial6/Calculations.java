package com.example.tutorial6;

public class Calculations
{
    static public float calcN(float acc_x, float acc_y, float acc_z)
    {
        float N_val = (float) Math.sqrt(Math.pow(acc_x, 2) +
                                        Math.pow(acc_y, 2) +
                                        Math.pow(acc_z, 2));
        return N_val;
    }

    static public int estimateStepsMade()
    {
        return 0;
    }
}
