import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LabeBoxExample extends Frame{
    public JPanel panel;
    JLabel label;

    LabeBoxExample() {
//        tf.setBounds(50, 50, 150, 20);
        label = new JLabel("Distance");
        label.setPreferredSize(new Dimension(300, 200));

        JPanel panel = new JPanel();
        panel.add(label);
        panel.setPreferredSize(new Dimension(400, 200));
        setPanel(panel);
//        add(b);
//        add(tf);
//        add(l);
//        setSize(400,400);
//        setLayout(null);
//        setVisible(true);
    }

    private void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setLabel(String str) {
        label.setText("<html>"+ str +"</html>");
    }

    public static void main(String[] args) {
        new LabeBoxExample();
    }
}