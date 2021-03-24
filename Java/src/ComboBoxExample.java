import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ComboBoxExample  extends ApplicationFrame {

    private Controller controller;
    public JPanel panel;
    boolean shapeletInit = false;
    boolean timeseriesInitiated = false;

    final int[] previousSwitchLabel = {-1, -1};
    final int[] previousSwitch = {-1, -1};

    final JComboBox cbTimeseriesLabel;
    final JComboBox cbTimeseries;

    ComboBoxExample(ArrayList<ArrayList<ArrayList<Double>>> localTimeseries, ArrayList<ArrayList<Double>> localShapelet, ArrayList<Double> localTimeseriesLabelArr, ArrayList<Double> localShapeletDimArr, TextAreaExample labelBox){
        super("ComboBox");

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

        int margin10 = 10;
        int margin20 = 25;
        cbTimeseriesLabel.setPreferredSize(new Dimension(90,30));
        cbTimeseriesLabel.setBorder(BorderFactory.createEmptyBorder(margin20, margin10, margin20, margin10));
        cbTimeseries.setPreferredSize(new Dimension(90,30));
        cbTimeseries.setBorder(BorderFactory.createEmptyBorder(margin20, margin10, margin20, margin10));

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
        panelTimeseries.setPreferredSize(new Dimension(250,50));
        panelTimeseries.setLayout(new GridLayout(3,1) );
        panelButton.setPreferredSize(new Dimension(75,40));
        panelButton.setLayout(new BorderLayout());

        JLabel jlabel_timeseries_class = new JLabel("Timeseries class: ");
        jlabel_timeseries_class.setVerticalAlignment(JLabel.TOP);
        jlabel_timeseries_class.setSize(100,50);

        panelTimeseries.add(cbTimeseriesLabel);
        panelTimeseries.add(cbTimeseries);

        JButton button_clear=new JButton("Clear");
        button_clear.setPreferredSize(new Dimension(75,20));
        button_clear.setMargin(new Insets(margin20, margin10, margin20, margin10));
        panelTimeseries.add(button_clear);

        panelButton.add(button, BorderLayout.CENTER);

        panelShapeletAndTimeseries.add(jlabel_timeseries_class, BorderLayout.NORTH);
        panelShapeletAndTimeseries.add(panelTimeseries, BorderLayout.CENTER);
        panel.add(panelShapeletAndTimeseries, BorderLayout.WEST);
        panel.add(panelButton, BorderLayout.EAST);

        // Set the panel globally
        setPanel(panel);


        button_clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cleanBtnAction();
            }});

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateComboBox(localTimeseriesLabelArr);
            }
        });
    }

    public void updateComboBox(ArrayList<Double> localTimeseriesLabelArr) {
        DualAxisChart_1 dualAxischart = controller.getDualAxisChart_1();
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

    private void cleanBtnAction() {
        DualAxisChart_3 dualAxisChart_3 = controller.getDualAxisChart_3();
        dualAxisChart_3.cleanTimeseries();
    }

    private void setPanel (JPanel panel) {
        this.panel = panel;
    }

    public void setController(Controller controller) {
        this.controller = controller;
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