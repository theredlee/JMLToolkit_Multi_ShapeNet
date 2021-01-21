import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class DualAxisChart extends ApplicationFrame {

    private CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot();
    final String team1 = "1st Team";
    final String team2 = "2nd Team";
    private ArrayList<ArrayList<ArrayList<Double>>> localTimeseries = new ArrayList<ArrayList<ArrayList<Double>>>();

    public DualAxisChart(String titel) {
        super(titel);

        final JFreeChart chart = createChart();
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(
                new java.awt.Dimension(600, 450));
        setContentPane(chartPanel);
    }

    public DualAxisChart(String titel, ArrayList<ArrayList<ArrayList<Double>>> localTimeseries) {
        super(titel);

        // set localTimeseries
        setLocalTimeseries(localTimeseries);

        final JFreeChart chart = createChart();
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(
                new java.awt.Dimension(600, 450));
        setContentPane(chartPanel);
    }

    public static void main(final String[] args) {

        final String title = "Score Bord";
        final DualAxisChart chart = new DualAxisChart(title);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getLocalTimeseries() {
        return localTimeseries;
    }

    private void setLocalTimeseries(ArrayList<ArrayList<ArrayList<Double>>> localTimeseries) {
        this.localTimeseries = localTimeseries;
    }

    private CategoryDataset createTimeserise1() {
        // Handling the first timeseries at first
        ArrayList<Double> firstTimeserise = localTimeseries.get(0).get(0);

        final DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        double[] run = run()[0];
        float num = 0;

        for (int i = 0; i < firstTimeserise.size(); i++) {
            double val = firstTimeserise.get(i);
            dataset.addValue(val,
                    team1+" Runrate", "" + (i + 1));
        }
        return dataset;
    }

    private CategoryDataset createTimeserise2() {
        // Handling the first timeseries at first
        ArrayList<Double> firstTimeserise = localTimeseries.get(1).get(0);

        final DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        double[] run = run()[0];
        float num = 0;

        for (int i = 0; i < firstTimeserise.size(); i++) {
            double val = firstTimeserise.get(i);
            dataset.addValue(val,
                    team1+" Runrate", "" + (i + 1));
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

    private CategoryDataset createRunDataset1() {
        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        double[] run = run()[0];

        for (int i = 0; i < run.length; i++) {
            dataset.addValue(run[i], team1+
                    " Run", "" + (i + 1));
        }
        return dataset;
    }

    private CategoryDataset createRunDataset2() {
        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        double[] run = run()[1];

        for (int i = 0; i < run.length; i++) {
            dataset.addValue(run[i], team2+
                    " Run", "" + (i + 1));
        }
        return dataset;
    }

    private CategoryDataset createRunRateDataset1() {
//         ----------------------------------------------
        final DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        double[] run = run()[0];
        float num = 0;

        for (int i = 0; i < run.length; i++) {
            num += run[i];
            dataset.addValue(num / (i + 1),
                    team1+" Runrate", "" + (i + 1));
        }
        return dataset;
//         ----------------------------------------------
    }

    private CategoryDataset createRunRateDataset2() {
        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        double[] run = run()[1];
        float num = 0;

        for (int i = 0; i < run.length; i++) {
            num += run[i];
            dataset.addValue(num / (i + 1),
                    team2+" Runrate", "" + (i + 1));
        }
        return dataset;
    }

    // For reference
    private JFreeChart createChart_Initial() {

        final CategoryDataset dataset1 = createRunDataset1();
        final NumberAxis rangeAxis1 = new NumberAxis("Run");
        rangeAxis1.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Bar render
        final BarRenderer renderer1 = new BarRenderer();
        renderer1.setSeriesPaint(0, Color.red);
        renderer1.setBaseToolTipGenerator(
                new StandardCategoryToolTipGenerator());
        final CategoryPlot subplot1 =
                new CategoryPlot(dataset1, null,
                        rangeAxis1, renderer1);
        subplot1.setDomainGridlinesVisible(true);

//        final CategoryDataset runrateDataset1
//                = createRunRateDataset1();
        final CategoryDataset timeserise1
                = createTimeserise1();
        final ValueAxis axis2 = new NumberAxis("Run Rate");
        subplot1.setRangeAxis(1, axis2);
//        subplot1.setDataset(1, runrateDataset1);
        subplot1.setDataset(1, timeserise1);
        subplot1.mapDatasetToRangeAxis(1, 1);
        // Line render
        final CategoryItemRenderer runrateRenderer1
                = new LineAndShapeRenderer();
        runrateRenderer1.setSeriesPaint(0, Color.red);

        subplot1.setForegroundAlpha(0.7f);
        subplot1.setRenderer(0, renderer1);
        subplot1.setRenderer(1, runrateRenderer1);

        // -----------------------------------------------

        final CategoryDataset dataset2 = createRunDataset2();
        final NumberAxis rangeAxis2 = new NumberAxis("Run");
        rangeAxis2.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Bar render
        final BarRenderer renderer2 = new BarRenderer();
        renderer2.setSeriesPaint(0, Color.blue);
        renderer2.setBaseToolTipGenerator(
                new StandardCategoryToolTipGenerator());
        final CategoryPlot subplot2 =
                new CategoryPlot(dataset2, null,
                        rangeAxis2, renderer2);
        subplot2.setDomainGridlinesVisible(true);

//        final CategoryDataset runrateDataset2 =
//                createRunRateDataset2();

        final CategoryDataset timeserise2 =
                createTimeserise2();

        final ValueAxis axis3 = new NumberAxis("Run Rate");
        subplot2.setRangeAxis(1, axis3);
//        subplot2.setDataset(1, runrateDataset2);
        subplot2.setDataset(1, timeserise2);
        subplot2.mapDatasetToRangeAxis(1, 1);
        // Line render
        final CategoryItemRenderer runrateRenderer2 =
                new LineAndShapeRenderer();
        runrateRenderer2.setSeriesPaint(0, Color.blue);

        subplot2.setForegroundAlpha(0.7f);
//        subplot2.setRenderer(0, renderer2);
        subplot2.setRenderer(1, runrateRenderer2);

        final CategoryAxis domainAxis = new CategoryAxis("Over");
        final CombinedDomainCategoryPlot plot =
                new CombinedDomainCategoryPlot(domainAxis);

        plot.add(subplot1, 1);
        plot.add(subplot2, 1);

        final JFreeChart chart = new JFreeChart(
                "Score Bord", new Font("SansSerif", Font.BOLD, 12),
                plot, true);
        return chart;
    }

    private JFreeChart createChart() {

        final CategoryDataset timeserise1 = createTimeserise1();
        final NumberAxis rangeAxis1 = new NumberAxis("Timeserise D1");
        rangeAxis1.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Line render
        final CategoryItemRenderer renderer1
                = new LineAndShapeRenderer();
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

        final CategoryDataset timeserise2 = createTimeserise2();
        final NumberAxis rangeAxis2 = new NumberAxis("Timeserise D2");
        rangeAxis2.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Line render
        final CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
        renderer2.setSeriesPaint(0, Color.blue);
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

    public void setTimeseriesInChart(int noOfPlot) {
        CategoryPlot subplot = (CategoryPlot) this.plot.getSubplots().get(noOfPlot);
        final CategoryDataset timeserise2 = createTimeserise2();
        subplot.setDataset(0, timeserise2);
    }
}