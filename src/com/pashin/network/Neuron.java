package com.pashin.network;

import java.io.Serializable;

public abstract class Neuron implements Serializable {

    protected double inputValue;

    protected double outputValue;

    protected double error;

    protected Neuron(int numberOfRelations) {
        error = 0.0;
    }

    public double getInputValue() {
        return inputValue;
    }

    public void setInputValue(double inputValue) {
        this.inputValue = inputValue;
    }

    public double getOutputValue() {
        return outputValue;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }
}
