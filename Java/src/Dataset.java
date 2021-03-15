import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dataset {

    public ArrayList<ArrayList<ArrayList<Double>>> globalTimeseries = new ArrayList<ArrayList<ArrayList<Double>>>();
    public ArrayList<ArrayList<ArrayList<Double>>> globalRawTimeseries = new ArrayList<ArrayList<ArrayList<Double>>>();
    public ArrayList<ArrayList<ArrayList<Double>>> globalNormalizedTimeseries = new ArrayList<ArrayList<ArrayList<Double>>>();
    public ArrayList<ArrayList<Double>> globalShapelet = new ArrayList<ArrayList<Double>>();
    public ArrayList<ArrayList<Double>> globalNormalizedShapelet = new ArrayList<ArrayList<Double>>();
    public ArrayList<Double> globalTimeseriesLabelArr = new ArrayList<Double>();
    public ArrayList<Double> globalShapeletLabelArr = new ArrayList<Double>();
    public ArrayList<ArrayList<Double>> globalCoefArr = new ArrayList<ArrayList<Double>>();
    public ArrayList<ArrayList<Double>> globalInterceptArr = new ArrayList<ArrayList<Double>>();
    public ArrayList<ArrayList<Double>> globalFeaturesArr = new ArrayList<ArrayList<Double>>();
    public ArrayList<ArrayList<Double>> globalMulti0And1Arr = new ArrayList<ArrayList<Double>>();
    public ArrayList<Double> globalMultiArr = new ArrayList<Double>();

    public ArrayList<ArrayList<Double>> globalMultPosAndNegArr = new ArrayList<ArrayList<Double>>();
    public ArrayList<ArrayList<Double>> globalMultTFArr = new ArrayList<ArrayList<Double>>();
    public ArrayList<ArrayList<ArrayList<Double>>> globalStartEndPoints_ALT_AND_AFP = new ArrayList<ArrayList<ArrayList<Double>>>();
    public double accuracy;
    public int count;

    private boolean normalization = false;
    private int testFileNo = 3;

    final String win = "M:\\JMLToolkit_Multi_ShapeNet-master\\datasets\\";
    final String mac = "/Users/student/Documents/RL_Folder/RedLee/datasets/";
    final String sys = mac;

    public Dataset(boolean normalization) {
        this.normalization = false;
    }
// Testing part------->
    public void loadShapelet_testing() throws IOException {
        // 2576
        // System.out.println(System.getProperty("user.dir"));
        String file_shapelet = sys + "raw-alt-afp-raw/shapelet_0" + testFileNo + ".txt";
        String file_dim = sys + "raw-alt-afp-raw/shapelet_dim_0" + testFileNo + ".txt";

        ArrayList<ArrayList<Double>> shapelet = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> shapeletLabelArr = new ArrayList<Double>();

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
            shapelet.add(valArr);

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
            shapeletLabelArr.add(val);

            // read next line
            line = reader.readLine();
        }
        reader.close();

        // Set global shapelet and label
        setGlobalShapelet(shapelet);
        setGlobalShapeletLabelArr(shapeletLabelArr);

//        System.out.println(shapelet);
        System.out.println("shapelet: " + shapelet);
        System.out.println("shapeletLabelArr: " + shapeletLabelArr);
        System.out.println("shapeletLabelArr.size(): " + shapeletLabelArr.size());
    }

    public void loadTimeseries_testing() throws IOException {
        // 2576
        // System.out.println("System.getProperty(\"user.dir\"): " + System.getProperty("user.dir"));

        String file1 = sys + "raw-alt-afp-raw/ALT_AND_AFP_0" + testFileNo + ".txt";

        String[] fileArr = {file1};

        ArrayList<ArrayList<ArrayList<Double>>> rawTimeseries = new ArrayList<ArrayList<ArrayList<Double>>>();
        ArrayList<Double> timeseriesLabelArr = new ArrayList<Double>();

        int count = 0;
        for (int i=0; i<fileArr.length; i++) {
            String file = fileArr[i];
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            String newline;
            List<String> newStrList;
            double label;
            while (line != null) {
                if (line.contains("\\n")){
                    // Get the label
                    newline = line;
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
                        newStrList = Arrays.asList(str.split(" "));

                        for (int k=0; k<newStrList.size(); k++) {
                            timeseriesArr.get(j).add(Double.valueOf(newStrList.get(k)));
                        }
                    }

                    label = Double.valueOf(-1);
                    timeseriesLabelArr.add(label);

                    rawTimeseries.add(timeseriesArr);
                    count++;
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
            System.out.println("count: " + count);
//            System.out.println("globalTimeseries: " + globalTimeseries);
//            System.out.println("timeseriesLabelArr: " + timeseriesLabelArr);
            System.out.println("globalLabelArr.size(): " + timeseriesLabelArr.size());
            System.out.println("globalLinesTimeseries.size(): " + rawTimeseries.size());
        }
//        System.out.println(globalLinesTimeseries);

        // Set rawTimeseries
        setGlobalRawTimeseries(rawTimeseries);
        setGlobalTimeseriesLabelArr(timeseriesLabelArr);
    }

    public void loadStartEndPoints_testing() throws IOException {
        // 2576
        // System.out.println("System.getProperty(\"user.dir\"): " + System.getProperty("user.dir"));

        String file1 = sys + "raw-alt-afp-raw/StartEndPoints0" + testFileNo + ".txt";

        String[] fileArr = {file1};

        ArrayList<ArrayList<ArrayList<Double>>> startEndPoints_ALT_AND_AFP = new ArrayList<ArrayList<ArrayList<Double>>>();
        int count = 0;
        for (int i=0; i<fileArr.length; i++) {
            String file = fileArr[i];
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            String newline;
            List<String> newStrList;
            while (line != null) {
                if (line.contains("\\n")){
                    newline = line;
                    // Regex has its own escape sequences, denoted with \\ (the escape sequence for \), since Java reserves \
                    // To split by "\n", you'll need \\\\n instead, because \\n in regex represents an actual line break, just as \n represents one in Java.
                    List<String> arrOfStr = Arrays.asList(newline.split("\\\\n"));

                    ArrayList<ArrayList<Double>> startEndPointArr = new ArrayList<ArrayList<Double>>();
                    // Initialize startEndPointArr with size 2
                    int size = 2;
                    for (int l=0; l<size; l++) {
                        startEndPointArr.add(new ArrayList<Double>());
                    }

                    for (int j=arrOfStr.size()-1; j>=0; j--) {
                        String str = arrOfStr.get(j);
                        newStrList = Arrays.asList(str.split(","));

                        for (int k=0; k<newStrList.size(); k++) {
                            startEndPointArr.get(j).add(Double.valueOf(newStrList.get(k)));
                        }
                    }
                    startEndPoints_ALT_AND_AFP.add(startEndPointArr);
//
//                    label = Double.valueOf(-1);
//                    timeseriesLabelArr.add(label);
//
//                    rawTimeseries.add(startEndPointArr);
                    count++;
                }
                // read next line
                line = reader.readLine();
            }

            reader.close();
            System.out.println("count: " + count);
            System.out.println("startEndPoints_ALT_AND_AFP: " + startEndPoints_ALT_AND_AFP);
            System.out.println("startEndPoints_ALT_AND_AFP.size(): " + startEndPoints_ALT_AND_AFP.size());
        }
        // Reformat startEndPoints_ALT_AND_AFP

        ArrayList<ArrayList<ArrayList<Double>>> newStartEndPoints_ALT_AND_AFP = new ArrayList<>();
        for (int k=0; k<2; k++) {
            ArrayList<ArrayList<Double>> arr = new ArrayList<>();
            for (int i=0; i<startEndPoints_ALT_AND_AFP.size(); i++) {
                ArrayList<ArrayList<Double>> aArr = startEndPoints_ALT_AND_AFP.get(i);

                ArrayList<Double> valArr = aArr.get(k);
                arr.add(valArr);
            }
            newStartEndPoints_ALT_AND_AFP.add(arr);
        }
//        System.out.println("newStartEndPoints_ALT_AND_AFP: " + newStartEndPoints_ALT_AND_AFP);
        // Set abc globally
        setGlobalStartEndPoints_ALT_AND_AFP(newStartEndPoints_ALT_AND_AFP);
    }

    private void setGlobalStartEndPoints_ALT_AND_AFP(ArrayList<ArrayList<ArrayList<Double>>> startEndPoints_03_06) {
        this.globalStartEndPoints_ALT_AND_AFP = startEndPoints_03_06;
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getGlobalStartEndPoints_ALT_AND_AFP() {
        return globalStartEndPoints_ALT_AND_AFP;
    }
// End testing part------->
    // ------------------------------------------------------------------------------------------

    public void loadShapelet() throws IOException {
        // 2576
        // System.out.println(System.getProperty("user.dir"));
        String file_shapelet = sys + "ALT_AFP_ShapeNet/ALT_AFP/shapelet.txt";
        String file_dim = sys + "ALT_AFP_ShapeNet/ALT_AFP/shapelet_dim.txt";

        ArrayList<ArrayList<Double>> shapelet = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> shapeletLabelArr = new ArrayList<Double>();

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
            shapelet.add(valArr);

            // read next line
            line = reader.readLine();
        }
        reader.close();

        // Read shapelet label
        reader = new BufferedReader(new FileReader(file_dim));
        line = reader.readLine();
        while (line != null) {
            ArrayList<Double> valArr = new ArrayList<Double>();
            // Only one value per line
            double val = Double.valueOf(line);
            shapeletLabelArr.add(val);

            // read next line
            line = reader.readLine();
        }
        reader.close();

        // Set global shapelet and label
        setGlobalShapelet(shapelet);
        setGlobalShapeletLabelArr(shapeletLabelArr);

//        System.out.println(shapelet);
        System.out.println("shapeletLabelArr.size(): " + shapeletLabelArr.size());
    }

    public void loadTimeseries() throws IOException {
        // 2576
        // System.out.println("System.getProperty(\"user.dir\"): " + System.getProperty("user.dir"));
        String file1 = sys + "ALT_AFP_ShapeNet/ALT_AFP/ALT_AND_AFP_ARFF/ALT_AND_AFP_TRAIN.arff";
        String file2 = sys + "ALT_AFP_ShapeNet/ALT_AFP/ALT_AND_AFP_ARFF/ALT_AND_AFP_TEST.arff";

        String[] fileArr = {file1, file2};

        ArrayList<ArrayList<ArrayList<Double>>> rawTimeseries = new ArrayList<ArrayList<ArrayList<Double>>>();
        ArrayList<Double> timeseriesLabelArr = new ArrayList<Double>();

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
                                    timeseriesLabelArr.add(label);
                                }
                            }else{
                                timeseriesArr.get(j).add(Double.valueOf(newStrList.get(k)));
                            }
                        }
                    }
                    rawTimeseries.add(timeseriesArr);
                    count++;
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
            System.out.println("count: " + count);
            System.out.println("globalLabelArr.size(): " + timeseriesLabelArr.size());
            System.out.println("globalLinesTimeseries.size(): " + rawTimeseries.size());
        }
//        System.out.println(globalLinesTimeseries);

        // Set globalRawTimeseries
        setGlobalRawTimeseries(rawTimeseries);
        setGlobalTimeseriesLabelArr(timeseriesLabelArr);

        // Set raw values or normalized value
        if (normalization) {
            int dimensionSize = 2;
            switchToNormalTimeseries(dimensionSize);
        } else {
            setGlobalTimeseries(globalRawTimeseries);
        }
    }

    public void loadCoef() throws IOException {
        // System.getProperty("user.dir"): /Users/leone/ShapeNet
        String file = sys + "Distance/coef.txt";

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
        String file = sys + "Distance/intercept.txt";

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
        String file1 = sys + "Distance/feature_train.txt";
        String file2 = sys + "Distance/feature_test.txt";

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

    public void loadTimeseries_5Dim() throws IOException {
        // 2576
        // System.out.println("System.getProperty(\"user.dir\"): " + System.getProperty("user.dir"));
        String file1 = sys + "5Dim/train.txt";
        String file2 = sys + "5Dim/test.txt";

        String[] fileArr = {file1, file2};

        ArrayList<ArrayList<ArrayList<Double>>> rawTimeseries = new ArrayList<ArrayList<ArrayList<Double>>>();
        ArrayList<Double> timeseriesLabelArr = new ArrayList<Double>();

        int count = 0;
        for (int i=0; i<fileArr.length; i++) {
            String file = fileArr[i];
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            double label;
            while (line != null) {
//                System.out.println("Hello world!");
//                System.out.println(line);
                if (line.contains("[")){
                    // read next first line - label
                    line = reader.readLine();
                    String[] arr_str_label = line.split(", ");

                    // arr_str_label[0] to arr_str_label[14] are the same
                    label = Double.valueOf(arr_str_label[0]);
//                    System.out.println("my_label: " + label);
                    timeseriesLabelArr.add(label);
                    // read next five lines - 5 dimensions timeseries
                    int size = 5;
                    ArrayList<ArrayList<Double>> multidim_timeseries_arr = new ArrayList<ArrayList<Double>>();
                    for (int j=0; j<size; j++) {
                        line = reader.readLine();

                        String[] arr_str_timeseries = line.split(", ");
                        ArrayList<Double> valArr = new ArrayList<>();
                        for (int k=0; k<arr_str_timeseries.length; k++) {
                            String str = arr_str_timeseries[k];
                            valArr.add(Double.valueOf(str));
                        }
                        multidim_timeseries_arr.add(valArr);
                    }
                    rawTimeseries.add(multidim_timeseries_arr);
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
            System.out.println("count: " + count);
            System.out.println("globalLabelArr.size(): " + timeseriesLabelArr.size());
            System.out.println("globalLinesTimeseries.size(): " + rawTimeseries.size());
        }
//        System.out.println(globalLinesTimeseries);

        // Set globalRawTimeseries
        setGlobalRawTimeseries(rawTimeseries);
        setGlobalTimeseriesLabelArr(timeseriesLabelArr);

        // Set raw values or normalized value
        if (normalization) {
            int dimensionSize = 2;
            switchToNormalTimeseries(dimensionSize);
        } else {
            setGlobalTimeseries(globalRawTimeseries);
        }
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

    private void setGlobalShapelet(ArrayList<ArrayList<Double>> shapelet) {
        this.globalShapelet = shapelet;
    }

    public ArrayList<ArrayList<Double>> getGlobalShapelet() {
        return globalShapelet;
    }

    private void setGlobalRawTimeseries(ArrayList<ArrayList<ArrayList<Double>>> rawTimeseries) {
        this.globalRawTimeseries = rawTimeseries;
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getGlobalRawTimeseries() {
        return globalRawTimeseries;
    }

    private void setGlobalTimeseries(ArrayList<ArrayList<ArrayList<Double>>> timeseries) {
        this.globalTimeseries = timeseries;
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

    private void setGlobalShapeletLabelArr(ArrayList<Double> shapeletLabelArr) {
        this.globalShapeletLabelArr = shapeletLabelArr;
    }

    public ArrayList<Double> getGlobalShapeletLabelArr() {
        return globalShapeletLabelArr;
    }

    private void setGlobalTimeseriesLabelArr(ArrayList<Double> timeseriesLabelArr) {
        this.globalTimeseriesLabelArr = timeseriesLabelArr;
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

    public static ArrayList<ArrayList<Double>> getMultiDimensionDistance(ArrayList<ArrayList<ArrayList<Double>>> localTimeseries, ArrayList<ArrayList<Double>> localShapelet, ArrayList<Double> localTimeseriesLabelArr, ArrayList<Double> localShapeletLabelArr, int timeseriesIndex, int shapeletIndex) {
        ArrayList<ArrayList<Double>> distanceAndStartIndexArr = new ArrayList<>();
        ArrayList<Double> startIndexArr = new ArrayList<>();
        ArrayList<Double> distanceArr = new ArrayList<>();
        double[][] distanceAndIndex = {{},{}};
        double aStartIndex;
        double aDistance;
        ArrayList<Double> aTimeseries;
        ArrayList<Double> aShapelet;
        int dimension;

        for (int j=0; j<localShapelet.size(); j++) {
            dimension = (int)((double)localShapeletLabelArr.get(j));
            aTimeseries = localTimeseries.get(timeseriesIndex).get(dimension);
            // Only calculate the distance with the matched dimension
            aShapelet = localShapelet.get(j);

            // distanceAndIndex[0][0]: startIndex;
            // distanceAndIndex[1][0]: distanceMin;
            distanceAndIndex = getDistance(aTimeseries, aShapelet);
            aStartIndex = distanceAndIndex[0][0];
            aDistance = distanceAndIndex[1][0];

            startIndexArr.add(aStartIndex);
            distanceArr.add(aDistance);
        }

        distanceAndStartIndexArr.add(startIndexArr);
        distanceAndStartIndexArr.add(distanceArr);
        return distanceAndStartIndexArr;
    }

    public static double[][] getDistance(ArrayList<Double> timeseries, ArrayList<Double> shapelet) {
        // distanceAndIndex[0][0]: startIndex;
        // distanceAndIndex[1][0]: distanceSumMin;
        double[][] distanceAndIndex = {{-1},{-1}};
        double distanceSum;
        double distanceSumMin = Double.MAX_VALUE;
        int startIndex = 0;

        for(int i=0; i<(timeseries.size()-(shapelet.size()))+1; i++) {
            // index in indexcurrentShapelet
            distanceSum = 0;
            for(int j=0; j< shapelet.size(); j++) {
                // index in indexcurrentShapelet
                distanceSum += Math.pow((timeseries.get(j+i) - shapelet.get(j)), 2.0);
            }
            distanceSum = Math.sqrt(distanceSum);
            if(distanceSum < distanceSumMin){
                distanceSumMin = distanceSum;
                startIndex = i;
                distanceAndIndex[0][0] = startIndex;
                distanceAndIndex[1][0] = distanceSumMin;
            }
        }
        return distanceAndIndex;
    }

    public void switchToNormalTimeseries(int dimensionSize) {
        ArrayList<ArrayList<ArrayList<Double>>> timeseries = getGlobalRawTimeseries();
        ArrayList<ArrayList<ArrayList<Double>>> normalTimeseries = getNormalTimeseries(timeseries, dimensionSize);
        setNormalTimeseries(normalTimeseries);
        setGlobalTimeseries(normalTimeseries);
    }

    private void setNormalTimeseries(ArrayList<ArrayList<ArrayList<Double>>> normalizedTimeseries) {
        this.globalNormalizedTimeseries = normalizedTimeseries;
    }

    private ArrayList<ArrayList<ArrayList<Double>>> getNormalTimeseries(ArrayList<ArrayList<ArrayList<Double>>> timeseriesArr, int dimensionSize) {
        ArrayList<ArrayList<Double>> dataArr = new ArrayList<>();
        int dimensionLevelIndex;
        double[][] meanAndAStd;
        ArrayList<Double> meanArr = new ArrayList<>();
        ArrayList<Double> stdArr = new ArrayList<>();

        for (int i=0; i<dimensionSize; i++) {
            dimensionLevelIndex = i;

            ArrayList<Double> arr = new ArrayList<>();
            for (int j=0; j<timeseriesArr.size(); j++) {
                ArrayList<Double> timeseriesOneDimensionArr = timeseriesArr.get(j).get(dimensionLevelIndex);
                for (int k=0; k<timeseriesOneDimensionArr.size(); k++) {
                    arr.add(timeseriesOneDimensionArr.get(k));
                }
            }
            dataArr.add(arr);

            // After get all values of one dimension, normalize them.
            meanAndAStd = getNormalizationProperty(dataArr.get(i));
            // meanAndAStd[0][0]: mean;
            // meanAndAStd[1][0]: std;
            meanArr.add(meanAndAStd[0][0]);
            stdArr.add(meanAndAStd[1][0]);
        }

        ArrayList<ArrayList<ArrayList<Double>>> normalizedTimeseriesArr = new ArrayList<ArrayList<ArrayList<Double>>>();
        // Initialize normalizedDataArr by copy all values in timeseriesArr
        double mean;
        double std;
        for (int i=0; i<timeseriesArr.size(); i++) {
            ArrayList<ArrayList<Double>> arrArr = new ArrayList<ArrayList<Double>>();
            for (int j=0; j<timeseriesArr.get(i).size(); j++) {
                dimensionLevelIndex = j;
                mean = meanArr.get(dimensionLevelIndex);
                std = meanArr.get(dimensionLevelIndex);
                ArrayList<Double> arr = new ArrayList<>();
                double val, zVal;
                for (int k=0; k<timeseriesArr.get(i).get(j).size(); k++) {
                    val = timeseriesArr.get(i).get(j).get(k);
                    zVal = getZvalue(mean, std, val);
                    arr.add(zVal);
                }
                arrArr.add(arr);
            }
            normalizedTimeseriesArr.add(arrArr);
        }

        // System.out.println(normalizedTimeseriesArr);

        return normalizedTimeseriesArr;
    }

    private double[][] getNormalizationProperty(ArrayList<Double> dataArr) {
        // meanAndAStd[0][0]: mean;
        // meanAndAStd[1][0]: std;
        double[][] meanAndAStd = {{-1}, {-1}};
        double sum = 0;
        double val;

        for (int i=0; i<dataArr.size(); i++) {
            val = dataArr.get(i);
            sum = sum + val;
        }

        double abs;
        double mean = sum/dataArr.size();
        double squareSum = 0;

        for (int i=0; i<dataArr.size(); i++) {
            abs = Math.abs(mean-dataArr.get(i));
            val = Math.pow(abs, 2);
            squareSum = squareSum + val;
        }

        double variance = squareSum/dataArr.size();
        double std = Math.sqrt(variance);

        meanAndAStd[0][0] = mean;
        meanAndAStd[1][0] = std;

        return meanAndAStd;
    }

    private double getZvalue(double mean, double std, double val) {
        return (val - mean)/std;
    }

    public static void main(String[] args) throws IOException {
        Dataset aDataset = new Dataset(false);
        aDataset.loadTimeseries_5Dim();
    }
}
