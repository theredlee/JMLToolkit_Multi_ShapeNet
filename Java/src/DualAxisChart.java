import java.awt.*;
import java.util.ArrayList;

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
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;

public class DualAxisChart extends ApplicationFrame {

    public ChartPanel panel;
    private CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot();
    final String team1 = "1st Team";
    final String team2 = "2nd Team";
    final String label0 = "1st Timeseries Dimension ";
    final String label1 = "2nd Timeseries Dimension ";

    private ArrayList<ArrayList<ArrayList<Double>>> localTimeseries = new ArrayList<ArrayList<ArrayList<Double>>>();

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

    public DualAxisChart(String titel, ArrayList<ArrayList<ArrayList<Double>>> localTimeseries) {
        super(titel);

        // set localTimeseries
        setLocalTimeseries(localTimeseries);

        final JFreeChart chart = createChart();
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(
                new Dimension(500, 300));
        setContentPane(chartPanel);
        // Set the panel globally
        setPanel(chartPanel);
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getLocalTimeseries() {
        return localTimeseries;
    }

    private void setLocalTimeseries(ArrayList<ArrayList<ArrayList<Double>>> localTimeseries) {
        this.localTimeseries = localTimeseries;
    }

    private CategoryDataset createTimeserise0(int index) {
        final int dimemsion = 0;
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

    private CategoryDataset createTimeserise1(int index) {
        final int dimemsion = 1;
        ArrayList<Double> timeserise = localTimeseries.get(index).get(dimemsion);

        final DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        for (int i = 0; i < timeserise.size(); i++) {
            double val = timeserise.get(i);
            dataset.addValue(val,
                    label1, "" + (i + 1));
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

    private JFreeChart createChart() {
        final int defaultInex = 0;

        final CategoryDataset timeserise1 = createTimeserise0(defaultInex);
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

        final CategoryDataset timeserise2 = createTimeserise1(defaultInex);
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

    public void setPlot(final CombinedDomainCategoryPlot plot) {
        this.plot = plot;
    }

    public final CombinedDomainCategoryPlot getPlot() {
        return this.plot;
    }

    public void setTimeseriesInChart(int index) {
        final int datesetIndex = 0;
        final int subPlotIndex0 = 0;
        final int subPlotIndex1 = 1;
        CategoryPlot subplot0 = (CategoryPlot) this.plot.getSubplots().get(subPlotIndex0);
        CategoryPlot subplot1 = (CategoryPlot) this.plot.getSubplots().get(subPlotIndex1);
        final CategoryDataset timeserise0 = createTimeserise0(index);
        final CategoryDataset timeserise1 = createTimeserise1(index);
        subplot0.setDataset(datesetIndex, timeserise0);
        subplot1.setDataset(datesetIndex, timeserise1);
    }

    private void setPanel (ChartPanel panel) {
        this.panel = panel;
    }

    public ChartPanel getPanel() {
        return panel;
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