import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.event.*;
import java.security.PublicKey;
import java.util.ArrayList;

public class TableExample {
    JScrollPane scrollPane;

    public TableExample(ArrayList<ArrayList<Double>> valPosAndNegArr, ArrayList<ArrayList<Double>> valTPandTPArrArr, int count) {
        final int positiveOrTrueIndex = 0;
        final int negativeOrFalseindex = 1;

        int posCount = valPosAndNegArr.get(positiveOrTrueIndex).size();
        int negCount = valPosAndNegArr.get(negativeOrFalseindex).size();
        int turePositiveCount = valTPandTPArrArr.get(positiveOrTrueIndex).size();
        int trueNegativeCount = valTPandTPArrArr.get(negativeOrFalseindex).size();
        int falsePositiveCount = posCount - turePositiveCount;
        int falseNegativeCount = negCount - trueNegativeCount;
        int otherCount = count - (turePositiveCount+trueNegativeCount);

        System.out.println("posCount: " + posCount + ", negCount: " + negCount + ", turePositiveCount: " + turePositiveCount + ", trueNegativeCount: " + trueNegativeCount + ", otherCount: " + otherCount);

        // ->->->->->->->->->->->->->->->->->->->->->->

        JFrame f = new JFrame("Confusion Matrix");
        String data[][]=
                {
                    {"Actual 0",String.valueOf(turePositiveCount), String.valueOf(falsePositiveCount), String.valueOf(posCount)},
                    {"Actual 1",String.valueOf(falseNegativeCount),String.valueOf(trueNegativeCount), String.valueOf(negCount)},
                    {"   ", String.valueOf(turePositiveCount+falseNegativeCount), String.valueOf(falsePositiveCount+trueNegativeCount), String.valueOf(posCount+negCount)}
                };
        String column[]={"Label","Predicted 0","Predicted 1", "   "};
        final JTable jt=new JTable(data,column);
        jt.setCellSelectionEnabled(true);
        ListSelectionModel select= jt.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        select.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                String Data = null;
                int[] row = jt.getSelectedRows();
                int[] columns = jt.getSelectedColumns();
                for (int i = 0; i < row.length; i++) {
                    for (int j = 0; j < columns.length; j++) {
                        Data = (String) jt.getValueAt(row[i], columns[j]);
                    } }
                System.out.println("Table element selected is: " + Data);
            }
        });
        scrollPane = new JScrollPane(jt);
        scrollPane.setViewportView(jt);
//        f.add(scrollPane);
//        f.setSize(300, 200);
//        f.setVisible(true);
    }

    public JScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public static void main(String[] a) {
    }
}