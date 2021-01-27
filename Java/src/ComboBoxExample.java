import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ComboBoxExample {
    JFrame f;
    public JPanel panel;
    boolean shapeletInit = false;
    boolean timeseriesInit = false;

    ComboBoxExample(){
        f=new JFrame("ComboBox Example");
        final JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setSize(400,100);
        JButton b=new JButton("Show");
        b.setBounds(200,100,75,20);

        String languages[]={"C","C++","C#","Java","PHP"};
        final JComboBox cb=new JComboBox(languages);

        cb.setBounds(50, 100,90,20);
        f.add(cb); f.add(label); f.add(b);
        f.setLayout(null);
        f.setSize(350,350);
        f.setVisible(true);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = "Programming language Selected: "
                        + cb.getItemAt(cb.getSelectedIndex());
                label.setText(data);
            }
        });
    }

    ComboBoxExample(ArrayList<ArrayList<ArrayList<Double>>> localTimeseries, ArrayList<ArrayList<Double>> localShapelet, ArrayList<Double> localTimeseriesLabelArr, ArrayList<Double> localShapeletLabelArr, DualAxisChart dualAxischart, LineChartExample lineChart, TextAreaExample labelBox){

        final JLabel label = new JLabel();
        final int[] previousSwitchLabel = {-1, -1};
        final int[] previousSwitch = {-1, -1};
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setSize(400,100);

        // Transform to set to remove all duplicated items
        Set<Double> labelSet = new HashSet<Double>(localTimeseriesLabelArr);
        ArrayList<Double> newLabelArr = new ArrayList<>();
        newLabelArr.addAll(labelSet);
        String[] labelArr = new String[newLabelArr.size()];

        for (int i=0; i<newLabelArr.size(); i++) {
            labelArr[i] = String.valueOf((int)((double)newLabelArr.get(i)));
        }

        final JComboBox cbShapeletLabel=new JComboBox(labelArr);
        final JComboBox cbShapelet =new JComboBox();
        final JComboBox cbTimeseriesLabel=new JComboBox(labelArr);
        final JComboBox cbTimeseries =new JComboBox();

        cbShapeletLabel.setPreferredSize(new Dimension(90,20));
        cbShapelet.setPreferredSize(new Dimension(90,20));
        cbTimeseriesLabel.setPreferredSize(new Dimension(90,20));
        cbTimeseries.setPreferredSize(new Dimension(90,20));

        JButton button=new JButton("Show");
//        button.setBounds(cbShapelet.getX()+cbShapelet.getWidth()+10,cbSwitch.getY(),75,20);
        button.setPreferredSize(new Dimension(75,20));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(300,100));
        JPanel panelShapeletAndTimeseries = new JPanel();
        panelShapeletAndTimeseries.setLayout(new BorderLayout());
        panelShapeletAndTimeseries.setPreferredSize(new Dimension(200,200));

        JPanel panelShapelet = new JPanel();
        JPanel panelTimeseries = new JPanel();
        JPanel panelButton = new JPanel();
        panelShapelet.setPreferredSize(new Dimension(200,30));
        panelTimeseries.setPreferredSize(new Dimension(200,30));
        panelButton.setPreferredSize(new Dimension(100,40));
        panelButton.setLayout(new BorderLayout());

//        panel.add(cbSwitch);
        panelShapelet.add(cbShapeletLabel);
        panelShapelet.add(cbShapelet);
        panelTimeseries.add(cbTimeseriesLabel);
        panelTimeseries.add(cbTimeseries);
//        panelButton.add(label, BorderLayout.WEST);
        panelButton.add(button, BorderLayout.CENTER);

        panelShapeletAndTimeseries.add(panelShapelet, BorderLayout.NORTH);
        panelShapeletAndTimeseries.add(panelTimeseries, BorderLayout.SOUTH);
        panel.add(panelShapeletAndTimeseries, BorderLayout.WEST);
        panel.add(panelButton, BorderLayout.CENTER);

        // Set the panel globally
        setPanel(panel);

//        f=new JFrame("ComboBox Example");
//        f.add(cbLabel);
//        f.add(cb);
//        f.add(label);
//        f.add(button);
//        f.setLayout(null);
//        f.setSize(350,350);
//        f.setVisible(true);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // -------- Set timeseries with specific label
                // Switch: timeseries or shapelet
                ArrayList<Double> localSwitchLabelArr;
                ArrayList<Integer>[] switchIndexArr = new ArrayList[2];
                for (int i=0; i<2; i++) {
                    switchIndexArr[i] = new ArrayList<>();
                }

                final int shapeletIndex = 0;
                final int timeseriesIndex = 1;
                // count
                int switchWithLabelCount = 0;
                String[] switchSelection = new String[2];
                int[] selectedSwitchLabel = new int[2];
                int[] selectedSwitch = new int[2];

                if (shapeletInit) {
                    selectedSwitch[shapeletIndex] = Integer.parseInt((String) cbShapelet.getItemAt(cbShapelet.getSelectedIndex()));
                } else {
                    // Default value: 0
                    selectedSwitch[shapeletIndex] = 0;
                    shapeletInit = true;
                }

                if (timeseriesInit) {
                    selectedSwitch[timeseriesIndex] = Integer.parseInt((String) cbTimeseries.getItemAt(cbTimeseries.getSelectedIndex()));
                } else {
                    // Default value: 0
                    selectedSwitch[timeseriesIndex] = 0;
                    timeseriesInit = true;
                }

                selectedSwitchLabel[shapeletIndex] = Integer.parseInt((String) cbShapeletLabel.getItemAt(cbShapeletLabel.getSelectedIndex()));
                selectedSwitchLabel[timeseriesIndex] = Integer.parseInt((String) cbTimeseriesLabel.getItemAt(cbTimeseriesLabel.getSelectedIndex()));

                String data = selectedSwitchLabel[shapeletIndex] + " (label) selected: "
                        + selectedSwitchLabel[shapeletIndex] + ", shapeletSelection: " + selectedSwitch[shapeletIndex];
                label.setText(data);

                // System.out.println("previousSwitch[shapeletIndex]: " + previousSwitch[shapeletIndex] + ", selectedSwitch[shapeletIndex]: " + selectedSwitch[shapeletIndex]);
                // Shapelet
                if ((selectedSwitchLabel[shapeletIndex] != previousSwitchLabel[shapeletIndex])) {
                    //  || (selectedSwitch[shapeletIndex] != previousSwitch[shapeletIndex])

                    for (int i=0; i<localShapeletLabelArr.size(); i++) {
                        double localLabel = localShapeletLabelArr.get(i);
                        if (localLabel==selectedSwitchLabel[shapeletIndex]) {
                            switchIndexArr[shapeletIndex].add(i);
                            switchWithLabelCount++;
                        }
                    }

                    String[] switchArr = new String[switchWithLabelCount];
                    for (int i=0; i<switchIndexArr[shapeletIndex].size(); i++) {
                        switchArr[i] = String.valueOf(switchIndexArr[shapeletIndex].get(i));
                    }

                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(switchArr);
                    cbShapelet.setModel( model );

                    // Update label and switch if they are changed
                    previousSwitchLabel[shapeletIndex] = selectedSwitchLabel[shapeletIndex];
                    selectedSwitch[shapeletIndex] = Integer.parseInt((String) cbShapelet.getItemAt(cbShapelet.getSelectedIndex()));
                    previousSwitch[shapeletIndex] = selectedSwitch[shapeletIndex];
                    lineChart.setShapeletInChart(selectedSwitch[shapeletIndex]);
                }

                // System.out.println("previousSwitch[timeseriesIndex]: " + previousSwitch[timeseriesIndex] + ", selectedSwitch[timeseriesIndex]: " + selectedSwitch[timeseriesIndex]);
                // Timeseries
                if ((selectedSwitchLabel[timeseriesIndex] != previousSwitchLabel[timeseriesIndex])) {
                    // || (selectedSwitch[timeseriesIndex] != previousSwitch[timeseriesIndex])

                    for (int i=0; i<localTimeseriesLabelArr.size(); i++) {
                        double localLabel = localTimeseriesLabelArr.get(i);
                        if (localLabel==selectedSwitchLabel[timeseriesIndex]) {
                            switchIndexArr[timeseriesIndex].add(i);
                            switchWithLabelCount++;
                        }
                    }

                    String[] switchArr = new String[switchWithLabelCount];
                    for (int i=0; i<switchIndexArr[timeseriesIndex].size(); i++) {
                        switchArr[i] = String.valueOf(switchIndexArr[timeseriesIndex].get(i));
                    }

                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(switchArr);
                    cbTimeseries.setModel( model );

                    // Update label and switch if they are changed
                    previousSwitchLabel[timeseriesIndex] = selectedSwitchLabel[timeseriesIndex];
                    selectedSwitch[timeseriesIndex] = Integer.parseInt((String) cbTimeseries.getItemAt(cbTimeseries.getSelectedIndex()));
                    previousSwitch[timeseriesIndex] = selectedSwitch[timeseriesIndex];
                    dualAxischart.setTimeseriesInChart(selectedSwitch[timeseriesIndex]);
                }

                if (selectedSwitch[shapeletIndex] != previousSwitch[shapeletIndex]) {
                    lineChart.setShapeletInChart(selectedSwitch[shapeletIndex]);
                }
                if (selectedSwitch[timeseriesIndex] != previousSwitch[timeseriesIndex]) {
                    dualAxischart.setTimeseriesInChart(selectedSwitch[timeseriesIndex]);
                }

                // Get multi-dimension distance of one timeseries
                // distanceAndStartIndexArr: [startIndexArr, distanceArr]
                int distanceArrIndex = 1;
                ArrayList<ArrayList<Double>> distanceAndStartIndexArr = Dataset.getMultiDimensionDistance(localTimeseries, localShapelet, localTimeseriesLabelArr, localShapeletLabelArr, selectedSwitch[timeseriesIndex], selectedSwitch[shapeletIndex]);
                ArrayList<Double> distanceArr = distanceAndStartIndexArr.get(distanceArrIndex);
                labelBox.setTextArea("Multi-dimension distance of timeseries " + selectedSwitch[timeseriesIndex] + ": " + distanceArr);
//                System.out.println("Multi-dimension distance of timeseries " + selectedSwitch[timeseriesIndex] + ": " + distanceArr);
//                System.out.println("Number of shapelet selected: " + selectedSwitch[shapeletIndex] + " with label: " + selectedSwitchLabel[shapeletIndex] + "\nNumber of timeseris selected: " + selectedSwitch[timeseriesIndex] + " with label: " + selectedSwitchLabel[timeseriesIndex]);
            }
        });
    }

    private void setPanel (JPanel panel) {
        this.panel = panel;
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        new ComboBoxExample();
    }
}    