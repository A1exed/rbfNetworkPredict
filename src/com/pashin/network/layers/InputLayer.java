package com.pashin.network.layers;

import com.pashin.network.Layer;
import com.pashin.network.neurons.InputNeuron;

import java.io.Serializable;
import java.util.ArrayList;

public class InputLayer extends Layer<InputNeuron> implements Serializable {

    public InputLayer(int numberOfNeuronsInLayer, int numberOfNeuronsInNextLayer) {
        super(numberOfNeuronsInLayer);
        for (int i = 0; i < numberOfNeuronsInLayer; i++) {
            super.getListOfNeurons().add(new InputNeuron(numberOfNeuronsInNextLayer));
        }
    }

    public void calculateValues(ArrayList<Double> data) {
        for (int i = 0; i < numberOfNeuronsInLayer; i++) {
            listOfNeurons.get(i).calculateValue(data.get(i));
        }
    }

    @Override
    public String toString() {
        return "InputLayer{" +
                "listOfNeurons=" + listOfNeurons +
                ", numberOfNeuronsInLayer=" + numberOfNeuronsInLayer +
                '}';
    }
}
