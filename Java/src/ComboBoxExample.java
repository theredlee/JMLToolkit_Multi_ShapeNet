import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ComboBoxExample  extends ApplicationFrame {
    JFrame f;
    public JPanel panel;
    boolean shapeletInit = false;
    boolean timeseriesInitiated = false;

    final JComboBox cbTimeseriesLabel;
    final JComboBox cbTimeseries;

    ComboBoxExample(ArrayList<ArrayList<ArrayList<Double>>> localTimeseries, ArrayList<ArrayList<Double>> localShapelet, ArrayList<Double> localTimeseriesLabelArr, ArrayList<Double> localShapeletDimArr, DualAxisChart_1 dualAxischart, TextAreaExample labelBox){
        super("ComboBox");

        final int[] previousSwitchLabel = {-1, -1};
        final int[] previousSwitch = {-1, -1};

        // Transform to set to remove all duplicated items
        Set<Double> labelSet = new HashSet<Double>(localTimeseriesLabelArr);
        ArrayList<Double> newLabelArr = new ArrayList<>();
        newLabelArr.addAll(labelSet);
        String[] labelArr = new String[newLabelArr.size()];

        for (int i=0; i<newLabelArr.size(); i++) {
            labelArr[i] = String.valueOf((int)((double)newLabelArr.get(i)));
        }

        cbTimeseriesLabel = new JComboBox(labelArr);
        cbTimeseries = new JComboBox();

        cbTimeseriesLabel.setPreferredSize(new Dimension(90,20));
        cbTimeseries.setPreferredSize(new Dimension(90,20));

        JButton button=new JButton("Show");
//        button.setBounds(cbShapelet.getX()+cbShapelet.getWidth()+10,cbSwitch.getY(),75,20);
        button.setPreferredSize(new Dimension(75,20));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setMinimumSize(new Dimension(300,100));
        JPanel panelShapeletAndTimeseries = new JPanel();
        panelShapeletAndTimeseries.setLayout(new BorderLayout());
        panelShapeletAndTimeseries.setPreferredSize(new Dimension(200,200));

        JPanel panelTimeseries = new JPanel();
        JPanel panelButton = new JPanel();
        panelTimeseries.setPreferredSize(new Dimension(200,30));
        panelButton.setPreferredSize(new Dimension(100,40));
        panelButton.setLayout(new BorderLayout());

        JLabel jlabel_timeseries = new JLabel("Timeseries: ");
        jlabel_timeseries.setSize(100,50);
        panelTimeseries.add(jlabel_timeseries);
        panelTimeseries.add(cbTimeseriesLabel);
        panelTimeseries.add(cbTimeseries);
        panelButton.add(button, BorderLayout.CENTER);

        panelShapeletAndTimeseries.add(panelTimeseries, BorderLayout.CENTER);
        panel.add(panelShapeletAndTimeseries, BorderLayout.WEST);
        panel.add(panelButton, BorderLayout.CENTER);

        // Set the panel globally
        setPanel(panel);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // -------- Set timeseries with specific label
                // Switch: timeseries or shapelet
                ArrayList<Integer>[] switchIndexArr = new ArrayList[2];
                for (int i=0; i<2; i++) {
                    switchIndexArr[i] = new ArrayList<>();
                }

                int timeseriesIndex = 0;
                // count
                int switchWithLabelCount = 0;
                int[] selectedSwitchLabel = new int[1];
                int[] selectedSwitch = new int[1];

                if (timeseriesInitiated) {
                    selectedSwitch[timeseriesIndex] = Integer.parseInt((String) cbTimeseries.getItemAt(cbTimeseries.getSelectedIndex()));
                    selectedSwitchLabel[timeseriesIndex] = Integer.parseInt((String) cbTimeseriesLabel.getItemAt(cbTimeseriesLabel.getSelectedIndex()));
//                    System.out.println("selectedSwitchLabel[timeseriesIndex] point 0: " + selectedSwitchLabel[timeseriesIndex]);
//                    System.out.println("previousSwitchLabel[timeseriesIndex] point 0: " + previousSwitchLabel[timeseriesIndex]);
                } else {
                    // Default value: 0
                    selectedSwitch[timeseriesIndex] = 0;
                    timeseriesInitiated = true;
                }

                // For timeseries label selection change
                if ((selectedSwitchLabel[timeseriesIndex] != previousSwitchLabel[timeseriesIndex])) {
//                    System.out.println("selectedSwitchLabel[timeseriesIndex] point 1: " + selectedSwitchLabel[timeseriesIndex]);
//                    System.out.println("previousSwitchLabel[timeseriesIndex] point 1: " + previousSwitchLabel[timeseriesIndex]);

                    for (int i=0; i<localTimeseriesLabelArr.size(); i++) {
                        double correspondingLocalLabel = localTimeseriesLabelArr.get(i);
                        if (correspondingLocalLabel==selectedSwitchLabel[timeseriesIndex]) {
                            switchIndexArr[timeseriesIndex].add(i);
                            switchWithLabelCount++;
                        }
                    }

                    String[] switchArr = new String[switchWithLabelCount];
                    for (int i=0; i<switchIndexArr[timeseriesIndex].size(); i++) {
                        switchArr[i] = String.valueOf(switchIndexArr[timeseriesIndex].get(i));
                    }

                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(switchArr);
                    cbTimeseries.setModel(model);

                    // Update label and switch if they are changed
                    previousSwitchLabel[timeseriesIndex] = selectedSwitchLabel[timeseriesIndex];
                    previousSwitch[timeseriesIndex] = selectedSwitch[timeseriesIndex];
                    selectedSwitch[timeseriesIndex] = Integer.parseInt((String) cbTimeseries.getItemAt(cbTimeseries.getSelectedIndex()));
                    dualAxischart.setTimeseriesInChart(selectedSwitch[timeseriesIndex]);
                }

                // For timeseries selection change
                if (selectedSwitch[timeseriesIndex] != previousSwitch[timeseriesIndex]) {
                    dualAxischart.setTimeseriesInChart(selectedSwitch[timeseriesIndex]);
                }

//                System.out.println("selectedSwitchLabel[timeseriesIndex] point 2: " + selectedSwitchLabel[timeseriesIndex]);
//                System.out.println("previousSwitchLabel[timeseriesIndex] point 2: " + previousSwitchLabel[timeseriesIndex]);

            }
        });
    }

    private void setPanel (JPanel panel) {
        this.panel = panel;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JComboBox getCbTimeseriesLabel() {
        return cbTimeseriesLabel;
    }

    public JComboBox getCbTimeseries() {
        return cbTimeseries;
    }

    public static void main(String[] args) {}
}    