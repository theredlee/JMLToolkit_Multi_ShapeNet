import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.io.IOException;

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

    public static void setDataset_testing(Dataset aDataset) throws IOException {
        aDataset.loadShapelet_testing();
        aDataset.loadTimeseries_testing();
        aDataset.loadStartEndPoints_testing();
    }

    public static void setHistogram(HistogramExample histogram) throws IOException {
        JFreeChart histogramChart = histogram.getHistogramChart();
    }

    public static void main(String[] args) throws IOException {
//        // 0. Initialize the frame and the global panel
//        Dataset aDataset = new Dataset(true);
//        setDataset(aDataset);
//
//        int maxWidth = 1500;
//        int maxHeight = 1200;
//        final JFrame frame = new JFrame("ShapeNet");
//        final JPanel panel = new JPanel();
//        GroupLayout groupLayout = new GroupLayout(panel);
//        panel.setLayout(groupLayout);
//        panel.setPreferredSize(new Dimension(maxWidth, maxHeight));
//        frame.add(panel);
//
//        // groupPanel1 ----------
//        int row = 1;
//        int col = 2;
//        JPanel groupPanel1 = new JPanel();
//        groupPanel1.setLayout(new GridLayout(row, col));
//        JPanel[][] panel1Holder = new JPanel[row][col];
//        for(int m = 0; m < row; m++) {
//            for(int n = 0; n < col; n++) {
//                panel1Holder[m][n] = new JPanel();
//                groupPanel1.add(panel1Holder[m][n]);
//            }
//        }
//        GroupLayout groupLayout1 = new GroupLayout(groupPanel1);
//        groupPanel1.setLayout(groupLayout1);
//        groupLayout1.setHorizontalGroup(groupLayout1.createSequentialGroup()
//                .addGroup(groupLayout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(panel1Holder[0][0]))
//                .addGroup(groupLayout1.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(panel1Holder[0][1]))
//        );
//
//        groupLayout1.setVerticalGroup(groupLayout1.createSequentialGroup()
//                .addGroup(groupLayout1.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(panel1Holder[0][0]).addComponent(panel1Holder[0][1])));
//
//        // For comBox and label on the left-hand side
//        row = 2;
//        col = 1;
//        JPanel[][] panel1HolderLeftHolder = new JPanel[row][col];
//        panel1Holder[0][0].setPreferredSize(new Dimension(300, 350));
//        for(int m = 0; m < row; m++) {
//            for(int n = 0; n < col; n++) {
//                panel1HolderLeftHolder[m][n] = new JPanel();
//                panel1Holder[0][0].add(panel1HolderLeftHolder[m][n]);
//            }
//        }
//
//        // groupPanel2 ----------
//        row = 1;
//        col = 2;
//        JPanel groupPanel2 = new JPanel();
//        JPanel[][] panel2Holder = new JPanel[row][col];
//        for(int m = 0; m < row; m++) {
//            for(int n = 0; n < col; n++) {
//                panel2Holder[m][n] = new JPanel();
//                groupPanel2.add(panel2Holder[m][n]);
//            }
//        }
//
//        // For Histogram and piechart on the right-hand side
//        panel2Holder[0][1].setPreferredSize(new Dimension(400,800));
//        panel2Holder[0][1].setLayout(new GridLayout(2,1));
//        row = 2;
//        col = 1;
//        JPanel[][] panel2HolderRightHolder = new JPanel[row][col];
//        for(int m = 0; m < row; m++) {
//            for(int n = 0; n < col; n++) {
//                panel2HolderRightHolder[m][n] = new JPanel();
//                panel2Holder[0][1].add(panel2HolderRightHolder[m][n]);
//            }
//        }
//
//        GroupLayout groupLayout2 = new GroupLayout(groupPanel2);
//        groupPanel2.setLayout(groupLayout2);
//        groupLayout2.setHorizontalGroup(groupLayout2.createSequentialGroup()
//                .addGroup(groupLayout2.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(panel2Holder[0][0]))
//                .addGroup(groupLayout2.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(panel2Holder[0][1])));
//
//        groupLayout2.setVerticalGroup(groupLayout2.createSequentialGroup()
//                .addGroup(groupLayout2.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(panel2Holder[0][0]).addComponent(panel2Holder[0][1])));
//
//        // groupPanel3 ----------
//        JPanel groupPanel3 = new JPanel();
//        groupPanel3.setLayout(new GridLayout(1,3));
//
//        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
//                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(groupPanel1).addComponent(groupPanel2).addComponent(groupPanel3))
//        );
//
//        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
//                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(groupPanel1))
//                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(groupPanel2))
//                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(groupPanel3)));
//
//        /*
//        1, 2, 3
//        4, 5, 6
//        7, 8, 9
//        */
//        // ---------------------------------------------------------------------------------------------------------
//
//        // 1. Initialize histogram and set panel for histogram
//        HistogramExample histogram = new HistogramExample(aDataset.getGlobalMultPosAndNegArr(), aDataset.getGlobalMultTFArr());
//        ChartPanel aHistogramChartPanel = histogram.getChartPanel();
//        panel2HolderRightHolder[0][0].add(aHistogramChartPanel);
//
//        // 2. Initialize lineChart and set panel for comboBox
//        LineChartExample lineChart = new LineChartExample("Line Chart Example", aDataset.getGlobalShapelet());
//        ChartPanel aLineChartPanel = lineChart.getPanel();
////        groupPanel3.add(aLineChartPanel);
//
//        // 3. Initialize dualAxisChart and set panel for dualAxisChart
////        // final DualAxisChart dualAxisChart = new DualAxisChart(title, aDataset.getGlobalTimeseries());
////        // ChartPanel aDualAxisChartPanel = dualAxisChart.getChartPanel();
////        // gridPanel4.add(aDualAxisChartPanel);
//        final DualAxisChart dualAxisChart = new DualAxisChart(aDataset.getGlobalTimeseries(), aDataset.getGlobalShapelet(), aDataset.getGlobalShapeletLabelArr());
//        JScrollPane aDualAxisScrollPane = dualAxisChart.getScrollPane();
//        panel2Holder[0][0].add(aDualAxisScrollPane);
//
//        // 4. Initialize textArea and set panel for textArea
//        JTextAreaExample textArea = new JTextAreaExample();
//        JScrollPane textAreaScrollPane = textArea.getScrollPane();
//        panel1HolderLeftHolder[1][0].add(textAreaScrollPane);
//
//        // 5. Initialize pieChart and set panel for pieChart
//        PieChartExample pieChart = new PieChartExample(aDataset.getGlobalMultPosAndNegArr(), aDataset.getGlobalMultTFArr(), aDataset.getCount());
//        ChartPanel pieChartPanel = pieChart.getPanel();
//        panel2HolderRightHolder[1][0].add(pieChartPanel);
//
//        // 6. Initialize PriceEstimator and set panel for PriceEstimator
//        int chartIndex0 = 0;
//        int chartIndex1 = 1;
//        int chartIndex2 = 2;
////        com.technobium.regression.PriceEstimator priceEstimator = new com.technobium.regression.PriceEstimator(aDataset.getGlobalMulti0And1Arr());
////        ChartPanel regressionChartPanel = priceEstimator.getPanel();
////        gridPanel9.add(regressionChartPanel);
//        com.technobium.regression.RegressionChartExample regressionChart = new com.technobium.regression.RegressionChartExample(aDataset.getGlobalMultiArr(), aDataset.getGlobalTimeseriesLabelArr());
//        ArrayList<ChartPanel> priceEstimatorPanelArr = regressionChart.getPanelArr();
//        JPanel regressionChartPanel = new JPanel();
//        regressionChartPanel.setPreferredSize(new Dimension(1350, 350));
//        regressionChartPanel.add(priceEstimatorPanelArr.get(chartIndex0));
//        regressionChartPanel.add(priceEstimatorPanelArr.get(chartIndex1));
//        regressionChartPanel.add(priceEstimatorPanelArr.get(chartIndex2));
//        JScrollPane regressionChartScrollPane = new JScrollPane(regressionChartPanel);
//        regressionChartScrollPane.setPreferredSize(new Dimension(1000, 350));
//        panel1Holder[0][1].add(regressionChartScrollPane);
////
//        // n-1. Initialize comboBox and set panel for comboBox
//        ComboBoxExample comboBox = new ComboBoxExample(aDataset.getGlobalRawTimeseries(), aDataset.getGlobalShapelet(), aDataset.getGlobalTimeseriesLabelArr(), aDataset.getGlobalShapeletLabelArr(), dualAxisChart, lineChart, textArea);
//        JPanel comboBoxPanel = comboBox.getPanel();
//        panel1HolderLeftHolder[0][0].add(comboBoxPanel);
//
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);

        // ------------------------------------------------------------------------------
        Dataset aDataset = new Dataset(false);
        setDataset_testing(aDataset);

        final DualAxisChart dualAxisChart = new DualAxisChart(aDataset.getGlobalRawTimeseries(), aDataset.getGlobalShapelet(), aDataset.getGlobalShapeletLabelArr(), aDataset.getGlobalStartEndPoints_ALT_AND_AFP());
        dualAxisChart.pack();
        RefineryUtilities.centerFrameOnScreen(dualAxisChart);
        dualAxisChart.setVisible(true);
        // ------------------------------------------------------------------------------
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


