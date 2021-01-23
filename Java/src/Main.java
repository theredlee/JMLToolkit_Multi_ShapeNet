import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public JFrame frame;

    public Main() {}

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JFrame getFrame() {
        return frame;
    }

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
        int maxLen = 1000;
        final JFrame frame = new JFrame("ShapeNet");
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(maxWidth, maxLen));

        JPanel EPanel = new JPanel();
        EPanel.setLayout(new BorderLayout());
        JPanel NEPanel = new JPanel();
        NEPanel.setLayout(new BorderLayout());
        JPanel SEPanel = new JPanel();
        SEPanel.setLayout(new BorderLayout());
        EPanel.add(NEPanel, BorderLayout.NORTH);
        EPanel.add(SEPanel, BorderLayout.SOUTH);

        JPanel WPanel = new JPanel();
        WPanel.setLayout(new BorderLayout());
        JPanel NWPanel = new JPanel();
        NWPanel.setLayout(new BorderLayout());
        JPanel SWPanel = new JPanel();
        SWPanel.setLayout(new BorderLayout());
        WPanel.add(NWPanel, BorderLayout.NORTH);
        WPanel.add(SWPanel, BorderLayout.SOUTH);

        // ---------------------------------------------------------------------------------------------------------

        // 1. Initialize histogram and set panel for histogram
        HistogramExample histogram = new HistogramExample(aDataset.getGlobalMultPosAndNegArr(), aDataset.getGlobalMultTFArr());
        ChartPanel aHistogramChartPanel = histogram.getChartPanel();
        NEPanel.add(aHistogramChartPanel, BorderLayout.CENTER);

        // 2. Initialize lineChart and set panel for comboBox
        JPanel SEEPanel = new JPanel();
        SEEPanel.setLayout(new BorderLayout());
        SWPanel.add(SEEPanel, BorderLayout.EAST);
        LineChartExample lineChart = new LineChartExample("Line Chart Example", aDataset.getGlobalShapelet());
        ChartPanel aLineChartPanel = lineChart.getPanel();
        SEEPanel.add(aLineChartPanel, BorderLayout.NORTH);

        // 3. Initialize dualAxisChart and set panel for dualAxisChart
        final String title = "Score Bord";
        final DualAxisChart dualAxisChart = new DualAxisChart(title, aDataset.getGlobalTimeseries());
        ChartPanel aDualAxisChartPanel = dualAxisChart.getPanel();
        SEEPanel.add(aDualAxisChartPanel, BorderLayout.SOUTH);

        // 4. Initialize comboBox and set panel for comboBox
        ComboBoxExample comboBox = new ComboBoxExample(aDataset.getGlobalTimeseriesLabelArr(), aDataset.getGlobalShapeletLabelArr(), dualAxisChart, lineChart);
        JPanel comboBoxPanel = comboBox.getPanel();
        NWPanel.add(comboBoxPanel, BorderLayout.WEST);

        // n. Add the globalPanel to frame
        panel.add(EPanel, BorderLayout.EAST);
        panel.add(WPanel, BorderLayout.WEST);

        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}


// One line chart to divide all timeseries distances into two sides (If can show the distance, it would be better)

// Show one bar/pie chart to show the accuracy

