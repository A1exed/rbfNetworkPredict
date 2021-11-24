package com.pashin.network.neurons;

import com.pashin.network.Neuron;

import java.io.Serializable;

public class OutputNeuron extends Neuron implements Serializable {


    public OutputNeuron() {
        super(0);
    }

    public void calculateValue(double inputValue) {
        setInputValue(inputValue);
        outputValue = inputValue;
    }

    @Override
    public String toString() {
        return "OutputNeuron{" +
                ", inputValue=" + inputValue +
                ", outputValue=" + outputValue +
                ", error=" + error +
                '}';
    }
}
