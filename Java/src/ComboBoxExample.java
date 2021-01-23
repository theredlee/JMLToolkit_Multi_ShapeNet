import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ComboBoxExample {
    JFrame f;
    final String[] switchArr = {"Shapelet", "Timeseries"};
    public JPanel panel;

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

    ComboBoxExample(ArrayList<Double> localTimeseriesLabelArr, ArrayList<Double> localShapeletLabelArr, DualAxisChart dualAxischart, LineChartExample lineChart){

        final JLabel label = new JLabel();
        final int[] previousLabel = {-1};
        final String[] previousSwitch = {"None"};
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

        final JComboBox cbSwitch=new JComboBox(switchArr);
        final JComboBox cbLabel=new JComboBox(labelArr);
        final JComboBox cb =new JComboBox();

        cbSwitch.setBounds(50, 100,90,20);
        cbLabel.setBounds(cbSwitch.getX()+cbSwitch.getWidth()+10, cbSwitch.getY(),90,20);
        cb.setBounds(cbLabel.getX()+cbLabel.getWidth()+10, cbSwitch.getY(),90,20);

        JButton b=new JButton("Show");
        b.setBounds(cb.getX()+cb.getWidth()+10,cbSwitch.getY(),75,20);

//        f=new JFrame("ComboBox Example");
//        f.add(cbLabel);
//        f.add(cb);
//        f.add(label);
//        f.add(b);
//        f.setLayout(null);
//        f.setSize(350,350);
//        f.setVisible(true);

        JPanel panel = new JPanel();
        panel.add(cbSwitch);
        panel.add(cbLabel);
        panel.add(cb);
        panel.add(label);
        panel.add(b);
        panel.setPreferredSize(new Dimension(500,350));
        // Set the panel globally
        setPanel(panel);

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // -------- Set timeseries with specific label
                // Switch: timeseries or shapelet
                ArrayList<Double> localSwitchLabelArr;
                ArrayList<Integer> switchIndexArr = new ArrayList<>();
                String switchSelection = (String) cbSwitch.getItemAt(cbSwitch.getSelectedIndex());
                int switchWithLabelCount = 0;
                int selectedLabel = Integer.parseInt((String) cbLabel.getItemAt(cbLabel.getSelectedIndex()));
                int selectedSwitch = 0;
                int selectedShapelet = 0;

                String data = switchSelection + " (label) selected: "
                        + selectedLabel;
                label.setText(data);

                if (switchSelection == switchArr[0]) {
                    localSwitchLabelArr = localShapeletLabelArr;
                }else {
                    localSwitchLabelArr = localTimeseriesLabelArr;
                }

                if ((selectedLabel != previousLabel[0]) || (switchSelection != previousSwitch[0])) {
                    // Update label and switch if they are changed
                    previousLabel[0] = selectedLabel;
                    previousSwitch[0] = switchSelection;

                    for (int i=0; i<localSwitchLabelArr.size(); i++) {
                        double localLabel = localSwitchLabelArr.get(i);
                        if (localLabel==selectedLabel) {
                            switchIndexArr.add(i);
                            switchWithLabelCount++;
                        }
                    }

                    String[] switchArr = new String[switchWithLabelCount];
                    for (int i=0; i<switchIndexArr.size(); i++) {
                        switchArr[i] = String.valueOf(switchIndexArr.get(i));
                    }

                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(switchArr);
                    cb.setModel( model );
                }

                // -------- Set timeseries
                // Get the selectedSwitch after assignment
                if (cb.getItemAt(cb.getSelectedIndex()) != null) {
                    selectedSwitch = Integer.parseInt((String) cb.getItemAt(cb.getSelectedIndex()));
                }

                if (switchSelection == switchArr[0]) {
                    lineChart.setShapeletInChart(selectedSwitch);
                }else {
                    dualAxischart.setTimeseriesInChart(selectedSwitch);
                }

                System.out.println("Number of timeseris/shapelet selected: " + selectedSwitch + " with label: " + selectedLabel);
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