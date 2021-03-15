import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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

import java.awt.*;
import java.util.ArrayList;

public class LineChartExample extends JFrame {

    private static final long serialVersionUID = 1L;
    public ChartPanel panel;
    private CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot();
    private ArrayList<ArrayList<Double>> localShapelet = new ArrayList<ArrayList<Double>>();
    final String label = "Shapelet  ";

    public LineChartExample(String title) {
        super(title);
        // Create dataset
        DefaultCategoryDataset dataset = createDataset();

        // ---------------------------------------------------------

        final NumberAxis rangeAxis1 = new NumberAxis("Timeserise D1");
        rangeAxis1.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Line render
        final CategoryItemRenderer renderer1 = new LineAndShapeRenderer();
        renderer1.setSeriesPaint(0, Color.red);
        renderer1.setBaseToolTipGenerator(
                new StandardCategoryToolTipGenerator());
        final CategoryPlot subplot1 = new CategoryPlot(dataset, null, rangeAxis1, renderer1);
        subplot1.setDomainGridlinesVisible(true);;

        subplot1.setForegroundAlpha(0.7f);
        subplot1.setRenderer(0, renderer1);

        // ----------------------------------------------------------
        final CategoryAxis domainAxis = new CategoryAxis("Over");

        final CombinedDomainCategoryPlot plot =
                new CombinedDomainCategoryPlot(domainAxis);

        plot.add(subplot1, 1);

        final JFreeChart chart = new JFreeChart(
                "Shapelet", new Font("SansSerif", Font.BOLD, 12),
                plot, true);

        // Create chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(300, 200));
//        setContentPane(chartPanel);
        // Set the panel globally
        setPanel(chartPanel);
    }

    public LineChartExample(String title, ArrayList<ArrayList<Double>> localShapelet) {
        super(title);
        // Create dataset
//        DefaultCategoryDataset dataset = createDataset();
        // Set localShapelet
        setLocalShapelet(localShapelet);

        final int defaultIndex = 0;
        final CategoryDataset shapelet1 = createShapelet(defaultIndex);

        // ---------------------------------------------------------

        final NumberAxis rangeAxis1 = new NumberAxis("Timeserise D1");
        rangeAxis1.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        // Line render
        final CategoryItemRenderer renderer1 = new LineAndShapeRenderer();
        renderer1.setSeriesPaint(0, Color.red);
        renderer1.setBaseToolTipGenerator(
                new StandardCategoryToolTipGenerator());
        final CategoryPlot subplot1 = new CategoryPlot(shapelet1, null, rangeAxis1, renderer1);
        subplot1.setDomainGridlinesVisible(true);;

        subplot1.setForegroundAlpha(0.7f);
        subplot1.setRenderer(0, renderer1);

        // ----------------------------------------------------------
        final CategoryAxis domainAxis = new CategoryAxis("Over");

        final CombinedDomainCategoryPlot plot =
                new CombinedDomainCategoryPlot(domainAxis);

        plot.add(subplot1, 1);

        final JFreeChart chart = new JFreeChart(
                "Shapelet", new Font("SansSerif", Font.BOLD, 12),
                plot, true);

        // Set plot
        setPlot(plot);

        // Create chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(300, 200));
        // Set the panel globally
        setPanel(chartPanel);
    }

    public void setPlot(final CombinedDomainCategoryPlot plot) {
        this.plot = plot;
    }

    public final CombinedDomainCategoryPlot getPlot() {
        return this.plot;
    }

    private DefaultCategoryDataset createDataset() {

        String series1 = "Visitor";
        String series2 = "Unique Visitor";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(200, series1, "2016-12-19");
        dataset.addValue(150, series1, "2016-12-20");
        dataset.addValue(100, series1, "2016-12-21");
        dataset.addValue(210, series1, "2016-12-22");
        dataset.addValue(240, series1, "2016-12-23");
        dataset.addValue(195, series1, "2016-12-24");
        dataset.addValue(245, series1, "2016-12-25");

        dataset.addValue(150, series2, "2016-12-19");
        dataset.addValue(130, series2, "2016-12-20");
        dataset.addValue(95, series2, "2016-12-21");
        dataset.addValue(195, series2, "2016-12-22");
        dataset.addValue(200, series2, "2016-12-23");
        dataset.addValue(180, series2, "2016-12-24");
        dataset.addValue(230, series2, "2016-12-25");

        return dataset;
    }

    private CategoryDataset createShapelet(int index) {
        ArrayList<Double> shapelet = localShapelet.get(index);

        final DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        for (int i = 0; i < shapelet.size(); i++) {
            double val = shapelet.get(i);
            dataset.addValue(val,
                    label, "" + (i + 1));
        }
        return dataset;
    }

    public void setShapeletInChart(int index) {
        final int datesetIndex = 0;
        final int subPlotIndex0 = 0;
        CategoryPlot subplot0 = (CategoryPlot) this.plot.getSubplots().get(subPlotIndex0);
        final CategoryDataset shapelet0 = createShapelet(index);
        subplot0.setDataset(datesetIndex, shapelet0);
    }

    private void setLocalShapelet(ArrayList<ArrayList<Double>> localShapelet) {
        this.localShapelet = localShapelet;
    }

    private void setPanel (ChartPanel panel) {
        this.panel = panel;
    }

    public ChartPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LineChartExample example = new LineChartExample("Line Chart Example");
            example.setAlwaysOnTop(true);
            example.pack();
            example.setSize(600, 400);
            example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
