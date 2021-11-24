package com.pashin.network;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Layer<N extends Neuron> implements Serializable {

    protected ArrayList<N> listOfNeurons;

    protected int numberOfNeuronsInLayer;

    protected Layer(int numberOfNeuronsInLayer) {
        this.numberOfNeuronsInLayer = numberOfNeuronsInLayer;
        this.listOfNeurons = new ArrayList<>();
    }

//    protected double derivativeGauss(double x) {
//        return
////        return (1 / (1 + Math.pow(2.718, -x))) * (1 - (1 / (1 + Math.pow(2.718, -x))));
//    }

    public ArrayList<N> getListOfNeurons() {
        return listOfNeurons;
    }

    public void setListOfNeurons(ArrayList<N> listOfNeurons) {
        this.listOfNeurons = listOfNeurons;
        this.numberOfNeuronsInLayer = listOfNeurons.size();
    }

    public int getNumberOfNeuronsInLayer() {
        return numberOfNeuronsInLayer;
    }

}
