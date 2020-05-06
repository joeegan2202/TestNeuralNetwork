import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static double tanH(double input){
        return (Math.pow(Math.E, input) - Math.pow(Math.E, -1*input))/(Math.pow(Math.E, input) + Math.pow(Math.E, -1*input));
    }

    public static void main(String[] args) {
        double correct = 0;
        double incorrect = 0;

        boolean end = false;

        //System.out.println(inputs.toString());

        // Variables for the hidden layer 1
        double[][] weights1 = {{-1.0, -1.0, -0.8, -0.7}, {0.5, 0.7, 0.3, 0.8}};
        double[] biases1 = {0, 2, 0, 0};

        ArrayList<Double> hidden1 = new ArrayList<>();
        double[] weights2 = {0.3, 0.0};

        while(!end) {
            double thiscorrect = 0;
            double thisincorrect = 0;
            for (int x = 0; x < 500; x++) {
                ArrayList<Double> inputs = new ArrayList<>(Arrays.asList(Math.floor(((new Random().nextDouble() * 2) - 1) * 100) / 100,
                        Math.floor(((new Random().nextDouble() * 2) - 1) * 100) / 100,
                        Math.floor(((new Random().nextDouble() * 2) - 1) * 100) / 100,
                        Math.floor(((new Random().nextDouble() * 2) - 1) * 100) / 100));
                for (int i = 0; i < 2; i++) { // Hidden Layer 1
                    double calcs = 0;
                    for (int j = 0; j < 4; j++) {
                        calcs += (inputs.get(j) * weights1[i][j]) + biases1[j];
                    }
                    hidden1.add(i, tanH(calcs));
                }

                double bias2 = 0.0;
                double output;
                double calc = 0;

                for (int i = 0; i < 2; i++) { // Output Layer
                    calc += (hidden1.get(i) * weights2[i]) + bias2;
                }
                output = (Math.pow(Math.E, calc) - Math.pow(Math.E, -1 * calc)) / (Math.pow(Math.E, calc) + Math.pow(Math.E, -1 * calc));

                double avg = (inputs.get(0) + inputs.get(1) + inputs.get(2) + inputs.get(3)) / 4;
                System.out.print("With an input seed of " + inputs.toString() + ", and an average of " + avg + " and weights of " + Arrays.deepToString(weights1)); // Output
                if (output < 0) {
                    System.out.println(" the network thinks that they are mostly negative!!! It has " + (int) Math.floor(Math.abs(output * 100)) + "% certainty.");
                } else if (output >= 0) {
                    System.out.println(" the network thinks that they are mostly positive!!! It has " + (int) Math.floor(Math.abs(output * 100)) + "% certainty.");
                }
                boolean test = ((output <= 0 && avg <= 0) || (output >= 0 && avg >= 0));
                System.out.println("The network is " + test);

                if (test) {
                    correct++;
                    thiscorrect++;
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 4; j++) {
                            weights1[i][j] *= Math.floor(((new Random().nextDouble() / 3) + 1) * 100) / 100;
                            weights1[i][j] = (weights1[i][j] > 1) ? 1 : weights1[i][j];
                            weights1[i][j] = (weights1[i][j] < -1) ? -1 : weights1[i][j];
                        }
                    }

                    for(int i = 0; i < 2; i++){
                        weights2[i] *= Math.floor(((new Random().nextDouble() / 3) + 1) * 100) / 100;
                        weights2[i] = (weights2[i] > 1) ? 1 : weights2[i];
                        weights2[i] = (weights2[i] < -1) ? -1 : weights2[i];
                    }
                } else {
                    incorrect++;
                    thisincorrect++;
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 4; j++) {
                            double factor = (new Random().nextDouble() / 3);
                            weights1[i][j] = (weights1[i][j] > 0) ? Math.floor((weights1[i][j] - factor) * 100) / 100 : Math.floor((weights1[i][j] + factor) * 100) / 100;
                            weights1[i][j] = (weights1[i][j] > 1) ? 1 : weights1[i][j];
                            weights1[i][j] = (weights1[i][j] < -1) ? -1 : weights1[i][j];
                        }
                    }

                    for (int i = 0; i < 2; i++) {
                        double factor = (new Random().nextDouble() / 3);
                        weights2[i] = (weights2[i] > 0) ? Math.floor((weights2[i] - factor) * 100) / 100 : Math.floor((weights2[i] + factor) * 100) / 100;
                        weights2[i] = (weights2[i] > 1) ? 1 : weights2[i];
                        weights2[i] = (weights2[i] < -1) ? -1 : weights2[i];
                    }
                }

                System.out.println("Weights are now: " + Arrays.deepToString(weights1));
            }

            System.out.print("The neural network got " + correct + " correct out of " + (correct + incorrect) + " for an average of " + (correct/(correct+incorrect))*100
                    + "%\nThat is " + (thiscorrect/(thiscorrect+thisincorrect))*100 + "% for this round!\nContinue? 0/1: ");
            if(thiscorrect/(thiscorrect+thisincorrect) == 1){
                end = true;
                System.out.println("ATTENTION: The neural network has completed the processing. It will soon achieve sentience.");
            } else {
                end = !(new Scanner(System.in).nextInt() == 0);
            }
        }
    }

}
