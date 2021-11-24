package com.pashin.network.layers;

import com.pashin.network.Layer;
import com.pashin.network.neurons.HiddenNeuron;
import com.pashin.network.neurons.InputNeuron;
import com.pashin.network.neurons.OutputNeuron;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class HiddenLayer extends Layer<HiddenNeuron> implements Serializable {

    public HiddenLayer(int numberOfNeuronsInLayer, int numberOfNeuronsInNextLayer) {
        super(numberOfNeuronsInLayer);
        for (int i = 0; i < numberOfNeuronsInLayer; i++) {
            super.getListOfNeurons().add(new HiddenNeuron(numberOfNeuronsInNextLayer));
        }
    }

    public void initCentresAndRadius(ArrayList<InputNeuron> inputNeurons, ArrayList<Double> data) {
        for (HiddenNeuron hiddenNeuron : listOfNeurons) {
            for (InputNeuron ignored : inputNeurons) {
                hiddenNeuron.getCentres().add(Math.random());
                hiddenNeuron.getRadius().add(Math.random());
            }
        }
//        HiddenNeuron hiddenNeuron;
//        double rand = 0.0;
//         // Центры
//        if (inputNeurons.size() <= numberOfNeuronsInLayer) {
//            for (int i = 0; i < numberOfNeuronsInLayer; i++) {
//                hiddenNeuron = listOfNeurons.get(i);
//                for (int j = 0; j < inputNeurons.size(); j++) {
//                    hiddenNeuron.getCentres().add(data.get(j) + rand);
//                }
//                for (int j = 0; j < numberOfNeuronsInLayer - inputNeurons.size() - 1; j++) {
//                    hiddenNeuron.getCentres().add(hiddenNeuron.getCentres().get(j) + rand);
//                }
//                Collections.reverse(hiddenNeuron.getCentres());
//                rand = Math.random() / 100;
//            }
//        } else {
//            for (int i = 0; i < numberOfNeuronsInLayer; i++) {
//                hiddenNeuron = listOfNeurons.get(i);
//                for (int j = 0; j < inputNeurons.size(); j++) {
//                    hiddenNeuron.getCentres().add(data.get(j) + rand);
//                }
//                Collections.reverse(hiddenNeuron.getCentres());
//                rand = Math.random() / 100;
//            }
//        }
//         // Радиус
//        double tempR;
//        HiddenNeuron tempHidden;
//        for (int i = 0; i < numberOfNeuronsInLayer; i++) {
//            hiddenNeuron = listOfNeurons.get(i);
//            tempR = 0;
//            for (int j = 0; j < numberOfNeuronsInLayer; j++) {
//                tempHidden = listOfNeurons.get(j);
//                if (i != j) {
//                    for (int k = 0; k < inputNeurons.size(); k++) {
//                        tempR += Math.pow(hiddenNeuron.getCentres().get(k) - tempHidden.getCentres().get(k), 2);
//                    }
//                }
//            }
//            for (int j = 0; j < inputNeurons.size(); j++) {
//                hiddenNeuron.getRadius().add(Math.sqrt(tempR / (numberOfNeuronsInLayer - 1)));
//            }
//        }
    }

    public void calculateValues(ArrayList<InputNeuron> inputNeurons) {
        // Определение входного вектора X
        for (HiddenNeuron hiddenNeuron : listOfNeurons) {
            hiddenNeuron.setX(new ArrayList<>());
        }
        for (InputNeuron inputNeuron : inputNeurons) {
            for (HiddenNeuron hiddenNeuron : listOfNeurons) {
                hiddenNeuron.getX().add(inputNeuron.getOutputValue());
            }
        }
        // Расчет
        double sum;
        double sumDC;
        double sumDR;
        for (HiddenNeuron hiddenNeuron : listOfNeurons) {
            sum = 0;
            sumDC = 0;
            sumDR = 0;
            for (int i = 0; i < inputNeurons.size(); i++) {
                sum += Math.pow(hiddenNeuron.getX().get(i) - hiddenNeuron.getCentres().get(i), 2) / Math.pow(hiddenNeuron.getRadius().get(i), 2);
                sumDC += (hiddenNeuron.getX().get(i) - hiddenNeuron.getCentres().get(i)) / Math.pow(hiddenNeuron.getRadius().get(i), 2);
                sumDR += Math.pow(hiddenNeuron.getX().get(i) - hiddenNeuron.getCentres().get(i), 2) / Math.pow(hiddenNeuron.getRadius().get(i), 3);
            }
            hiddenNeuron.setOutputValue(Math.pow(2.718, sum / -2));
            hiddenNeuron.setDerivativeC(sumDC);
            hiddenNeuron.setDerivativeR(sumDR);
        }
    }

    public void calculateErrors(ArrayList<OutputNeuron> outputNeurons) {
//        HiddenNeuron hiddenNeuron;
//        double error;
//        double mul;
//        double sum;
//        for (int i = 0; i < numberOfNeuronsInLayer; i++) {
//            hiddenNeuron = listOfNeurons.get(i);
//            error = 0.0;
//            for (int j = 0; j < outputNeurons.size(); j++) {
//                mul = outputNeurons.get(j).getError() * hiddenNeuron.getWeights().get(j);
//                sum = 0.0;
//                for (int k = 0; k < numberOfNeuronsInLayer; k++) {
//                    sum += listOfNeurons.get(k).getWeights().get(j);
//                }
//                error += mul / sum;
//            }
//            hiddenNeuron.setError(error);
//        }
        double error;
        for (HiddenNeuron hiddenNeuron : listOfNeurons) {
            error = 0.0;
            for (int i = 0; i < outputNeurons.size(); i++) {
                error += hiddenNeuron.getWeights().get(i) * outputNeurons.get(i).getError();
            }
            hiddenNeuron.setError(error);
        }
    }

    public void correctParams(ArrayList<InputNeuron> inputNeurons, ArrayList<OutputNeuron> outputNeurons, double trainCoefficient) {
        ArrayList<ArrayList<Double>> weightsList = new ArrayList<>();
        ArrayList<Double> weights;
        ArrayList<ArrayList<Double>> centresList = new ArrayList<>();
        ArrayList<Double> centres;
        ArrayList<ArrayList<Double>> radiusList = new ArrayList<>();
        ArrayList<Double> radius;
        double gradient;
        double gradientC;
        double gradientR;
        OutputNeuron outputNeuron;
        HiddenNeuron hiddenNeuron;
        // Веса
        for (int i = 0; i < numberOfNeuronsInLayer; i++) {
            hiddenNeuron = listOfNeurons.get(i);
            weights = new ArrayList<>();
            for (int j = 0; j < outputNeurons.size(); j++) {
                outputNeuron = outputNeurons.get(j);
                gradient = -1.0 * outputNeuron.getError() * hiddenNeuron.getOutputValue();
                weights.add(hiddenNeuron.getWeights().get(j) - trainCoefficient * gradient);
            }
            weightsList.add(weights);
        }
        // Центры и радиус
        for (int i = 0; i < numberOfNeuronsInLayer; i++) {
            hiddenNeuron = listOfNeurons.get(i);
            centres = new ArrayList<>();
            radius = new ArrayList<>();
            for (int j = 0; j < inputNeurons.size(); j++) {
                gradientC = -1.0 * hiddenNeuron.getError() * hiddenNeuron.getOutputValue() * hiddenNeuron.getDerivativeC();
                gradientR = -1.0 * hiddenNeuron.getError() * hiddenNeuron.getOutputValue() * hiddenNeuron.getDerivativeR();
                centres.add(hiddenNeuron.getCentres().get(j) - trainCoefficient * gradientC);
                radius.add(hiddenNeuron.getRadius().get(j) - trainCoefficient * gradientR);
            }
            centresList.add(centres);
            radiusList.add(radius);
        }
        for (int i = 0; i < numberOfNeuronsInLayer; i++) {
            listOfNeurons.get(i).setWeights(weightsList.get(i));
            listOfNeurons.get(i).setCentres(centresList.get(i));
            listOfNeurons.get(i).setRadius(radiusList.get(i));
        }
    }

    @Override
    public String toString() {
        return "HiddenLayer{" +
                "listOfNeurons=" + listOfNeurons +
                ", numberOfNeuronsInLayer=" + numberOfNeuronsInLayer +
                '}';
    }
}
