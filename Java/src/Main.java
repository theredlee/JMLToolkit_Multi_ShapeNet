import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public JFrame frame;

    public Main() {}

    public static void setDataset(Dataset aDataset) throws IOException {
        aDataset.loadShapelet();
        aDataset.loadTimeseries();
        aDataset.loadCoef();
        aDataset.loadIntercept();
        aDataset.loadFeatures();
        aDataset.multiplication_PN_TF();
    }

    public static void setHistogram(HistogramExample histogram) throws IOException {
        JFreeChart histogramChart = histogram.getHistogramChart();
    }

    public static void main(String[] args) throws IOException {
        // 0. Initialize the frame and the global panel
        Dataset aDataset = new Dataset();
        setDataset(aDataset);
        int maxWidth = 1500;
        int maxHeight = 900;
        final JFrame frame = new JFrame("ShapeNet");
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        panel.setPreferredSize(new Dimension(maxWidth, maxHeight));

        JPanel gridPanel1 = new JPanel();
        panel.add(gridPanel1);
        JPanel gridPanel2 = new JPanel();
        panel.add(gridPanel2);
        JPanel gridPanel3 = new JPanel();
        panel.add(gridPanel3);
        JPanel gridPanel4 = new JPanel();
        panel.add(gridPanel4);
        JPanel gridPanel5 = new JPanel();
        panel.add(gridPanel5);
        JPanel gridPanel6 = new JPanel();
        panel.add(gridPanel6);
        JPanel gridPanel7 = new JPanel();
        panel.add(gridPanel7);
        JPanel gridPanel8 = new JPanel();
        panel.add(gridPanel8);
        JPanel gridPanel9 = new JPanel();
        panel.add(gridPanel9);
        /*
        1, 2, 3
        4, 5, 6
        7, 8, 9
         */
        // ---------------------------------------------------------------------------------------------------------

        // 1. Initialize histogram and set panel for histogram
        HistogramExample histogram = new HistogramExample(aDataset.getGlobalMultPosAndNegArr(), aDataset.getGlobalMultTFArr());
        ChartPanel aHistogramChartPanel = histogram.getChartPanel();
        gridPanel3.add(aHistogramChartPanel);

        // 2. Initialize lineChart and set panel for comboBox
        LineChartExample lineChart = new LineChartExample("Line Chart Example", aDataset.getGlobalShapelet());
        ChartPanel aLineChartPanel = lineChart.getPanel();
        gridPanel2.add(aLineChartPanel);

        // 3. Initialize dualAxisChart and set panel for dualAxisChart
        final String title = "Score Bord";
        final DualAxisChart dualAxisChart = new DualAxisChart(title, aDataset.getGlobalTimeseries());
        ChartPanel aDualAxisChartPanel = dualAxisChart.getPanel();
        gridPanel4.add(aDualAxisChartPanel);

        // 4. Initialize labelBox and set panel for labelBox
        LabeBoxExample labelBox = new LabeBoxExample();
        JPanel labeBoxPanel = labelBox.getPanel();
        gridPanel2.add(labeBoxPanel);

        // 5. Initialize pieChart and set panel for pieChart
        PieChartExample pieChart = new PieChartExample(aDataset.getGlobalMultPosAndNegArr(), aDataset.getGlobalMultTFArr(), aDataset.getCount());
        ChartPanel pieChartPanel = pieChart.getPanel();
        gridPanel6.add(pieChartPanel);

        // 6. Initialize PriceEstimator and set panel for PriceEstimator
        int chartIndex0 = 0;
        int chartIndex1 = 1;
        int chartIndex2 = 2;
//        com.technobium.regression.PriceEstimator priceEstimator = new com.technobium.regression.PriceEstimator(aDataset.getGlobalMulti0And1Arr());
//        ChartPanel priceEstimatorPanel = priceEstimator.getPanel();
//        gridPanel9.add(priceEstimatorPanel);
        com.technobium.regression.RegressionChartExample regressionChart = new com.technobium.regression.RegressionChartExample(aDataset.getGlobalMultiArr(), aDataset.getGlobalTimeseriesLabelArr());
        ArrayList<ChartPanel> priceEstimatorPanelArr = regressionChart.getPanelArr();
        gridPanel7.add(priceEstimatorPanelArr.get(chartIndex0));
        gridPanel8.add(priceEstimatorPanelArr.get(chartIndex1));
        gridPanel9.add(priceEstimatorPanelArr.get(chartIndex2));

        // n-1. Initialize comboBox and set panel for comboBox
        ComboBoxExample comboBox = new ComboBoxExample(aDataset.getGlobalTimeseries(), aDataset.getGlobalShapelet(), aDataset.getGlobalTimeseriesLabelArr(), aDataset.getGlobalShapeletLabelArr(), dualAxisChart, lineChart, labelBox);
        JPanel comboBoxPanel = comboBox.getPanel();
        gridPanel1.add(comboBoxPanel);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}


// One line chart to divide all timeseries distances into two sides (If can show the distance, it would be better)

// Show one bar/pie chart to show the accuracy

