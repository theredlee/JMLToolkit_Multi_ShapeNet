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
import java.util.*;
// MultiShapelet and Multiple Timeseries
public class DualAxisChart_2 extends ApplicationFrame {

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
    final int width = 350;
    final int height = 450;
    final int dim_cnt = 2;

    Color[] ten_colors = {new Color(255,204,51), new Color(51,153,255), new Color(255,102,0), new Color(153,153,153), new Color(153,102,0), new Color(102,51,0), new Color(0,204,0), new Color(255,0,0), new Color(102,0,153), new Color(0,0,0)};

    // ------------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------------------------

    public DualAxisChart_2(ArrayList<ArrayList<ArrayList<Double>>> localTimeseries, ArrayList<ArrayList<Double>> localShapelet, ArrayList<Double> localShapeletLabelArr) {
        super("DualAxisChart");

        // set localTimeseries and localShapelet
        setLocalTimeseries(localTimeseries);
        setLocalShapelet(localShapelet);

        // Set charts according to the shapelets
        for (int i=0; i<localShapelet.size(); i++) {
            int index = i;
            int classDimension = (int) ((double) localShapeletLabelArr.get(i));
            final JFreeChart chart = createChart(index, classDimension);
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
        final int seriesNo = 0;

        // Shapelet
        final CategoryDataset shapelet = createShapelet(shapeletIndex);
        final NumberAxis rangeAxisShapelet = new NumberAxis("Shapelet D" + shapeletIndex);
        rangeAxisShapelet.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Line render
        final CategoryItemRenderer rendererShapelet = new LineAndShapeRenderer();
        rendererShapelet.setSeriesPaint(seriesNo, ten_colors[classDimension%10]);
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
        final CategoryDataset timeserise = createTimeserise(shapeletIndex, defaultTimeseriesFirstK, classDimension);
        final NumberAxis rangeAxisTimeseries = new NumberAxis("Timeserise D" + classDimension);
        rangeAxisTimeseries.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Line render
        final CategoryItemRenderer rendererTimeseries = new LineAndShapeRenderer();
        rendererTimeseries.setSeriesPaint(seriesNo, ten_colors[classDimension%10]);
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
                "DualAxisChart_2", new Font("SansSerif", Font.BOLD, 8),
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

            System.out.println("timeserise.size: " + timeserise.size());
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

    public void setController(Controller controller) {
        this.controller = controller;
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
//        final String title = "DualAxisChart_2";
//        final DualAxisChart chart = new DualAxisChart(title);
//        chart.pack();
//        RefineryUtilities.centerFrameOnScreen(chart);
//        chart.setVisible(true);
    }
}