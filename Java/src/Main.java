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

    public static void setDataset_testing(Dataset aDataset) throws IOException {
        aDataset.loadShapelet_testing();
        aDataset.loadTimeseries_testing();
        aDataset.loadStartEndPoints_testing();
    }

    public static void setHistogram(HistogramExample histogram) throws IOException {
        JFreeChart histogramChart = histogram.getHistogramChart();
    }

//    private static void testing_main() throws IOException {
//        // ------------------------------------------------------------------------------
//        Dataset aDataset = new Dataset(false);
//        setDataset_testing(aDataset);
////        setDataset(aDataset);
//
//        final DualAxisChart dualAxisChart = new DualAxisChart(aDataset.getGlobalRawTimeseries(), aDataset.getGlobalShapelet(), aDataset.getGlobalShapeletLabelArr(), aDataset.getGlobalStartEndPoints_ALT_AND_AFP());
//        dualAxisChart.pack();
//        RefineryUtilities.centerFrameOnScreen(dualAxisChart);
//        dualAxisChart.setVisible(true);
//        // ------------------------------------------------------------------------------
//    }

    private static void formal_main() throws IOException {

        final int maxWidth = 800;
        final int maxHeight = 600;

        Dataset aDataset = new Dataset(true);
        setDataset(aDataset);

        // -----------------------------------------------
        final JFrame frame = new JFrame("ShapeNet");
        frame.setSize(maxWidth, maxHeight);

        //------------------------
        // Canvas pane
        JPanel panel_1 = new JPanel();

        JPanel panel_2 = new JPanel();

        JPanel panel_2_1 = new JPanel();

        JPanel panel_2_2 = new JPanel();

        JPanel panel_2_2_1 = new JPanel();

        JPanel panel_2_2_2 = new JPanel();
        //------------------------

        // ---------------------------------------------------------------------------------------------------------
        // 1. Initialize histogram and set panel for histogram
        final HistogramExample histogram = new HistogramExample(aDataset.getGlobalMultPosAndNegArr(), aDataset.getGlobalMultTFArr());
//        final LineChartExample lineChart = new LineChartExample("Line Chart Example", aDataset.getGlobalShapelet());
        final DualAxisChart_1 dualAxisChart_1 = new DualAxisChart_1(aDataset.getGlobalTimeseries(), aDataset.getGlobalShapelet(), aDataset.getGlobalShapeletLabelArr());
        DualAxixChart_Carousel animator = new DualAxixChart_Carousel(aDataset);

        final TextAreaExample textArea = new TextAreaExample();
        final PieChartExample pieChart = new PieChartExample(aDataset.getGlobalMultPosAndNegArr(), aDataset.getGlobalMultTFArr(), aDataset.getCount());
        final com.technobium.regression.RegressionChartExample regressionChart = new com.technobium.regression.RegressionChartExample(aDataset.getGlobalMultiArr(), aDataset.getGlobalTimeseriesLabelArr());
        final ComboBoxExample comboBox = new ComboBoxExample(aDataset.getGlobalRawTimeseries(), aDataset.getGlobalShapelet(), aDataset.getGlobalTimeseriesLabelArr(), aDataset.getGlobalShapeletLabelArr(), dualAxisChart_1, textArea);

        // --------------------------------------------------------
        // 1. Initialize comboBox and set panel for comboBox
        JPanel comboBoxPanel = comboBox.getPanel();
        panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
        panel_1.setPreferredSize(new Dimension(250+20, frame.getHeight()));
        panel_1.add(comboBoxPanel);
        panel_1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frame.add(panel_1, BorderLayout.WEST);

        // 2. Initialize textArea and set panel for textArea
        JScrollPane textAreaScrollPane = textArea.getScrollPane();
        panel_1.add(textAreaScrollPane);

        // 3. Initialize dualAxisChart and set panel for dualAxisChart
        JScrollPane aDualAxisScrollPane_4 = dualAxisChart_1.getScrollPane();
        panel_1.add(aDualAxisScrollPane_4);

        // 4. Initialize PriceEstimator and set panel for PriceEstimator
        int chartIndex0 = 0;
        int chartIndex1 = 1;
        int chartIndex2 = 2;
        ArrayList<ChartPanel> estimatorPanelArr = regressionChart.getPanelArr();
        JPanel regressionChartPanel = new JPanel();
        regressionChartPanel.setLayout(new BoxLayout(regressionChartPanel, BoxLayout.X_AXIS));
        int regress_width = 250;
        int regress_height = 200;
        regressionChartPanel.setPreferredSize(new Dimension(regress_width, regress_height));
        regressionChartPanel.add(estimatorPanelArr.get(chartIndex0));
        regressionChartPanel.add(estimatorPanelArr.get(chartIndex1));
        regressionChartPanel.add(estimatorPanelArr.get(chartIndex2));
        JScrollPane regressionChartScrollPane = new JScrollPane(regressionChartPanel);
        regressionChartScrollPane.setPreferredSize(new Dimension(regress_width, regress_height));
        panel_2_1.setLayout(new BoxLayout(panel_2_1, BoxLayout.X_AXIS));
        panel_2_1.add(regressionChartScrollPane);
        panel_2_1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel_2.setLayout (new BoxLayout(panel_2, BoxLayout.Y_AXIS));
        panel_2.add(panel_2_1);
        frame.add(panel_2, BorderLayout.EAST);

        // 5. Initialize lineChart and set panel for comboBox
//        ChartPanel aLineChartPanel = lineChart.getPanel();
//        panel_2_1.add(aLineChartPanel);

        // 6. Initialize dualAxisChart_2
//        JScrollPane aDualAxisScrollPane_2 = dualAxisChart_2.getScrollPane();
//        panel_2_2_1.add(aDualAxisScrollPane_2, BorderLayout.CENTER);

        panel_2_2_1.add(animator, BorderLayout.CENTER);

        panel_2_2_1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel_2_2_1.setMinimumSize(new Dimension(aDualAxisScrollPane_4.getWidth(), aDualAxisScrollPane_4.getHeight()));
        panel_2_2.setLayout(new BoxLayout(panel_2_2, BoxLayout.X_AXIS));
        panel_2_2.add(panel_2_2_1);
        panel_2.add(panel_2_2);

        // 6. Initialize pieChart and set panel for pieChart
        ChartPanel pieChartPanel = pieChart.getPanel();
        panel_2_2_2.setLayout(new BoxLayout(panel_2_2_2, BoxLayout.Y_AXIS));
        panel_2_2_2.add(pieChartPanel);
        panel_2_2_2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel_2_2_2.setMinimumSize(new Dimension(pieChartPanel.getWidth(),panel_2_2_1.getHeight()));
        panel_2_2.setLayout(new BoxLayout(panel_2_2, BoxLayout.X_AXIS));
        panel_2_2.add(panel_2_2_2);
//        panel_2.add(panel_2_2);

        // 7. Initialize histogram and set panel for histogram
        ChartPanel aHistogramChartPanel = histogram.getChartPanel();
        panel_2_2_2.add(aHistogramChartPanel);

        // Frame setting
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        // 0. Initialize the frame and the global panel
        formal_main();
//        testing_main();
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


