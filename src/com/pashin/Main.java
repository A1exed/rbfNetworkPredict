package com.pashin;

import com.pashin.network.NeuralNetwork;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        Dataset dataset = new Dataset(new File("src/com/pashin/resources/dol-rub.data"));
        dataset.normalize();

//        Dataset dataset = new Dataset(new File("src/com/pashin/resources/function.data"));

        NeuralNetwork network = new NeuralNetwork(10, 8, 2);

        network.train(dataset, 1000, 0.001, 10);

    }
}
