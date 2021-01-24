package com.technobium.regression;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class RegressionChartExample extends ApplicationFrame {

    private static final long serialVersionUID = 1L;
    public ArrayList<ChartPanel> panelArr;
    public ChartPanel panel;

    XYDataset inputData;
    XYDataset inputData0;
    XYDataset inputData1;
    JFreeChart chart;
    JFreeChart chart0;
    JFreeChart chart1;

    public static void main(String[] args) throws IOException {
        com.technobium.regression.RegressionChartExample demo = new com.technobium.regression.RegressionChartExample("prices.txt");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

    public RegressionChartExample(String inputFileName) throws IOException {
        super("Technobium - Linear Regression");

        // Read sample data from prices.txt file
        inputData = createDatasetFromFile(inputFileName);

        // Create the chart using the sample data
        chart = createChart(inputData);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    public RegressionChartExample(ArrayList<ArrayList<Double>> localMultArr) {
        super("Technobium - Linear Regression");

        // Read sample data from prices.txt file
        int chartIndex = 0;
        inputData = createDataset(chartIndex, localMultArr);

        // Create the chart using the sample data
        chart = createChart(inputData);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        // Set the panel globally
        setPanel(chartPanel);
        setContentPane(chartPanel);

        // Draw chart
        drawRegressionLine();
    }

    public RegressionChartExample(ArrayList<Double> localMultArr, ArrayList<Double> localTimeseriesLabelArr) {
        super("Technobium - Linear Regression");

        // Read sample data from prices.txt file
        int chartIndex = -1;
        int chartIndex0 = 0;
        int chartIndex1 = 1;
//        inputData0 = createDataset(chartIndex0, localMultArr, localTimeseriesLabelArr);
//        inputData1 = createDataset(chartIndex1, localMultArr, localTimeseriesLabelArr);
        inputData = createDataset(localMultArr, localTimeseriesLabelArr);
        inputData0 = createDataset(chartIndex0, localMultArr, localTimeseriesLabelArr);
        inputData1 = createDataset(chartIndex1, localMultArr, localTimeseriesLabelArr);

        // Create the chart using the sample data
        chart = createChart(chartIndex, inputData);
        chart0 = createChart(chartIndex0, inputData0);
        chart1 = createChart(chartIndex1, inputData1);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        ChartPanel chartPanel0 = new ChartPanel(chart0);
        chartPanel0.setPreferredSize(new Dimension(500, 300));
        ChartPanel chartPanel1 = new ChartPanel(chart1);
        chartPanel1.setPreferredSize(new Dimension(500, 300));

        ArrayList<ChartPanel> aChartPanelArr = new ArrayList<>();
        aChartPanelArr.add(chartPanel);
        aChartPanelArr.add(chartPanel0);
        aChartPanelArr.add(chartPanel1);

        // Set the panel/aChartPanelArr globally
        // setPanel(chartPanel0);
        setPanelArr(aChartPanelArr);
        setContentPane(chartPanel);

        // Draw chart
        drawRegressionLine();
    }

    public XYDataset createDatasetFromFile(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        Scanner scanner = new Scanner(file);

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Real estate item");

        // Read the price and the living area
        while (scanner.hasNextLine()) {
            if (scanner.hasNextFloat()) {
                float livingArea = scanner.nextFloat();
                float price = scanner.nextFloat();
                series.add(livingArea, price);
            }
        }
        scanner.close();
        dataset.addSeries(series);

        return dataset;
    }

    public XYDataset createDataset(int index, ArrayList<ArrayList<Double>> localMultArr){
        int count = 0;
        double distance;

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Real estate item");

        // Read the price and the living area
        for (int i=0; i<localMultArr.get(index).size(); i++) {
//            if (count%2 == 0) {
                distance = localMultArr.get(index).get(i);
                series.add(count, distance);
//            }else {
//                series.add(count, null);
//            }
            count++;
        }

        dataset.addSeries(series);

        return dataset;
    }

    public XYDataset createDataset(int index, ArrayList<Double> localMultArr, ArrayList<Double> localTimeseriesLabelArr){
        int count = 0;
        int label;
        double distance;

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Real estate item");

        // Read the price and the living area
        for (int i=0; i<localMultArr.size(); i++) {
            label = (int) ((double) localTimeseriesLabelArr.get(i));
            if (label == index) {
                distance = localMultArr.get(i);
                series.add(count, distance);
            }else {
                series.add(count, null);
            }
            count++;
        }

        dataset.addSeries(series);

        return dataset;
    }

    public XYDataset createDataset(ArrayList<Double> localMultArr, ArrayList<Double> localTimeseriesLabelArr){
        int count = 0;
        int label;
        double distance;

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Real estate item");

        // Read the price and the living area
        for (int i=0; i<localMultArr.size(); i++) {
            distance = localMultArr.get(i);
            series.add(count, distance);
            count++;
        }

        dataset.addSeries(series);

        return dataset;
    }

    private void drawRegressionLine() {
        // Get the parameters 'a' and 'b' for an equation y = a + b * x,
        // fitted to the inputData using ordinary least squares regression.
        // a - regressionParameters[0], b - regressionParameters[1]
        double regressionParameters[] = Regression.getOLSRegression(inputData,
                0);

        // Prepare a line function using the found parameters
        LineFunction2D linefunction2d = new LineFunction2D(
                regressionParameters[0], regressionParameters[1]);

        // Creates a dataset by taking sample values from the line function
        XYDataset dataset = DatasetUtilities.sampleFunction2D(linefunction2d,
                0D, 13000, 20000, "Fitted Regression Line");

        // Draw the line dataset
        XYPlot xyplot = chart.getXYPlot();
        xyplot.setDataset(1, dataset);
        XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(
                true, false);
        xylineandshaperenderer.setSeriesPaint(0, Color.RED);
        xyplot.setRenderer(1, xylineandshaperenderer);
    }

    private JFreeChart createChart(XYDataset inputData) {
        // Create the chart using the data read from the prices.txt file
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Price for living area", "Price", "Living area", inputData,
                PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = chart.getXYPlot();
        plot.getRenderer().setSeriesPaint(0, Color.blue);
        return chart;
    }

    private JFreeChart createChart(int index, XYDataset inputData) {
        // Create the chart using the data read from the prices.txt file
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Price for living area", "Price", "Living area", inputData,
                PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = chart.getXYPlot();

        Color color;
        if (index==-1) {
            color = Color.blue;
        }else if (index==0) {
            color = Color.RED;
        }else {
            color = Color.orange;
        }

        plot.getRenderer().setSeriesPaint(0, color);
        return chart;
    }

    private void setPanel(ChartPanel panel) {
        this.panel = panel;
    }

    public ChartPanel getPanel() {
        return panel;
    }

    private void setPanelArr(ArrayList<ChartPanel> panelArr) {
        this.panelArr = panelArr;
    }

    public ArrayList<ChartPanel> getPanelArr() {
        return panelArr;
    }
}