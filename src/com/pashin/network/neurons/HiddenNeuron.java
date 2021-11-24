package com.pashin.network.neurons;

import com.pashin.network.Neuron;

import java.io.Serializable;
import java.util.ArrayList;

public class HiddenNeuron extends Neuron implements Serializable {

    private ArrayList<Double> weights;

    private ArrayList<Double> centres;

    private ArrayList<Double> x;

    private ArrayList<Double> radius;

    private double derivativeC;

    private double derivativeR;

    public HiddenNeuron(int numberOfRelations) {
        super(numberOfRelations);
        x = new ArrayList<>();
        centres = new ArrayList<>();
        radius = new ArrayList<>();
        weights = new ArrayList<>();
        for (int i = 0; i < numberOfRelations; i++) {
            weights.add(Math.random());
        }
    }

    public double getDerivativeC() {
        return derivativeC;
    }

    public void setDerivativeC(double derivativeC) {
        this.derivativeC = derivativeC;
    }

    public double getDerivativeR() {
        return derivativeR;
    }

    public void setDerivativeR(double derivativeR) {
        this.derivativeR = derivativeR;
    }

    public void setOutputValue(double newOutputValue) {
        outputValue = newOutputValue;
    }

    public double getOutputValue() {
        return outputValue;
    }

    public ArrayList<Double> getX() {
        return x;
    }

    public void setX(ArrayList<Double> x) {
        this.x = x;
    }

    public ArrayList<Double> getRadius() {
        return radius;
    }

    public void setRadius(ArrayList<Double> radius) {
        this.radius = radius;
    }

    public ArrayList<Double> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<Double> weights) {
        this.weights = weights;
    }

    public ArrayList<Double> getCentres() {
        return centres;
    }

    public void setCentres(ArrayList<Double> centres) {
        this.centres = centres;
    }

    @Override
    public String toString() {
        return "HiddenNeuron{" +
                "inputValue=" + inputValue +
                ", outputValue=" + outputValue +
                ", error=" + error +
                ", weights=" + weights +
                ", centres=" + centres +
                ", radius=" + radius +
                '}';
    }
}
