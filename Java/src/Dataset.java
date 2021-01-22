import org.jfree.ui.RefineryUtilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dataset {

    public ArrayList<ArrayList<ArrayList<Double>>> globalTimeseries = new ArrayList<ArrayList<ArrayList<Double>>>();
    public ArrayList<Double> globalLabelArr = new ArrayList<Double>();
    public ArrayList<ArrayList<Double>> globalCoefArr = new ArrayList<ArrayList<Double>>();
    public ArrayList<ArrayList<Double>> globalInterceptArr = new ArrayList<ArrayList<Double>>();
    public ArrayList<ArrayList<Double>> globalFeaturesArr = new ArrayList<ArrayList<Double>>();
    public ArrayList<Double> globalMultArr = new ArrayList<Double>();

    public ArrayList<ArrayList<Double>> globalMultPosAndNegArr = new ArrayList<ArrayList<Double>>();
    public ArrayList<ArrayList<Double>> globalMultTFArr = new ArrayList<ArrayList<Double>>();
    public Dataset() {}

    public void loadTimeseries() throws IOException {
        // 2576
        // System.out.println(System.getProperty("user.dir")); /Users/leone/ShapeNet
        // C:\Users\e9214294\Desktop\RedLee\JMLToolkit_Multi_ShapeNet-master\Java
//        String expected_value = "Hello, world!";
//        String file1 = "/Users/leone/Documents/*Summer_research/*ShapeNet/datasets/ALT_AND_AFP_ARFF/ALT_AND_AFP_TRAIN.arff";
//        String file2 = "/Users/leone/Documents/*Summer_research/*ShapeNet/datasets/ALT_AND_AFP_ARFF/ALT_AND_AFP_TEST.arff";
        String file1 = "C:/Users/e9214294/Desktop/RedLee/JMLToolkit_Multi_ShapeNet-master/datasets/ALT_AND_AFP_ARFF/ALT_AND_AFP_TRAIN.arff";
        String file2 = "C:/Users/e9214294/Desktop/RedLee/JMLToolkit_Multi_ShapeNet-master/datasets/ALT_AND_AFP_ARFF/ALT_AND_AFP_TEST.arff";

        String[] fileArr = {file1, file2};

        int count = 0;
        for (int i=0; i<fileArr.length; i++) {
            String file = fileArr[i];
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            String newline;
            List<String> newStrList;
            double label;
            while (line != null) {
                if (line.contains("\\n") && line.contains("'")){
                    // Get the label
                    newline = line.replaceAll("'", "");
                    // Regex has its own escape sequences, denoted with \\ (the escape sequence for \), since Java reserves \
                    // To split by "\n", you'll need \\\\n instead, because \\n in regex represents an actual line break, just as \n represents one in Java.
                    List<String> arrOfStr = Arrays.asList(newline.split("\\\\n"));

                    ArrayList<ArrayList<Double>> timeseriesArr = new ArrayList<ArrayList<Double>>();
                    // Initialize timeseriesArr with size 2
                    int size = 2;
                    for (int l=0; l<size; l++) {
                        timeseriesArr.add(new ArrayList<Double>());
                    }

                    for (int j=0; j<arrOfStr.size(); j++) {
                        String str = arrOfStr.get(j);
                        newStrList = Arrays.asList(str.split("\\s*,\\s*"));

                        for (int k=0; k<newStrList.size(); k++) {

                            if (j>0) {
                                if (k<newStrList.size()-1) {
                                    // For the second timeseries and ignore the last label element
                                    timeseriesArr.get(j).add(Double.valueOf(newStrList.get(k)));
                                }else{
                                    // Get the labels
                                    label = Double.valueOf(newStrList.get(k));
                                    globalLabelArr.add(label);
                                }
                            }else{
                                timeseriesArr.get(j).add(Double.valueOf(newStrList.get(k)));
                            }
                        }
                    }
                    globalTimeseries.add(timeseriesArr);
                    count++;
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
            System.out.println("count: " + count);
            System.out.println("globalLabelArr.size(): " + globalLabelArr.size());
            System.out.println("globalLinesTimeseries.size(): " + globalTimeseries.size());
        }
//        System.out.println(globalLinesTimeseries);
    }

    public void loadCoef() throws IOException {
        // System.getProperty("user.dir"): /Users/leone/ShapeNet
        String expected_value = "Hello, world!";
        String file ="/Users/leone/Documents/*Summer_research/*ShapeNet/datasets/Distance/coef.txt";

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while (line != null) {
            String[] arrOfStr = line.split(", ");
            ArrayList<Double> valArr = new ArrayList<Double>();
            for (int i=0; i<arrOfStr.length; i++) {
                String str = arrOfStr[i];
                valArr.add(Double.valueOf(str));
            }
            globalCoefArr.add(valArr);
//            System.out.println(line);
            // read next line
            line = reader.readLine();
        }
        reader.close();
    }

    public void loadIntercept() throws IOException {
        // System.getProperty("user.dir"): /Users/leone/ShapeNet
        String expected_value = "Hello, world!";
        String file ="/Users/leone/Documents/*Summer_research/*ShapeNet/datasets/Distance/intercept.txt";

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while (line != null) {
            String[] arrOfStr = line.split(" ");
            ArrayList<Double> valArr = new ArrayList<Double>();
            for (int i=0; i<arrOfStr.length; i++) {
                String str = arrOfStr[i];
                valArr.add(Double.valueOf(str));
            }
            globalInterceptArr.add(valArr);
//            System.out.println(line);
            // read next line
            line = reader.readLine();
        }
        reader.close();
    }

    public void loadFeatures() throws IOException {
        // System.getProperty("user.dir"): /Users/leone/ShapeNet
        String expected_value = "Hello, world!";
        String file1 = "/Users/leone/Documents/*Summer_research/*ShapeNet/datasets/Distance/feature_train.txt";
        String file2 = "/Users/leone/Documents/*Summer_research/*ShapeNet/datasets/Distance/feature_test.txt";
        String[] fileArr = {file1, file2};

        for (int i=0; i<fileArr.length; i++) {
            String file = fileArr[i];
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                String[] arrOfStr = line.split(" ");
                ArrayList<Double> valArr = new ArrayList<Double>();
                for (int j=0; j<arrOfStr.length; j++) {
                    String str = arrOfStr[j];
                    valArr.add(Double.valueOf(str));
                }
                globalFeaturesArr.add(valArr);
//            System.out.println(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
//            System.out.println(globalFeaturesArr.size());
        }
//        System.out.println(globalFeaturesArr.size());
    }

    public void multiplication() {
        final int[] count = {0};
        System.out.println("-------------------");
        double intercept = globalInterceptArr.get(0).get(0);

        globalFeaturesArr.forEach((featureRow) -> {
            double rowSum = 0;
            for (int i=0; i < featureRow.size(); i++) {
                double fVal = featureRow.get(i);
                double coefVal = globalCoefArr.get(0).get(i);
                // Perform dot product

                rowSum = rowSum + fVal * coefVal;
                // System.out.println('fVal * coefVal: ' + fVal * coefVal);
            }
            rowSum = rowSum + intercept;
            globalMultArr.add(rowSum);
            count[0] += 1;
            // System.out.println('rowSum: ' + rowSum);
    });

        System.out.println("count: " + count[0]);

        System.out.println("globalMultArr: " + globalMultArr);
    }

    public void multiplication_PN_TF() {
        final int[] count = {0};
        final int[] accuracyCount = {0};
        System.out.println("-------------------");
        double intercept = globalInterceptArr.get(0).get(0);
        ArrayList<Double> arrPos = new ArrayList<Double>();
        ArrayList<Double> arrNeg = new ArrayList<Double>();
        ArrayList<Double> arrT = new ArrayList<Double>();
        ArrayList<Double> arrF = new ArrayList<Double>();

        ArrayList<Double> arrPosT = new ArrayList<Double>();
        ArrayList<Double> arrNegT = new ArrayList<Double>();


        final int[] positiveCount = {0};

        globalFeaturesArr.forEach((featureRow) -> {
            double rowSum = 0;

            for (int i=0; i < featureRow.size(); i++) {
                double fVal = featureRow.get(i);
                double coefVal = globalCoefArr.get(0).get(i);
                // Perform dot product
                rowSum = rowSum + fVal * coefVal;
                // System.out.println('fVal * coefVal: ' + fVal * coefVal);
            }

            rowSum = rowSum + intercept;
            double label = globalLabelArr.get(count[0]);

            if (rowSum>0) {
                if (label==0) {
                    positiveCount[0]++;
                    accuracyCount[0]++;
//                    arrT.add(rowSum);
                    arrPosT.add(rowSum);
                }else{
//                    arrF.add(rowSum);
                }
                arrPos.add(rowSum);
            }else {
                if (label==1) {
                    accuracyCount[0]++;
//                    arrT.add(rowSum);
                    arrNegT.add(rowSum);
                }else{
//                    arrF.add(rowSum);
                }
                arrNeg.add(rowSum);
            }

            // Test the accuracy, see whether is close to 90%.
            count[0] += 1;
            // System.out.println('rowSum: ' + rowSum);
        });

        globalMultPosAndNegArr.add(arrPos);
        globalMultPosAndNegArr.add(arrNeg);
//        globalMultTFArr.add(arrT);
//        globalMultTFArr.add(arrF);
        globalMultTFArr.add(arrPosT);
        globalMultTFArr.add(arrNegT);

        System.out.println("positiveCount: " + positiveCount[0] + ", negativeCount: " + (count[0]-positiveCount[0]));
        System.out.println("accuracyCount: " + accuracyCount[0]);
        System.out.println("count: " + count[0]);
        System.out.println("accuracy: " + accuracyCount[0]*1.0/count[0]);
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getGlobalTimeseries() {
        return globalTimeseries;
    }

    public ArrayList<Double> getGlobalMultArr() {
        return globalMultArr;
    }

    public ArrayList<ArrayList<Double>> getGlobalMultPosAndNegArr() {
        return globalMultPosAndNegArr;
    }

    public ArrayList<ArrayList<Double>> getGlobalMultTFArr() {
        return globalMultTFArr;
    }

    public ArrayList<Double> getGlobalLabelArr() {
        return globalLabelArr;
    }

    public static void main(String[] args) throws IOException {
        Dataset aDataset = new Dataset();
        aDataset.loadTimeseries();
//        aDataset.loadCoef();
//        aDataset.loadIntercept();
//        aDataset.loadFeatures();
//        aDataset.multiplication_PN_TF();
//        aDataset.multiplicationTF();
        //---------
        final String title = "Score Bord";
        final DualAxisChart chart = new DualAxisChart(title, aDataset.getGlobalTimeseries());
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
        //---------
        new ComboBoxExample(aDataset.getGlobalTimeseries(), aDataset.getGlobalLabelArr(), chart);
    }
}
