package com.pashin.network.layers;

import com.pashin.network.Layer;
import com.pashin.network.neurons.HiddenNeuron;
import com.pashin.network.neurons.OutputNeuron;

import java.io.Serializable;
import java.util.ArrayList;

public class OutputLayer extends Layer<OutputNeuron> implements Serializable {

    private ArrayList<Double> w0;

    public OutputLayer(int numberOfNeuronsInLayer) {
        super(numberOfNeuronsInLayer);
        w0 = new ArrayList<>();
        for (int i = 0; i < numberOfNeuronsInLayer; i++) {
            super.getListOfNeurons().add(new OutputNeuron());
            w0.add(Math.random());
        }
    }

    public void calculateValues(ArrayList<HiddenNeuron> hiddenNeurons) {
        double sum;
        for (int i = 0; i < numberOfNeuronsInLayer; i++) {
            sum = 0.0;
            for (HiddenNeuron hiddenNeuron : hiddenNeurons) {
                sum += hiddenNeuron.getOutputValue() * hiddenNeuron.getWeights().get(i);
            }
            sum += 1 * w0.get(i);
            listOfNeurons.get(i).calculateValue(sum);
        }
    }

    public void calculateErrors(ArrayList<Double> data) {
        OutputNeuron neuron;
        for (int i = 0; i < numberOfNeuronsInLayer; i++) {
            neuron = listOfNeurons.get(i);
            neuron.setError(data.get(i) - neuron.getOutputValue());
        }
    }

    public void correctParams(double trainCoefficient) {
        for (int i = 0; i < numberOfNeuronsInLayer; i++) {
            w0.set(i, w0.get(i) - (-1 * trainCoefficient * listOfNeurons.get(i).getError()));
        }
    }

    @Override
    public String toString() {
        return "OutputLayer{" +
                "listOfNeurons=" + listOfNeurons +
                ", numberOfNeuronsInLayer=" + numberOfNeuronsInLayer +
                '}';
    }
}
