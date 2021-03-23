import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartTheme;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// MultiShapelet Only
public class DualAxisChart_3 extends ApplicationFrame {

    private Controller controller;
    public ArrayList<ChartPanel> chartPanelArr = new ArrayList<>();
    public ChartPanel chartPanel;
    public JPanel panel;
    public JScrollPane scrollPane;
    private CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot();
    final String team1 = "1st Team";
    final String team2 = "2nd Team";
    final String label0 = "1st Timeseries Dimension ";
    final String label1 = "2nd Timeseries Dimension ";
    final String labelTimeseries = "Timeseries Dimension ";
    final String labelShapelet = "Shapelet Dimension ";
    // ---------------------------
    final String label_Shapelet_testing = "Shapelet only ";
    final String labelTimeseries_testing = "Timeseries No ";
    final String labelShapelet_testing = "Shapelet ";

    private ArrayList<ArrayList<ArrayList<Double>>> localTimeseries = new ArrayList<ArrayList<ArrayList<Double>>>();
    private ArrayList<ArrayList<Double>> localShapelet = new ArrayList<ArrayList<Double>>();
    final int topK = 16;
    final int width = 500;
    final int height = 550;
    final int dim_cnt = 2;

    Color[] ten_colors = {new Color(255,0,0), new Color(255,204,51), new Color(0,204,0), new Color(51,153,255), new Color(255,102,0), new Color(153,153,153), new Color(153,102,0), new Color(102,51,0), new Color(102,0,153), new Color(0,0,0)};
    Color timeseries_color = new Color(153,0,0);

    // ------------------------------------------------------------------------------------------------------\
    public DualAxisChart_3(ArrayList<ArrayList<ArrayList<Double>>> localTimeseries, ArrayList<ArrayList<Double>> localShapelet, ArrayList<Double> localShapeletLabelArr, ArrayList<ArrayList<ArrayList<Double>>> startEndPoints_ALT_AND_AFP) {
        super("DualAxisChart");

        // set localTimeseries and localShapelet
        setLocalTimeseries(localTimeseries);
        setLocalShapelet(localShapelet);

        int timesriesIndexSize = localTimeseries.size();

        // Set charts according to the shapelets
        for (int i=0; i<localShapelet.size(); i++) {
            int index = i;
            int dimension = i;

            final ArrayList<JFreeChart> chartArr = createChartArr_testing(index, timesriesIndexSize, dimension, startEndPoints_ALT_AND_AFP);
            for (int j=0; j<chartArr.size(); j++) {
                JFreeChart chart = chartArr.get(j);
                ChartPanel chartPanel = new ChartPanel(chart);

                chartPanel.setPreferredSize(
                        new Dimension(width, height));

                chartPanelArr.add(chartPanel);
            }
        }

        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(4, 1));
        panel.setPreferredSize(new Dimension(width, height*5));
        for (int i=0; i<chartPanelArr.size(); i++) {
            panel.add(chartPanelArr.get(i));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(width,height*5));
        setContentPane(scrollPane);
        // Set the panel globally
//        setScrollPane(scrollPane);
        // setPanel(panel);
        // setChartPanelArr(chartPanelArr);
    }

    private ArrayList<JFreeChart> createChartArr_testing(int shapeletIndex, int timeseriesIndexSize, int timesriesDimension, ArrayList<ArrayList<ArrayList<Double>>> startEndPoints_ALT_AND_AFP) {
        final String[] strShapelet = {"Z-normalized AFP(μg/L)", "Z-normalized ALT(U/L)"};
        final String[] strTimeseries = {"AFP(μg/L)", "ALT(U/L)"};

//        System.out.println("startEndPoints_ALT_AND_AFP: " + startEndPoints_ALT_AND_AFP);
        // index 0: shapelet Chart
        // index 1: timeseries Chart
        int shapeletChartIndex = 0;
        int timeseriesChartIndex = 1;
        ArrayList<XYSeriesCollection> timeseriseAndShapelet = createTimeseriseAndShapet_testing(shapeletIndex, timeseriesIndexSize, timesriesDimension, startEndPoints_ALT_AND_AFP);

        // Shapelet
        final XYSeriesCollection shapelet = timeseriseAndShapelet.get(shapeletChartIndex);
        final NumberAxis xaxShapelet = new NumberAxis("Shapelet for ALT");
        final NumberAxis xaxTimeseries = new NumberAxis("Time points");
//        final NumberAxis xaxTimeseries = new NumberAxis("Top 16 timeseries having the minimum distance from the shapelet (aligned with the best matching position)");
        xaxTimeseries.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        final NumberAxis rangeAxisShapelet = new NumberAxis("" + strShapelet [timesriesDimension]);
        rangeAxisShapelet.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // Line render
        final XYSplineRenderer rendererShapelet = new XYSplineRenderer(1);
        rendererShapelet.setBaseStroke(new BasicStroke(2.0f));
        rendererShapelet.setAutoPopulateSeriesStroke(false);
        final XYPlot xyPlotShapelet =
                new XYPlot (shapelet, xaxTimeseries,
                        rangeAxisShapelet, rendererShapelet);
        xyPlotShapelet.setDomainGridlinesVisible(true);;

        // Get min and max of the shapelet
        // Index 0: min
        // Index 1: max
//        ArrayList<Double> minMaxShapeletArr = getMinMaxShapelet(shapeletIndex);
//        xyPlotShapelet.getRangeAxis().setLowerBound(minMaxShapeletArr.get(0)-1);
//        xyPlotShapelet.getRangeAxis().setUpperBound(minMaxShapeletArr.get(1)+1);
//        xyPlotShapelet.setForegroundAlpha(0.7f);

        final JFreeChart chartShapelet = new JFreeChart(
                "Score Bord", new Font("SansSerif", Font.BOLD, 8),
                xyPlotShapelet, true);

        // ---------------------------------------------------------------
        // ---------------------------------------------------------------

        // Timeserise

        final XYSeriesCollection timeserise = timeseriseAndShapelet.get(timeseriesChartIndex);
        final NumberAxis rangeAxisTimeseries = new NumberAxis("" + strTimeseries[timesriesDimension]);
        rangeAxisTimeseries.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Line render
        final XYSplineRenderer rendererTimeseries = new XYSplineRenderer(1);
        rendererTimeseries.setBaseStroke(new BasicStroke(2.0f));
        rendererTimeseries.setAutoPopulateSeriesStroke(false);
        rendererTimeseries.setBaseToolTipGenerator(
                new StandardXYToolTipGenerator());

        // Set color manually
        rendererTimeseries.setSeriesPaint(0, Color.RED);
        rendererTimeseries.setSeriesPaint(1, Color.BLUE);
        rendererTimeseries.setSeriesPaint(2, Color.GREEN);
        rendererTimeseries.setSeriesPaint(3, ChartColor.ORANGE);
        rendererTimeseries.setSeriesPaint(4, Color.MAGENTA);
        rendererTimeseries.setSeriesPaint(6, Color.CYAN);
        rendererTimeseries.setSeriesPaint(7, Color.PINK);
        rendererTimeseries.setSeriesPaint(8, Color.GRAY);
        rendererTimeseries.setSeriesPaint(9, ChartColor.DARK_RED);
        rendererTimeseries.setSeriesPaint(10, ChartColor.DARK_BLUE);
        rendererTimeseries.setSeriesPaint(11, ChartColor.VERY_DARK_YELLOW);
        rendererTimeseries.setSeriesPaint(12, ChartColor.DARK_YELLOW);
        rendererTimeseries.setSeriesPaint(13, ChartColor.GRAY);
        rendererTimeseries.setSeriesPaint(14, ChartColor.DARK_MAGENTA);
        rendererTimeseries.setSeriesPaint(15, ChartColor.DARK_GREEN);

        final XYPlot xyPlotTimeseries =
                new XYPlot(timeserise, xaxTimeseries,
                        rangeAxisTimeseries, rendererTimeseries);
        xyPlotTimeseries.setDomainGridlinesVisible(true);

        // Get min and max of the timeseries
        // Index 0: min
        // Index 1: max
//        ArrayList<Double> minMaxTimeseriesArr = getMinMaxTimeseries(timeseriesIndexSize, timesriesDimension);
//        xyPlotTimeseries.getRangeAxis().setLowerBound(minMaxTimeseriesArr.get(0)-5);
//        xyPlotTimeseries.getRangeAxis().setUpperBound(minMaxTimeseriesArr.get(1)+5);

        final JFreeChart chartTimeserise = new JFreeChart(
                "", new Font("SansSerif", Font.BOLD, 8),
                xyPlotTimeseries, true);

//
//        XYPlot xyPlot = chartShapelet.getXYPlot();
//        ValueAxis domainAxis = xyPlot.getDomainAxis();
//        ValueAxis rangeAxis = xyPlot.getRangeAxis();
//
//        domainAxis.setRange(0.0, 1.0);
//        rangeAxis.setRange(0.0, 1.0);

        // Add chartShapelet and chartTimeserise into a chart array
        ArrayList<JFreeChart> chartArr = new ArrayList<>();
        chartArr.add(chartShapelet);
        chartArr.add(chartTimeserise);

        return chartArr;
    }

    private ArrayList<XYSeriesCollection> createTimeseriseAndShapet_testing(int shapeletIndex, int timeseriesIndexSize, int dimemsion, ArrayList<ArrayList<ArrayList<Double>>> startEndPoints_ALT_AND_AFP) {

        final XYSeriesCollection dataset
                = new XYSeriesCollection();

        // Shapelet
        ArrayList<Double> shapelet = localShapelet.get(shapeletIndex);

        // Get distances and start position between each pair of timeseries and shapelet
        ArrayList<Double> timeserise;
        double[][] distanceSortingArr = new double[timeseriesIndexSize][];
        double[][] distanceAndIndex = {{},{}};
        int xAxisStartIndex;
        double distance;
        ArrayList<Integer> shiftLenBeforeShapeletArr = new ArrayList<>();
        int maxShiftLen = (int) Double.NEGATIVE_INFINITY;
        // Get the global start index for all timesries
        for (int i=0; i<timeseriesIndexSize; i++){
            double[] distanceSorting = {-1, -1};
            int timeseriesIndex = i;
            // Timeseries
            timeserise = localTimeseries.get(timeseriesIndex).get(dimemsion);

            // distanceAndIndex[0][0]: startIndex;
            // distanceAndIndex[1][0]: distanceMin;
            distanceAndIndex = Dataset.getDistance(timeserise, shapelet);
            distance = distanceAndIndex[1][0];

            distanceSorting[0] = timeseriesIndex;
            distanceSorting[1] = distance;
            distanceSortingArr[i] = distanceSorting;
        }

        ArrayList<ArrayList<Double>> startEndPoints_ALT_OR_AFP = startEndPoints_ALT_AND_AFP.get(shapeletIndex);
        int startPointIndex = 0;
        for (int i=0; i<startEndPoints_ALT_OR_AFP.size(); i++){
            xAxisStartIndex = (int) ((double) startEndPoints_ALT_OR_AFP.get(i).get(startPointIndex));
            // Record each xAxisStartIndex between each pair of timeseries and shapelet
            shiftLenBeforeShapeletArr.add(xAxisStartIndex);
            // Set/update the maximum xAxisStartIndex
            if (xAxisStartIndex>maxShiftLen) {
                maxShiftLen = xAxisStartIndex;
            }
        }

        java.util.Arrays.sort(distanceSortingArr, new java.util.Comparator<double[]>() {
            public int compare(double[] a, double[] b) {
                return Double.compare(a[1], b[1]);
            }
        });

        // Timeseries
        ArrayList<ArrayList<Double>> timeseriesArrList = twoDArrayToList(distanceSortingArr);

        Set<Integer> timeseriesIndexSet = new HashSet<Integer>();
        for (int i=0; i<topK; i++) {
//            ArrayList<Double> arr = timeseriesArrList.get(i);
            // Use add() method to add elements into the Set
            // arr index 0: timeseries index
            // arr index 1: timeseries distance
            timeseriesIndexSet.add(i);
        }

        System.out.println("timeseriesIndexSet: " + timeseriesIndexSet);

        // Create shapelet and corresponding timeseries
        ArrayList<XYSeriesCollection> timeseriseAndShapelet = new ArrayList<>();
        timeseriseAndShapelet.add(
                shapelet_testing(shapeletIndex, maxShiftLen));
        timeseriseAndShapelet.add(timeserise_testing(shapeletIndex, timeseriesIndexSize, dimemsion, timeseriesIndexSet, shiftLenBeforeShapeletArr, maxShiftLen));

        return timeseriseAndShapelet;
    }

    private XYSeriesCollection shapelet_testing(int shapeletIndex, int maxShiftLen) {
        ArrayList<Double> shapelet = localShapelet.get(shapeletIndex);

        final XYSeriesCollection dataset
                = new XYSeriesCollection();

        XYSeries series = new XYSeries("Series Shapelet");

        for (int i = 0; i < shapelet.size() + maxShiftLen; i++) {
            if (i+1 > maxShiftLen) {
                int index = i-maxShiftLen;
                double val = shapelet.get(index);
                series.add((i + 1), val);
            } else {
                series.add((i + 1), null);
            }
        }

        dataset.addSeries(series);

        return dataset;
    }

    private XYSeriesCollection timeserise_testing(int shapeletIndex, int timeseriesIndexSize, int dimemsion, Set<Integer> timeseriesIndexSet, ArrayList<Integer> shiftLenBeforeShapeletArr, int maxShiftLen) {

        final XYSeriesCollection dataset
                = new XYSeriesCollection();

        // Get distances and start position between each pair of timeseries and shapelet
        ArrayList<Double> timeserise;

        // Timeseries Plot Dataset ----- Plot timeseries by using globsal start index
        int xAxisShiftLen;
        for (int i=0; i<timeseriesIndexSize; i++){
            // Create a series to store the values
            XYSeries series = new XYSeries("Series Timeserise " + (i+1));

            int timeseriesIndex = i;

            // Timeseries
            if (timeseriesIndexSet.contains(timeseriesIndex)) {
                timeserise = localTimeseries.get(timeseriesIndex).get(dimemsion);
                xAxisShiftLen = maxShiftLen - shiftLenBeforeShapeletArr.get(i);

                for (int j = 0; j < timeserise.size() + xAxisShiftLen; j++) {
                    if (j+1 > xAxisShiftLen) {
                        int index = j-xAxisShiftLen;
                        double val = timeserise.get(index);
                        series.add((j + 1), val);
                    } else {
                        series.add((j + 1), null);
                    }
                }

                // Add the series into dataset
                dataset.addSeries(series);
            }
        }

        return dataset;
    }

    private ArrayList<ArrayList<Double>> twoDArrayToList(double[][] twoDArray) {
        ArrayList<ArrayList<Double>> arrayList = new ArrayList<>();
        int m = twoDArray.length;

        ArrayList<Double> arr;
        for(int i=0;i<m;i++) {
            arr = new ArrayList<>();
            int n = twoDArray[i].length;
            for(int j=0;j<n;j++) {
                arr.add(twoDArray[i][j]);
            }
            arrayList.add(arr);
        }

        return arrayList;
    }

    private ArrayList<Double> getMinMaxShapelet (int shapeletIndex) {
        ArrayList<Double> shapelet = localShapelet.get(shapeletIndex);
        double min = Collections.min(shapelet);
        double max = Collections.max(shapelet);

        ArrayList<Double> minMaxArr = new ArrayList<>();
        // Index 0: min
        // Index 1: max
        minMaxArr.add(min);
        minMaxArr.add(max);

        return minMaxArr;
    }

    private ArrayList<Double> getMinMaxTimeseries (int timeseriesIndexSize, int dimemsion) {
        ArrayList<Double> timeserise;
        ArrayList<Double> minArr = new ArrayList<>();
        ArrayList<Double> maxArr = new ArrayList<>();
        ArrayList<Double> globalMinMaxArr = new ArrayList<>();

        double min;
        double max;
        for (int i=0; i<timeseriesIndexSize; i++){
            int timeseriesIndex = i;
            // Timeseries
            timeserise = localTimeseries.get(timeseriesIndex).get(dimemsion);

            min = Collections.min(timeserise);
            max = Collections.max(timeserise);
            minArr.add(min);
            maxArr.add(max);
        }

        double globalMin = Collections.min(minArr);
        double globalMax = Collections.max(maxArr);

        // Index 0: min
        // Index 1: max
        globalMinMaxArr.add(globalMin);
        globalMinMaxArr.add(globalMax);

        return globalMinMaxArr;
    }
    // ------------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------------------------

    public DualAxisChart_3(ArrayList<ArrayList<ArrayList<Double>>> localTimeseries, ArrayList<ArrayList<Double>> localShapelet, ArrayList<Double> localShapeletLabelArr) {
        super("DualAxisChart_3");

        // set localTimeseries and localShapelet
        setLocalTimeseries(localTimeseries);
        setLocalShapelet(localShapelet);

        // Set charts according to the shapelets
        for (int i=0; i<localShapelet.size(); i++) {
            int shapeletIndex = i;
            int classDimension = (int) ((double) localShapeletLabelArr.get(i));
            final JFreeChart chart = createChart(shapeletIndex, classDimension);
            final ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(
                    new Dimension(width, height));
            chartPanelArr.add(chartPanel);
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(dim_cnt, 1));
        panel.setPreferredSize(new Dimension(width, height*dim_cnt));
        for (int i=0; i<chartPanelArr.size(); i++) {
            panel.add(chartPanelArr.get(i));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(width,height*dim_cnt));
        // setContentPane(scrollPane);
        // Set the panel globally
        setScrollPane(scrollPane);
        // setPanel(panel);
        // setChartPanelArr(chartPanelArr);
    }

    private JFreeChart createChart(int shapeletIndex, int classDimension) {
        final int shapeletRenderIndex = 0;
        final int timeseriesRenderIndex = 0;
        final int defaultTimeseriesFirstK = 10;
        final int seriesNo_shapelet = 0;
        final int timeseries_lengh = localTimeseries.get(0).get(0).size();

        int timeseriesIndexSize = localTimeseries.size();
        ArrayList<Integer> timeseriesIndexArr = new ArrayList<>();
        for (int i=0; i<timeseriesIndexSize; i++) {
            timeseriesIndexArr.add(i);
        }
        // Get distances and start position between each pair of timeseries and shapelet
        ArrayList<Integer> shiftLenBeforeShapeletArr = getShiftLenBeforeShapeletArr(timeseriesIndexArr, shapeletIndex, classDimension);
        int maxShiftLen = Collections.max(shiftLenBeforeShapeletArr);

        Set<Integer> timeseriesIndexSet = new HashSet<Integer>();
        for (int i=0; i<topK; i++) {
            timeseriesIndexSet.add(i);
        }

        //->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->
        // Shapelet
        final CategoryDataset shapelet = createShapelet(shapeletIndex, maxShiftLen, timeseries_lengh);
        final NumberAxis rangeAxisShapelet = new NumberAxis("Shapelet " + shapeletIndex + " - D" + classDimension);
        rangeAxisShapelet.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Line render
        final CategoryItemRenderer rendererShapelet = new LineAndShapeRenderer();
        rendererShapelet.setSeriesPaint(seriesNo_shapelet, ten_colors[classDimension%10]);
        rendererShapelet.setBaseToolTipGenerator(
                new StandardCategoryToolTipGenerator());

        final CategoryPlot subplotShapelet =
                new CategoryPlot(shapelet, null,
                        rangeAxisShapelet, rendererShapelet);
        subplotShapelet.setDomainGridlinesVisible(true);;

        subplotShapelet.setForegroundAlpha(0.7f);
        subplotShapelet.setRenderer(shapeletRenderIndex, rendererShapelet);

        // ---------------------------------------------------------------
        // ---------------------------------------------------------------

        // TimeseriseAndShapelet
        final CategoryDataset timeserise = createTimeserise(defaultTimeseriesFirstK, classDimension, timeseriesIndexSet, shiftLenBeforeShapeletArr, maxShiftLen);
        final NumberAxis rangeAxisTimeseries = new NumberAxis("Multi-Timeserise D" + classDimension);
        rangeAxisTimeseries.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Line render
        final CategoryItemRenderer rendererTimeseries = new LineAndShapeRenderer();
        rendererTimeseries.setBaseToolTipGenerator(
                new StandardCategoryToolTipGenerator());
        final CategoryPlot subplotTimeseries =
                new CategoryPlot(timeserise, null,
                        rangeAxisTimeseries, rendererTimeseries);
        subplotTimeseries.setDomainGridlinesVisible(true);;

        subplotTimeseries.setForegroundAlpha(0.7f);
        // Add timeseries into timeseries chart
        subplotTimeseries.setRenderer(timeseriesRenderIndex, rendererTimeseries);

        // -----------------------------------------------

        final CategoryAxis domainAxis = new CategoryAxis("Over");

        final CombinedDomainCategoryPlot plot =
                new CombinedDomainCategoryPlot(domainAxis);

        plot.add(subplotShapelet, 1);
        plot.add(subplotTimeseries, 2);

        final JFreeChart chart = new JFreeChart(
                "DualAxisChart_3", new Font("SansSerif", Font.BOLD, 8),
                plot, true);

        setPlot(plot);
        return chart;
    }

    private CategoryDataset createTimeserise(int timeseriesIndexSize, int dimemsion, Set<Integer> timeseriesIndexSet, ArrayList<Integer> shiftLenBeforeShapeletArr, int maxShiftLen) {

        final DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        // ->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->-
        // Get distances and start position between each pair of timeseries and shapelet
        ArrayList<Double> timeserise;

        // Timeseries Plot Dataset ----- Plot timeseries by using globsal start index
        int xAxisShiftLen;
        for (int i=0; i<timeseriesIndexSize; i++){
            // Create a series to store the values

            int timeseriesIndex = i;
            // Timeseries
            if (timeseriesIndexSet.contains(timeseriesIndex)) {
                timeserise = localTimeseries.get(timeseriesIndex).get(dimemsion);
                xAxisShiftLen = maxShiftLen - shiftLenBeforeShapeletArr.get(i);

                for (int j = 0; j < timeserise.size() + xAxisShiftLen; j++) {
                    if (j+1 > xAxisShiftLen) {
                        double val = timeserise.get(j-xAxisShiftLen);
                        dataset.addValue(val,
                                labelTimeseries+(i+1), "" + (j + 1));
                    } else {
                        dataset.addValue(null,
                                labelTimeseries+(i+1), "" + (j + 1));
                    }
                }
            }
        }

        return dataset;
    }

    private CategoryDataset createShapelet(int index, int maxShiftLen, int timeseries_lengh) {
        ArrayList<Double> shapelet = localShapelet.get(index);

        final DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        int size = maxShiftLen + timeseries_lengh;

        System.out.println("timeseries_lengh: " + timeseries_lengh);
        for (int i = 0; i < size; i++) {
            if (i >= maxShiftLen && i < shapelet.size() + maxShiftLen) {
                double val = shapelet.get(i-maxShiftLen);
                dataset.addValue(val,
                        label_Shapelet_testing, "" + (i + 1));
            }else {
                dataset.addValue(null,
                        label_Shapelet_testing, "" + (i + 1));
            }
        }
        return dataset;
    }

    public ArrayList<Integer> getShiftLenBeforeShapeletArr(ArrayList<Integer> timeseriesIndexArr, int shapeletIndex, int classDimension) {
        // Get distances and start position between each pair of timeseries and shapelet
        double[][] distanceSortingArr = new double[timeseriesIndexArr.size()][];
        double[][] distanceAndIndex = {{},{}};
        int xAxisStartIndex;
        double distance;
        ArrayList<Integer> shiftLenBeforeShapeletArr = new ArrayList<>();
        int maxShiftLen = Integer.MIN_VALUE;
        // Get the global start index for all timesries
        for (int i=0; i<timeseriesIndexArr.size(); i++){
            double[] distanceSorting = {-1, -1};
            int timeseriesIndex = timeseriesIndexArr.get(i);

            // distanceAndIndex[0][0]: startIndex;
            // distanceAndIndex[1][0]: distanceMin;
            distanceAndIndex = Dataset.getDistance(localTimeseries.get(timeseriesIndex).get(classDimension), localShapelet.get(shapeletIndex));
            xAxisStartIndex = (int) distanceAndIndex[0][0];
            distance = distanceAndIndex[1][0];

            distanceSorting[0] = xAxisStartIndex;
            distanceSorting[1] = distance;
            distanceSortingArr[i] = distanceSorting;
        }

        java.util.Arrays.sort(distanceSortingArr, new java.util.Comparator<double[]>() {
            public int compare(double[] a, double[] b) {
                return Double.compare(a[1], b[1]);
            }
        });

        int startPointIndex = 0;
        for (int i=0; i<distanceSortingArr.length; i++){
            xAxisStartIndex = (int) distanceSortingArr[i][startPointIndex];
            // Record each xAxisStartIndex between each pair of timeseries and shapelet
            shiftLenBeforeShapeletArr.add(xAxisStartIndex);
            // Set/update the maximum xAxisStartIndex
            if (xAxisStartIndex>maxShiftLen) {
                maxShiftLen = xAxisStartIndex;
            }
        }

        return shiftLenBeforeShapeletArr;
    }

    public double[][] run() {
        double[][] run = new double[][]{
                {10, 6, 2, 4, 7, 2, 8, 12, 9, 4},
                {2, 6, 3, 8, 1, 6, 4, 9, 2, 10}
        };
        return run;
    }

    public void setPlot(final CombinedDomainCategoryPlot plot) {
        this.plot = plot;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public final CombinedDomainCategoryPlot getPlot() {
        return this.plot;
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getLocalTimeseries() {
        return localTimeseries;
    }

    private void setLocalTimeseries(ArrayList<ArrayList<ArrayList<Double>>> localTimeseries) {
        this.localTimeseries = localTimeseries;
    }

    public ArrayList<ArrayList<Double>> getLocalShapelet() {
        return localShapelet;
    }

    private void setLocalShapelet(ArrayList<ArrayList<Double>> localShapelet) {
        this.localShapelet = localShapelet;
    }

    private void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JPanel getPanel() {
        return panel;
    }

    private void setChartPanel(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }

    private void setChartPanelArr(ArrayList<ChartPanel> chartPanelArr) {
        this.chartPanelArr = chartPanelArr;
    }

    public ArrayList<ChartPanel> getChartPanelArr() {
        return chartPanelArr;
    }

    private void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void addTimeseries(int classDimension, ArrayList<Integer> chartPanelFirstAndLastArr, int timeseriesIndex) {
        // There could be more than 1 shapelets in one dimension
        // Get the correlated dataset at first
        int correlatedChartPanelStartIndex = 0;
        int correlatedChartPanelEndIndex = 1;
        for (int dim_i=chartPanelFirstAndLastArr.get(correlatedChartPanelStartIndex); dim_i<chartPanelFirstAndLastArr.get(correlatedChartPanelEndIndex)+1; dim_i++) {
            // Iteratively set the chartPanel of the same dimemsion
            // (classDimension+dim_i): Since multiple dimension chartPanels are in one chartPanelArr,
            // we need to use the classDimension as the base and plus the number of charts in the one dimension
            // to retrieve the correct chartPanel
            ChartPanel panel1 = chartPanelArr.get(dim_i);
            JFreeChart chart1 = panel1.getChart();
            CombinedDomainCategoryPlot plot1 = (CombinedDomainCategoryPlot) chart1.getCategoryPlot();
            ArrayList<CategoryPlot> subplots1 = new ArrayList<CategoryPlot>(plot1.getSubplots());
            int shapeletDimIndex = 0;
            int timesereisDimIndex = 1;
            CategoryPlot shapeletPlot = subplots1.get(shapeletDimIndex);
            CategoryPlot timeserisePlot = subplots1.get(timesereisDimIndex);
            DefaultCategoryDataset shapelet_dataset = (DefaultCategoryDataset) shapeletPlot.getDataset();
            DefaultCategoryDataset timeseries_dataset = (DefaultCategoryDataset) timeserisePlot.getDataset();
            // Clear the chart for new task
            timeseries_dataset.clear();

            // Add new selected timeserise
            // Get distances and start position between each pair of timeseries and shapelet
            ArrayList<Integer> timeseriesIndexArr = new ArrayList<>();
            timeseriesIndexArr.add(timeseriesIndex);
            // Later there need to add other reserved timeseries back into the chart

            int shapeletIndex = timeseriesIndex;
            ArrayList<Integer> shiftLenBeforeShapeletArr = getShiftLenBeforeShapeletArr(timeseriesIndexArr, shapeletIndex, classDimension);
            int maxShiftLen = Collections.max(shiftLenBeforeShapeletArr);

            for (int i=0; i<timeseriesIndexArr.size(); i++) {

                ArrayList<Double>  timeserise = localTimeseries.get(timeseriesIndex).get(classDimension);
                int xAxisShiftLen = maxShiftLen - shiftLenBeforeShapeletArr.get(i);

                for (int j = 0; j < timeserise.size() + xAxisShiftLen; j++) {
                    if (j+1 > xAxisShiftLen) {
                        double val = timeserise.get(j-xAxisShiftLen);
                        timeseries_dataset.addValue(val,
                                labelTimeseries+(i+1), "" + (j + 1));
                    } else {
                        timeseries_dataset.addValue(null,
                                labelTimeseries+(i+1), "" + (j + 1));
                    }
                }
            }
        }
    }

    // ------------------------------------------------------------------------------

    public static void main(final String[] args) {
//        final String title = "DualAxisChart_3";
//        final DualAxisChart chart = new DualAxisChart(title);
//        chart.pack();
//        RefineryUtilities.centerFrameOnScreen(chart);
//        chart.setVisible(true);
    }
}