import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dataset {

    public ArrayList<ArrayList<ArrayList<Double>>> globalTimeseries = new ArrayList<ArrayList<ArrayList<Double>>>();
    public ArrayList<ArrayList<Double>> globalShapelet = new ArrayList<ArrayList<Double>>();
    public ArrayList<Double> globalTimeseriesLabelArr = new ArrayList<Double>();
    public ArrayList<Double> globalShapeletLabelArr = new ArrayList<Double>();
    public ArrayList<ArrayList<Double>> globalCoefArr = new ArrayList<ArrayList<Double>>();
    public ArrayList<ArrayList<Double>> globalInterceptArr = new ArrayList<ArrayList<Double>>();
    public ArrayList<ArrayList<Double>> globalFeaturesArr = new ArrayList<ArrayList<Double>>();
    public ArrayList<ArrayList<Double>> globalMulti0And1Arr = new ArrayList<ArrayList<Double>>();
    public ArrayList<Double> globalMultiArr = new ArrayList<Double>();

    public ArrayList<ArrayList<Double>> globalMultPosAndNegArr = new ArrayList<ArrayList<Double>>();
    public ArrayList<ArrayList<Double>> globalMultTFArr = new ArrayList<ArrayList<Double>>();
    public Dataset() {}
    public double accuracy;
    public int count;

    public void loadTimeseries() throws IOException {
        // 2576
        // System.out.println(System.getProperty("user.dir"));
        // /Users/leone/ShapeNet
        // C:\Users\e9214294\Desktop\RedLee\JMLToolkit_Multi_ShapeNet-master\Java
//        String expected_value = "Hello, world!";
//        String file1 = "/Users/leone/Documents/*Summer_research/*ShapeNet/datasets/ALT_AND_AFP_ARFF/ALT_AND_AFP_TRAIN.arff";
//        String file2 = "/Users/leone/Documents/*Summer_research/*ShapeNet/datasets/ALT_AND_AFP_ARFF/ALT_AND_AFP_TEST.arff";
//        String file1 = "C:/Users/e9214294/Desktop/RedLee/JMLToolkit_Multi_ShapeNet-master/datasets/ALT_AND_AFP_ARFF/ALT_AND_AFP_TRAIN.arff";
//        String file2 = "C:/Users/e9214294/Desktop/RedLee/JMLToolkit_Multi_ShapeNet-master/datasets/ALT_AND_AFP_ARFF/ALT_AND_AFP_TEST.arff";
        String file1 = "/Users/student/Desktop/RedLee/datasets/ALT_AND_AFP_ARFF/ALT_AND_AFP_TRAIN.arff";
        String file2 = "/Users/student/Desktop/RedLee/datasets/ALT_AND_AFP_ARFF/ALT_AND_AFP_TEST.arff";

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
                                    globalTimeseriesLabelArr.add(label);
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
            System.out.println("globalLabelArr.size(): " + globalTimeseriesLabelArr.size());
            System.out.println("globalLinesTimeseries.size(): " + globalTimeseries.size());
        }
//        System.out.println(globalLinesTimeseries);
    }

    public void loadShapelet() throws IOException {
        // 2576
        // System.out.println(System.getProperty("user.dir"));
        // /Users/leone/ShapeNet
        // C:\Users\e9214294\Desktop\RedLee\JMLToolkit_Multi_ShapeNet-master\Java
        String file_shapelet = "/Users/student/Desktop/RedLee/datasets/shapeNet/shapelet.txt";
        String file_dim = "/Users/student/Desktop/RedLee/datasets/shapeNet/shapelet_dim.txt";

        // Read shapelet
        BufferedReader reader = new BufferedReader(new FileReader(file_shapelet));
        String line = reader.readLine();
        String newline;
        List<String> newStrList;
        double label;
        while (line != null) {
            String[] arrOfStr = line.split(" ");

            ArrayList<Double> valArr = new ArrayList<Double>();
            for (int j=0; j<arrOfStr.length; j++) {
                String str = arrOfStr[j];
                valArr.add(Double.valueOf(str));
            }
            globalShapelet.add(valArr);

            // read next line
            line = reader.readLine();
        }
        reader.close();

        // Read shapelet label
        reader = new BufferedReader(new FileReader(file_dim));
        line = reader.readLine();
        while (line != null) {
            ArrayList<Double> valArr = new ArrayList<Double>();
            double val = Double.valueOf(line);
            globalShapeletLabelArr.add(val);

            // read next line
            line = reader.readLine();
        }
        reader.close();

//        System.out.println(globalShapelet);
        System.out.println("globalShapeletLabelArr.size(): " + globalShapeletLabelArr.size());
    }

    public void loadCoef() throws IOException {
        // System.getProperty("user.dir"): /Users/leone/ShapeNet
        String expected_value = "Hello, world!";
//        String file ="/Users/leone/Documents/*Summer_research/*ShapeNet/datasets/Distance/coef.txt";
        String file = "/Users/student/Desktop/RedLee/datasets/Distance/coef.txt";

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
//        String file ="/Users/leone/Documents/*Summer_research/*ShapeNet/datasets/Distance/intercept.txt";
        String file = "/Users/student/Desktop/RedLee/datasets/Distance/intercept.txt";

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
//        String file1 = "/Users/leone/Documents/*Summer_research/*ShapeNet/datasets/Distance/feature_train.txt";
//        String file2 = "/Users/leone/Documents/*Summer_research/*ShapeNet/datasets/Distance/feature_test.txt";
        String file1 = "/Users/student/Desktop/RedLee/datasets/Distance/feature_train.txt";
        String file2 = "/Users/student/Desktop/RedLee/datasets/Distance/feature_test.txt";

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

    public void multiplication_PN_TF() {
        final int label0Index=0;
        final int label1Index=1;
        final int[] count = {0};
        final int[] accuracyCount = {0};
        System.out.println("-------------------");
        double intercept = globalInterceptArr.get(0).get(0);
        ArrayList<Double> arrPos = new ArrayList<Double>();
        ArrayList<Double> arrNeg = new ArrayList<Double>();
        ArrayList<Double> arrT = new ArrayList<Double>();
        ArrayList<Double> arrF = new ArrayList<Double>();

        ArrayList<Double> arrPosT = new ArrayList<Double>();
        ArrayList<Double> arrNegF = new ArrayList<Double>();

        final int[] positiveCount = {0};

        // Initialize globalMulti0And1Arr
        for (int i=0; i<2; i++) {
            ArrayList<Double> arr = new ArrayList<Double>();
            globalMulti0And1Arr.add(arr);
        }

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
            double label = globalTimeseriesLabelArr.get(count[0]);

            if (rowSum>0) {
                if (label==0) {
                    // Label = 0
                    positiveCount[0]++;
                    accuracyCount[0]++;
//                    arrT.add(rowSum);
                    arrPosT.add(rowSum);
                    globalMulti0And1Arr.get(label0Index).add(rowSum);
                }else{
                    // Label = 1
//                    arrF.add(rowSum);
                    globalMulti0And1Arr.get(label1Index).add(rowSum);
                }
                arrPos.add(rowSum);
            }else {
                if (label==1) {
                    // Label = 1
                    accuracyCount[0]++;
//                    arrT.add(rowSum);
                    arrNegF.add(rowSum);
                    globalMulti0And1Arr.get(label1Index).add(rowSum);
                }else{
                    // Label = 0
//                    arrF.add(rowSum);
                    globalMulti0And1Arr.get(label0Index).add(rowSum);
                }
                arrNeg.add(rowSum);
            }

            // Add each rowSum to globalMultiArr
            globalMultiArr.add(rowSum);

            // Test the accuracy, see whether is close to 90%.
            count[0] += 1;
            // System.out.println('rowSum: ' + rowSum);
        });

        // Set count gloally
        setCount(count[0]);

        globalMultPosAndNegArr.add(arrPos);
        globalMultPosAndNegArr.add(arrNeg);
        globalMultTFArr.add(arrPosT);
        globalMultTFArr.add(arrNegF);
//        globalMultTFArr.add(arrT);
//        globalMultTFArr.add(arrF);

        accuracy = accuracyCount[0]*1.0/count[0];

        System.out.println("positiveCount: " + positiveCount[0] + ", negativeCount: " + (count[0]-positiveCount[0]));
        System.out.println("accuracyCount: " + accuracyCount[0]);
//        System.out.println("globalMulti0And1Arr: " + globalMulti0And1Arr);
        System.out.println("accuracy: " + accuracy);
        System.out.println("Total count: " + count[0]);
    }

    public ArrayList<ArrayList<Double>> getGlobalShapelet() {
        return globalShapelet;
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getGlobalTimeseries() {
        return globalTimeseries;
    }

    public ArrayList<ArrayList<Double>> getGlobalMulti0And1Arr() { return globalMulti0And1Arr; }

    public ArrayList<Double> getGlobalMultiArr() { return globalMultiArr; }

    public ArrayList<ArrayList<Double>> getGlobalMultPosAndNegArr() {
        return globalMultPosAndNegArr;
    }

    public ArrayList<ArrayList<Double>> getGlobalMultTFArr() {
        return globalMultTFArr;
    }

    public ArrayList<Double> getGlobalTimeseriesLabelArr() {
        return globalTimeseriesLabelArr;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public ArrayList<Double> getGlobalShapeletLabelArr() {
        return globalShapeletLabelArr;
    }

    public static ArrayList<Double> getMultiDimensionDistance(ArrayList<ArrayList<ArrayList<Double>>> localTimeseries, ArrayList<ArrayList<Double>> localShapelet, ArrayList<Double> localTimeseriesLabelArr, ArrayList<Double> localShapeletLabelArr, int timeseriesIndex, int shapeletIndex) {
        ArrayList<Double> distanceArr = new ArrayList<>();
        double aDistance;
        ArrayList<Double> aTimeseries;
        ArrayList<Double> aShapelet;
        int dimension;


        for (int j=0; j<localShapelet.size(); j++) {
            dimension = (int)((double)localShapeletLabelArr.get(j));
            aTimeseries = localTimeseries.get(timeseriesIndex).get(dimension);
            // Only calculate the distance with the matched dimension
            aShapelet = localShapelet.get(j);

            aDistance = getDistance(aTimeseries, aShapelet);
            distanceArr.add(aDistance);
        }
        return distanceArr;
    }

    public static double getDistance(ArrayList<ArrayList<ArrayList<Double>>> localTimeseries, ArrayList<ArrayList<Double>> localShapelet, ArrayList<Double> localTimeseriesLabelArr, ArrayList<Double> localShapeletLabelArr, int timeseriesIndex, int shapeletIndex) {
        ArrayList<Double> distanceArr = new ArrayList<>();
        double aDistance;
        ArrayList<Double> aTimeseries;
        ArrayList<Double> aShapelet;
        int dimension;

        dimension = (int)((double)localShapeletLabelArr.get(shapeletIndex));
        // Only calculate the distance with the matched dimension
        aTimeseries = localTimeseries.get(timeseriesIndex).get(dimension);
        aShapelet = localShapelet.get(shapeletIndex);
        aDistance = getDistance(aTimeseries, aShapelet);

        double distanceSum = 0;
        double distanceMin = Double.MAX_VALUE;;
        for(int i=0; i<(aTimeseries.size()-(aShapelet.size())); i++) {
            // index in indexcurrentShapelet
            distanceSum = 0;
            for(int j=0; j< aShapelet.size(); j++) {
                // index in indexcurrentShapelet
                distanceSum += Math.pow(aTimeseries.get(j+i) - aShapelet.get(j), 2.0);
            }
            distanceSum = Math.sqrt(distanceSum);
            if(distanceSum < distanceMin){
                distanceMin = distanceSum;
            }
        }
        return distanceMin;
    }

    private static double getDistance(ArrayList<Double> timeseries, ArrayList<Double> shapelet) {
        double distanceSum = 0;
        double distanceMin = Double.MAX_VALUE;;
        for(int i=0; i<(timeseries.size()-(shapelet.size())); i++) {
            // index in indexcurrentShapelet
            distanceSum = 0;
            for(int j=0; j< shapelet.size(); j++) {
                // index in indexcurrentShapelet
                distanceSum += Math.pow(timeseries.get(j+i) - shapelet.get(j), 2.0);
            }
            distanceSum = Math.sqrt(distanceSum);
            if(distanceSum < distanceMin){
                distanceMin = distanceSum;
            }
        }
        return distanceMin;
    }

    public static void main(String[] args) throws IOException {
        Dataset aDataset = new Dataset();
        aDataset.loadShapelet();
        aDataset.loadTimeseries();
        aDataset.loadCoef();
        aDataset.loadIntercept();
        aDataset.loadFeatures();
        aDataset.multiplication_PN_TF();
        //---------
    }
}
