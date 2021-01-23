import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;

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

        // ---------------------------------------------------------------------------------------------------------

        // 1. Initialize histogram and set panel for histogram
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        HistogramExample histogram = new HistogramExample(aDataset.getGlobalMultPosAndNegArr(), aDataset.getGlobalMultTFArr());
        ChartPanel aHistogramChartPanel = histogram.getChartPanel();

        northPanel.add(aHistogramChartPanel, BorderLayout.EAST);
        panel.add(northPanel, BorderLayout.NORTH);

        // 2. Initialize dualAxisChart and set panel for dualAxisChart
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        final String title = "Score Bord";
        final DualAxisChart dualAxisChart = new DualAxisChart(title, aDataset.getGlobalTimeseries());
        ChartPanel aDualAxisChartPanel = dualAxisChart.getPanel();

        southPanel.add(aDualAxisChartPanel, BorderLayout.EAST);
        panel.add(southPanel, BorderLayout.SOUTH);

        // 3. Initialize comboBox and set panel for comboBox
        ComboBoxExample comboBox = new ComboBoxExample(aDataset.getGlobalTimeseries(), aDataset.getGlobalLabelArr(), dualAxisChart);
        JPanel comboBoxPanel = comboBox.getPanel();

        panel.add(comboBoxPanel, BorderLayout.WEST);

        // n. Add the globalPanel to frame
        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

