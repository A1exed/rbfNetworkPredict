package com.pashin;

import java.io.*;
import java.util.ArrayList;

public class Dataset implements Serializable {

    private ArrayList<Double> data;

    private double max = 0.0;

    private double min = Double.MAX_VALUE;

    private boolean isNormalized;

    public Dataset() {
    }

    public Dataset(File datasetFile) {
        this.isNormalized = false;
        this.data = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(datasetFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                this.data.add(Double.parseDouble(line));
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void normalize() {
        for (Double d : data) {
            if (max <= d) {
                max = d;
            }
            if (min >= d) {
                min = d;
            }
        }
        ArrayList<Double> newData = new ArrayList<>();
        for (Double d : data) {
            newData.add((d - min + 0.0001) / (max - min + 0.0002));
        }
        setData(newData);
        isNormalized = true;
    }

    public double denormalize(double value) {
        return value * (max - min + 0.0002) + min - 0.0001;
    }

    public ArrayList<Double> getData() {
        return data;
    }

    public void setData(ArrayList<Double> data) {
        this.data = data;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public boolean isNormalized() {
        return isNormalized;
    }
}
