import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HistogramExample extends JFrame {

    public JFreeChart histogramChart;
    public ChartPanel chartPanel;

    public HistogramExample() {
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
//
        double[][] vals = {{ 95, 49, 14, 59, 50, 66, 47, 40, 1, 67,
                12, 58, 28, 63, 14, 9, 31, 17, 94, 71,
                49, 64, 73, 97, 15,}, { 63, 10, 12, 31, 62,
                93, 49, 74, 90, 59, 14, 15, 88, 26, 57,
                77, 44, 58, 91, 10, 67, 57, 19, 88, 84
        }};

        for(int k=0; k<vals.length; k++){
            String key = "Class label: " + k; // Each first level [*][] stands for one class of distance. Therefore, play a tricky representation.
            dataset.addSeries(key, vals[k], 25);
        }

        PlotOrientation orientation = PlotOrientation.VERTICAL;
        boolean show = true;
        boolean toolTips = true;
        boolean urls = false;
        JFreeChart histogram = ChartFactory.createHistogram("Distance - Shapelet To Time Series",
                "distance", "frequency", dataset, orientation, show, toolTips, urls);

        XYPlot plot = (XYPlot)histogram.getPlot();
        plot.setBackgroundPaint(Color.white);
        XYBarRenderer renderer = (XYBarRenderer)plot.getRenderer();
        renderer.setBarPainter(new StandardXYBarPainter());
        renderer.setShadowVisible(false);
        renderer.setSeriesPaint(0, new Color(1, 0, 0, 0.8f));
        renderer.setSeriesPaint(1, new Color(0, 0, 1, 0.8f));
        renderer.setSeriesPaint(2, new Color(0, 1, 0, 0.8f));

        ChartPanel aChartPanel = new ChartPanel(histogram);
        aChartPanel.setBounds(0, 0, 905-540, 350);

        JFrame frame = new JFrame();

//        JPanel panel = new JPanel();
//        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
//        panel.setLayout(new GridLayout(0, 1));

        frame.add(aChartPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Our GUI");
        frame.pack();
        frame.setVisible(true);
    }

    public HistogramExample(ArrayList<ArrayList<Double>> valPosAndNegArr, ArrayList<ArrayList<Double>> valTFArr) {
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);

        double[] valPos = new double[valPosAndNegArr.get(0).size()];
        double[] valNeg = new double[valPosAndNegArr.get(1).size()];
        double[] valT = new double[valTFArr.get(0).size()];
        double[] valF = new double[valTFArr.get(1).size()];
        double[][] aPosNegArr = {valPos, valNeg};
        double[][] TFArr = {valT, valF};

        // (>0 & label==0) && (<0 & label==1): T, otherwise: F
        String[] PNlabelArr = {">0", "<0"};
        for (int i=0; i<valPosAndNegArr.size(); i++) {
            ArrayList<Double> valArr = valPosAndNegArr.get(i);
            for (int j=0; j<valArr.size(); j++) {
                aPosNegArr[i][j] = valArr.get(j);
            }
            String key = PNlabelArr[i];
//            if(i==0) {}
            dataset.addSeries(key, aPosNegArr[i], 75);
        }

        // 0: arrT & 1: arrF
        String[] TFlabelArr = {">0_True", "<0_True"};
        for (int i=0; i<valTFArr.size(); i++) {
            ArrayList<Double> valArr = valTFArr.get(i);
            for (int j=0; j<valArr.size(); j++) {
                TFArr[i][j] = valArr.get(j);
            }
            String key = TFlabelArr[i];
//            if(i==0) {}
            dataset.addSeries(key, TFArr[i], 75);
        }

        PlotOrientation orientation = PlotOrientation.VERTICAL;
        boolean show = true;
        boolean toolTips = true;
        boolean urls = false;
        JFreeChart histogram = ChartFactory.createHistogram("Distance - Shapelet To Time Series",
                "distance", "frequency", dataset, orientation, show, toolTips, urls);

        XYPlot plot = (XYPlot)histogram.getPlot();
        plot.setBackgroundPaint(Color.white);
        XYBarRenderer renderer = (XYBarRenderer)plot.getRenderer();
        renderer.setBarPainter(new StandardXYBarPainter());
        renderer.setShadowVisible(false);
        renderer.setSeriesPaint(0, new Color(0, 0, 1, 0.5f));
        renderer.setSeriesPaint(1, new Color(102,102,102, 150));
        renderer.setSeriesPaint(2, new Color(1, 0, 0, 0.5f));
        renderer.setSeriesPaint(3, new Color(0,255,51, 150));

//        setHistogramChart(histogram);

        int width = 905;
        int height = 350;
        ChartPanel aChartPanel = new ChartPanel(histogram);
        aChartPanel.setPreferredSize(new Dimension(width, height));
        aChartPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        // Set the panel globally
        setChartPanel(aChartPanel);
//
//        JFrame frame = new JFrame();
//        frame.add(chartPanel, BorderLayout.CENTER);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
    }

    private void setHistogramChart(JFreeChart histogramChart) {
        this.histogramChart = histogramChart;
    }

    public JFreeChart getHistogramChart() {
        return histogramChart;
    }

    private void setChartPanel(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }

    public static void main(String[] args) throws Exception {
        HistogramExample aHistogramExample = new HistogramExample();
    }
}