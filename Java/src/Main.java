import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;

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
        int maxWidth = 1200;
        int maxHeight = 1200;
        final JFrame frame = new JFrame("ShapeNet");
        final JPanel panel = new JPanel();
        GroupLayout groupLayout = new GroupLayout(panel);
        panel.setLayout(groupLayout);
        panel.setPreferredSize(new Dimension(maxWidth, maxHeight));
        frame.add(panel);

        int row = 2;
        int col = 1;
        JPanel groupPanel1 = new JPanel();
        groupPanel1.setLayout(new GridLayout(row, col));
        JPanel[][] panel1Holder = new JPanel[row][col];
        for(int m = 0; m < row; m++) {
            for(int n = 0; n < col; n++) {
                panel1Holder[m][n] = new JPanel();
                groupPanel1.add(panel1Holder[m][n]);
            }
        }

        JPanel groupPanel2 = new JPanel();
        JPanel groupPanel3 = new JPanel();
        JPanel groupPanel4 = new JPanel();
        JPanel groupPanel5 = new JPanel();
        JPanel groupPanel6 = new JPanel();
        groupPanel6.setLayout(new GridLayout(1,3));

        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(groupPanel1).addComponent(groupPanel4).addComponent(groupPanel6))
                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(groupPanel2).addComponent(groupPanel5))
                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(groupPanel3)));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(groupPanel1).addComponent(groupPanel2).addComponent(groupPanel3))
                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(groupPanel4).addComponent(groupPanel5))
                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(groupPanel6)));

        /*
        1, 2, 3
        4, 5, 6
        7, 8, 9
         */
        // ---------------------------------------------------------------------------------------------------------

        // 1. Initialize histogram and set panel for histogram
        HistogramExample histogram = new HistogramExample(aDataset.getGlobalMultPosAndNegArr(), aDataset.getGlobalMultTFArr());
        ChartPanel aHistogramChartPanel = histogram.getChartPanel();
        groupPanel3.add(aHistogramChartPanel);

        // 2. Initialize lineChart and set panel for comboBox
        LineChartExample lineChart = new LineChartExample("Line Chart Example", aDataset.getGlobalShapelet());
        ChartPanel aLineChartPanel = lineChart.getPanel();
        groupPanel2.add(aLineChartPanel);

        // 3. Initialize dualAxisChart and set panel for dualAxisChart
//        // final DualAxisChart dualAxisChart = new DualAxisChart(title, aDataset.getGlobalTimeseries());
//        // ChartPanel aDualAxisChartPanel = dualAxisChart.getChartPanel();
//        // gridPanel4.add(aDualAxisChartPanel);
        final DualAxisChart dualAxisChart = new DualAxisChart(aDataset.getGlobalTimeseries(), aDataset.getGlobalShapelet(), aDataset.getGlobalShapeletLabelArr());
        JScrollPane aDualAxisScrollPane = dualAxisChart.getScrollPane();
        groupPanel4.add(aDualAxisScrollPane);

        // 4. Initialize labelBox and set panel for labelBox
        LabeBoxExample labelBox = new LabeBoxExample();
        JPanel labeBoxPanel = labelBox.getPanel();
        panel1Holder[1][0].add(labeBoxPanel);

        // 5. Initialize pieChart and set panel for pieChart
        PieChartExample pieChart = new PieChartExample(aDataset.getGlobalMultPosAndNegArr(), aDataset.getGlobalMultTFArr(), aDataset.getCount());
        ChartPanel pieChartPanel = pieChart.getPanel();
        groupPanel5.add(pieChartPanel);

        // 6. Initialize PriceEstimator and set panel for PriceEstimator
        int chartIndex0 = 0;
        int chartIndex1 = 1;
        int chartIndex2 = 2;
//        com.technobium.regression.PriceEstimator priceEstimator = new com.technobium.regression.PriceEstimator(aDataset.getGlobalMulti0And1Arr());
//        ChartPanel priceEstimatorPanel = priceEstimator.getPanel();
//        gridPanel9.add(priceEstimatorPanel);
        com.technobium.regression.RegressionChartExample regressionChart = new com.technobium.regression.RegressionChartExample(aDataset.getGlobalMultiArr(), aDataset.getGlobalTimeseriesLabelArr());
        ArrayList<ChartPanel> priceEstimatorPanelArr = regressionChart.getPanelArr();
        groupPanel6.add(priceEstimatorPanelArr.get(chartIndex0));
        groupPanel6.add(priceEstimatorPanelArr.get(chartIndex1));
        groupPanel6.add(priceEstimatorPanelArr.get(chartIndex2));
//
        // n-1. Initialize comboBox and set panel for comboBox
        ComboBoxExample comboBox = new ComboBoxExample(aDataset.getGlobalTimeseries(), aDataset.getGlobalShapelet(), aDataset.getGlobalTimeseriesLabelArr(), aDataset.getGlobalShapeletLabelArr(), dualAxisChart, lineChart, labelBox);
        JPanel comboBoxPanel = comboBox.getPanel();
        panel1Holder[0][0].add(comboBoxPanel);

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

