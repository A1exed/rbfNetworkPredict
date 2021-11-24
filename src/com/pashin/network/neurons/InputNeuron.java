package com.pashin.network.neurons;

import com.pashin.network.Neuron;

import java.io.Serializable;

public class InputNeuron extends Neuron implements Serializable {

    public InputNeuron(int numberOfRelations) {
        super(numberOfRelations);
    }

    public void calculateValue(double inputValue) {
        setInputValue(inputValue);
        outputValue = inputValue;
    }

    @Override
    public String toString() {
        return "InputNeuron{" +
                ", inputValue=" + inputValue +
                ", outputValue=" + outputValue +
                ", error=" + error +
                '}';
    }
}
