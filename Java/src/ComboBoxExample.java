import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ComboBoxExample {
    JFrame f;
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
        f=new JFrame("ComboBox Example");
        final JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setSize(400,100);

        String languages[]={"C","C++","C#","Java","PHP"};

        String[] selectionArr = new String[localTimeseries.size()];

        for (int i=0; i<localTimeseries.size(); i++) {
            selectionArr[i] = String.valueOf(i);
        }

        // Transform to set to remove all duplicated items
        Set<Double> labelSet = new HashSet<Double>(localLabelArr);
        ArrayList<Double> newLabelArr = new ArrayList<>();
        newLabelArr.addAll(labelSet);
        String[] labelArr = new String[newLabelArr.size()];

        for (int i=0; i<newLabelArr.size(); i++) {
            labelArr[i] = String.valueOf((int)((double)newLabelArr.get(i)));
        }

        final JComboBox cbLabel=new JComboBox(labelArr);
        final JComboBox cbTimeseries =new JComboBox(selectionArr);

        cbLabel.setBounds(50, 100,90,20);
        cbTimeseries.setBounds(cbLabel.getX()+cbLabel.getWidth()+10, 100,90,20);

        JButton b=new JButton("Show");
        b.setBounds(cbTimeseries.getX()+cbTimeseries.getWidth()+10,100,75,20);

        f.add(cbLabel);
        f.add(cbTimeseries);
        f.add(label);
        f.add(b);
        f.setLayout(null);
        f.setSize(350,350);
        f.setVisible(true);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = "Programming language Selected: "
                        + cbTimeseries.getItemAt(cbTimeseries.getSelectedIndex());
                label.setText(data);

                // --------
                chart.setTimeseriesInChart(0);
            }
        });
    }

    public static void main(String[] args) {
        new ComboBoxExample();
    }
}    