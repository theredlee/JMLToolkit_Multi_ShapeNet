import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// MultiShapelet Only
public class DualAxisChart_3 extends ApplicationFrame {

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

    // ------------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------------------------

    public DualAxisChart_3(ArrayList<ArrayList<ArrayList<Double>>> localTimeseries, ArrayList<ArrayList<Double>> localShapelet, ArrayList<Double> localShapeletLabelArr) {
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

    private JFreeChart createChart(int shapeletIndex, int timesriesDimension) {
        final int shapeletRenderIndex = 0;
        final int timeseriesRenderIndex = 0;
        final int defaultTimeseriesFirstK = 10;
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
        final CategoryDataset timeserise = createTimeserise(shapeletIndex, defaultTimeseriesFirstK, timesriesDimension);
        final NumberAxis rangeAxisTimeseries = new NumberAxis("Timeserise D" + timesriesDimension);
        rangeAxisTimeseries.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Line render
        final CategoryItemRenderer rendererTimeseries = new LineAndShapeRenderer();
        rendererTimeseries.setSeriesPaint(seriesNo, Color.red);
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

    private CategoryDataset createTimeserise(int shapeletIndex, int size, int dimemsion) {

        final DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        // Timeseries
        for (int i=0; i<size; i++) {
            ArrayList<Double> timeserise = localTimeseries.get(i).get(dimemsion);

//            System.out.println("timeserise.size: " + timeserise.size());
            for (int j=0; j<timeserise.size(); j++) {
                double val = timeserise.get(j);
                dataset.addValue(val,
                        labelTimeseries+(i+1), "" + (j + 1));
            }
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