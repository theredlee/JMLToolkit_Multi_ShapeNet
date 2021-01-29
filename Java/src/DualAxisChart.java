import java.awt.*;
import java.util.ArrayList;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;

public class DualAxisChart extends ApplicationFrame {

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
    final String labelShapelet_testing = "Shapelet  ";

    private ArrayList<ArrayList<ArrayList<Double>>> localTimeseries = new ArrayList<ArrayList<ArrayList<Double>>>();
    private ArrayList<ArrayList<Double>> localShapelet = new ArrayList<ArrayList<Double>>();

    public DualAxisChart(int testingCode, ArrayList<ArrayList<ArrayList<Double>>> localTimeseries, ArrayList<ArrayList<Double>> localShapelet, ArrayList<Double> localShapeletLabelArr) {
        super("DualAxisChart");

        // set localTimeseries and localShapelet
        setLocalTimeseries(localTimeseries);
        setLocalShapelet(localShapelet);

        int timesriesIndexSize = localTimeseries.size();

        // Set charts according to the shapelets
        for (int i=0; i<localShapelet.size(); i++) {
            int index = i;
            int dimension = i;

            final ArrayList<JFreeChart> chartArr = createChartArr_testing(index, timesriesIndexSize, dimension);
            for (int j=0; j<chartArr.size(); j++) {
                JFreeChart chart = chartArr.get(j);
                final ChartPanel chartPanel = new ChartPanel(chart);
                chartPanel.setPreferredSize(
                        new Dimension(900, 2000));
                chartPanelArr.add(chartPanel);
            }
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.setPreferredSize(new Dimension(900, 2000*2));
        for (int i=0; i<chartPanelArr.size(); i++) {
            panel.add(chartPanelArr.get(i));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(900,1100));
         setContentPane(scrollPane);
        // Set the panel globally
//        setScrollPane(scrollPane);
        // setPanel(panel);
        // setChartPanelArr(chartPanelArr);
    }

    private ArrayList<JFreeChart> createChartArr_testing(int shapeletIndex, int timeseriesIndexSize, int timesriesDimension) {
        final String[] str = {"ALT", "AFP"};
        final int shapeletRenderIndex = 0;
        final int timeseriesRenderIndex = 0;

        // index 0: shapelet Chart
        // index 1: timeseries Chart
        int shapeletChartIndex = 0;
        int timeseriesChartIndex = 1;
        ArrayList<XYSeriesCollection> timeseriseAndShapelet = createTimeseriseAndShapet_testing(shapeletIndex, timeseriesIndexSize, timesriesDimension);

        // Shapelet
        final XYSeriesCollection shapelet = timeseriseAndShapelet.get(shapeletChartIndex);
        final NumberAxis xax = new NumberAxis("Aligned by the best match position of the shapelet");
        final NumberAxis rangeAxisShapelet = new NumberAxis("Shapelet " + str[timesriesDimension]);
        rangeAxisShapelet.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // Line render
        final XYSplineRenderer rendererShapelet = new XYSplineRenderer();
        rendererShapelet.setBaseStroke(new BasicStroke(2.0f));
        rendererShapelet.setAutoPopulateSeriesStroke(false);
        final XYPlot xyPlotShapelet =
                new XYPlot (shapelet, xax,
                        rangeAxisShapelet, rendererShapelet);
        xyPlotShapelet.setDomainGridlinesVisible(true);;
//        xyPlotShapelet.setForegroundAlpha(0.7f);

        final JFreeChart chartShapelet = new JFreeChart(
                "Score Bord", new Font("SansSerif", Font.BOLD, 8),
                xyPlotShapelet, true);

        // ---------------------------------------------------------------
        // ---------------------------------------------------------------

        // Timeserise

        final XYSeriesCollection timeserise = timeseriseAndShapelet.get(timeseriesChartIndex);
        final NumberAxis rangeAxisTimeseries = new NumberAxis("Dimension " + str[timesriesDimension]);
        rangeAxisTimeseries.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Line render
        final XYSplineRenderer rendererTimeseries = new XYSplineRenderer();
        rendererTimeseries.setBaseStroke(new BasicStroke(2.0f));
        rendererTimeseries.setAutoPopulateSeriesStroke(false);
        rendererTimeseries.setSeriesPaint(0, Color.red);
        rendererTimeseries.setBaseToolTipGenerator(
                new StandardXYToolTipGenerator());

        final XYPlot xyPlotTimeseries =
                new XYPlot(timeserise, xax,
                        rangeAxisTimeseries, rendererTimeseries);
        xyPlotTimeseries.setDomainGridlinesVisible(true);

        final JFreeChart chartTimeserise = new JFreeChart(
                "Score Bord", new Font("SansSerif", Font.BOLD, 8),
                xyPlotTimeseries, true);

        // Add chartShapelet and chartTimeserise into a chart array
        ArrayList<JFreeChart> chartArr = new ArrayList<>();
        chartArr.add(chartShapelet);
        chartArr.add(chartTimeserise);

        return chartArr;
    }

    private ArrayList<XYSeriesCollection> createTimeseriseAndShapet_testing(int shapeletIndex, int timeseriesIndexSize, int dimemsion) {

        final XYSeriesCollection dataset
                = new XYSeriesCollection();

        // Shapelet
        ArrayList<Double> shapelet = localShapelet.get(shapeletIndex);

        // Get distances and start position between each pair of timeseries and shapelet
        ArrayList<Double> timeserise;
        double[][] distanceAndIndex = {{},{}};
        int xAxisStartIndex;
        ArrayList<Integer> shiftLenBeforeShapeletArr = new ArrayList<>();
        int maxShiftLen = (int) Double.NEGATIVE_INFINITY;
        // Get the global start index for all timesries
        for (int i=0; i<timeseriesIndexSize; i++){
            int timeseriesIndex = i;
            // Timeseries
            timeserise = localTimeseries.get(timeseriesIndex).get(dimemsion);

            // distanceAndIndex[0][0]: startIndex;
            // distanceAndIndex[1][0]: distanceMin;
            distanceAndIndex = Dataset.getDistance(timeserise, shapelet);
            xAxisStartIndex = (int) distanceAndIndex[0][0];
            // Record each xAxisStartIndex between each pair of timeseries and shapelet
            shiftLenBeforeShapeletArr.add(xAxisStartIndex);
            // Set/update the maximum xAxisStartIndex
            if (xAxisStartIndex>maxShiftLen) {
                maxShiftLen = xAxisStartIndex;
            }
        }

        System.out.println("shiftLenBeforeShapeletArr: " + shiftLenBeforeShapeletArr);
        System.out.println("maxShiftLen: " + maxShiftLen);

        // Create shapelet and corresponding timeseries
        ArrayList<XYSeriesCollection> timeseriseAndShapelet = new ArrayList<>();
        timeseriseAndShapelet.add(
                shapelet_testing(shapeletIndex, maxShiftLen));
        timeseriseAndShapelet.add(timeserise_testing(shapeletIndex, timeseriesIndexSize, dimemsion, shiftLenBeforeShapeletArr, maxShiftLen));

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

    private XYSeriesCollection timeserise_testing(int shapeletIndex, int timeseriesIndexSize, int dimemsion, ArrayList<Integer> shiftLenBeforeShapeletArr, int maxShiftLen) {

        final XYSeriesCollection dataset
                = new XYSeriesCollection();

        // Shapelet
        ArrayList<Double> shapelet = localShapelet.get(shapeletIndex);

        // Get distances and start position between each pair of timeseries and shapelet
        ArrayList<Double> timeserise;

        // Timeseries Plot Dataset ----- Plot timeseries by using globsal start index
        int xAxisShiftLen;
        for (int i=0; i<timeseriesIndexSize; i++){
            // Create a series to store the values
            XYSeries series = new XYSeries("Series Timeserise " + (i+1));

            int timeseriesIndex = i;
            // Timeseries
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

        return dataset;
    }

    // ---------------------------------------------------------
    // --------------------------------------------------------
    public DualAxisChart(String titel, ArrayList<ArrayList<ArrayList<Double>>> localTimeseries) {
        super(titel);

        // set localTimeseries
        setLocalTimeseries(localTimeseries);

        final JFreeChart chart = createChart();
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(
                new Dimension(400, 300));
        setContentPane(chartPanel);
        // Set the panel globally
        setChartPanel(chartPanel);
    }

    public DualAxisChart(ArrayList<ArrayList<ArrayList<Double>>> localTimeseries, ArrayList<ArrayList<Double>> localShapelet, ArrayList<Double> localShapeletLabelArr) {
        super("DualAxisChart");

        // set localTimeseries and localShapelet
        setLocalTimeseries(localTimeseries);
        setLocalShapelet(localShapelet);

        // Set charts according to the shapelets
        for (int i=0; i<localShapelet.size(); i++) {
            int index = i;
            int timesriesDimension = (int) ((double) localShapeletLabelArr.get(i));
            final JFreeChart chart = createChart(index, timesriesDimension);
            final ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(
                    new Dimension(730, 700));
            chartPanelArr.add(chartPanel);
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.setPreferredSize(new Dimension(730, 700*5));
        for (int i=0; i<chartPanelArr.size(); i++) {
            panel.add(chartPanelArr.get(i));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(750,800));
        // setContentPane(scrollPane);
        // Set the panel globally
        setScrollPane(scrollPane);
        // setPanel(panel);
        // setChartPanelArr(chartPanelArr);
    }

    private JFreeChart createChart() {
        final int defaultInex = 0;
        final int defaultDimension0 = 0;
        final int defaultDimension1 = 1;

        final CategoryDataset timeserise1 = createTimeserise(defaultInex, defaultDimension0);
        final NumberAxis rangeAxis1 = new NumberAxis("Timeserise D1");
        rangeAxis1.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Line render
        final CategoryItemRenderer renderer1 = new LineAndShapeRenderer();
        renderer1.setSeriesPaint(0, Color.red);
        renderer1.setBaseToolTipGenerator(
                new StandardCategoryToolTipGenerator());
        final CategoryPlot subplot1 =
                new CategoryPlot(timeserise1, null,
                        rangeAxis1, renderer1);
        subplot1.setDomainGridlinesVisible(true);;

        subplot1.setForegroundAlpha(0.7f);
        subplot1.setRenderer(0, renderer1);

        // -----------------------------------------------

        final CategoryDataset timeserise2 = createTimeserise(defaultInex, defaultDimension1);
        final NumberAxis rangeAxis2 = new NumberAxis("Timeserise D2");
        rangeAxis2.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Line render
        final CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
        renderer2.setSeriesPaint(0, Color.blue);
        renderer2.setBaseToolTipGenerator(
                new StandardCategoryToolTipGenerator());
        final CategoryPlot subplot2 =
                new CategoryPlot(timeserise2, null,
                        rangeAxis2, renderer2);
        subplot2.setDomainGridlinesVisible(true);

        subplot2.setForegroundAlpha(0.7f);
        subplot2.setRenderer(0, renderer2);

        final CategoryAxis domainAxis = new CategoryAxis("Over");

        final CombinedDomainCategoryPlot plot =
                new CombinedDomainCategoryPlot(domainAxis);

        plot.add(subplot1, 1);
        plot.add(subplot2, 1);

        final JFreeChart chart = new JFreeChart(
                "Score Bord", new Font("SansSerif", Font.BOLD, 12),
                plot, true);

        setPlot(plot);
        return chart;
    }

    private JFreeChart createChart(int shapeletIndex, int timesriesDimension) {
        final int shapeletRenderIndex = 0;
        final int timeseriesRenderIndex = 0;
        final int defaultTimeseriesInex = 0;
        final int seriesNo = 0;

        // Shapelet
        final CategoryDataset shapelet = createShapelet(shapeletIndex);
        final NumberAxis rangeAxisShapelet = new NumberAxis("Shapelet D" + shapeletIndex);
        rangeAxisShapelet.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Line render
        final CategoryItemRenderer rendererShapelet = new LineAndShapeRenderer();
        rendererShapelet.setSeriesPaint(seriesNo, Color.red);
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
        final CategoryDataset timeseriseAndShapelet = createTimeseriseAndShapelet(shapeletIndex, defaultTimeseriesInex, timesriesDimension);
        final NumberAxis rangeAxisTimeseries = new NumberAxis("Timeserise D" + timesriesDimension);
        rangeAxisTimeseries.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Line render
        final CategoryItemRenderer rendererTimeseriesAndShapelet = new LineAndShapeRenderer();
        rendererTimeseriesAndShapelet.setSeriesPaint(seriesNo, Color.red);
        rendererTimeseriesAndShapelet.setBaseToolTipGenerator(
                new StandardCategoryToolTipGenerator());
        final CategoryPlot subplotTimeseriesAndShapelet =
                new CategoryPlot(timeseriseAndShapelet, null,
                        rangeAxisTimeseries, rendererTimeseriesAndShapelet);
        subplotTimeseriesAndShapelet.setDomainGridlinesVisible(true);;

        subplotTimeseriesAndShapelet.setForegroundAlpha(0.7f);
        // Add timeseries into timeseries chart
        subplotTimeseriesAndShapelet.setRenderer(timeseriesRenderIndex, rendererTimeseriesAndShapelet);

        // -----------------------------------------------

        final CategoryAxis domainAxis = new CategoryAxis("Over");

        final CombinedDomainCategoryPlot plot =
                new CombinedDomainCategoryPlot(domainAxis);

        plot.add(subplotShapelet, 1);
        plot.add(subplotTimeseriesAndShapelet, 2);

        final JFreeChart chart = new JFreeChart(
                "Score Bord", new Font("SansSerif", Font.BOLD, 8),
                plot, true);

        setPlot(plot);
        return chart;
    }

    private CategoryDataset createTimeserise(int index, int dimemsion) {
        ArrayList<Double> timeserise = localTimeseries.get(index).get(dimemsion);

        final DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        for (int i = 0; i < timeserise.size(); i++) {
            double val = timeserise.get(i);
            dataset.addValue(val,
                    label0, "" + (i + 1));
        }
        return dataset;
    }

    private CategoryDataset createTimeseriseAndShapelet(int shapeletIndex, int timeseriesIndex, int dimemsion) {

        final DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        // Shapelet
        ArrayList<Double> shapelet = localShapelet.get(shapeletIndex);

        for (int i = 0; i < shapelet.size(); i++) {
            double val = shapelet.get(i);
            dataset.addValue(val,
                    label1, "" + (i + 1));
        }

        // Timeseries
        ArrayList<Double> timeserise = localTimeseries.get(timeseriesIndex).get(dimemsion);

        for (int i = 0; i < timeserise.size(); i++) {
            double val = timeserise.get(i);
            dataset.addValue(val,
                    labelTimeseries, "" + (i + 1));
        }
        return dataset;
    }

    private CategoryDataset createShapelet(int index) {
        ArrayList<Double> shapelet = localShapelet.get(index);

        final DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        for (int i = 0; i < shapelet.size(); i++) {
            double val = shapelet.get(i);
            dataset.addValue(val,
                    label_Shapelet_testing, "" + (i + 1));
        }
        return dataset;
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

    public final CombinedDomainCategoryPlot getPlot() {
        return this.plot;
    }

    public void setTimeseriesInChart(int index) {
        //  datesetIndex = 0: timeseries plot, datesetIndex = 1: shapelet plot
        final int datesetIndex = 0;
        final int subPlotIndex0 = 0;
        final int subPlotIndex1 = 1;
        final int dimension0  = 0;
        final int dimension1 = 1;
        CategoryPlot subplot0 = (CategoryPlot) this.plot.getSubplots().get(subPlotIndex0);
        CategoryPlot subplot1 = (CategoryPlot) this.plot.getSubplots().get(subPlotIndex1);
        final CategoryDataset timeserise0 = createTimeserise(index, dimension0);
        final CategoryDataset timeserise1 = createTimeserise(index, dimension1);
        subplot0.setDataset(datesetIndex, timeserise0);
        subplot1.setDataset(datesetIndex, timeserise1);
    }

    public void setShapeletInChart(int index) {
        //  datesetIndex = 0: timeseries plot, datesetIndex = 1: shapelet plot
        final int datesetIndex = 1;
        final int subPlotIndex0 = 0;
        final int subPlotIndex1 = 1;
        CategoryPlot subplot0 = (CategoryPlot) this.plot.getSubplots().get(subPlotIndex0);
        CategoryPlot subplot1 = (CategoryPlot) this.plot.getSubplots().get(subPlotIndex1);
        final CategoryDataset shapelet0 = createShapelet(index);
        final CategoryDataset shapelet1 = createShapelet(index);
        subplot0.setDataset(datesetIndex, shapelet0);
        subplot1.setDataset(datesetIndex, shapelet1);
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

    // ------------------------------------------------------------------------------

    public static void main(final String[] args) {
//        final String title = "Score Bord";
//        final DualAxisChart chart = new DualAxisChart(title);
//        chart.pack();
//        RefineryUtilities.centerFrameOnScreen(chart);
//        chart.setVisible(true);
    }
}

//    public DualAxisChart(String titel) {
//        super(titel);
//
//        final JFreeChart chart = createChart();
//        final ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(
//                new java.awt.Dimension(600, 450));
//        setContentPane(chartPanel);
//    }
//
//        private CategoryDataset createRunDataset1() {
//        final DefaultCategoryDataset dataset =
//                new DefaultCategoryDataset();
//
//        double[] run = run()[0];
//
//        for (int i = 0; i < run.length; i++) {
//            dataset.addValue(run[i], team1+
//                    " Run", "" + (i + 1));
//        }
//        return dataset;
//    }
//
//    private CategoryDataset createRunDataset2() {
//        final DefaultCategoryDataset dataset =
//                new DefaultCategoryDataset();
//
//        double[] run = run()[1];
//
//        for (int i = 0; i < run.length; i++) {
//            dataset.addValue(run[i], team2+
//                    " Run", "" + (i + 1));
//        }
//        return dataset;
//    }
//
//    private CategoryDataset createRunRateDataset1() {
////         ----------------------------------------------
//        final DefaultCategoryDataset dataset
//                = new DefaultCategoryDataset();
//
//        double[] run = run()[0];
//        float num = 0;
//
//        for (int i = 0; i < run.length; i++) {
//            num += run[i];
//            dataset.addValue(num / (i + 1),
//                    team1+" Runrate", "" + (i + 1));
//        }
//        return dataset;
////         ----------------------------------------------
//    }
//
//    private CategoryDataset createRunRateDataset2() {
//        final DefaultCategoryDataset dataset =
//                new DefaultCategoryDataset();
//
//        double[] run = run()[1];
//        float num = 0;
//
//        for (int i = 0; i < run.length; i++) {
//            num += run[i];
//            dataset.addValue(num / (i + 1),
//                    team2+" Runrate", "" + (i + 1));
//        }
//        return dataset;
//    }
//
//    // For reference
//    private JFreeChart createChart_Initial() {
//        final int defaultInex = 0;
//
//        final CategoryDataset dataset1 = createRunDataset1();
//        final NumberAxis rangeAxis1 = new NumberAxis("Run");
//        rangeAxis1.setStandardTickUnits(
//                NumberAxis.createIntegerTickUnits());
//        // Bar render
//        final BarRenderer renderer1 = new BarRenderer();
//        renderer1.setSeriesPaint(0, Color.red);
//        renderer1.setBaseToolTipGenerator(
//                new StandardCategoryToolTipGenerator());
//        final CategoryPlot subplot1 =
//                new CategoryPlot(dataset1, null,
//                        rangeAxis1, renderer1);
//        subplot1.setDomainGridlinesVisible(true);
//
////        final CategoryDataset runrateDataset1
////                = createRunRateDataset1();
//        final CategoryDataset timeserise1
//                = createTimeserise0(defaultInex);
//        final ValueAxis axis2 = new NumberAxis("Run Rate");
//        subplot1.setRangeAxis(1, axis2);
////        subplot1.setDataset(1, runrateDataset1);
//        subplot1.setDataset(1, timeserise1);
//        subplot1.mapDatasetToRangeAxis(1, 1);
//        // Line render
//        final CategoryItemRenderer runrateRenderer1
//                = new LineAndShapeRenderer();
//        runrateRenderer1.setSeriesPaint(0, Color.red);
//
//        subplot1.setForegroundAlpha(0.7f);
//        subplot1.setRenderer(0, renderer1);
//        subplot1.setRenderer(1, runrateRenderer1);
//
//        // -----------------------------------------------
//
//        final CategoryDataset dataset2 = createRunDataset2();
//        final NumberAxis rangeAxis2 = new NumberAxis("Run");
//        rangeAxis2.setStandardTickUnits(
//                NumberAxis.createIntegerTickUnits());
//        // Bar render
//        final BarRenderer renderer2 = new BarRenderer();
//        renderer2.setSeriesPaint(0, Color.blue);
//        renderer2.setBaseToolTipGenerator(
//                new StandardCategoryToolTipGenerator());
//        final CategoryPlot subplot2 =
//                new CategoryPlot(dataset2, null,
//                        rangeAxis2, renderer2);
//        subplot2.setDomainGridlinesVisible(true);
//
////        final CategoryDataset runrateDataset2 =
////                createRunRateDataset2();
//
//        final CategoryDataset timeserise2 =
//                createTimeserise1(defaultInex);
//
//        final ValueAxis axis3 = new NumberAxis("Run Rate");
//        subplot2.setRangeAxis(1, axis3);
////        subplot2.setDataset(1, runrateDataset2);
//        subplot2.setDataset(1, timeserise2);
//        subplot2.mapDatasetToRangeAxis(1, 1);
//        // Line render
//        final CategoryItemRenderer runrateRenderer2 =
//                new LineAndShapeRenderer();
//        runrateRenderer2.setSeriesPaint(0, Color.blue);
//
//        subplot2.setForegroundAlpha(0.7f);
////        subplot2.setRenderer(0, renderer2);
//        subplot2.setRenderer(1, runrateRenderer2);
//
//        final CategoryAxis domainAxis = new CategoryAxis("Over");
//        final CombinedDomainCategoryPlot plot =
//                new CombinedDomainCategoryPlot(domainAxis);
//
//        plot.add(subplot1, 1);
//        plot.add(subplot2, 1);
//
//        final JFreeChart chart = new JFreeChart(
//                "Score Bord", new Font("SansSerif", Font.BOLD, 12),
//                plot, true);
//        return chart;
//    }