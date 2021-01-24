import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LabeBoxExample extends Frame implements ActionListener {
    public JPanel panel;
    JLabel label;

    LabeBoxExample() {
//        tf.setBounds(50, 50, 150, 20);
        label = new JLabel("Distance: ");
        label.setPreferredSize(new Dimension(250, 50));

        JPanel panel = new JPanel();
        panel.add(label);
        panel.setPreferredSize(new Dimension(400, 400));
        setPanel(panel);
//        add(b);
//        add(tf);
//        add(l);
//        setSize(400,400);
//        setLayout(null);
//        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
//        try {
//            String host = tf.getText();
//            String ip = java.net.InetAddress.getByName(host).getHostAddress();
//            l.setText("IP of " + host + " is: " + ip);
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
    }

    private void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        new LabeBoxExample();
    }
}