import java.awt.*;
import java.lang.reflect.Member;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
public class PieChartExample extends JFrame {
    private static final long serialVersionUID = 6294689542092367723L;
    public ChartPanel panel;
    final int width = 220;
    final int height = 220;

    public PieChartExample(String title) {
        super(title);

        // Create dataset
        PieDataset dataset = createDataset();

        // Create chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Pie Chart Example",
                dataset,
                true,
                true,
                false);

        //Format Label
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "Marks {0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);

        // Create Panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(width, height));
        // Set the panel globally
        setPanel(chartPanel);
        setContentPane(chartPanel);
    }

    public PieChartExample(ArrayList<ArrayList<Double>> valPosAndNegArr, ArrayList<ArrayList<Double>> valTPandTPArrArr, int count) {
        super("Distance (SVM) Accuracy");

        // Create dataset
        // PieDataset dataset = createDataset();
        PieDataset dataset = createDistance(valPosAndNegArr, valTPandTPArrArr, count);

        // Create chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Pie Chart Example",
                dataset,
                true,
                true,
                false);

        //Format Label
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "Marks {0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);

        // Create Panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(width, height));
        // Set the panel globally
        setPanel(chartPanel);
        setContentPane(chartPanel);
    }

    private PieDataset createDataset() {
        DefaultPieDataset dataset=new DefaultPieDataset();
        dataset.setValue("80-100", 120);
        dataset.setValue("60-79", 80);
        dataset.setValue("40-59", 20);
        dataset.setValue("20-39", 7);
        dataset.setValue("0-19", 3);
        return dataset;
    }

    private PieDataset createDistance(ArrayList<ArrayList<Double>> valPosAndNegArr, ArrayList<ArrayList<Double>> valTPandTPArrArr, int count) {
        final int positiveOrTrueIndex = 0;
        final int negativeOrFalseindex = 1;
        int posCount = valPosAndNegArr.get(positiveOrTrueIndex).size();
        int negCount = valPosAndNegArr.get(negativeOrFalseindex).size();
        int turePositiveCount = valTPandTPArrArr.get(positiveOrTrueIndex).size();
        int trueNegativeCount = valTPandTPArrArr.get(negativeOrFalseindex).size();
        int otherCount = count - (turePositiveCount+trueNegativeCount);

        System.out.println("posCount: " + posCount + ", negCount: " + negCount + ", turePositiveCount: " + turePositiveCount + ", trueNegativeCount: " + trueNegativeCount + ", otherCount: " + otherCount);

        DefaultPieDataset dataset=new DefaultPieDataset();
//        dataset.setValue("posCount", posCount);
//        dataset.setValue("negCount", negCount);
        dataset.setValue("FT&FN(Others)", otherCount);
        dataset.setValue("TPCount", turePositiveCount);
        dataset.setValue("TNCount", trueNegativeCount);
        return dataset;
    }

    private void setPanel(ChartPanel panel) {
        this.panel = panel;
    }

    public ChartPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            PieChartExample example = new PieChartExample("Pie Chart Example");
//            example.setSize(800, 400);
//            example.setLocationRelativeTo(null);
//            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//            example.setVisible(true);
//        });
    }
}  