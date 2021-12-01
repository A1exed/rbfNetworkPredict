package com.pashin.network;

import com.pashin.Dataset;
import com.pashin.network.layers.*;
import com.pashin.network.neurons.OutputNeuron;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class NeuralNetwork implements Serializable {

    private InputLayer inputLayer;

    private HiddenLayer hiddenLayer;

    private OutputLayer outputLayer;

    private boolean isTrained;

    public NeuralNetwork(int windowSize, int numberOfHiddenNeurons, int numberOfPrediction) {
        inputLayer = new InputLayer(windowSize, numberOfHiddenNeurons);
        hiddenLayer = new HiddenLayer(numberOfHiddenNeurons, numberOfPrediction);
        outputLayer = new OutputLayer(numberOfPrediction);
        isTrained = false;
    }

    public void train(Dataset dataset, int numOfEpoch, double trainCoefficient, int percentOfTestData) {
        System.out.println("-------------------------");
        System.out.println("||   Начало обучения   ||");
        System.out.println("-------------------------");
        hiddenLayer.initCentresAndRadius(inputLayer.getListOfNeurons(), dataset.getData());
        isTrained = true;
        int epoch = 1;
        int numTrainData = dataset.getData().size() * (100 - percentOfTestData) / 100;
        Dataset trainData = new Dataset();
        Dataset testData = new Dataset();
        trainData.setData(new ArrayList<>());
        testData.setData(new ArrayList<>());
        for (Double data : dataset.getData().subList(0, numTrainData)) {
            trainData.getData().add(data);
        }
        for (Double data : dataset.getData().subList(numTrainData, dataset.getData().size())) {
            testData.getData().add(data);
        }
        double trainError = 0;
        double testError;
        ArrayList<Double> inData;
        ArrayList<Double> outData;
        while (epoch <= numOfEpoch) {
            System.out.println("---------------------------------------");
            System.out.println("Эпоха #" + epoch);
            trainError = 0;
            for (int i = 0; i < trainData.getData().size() - inputLayer.getNumberOfNeuronsInLayer() - outputLayer.getNumberOfNeuronsInLayer() + 1; i++) {
                inData = new ArrayList<>();
                for (int j = 0; j < inputLayer.getNumberOfNeuronsInLayer(); j++) {
                    inData.add(trainData.getData().get(i + j));
                }
                outData = new ArrayList<>();
                for (int j = inputLayer.getNumberOfNeuronsInLayer(); j < outputLayer.getNumberOfNeuronsInLayer() + inputLayer.getNumberOfNeuronsInLayer(); j++) {
                    outData.add(trainData.getData().get(i + j));
                }
                calculateValues(inData);
                calculateErrors(outData);
                correctParams(trainCoefficient);
                for (OutputNeuron outputNeuron : outputLayer.getListOfNeurons()) {
                    trainError += Math.pow(outputNeuron.getError(), 2);
                }
            }
            trainError = Math.sqrt(trainError / (outputLayer.getNumberOfNeuronsInLayer() * (trainData.getData().size() - inputLayer.getNumberOfNeuronsInLayer() - outputLayer.getNumberOfNeuronsInLayer())));
            // Ошибка теста
            testError = 0;
            for (int i = 0; i < testData.getData().size() - inputLayer.getNumberOfNeuronsInLayer() - outputLayer.getNumberOfNeuronsInLayer() + 1; i++) {
                inData = new ArrayList<>();
                for (int j = 0; j < inputLayer.getNumberOfNeuronsInLayer(); j++) {
                    inData.add(testData.getData().get(i + j));
                }
                outData = new ArrayList<>();
                for (int j = inputLayer.getNumberOfNeuronsInLayer(); j < outputLayer.getNumberOfNeuronsInLayer() + inputLayer.getNumberOfNeuronsInLayer(); j++) {
                    outData.add(testData.getData().get(i + j));
                }
                calculateValues(inData);
                calculateErrors(outData);
                for (OutputNeuron outputNeuron : outputLayer.getListOfNeurons()) {
                    testError += Math.abs(outputNeuron.getError());
                }
            }
            testError = testError / (outputLayer.getNumberOfNeuronsInLayer() * (testData.getData().size() - inputLayer.getNumberOfNeuronsInLayer() - outputLayer.getNumberOfNeuronsInLayer()));

            System.out.printf("Погрешность обучения (СКО): %f\n", trainError);
            System.out.printf("Погрешность тестирования (САО): %f\n", testError);
            epoch++;
        }
        System.out.println("----------------------------");
        System.out.println("||   Обучение завершено   ||");
        System.out.println("----------------------------");

        System.out.println("-----------------------------");
        System.out.println("||   Начало тестирования   ||");
        System.out.println("-----------------------------");
        testError = 0;
        for (int i = 0; i < testData.getData().size() - inputLayer.getNumberOfNeuronsInLayer() - outputLayer.getNumberOfNeuronsInLayer() + 1; i++) {
            inData = new ArrayList<>();
            for (int j = 0; j < inputLayer.getNumberOfNeuronsInLayer(); j++) {
                inData.add(testData.getData().get(i + j));
            }
            outData = new ArrayList<>();
            for (int j = inputLayer.getNumberOfNeuronsInLayer(); j < outputLayer.getNumberOfNeuronsInLayer() + inputLayer.getNumberOfNeuronsInLayer(); j++) {
                outData.add(testData.getData().get(i + j));
            }
            System.out.println("---------------------------------------");
            predicate(inData, dataset.isNormalized(), dataset.getMin(), dataset.getMax());
            calculateErrors(outData);
            System.out.println("Должно быть:");
            for (Double d : outData) {
                System.out.printf(" %f", dataset.isNormalized() ? dataset.denormalize(d) : d);
            }
            System.out.println();
            for (OutputNeuron outputNeuron : outputLayer.getListOfNeurons()) {
                testError += Math.abs(outputNeuron.getError());
            }
        }
        testError = testError / (outputLayer.getNumberOfNeuronsInLayer() * (testData.getData().size() - inputLayer.getNumberOfNeuronsInLayer() - outputLayer.getNumberOfNeuronsInLayer()));

        System.out.println("--------------------------------");
        System.out.println("||   Тестирование завершено   ||");
        System.out.println("--------------------------------");
        System.out.println("---------------------------------------");

        System.out.printf("Погрешность обучения (СКО): %f\n", trainError);
        System.out.printf("Погрешность тестирования (САО): %f\n", testError);
    }

    public void predicate(ArrayList<Double> data, boolean isNormalized, double min, double max) {
        if (!isTrained) hiddenLayer.initCentresAndRadius(inputLayer.getListOfNeurons(), data);
        System.out.println("Входные данные:");
        for (Double d : data) {
            System.out.printf(" %f", isNormalized ? d * (max - min + 0.0002) + min - 0.0001 : d);
        }
        System.out.println();
        calculateValues(data);
        System.out.println("Результат предсказания:");
        for (OutputNeuron neuron : outputLayer.getListOfNeurons()) {
            System.out.printf(" %f", isNormalized ? neuron.getOutputValue() * (max - min + 0.0002) + min - 0.0001 : neuron.getOutputValue());
        }
        System.out.println();
    }

    public void saveNetwork() {
        File file = new File("src/com/pashin/resources/network-" + new Date().getTime() + ".trainedstate");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static NeuralNetwork openNetwork(File file) {
        NeuralNetwork neuralNetwork = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            neuralNetwork = (NeuralNetwork) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return neuralNetwork;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return neuralNetwork;
    }

    private void calculateValues(ArrayList<Double> data) {
        inputLayer.calculateValues(data);
        hiddenLayer.calculateValues(inputLayer.getListOfNeurons());
        outputLayer.calculateValues(hiddenLayer.getListOfNeurons());
    }

    private void calculateErrors(ArrayList<Double> data) {
        outputLayer.calculateErrors(data);
        hiddenLayer.calculateErrors(outputLayer.getListOfNeurons());
    }

    private void correctParams(double trainCoefficient) {
        outputLayer.correctParams(trainCoefficient);
        hiddenLayer.correctParams(inputLayer.getListOfNeurons(), outputLayer.getListOfNeurons(), trainCoefficient);
    }

}
