import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ComboBoxExample {
    JFrame f;
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

    ComboBoxExample(ArrayList<ArrayList<ArrayList<Double>>> localTimeseries, ArrayList<Double> localLabelArr, DualAxisChart chart){

        final JLabel label = new JLabel();
        final int[] lastLabel = {-1};
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setSize(400,100);

        // Transform to set to remove all duplicated items
        Set<Double> labelSet = new HashSet<Double>(localLabelArr);
        ArrayList<Double> newLabelArr = new ArrayList<>();
        newLabelArr.addAll(labelSet);
        String[] labelArr = new String[newLabelArr.size()];

        for (int i=0; i<newLabelArr.size(); i++) {
            labelArr[i] = String.valueOf((int)((double)newLabelArr.get(i)));
        }

        final JComboBox cbLabel=new JComboBox(labelArr);
        final JComboBox cbTimeseries =new JComboBox();

        cbLabel.setBounds(50, 100,90,20);
        cbTimeseries.setBounds(cbLabel.getX()+cbLabel.getWidth()+10, 100,90,20);

        JButton b=new JButton("Show");
        b.setBounds(cbTimeseries.getX()+cbTimeseries.getWidth()+10,100,75,20);

//        f=new JFrame("ComboBox Example");
//        f.add(cbLabel);
//        f.add(cbTimeseries);
//        f.add(label);
//        f.add(b);
//        f.setLayout(null);
//        f.setSize(350,350);
//        f.setVisible(true);

        JPanel panel = new JPanel();
        panel.add(cbLabel);
        panel.add(cbTimeseries);
        panel.add(label);
        panel.add(b);
        panel.setPreferredSize(new Dimension(500,350));
        // Set the panel globally
        setPanel(panel);

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // -------- Set timeseries with specific label
                ArrayList<Integer> timeseriesIndexArr = new ArrayList<>();
                int timeserisWithLabelCount = 0;
                int selectedLabel = Integer.parseInt((String) cbLabel.getItemAt(cbLabel.getSelectedIndex()));
                int selectedTimeseries = 0;

                String data = "Programming language (label) Selected: "
                        + selectedLabel;
                label.setText(data);

                if (selectedLabel != lastLabel[0]) {
                    // Update the timeseries if label is changed
                    lastLabel[0] = selectedLabel;

                    for (int i=0; i<localLabelArr.size(); i++) {
                        double localLabel = localLabelArr.get(i);
                        if (localLabel==selectedLabel) {
                            timeseriesIndexArr.add(i);
                            timeserisWithLabelCount++;
                        }
                    }

                    String[] timeseriesArr = new String[timeserisWithLabelCount];
                    for (int i=0; i<timeseriesIndexArr.size(); i++) {
                        timeseriesArr[i] = String.valueOf(timeseriesIndexArr.get(i));
                    }

                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>( timeseriesArr );
                    cbTimeseries.setModel( model );
                }

                // -------- Set timeseries
                // Get the selectedTimeseries after assignment
                if (cbTimeseries.getItemAt(cbTimeseries.getSelectedIndex()) != null) {
                    selectedTimeseries = Integer.parseInt((String) cbTimeseries.getItemAt(cbTimeseries.getSelectedIndex()));
                }

                chart.setTimeseriesInChart(selectedTimeseries);

                System.out.println("Number of timeseris selected: " + selectedTimeseries + " with label: " + selectedLabel);
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