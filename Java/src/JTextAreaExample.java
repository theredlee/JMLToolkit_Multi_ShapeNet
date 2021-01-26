import javax.swing.*;
import java.awt.*;

public class JTextAreaExample extends Frame{
    public JPanel panel;
    public JScrollPane scrollPane;
    private JTextArea textArea;

    JTextAreaExample() {
//        tf.setBounds(50, 50, 150, 20);
        JTextArea textArea = new JTextArea("Welcome to Shapenet");
        textArea.setPreferredSize(new Dimension(350, 200));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        setTextArea(textArea);

//        JPanel panel = new JPanel();
//        panel.add(textArea);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 200));
        setScrollPane(scrollPane);
//        setPanel(panel);
//        add(b);
//        add(tf);
//        add(l);
//        setSize(400,400);
//        setLayout(null);
//        setVisible(true);
    }

    private void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    private void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JPanel getPanel() {
        return panel;
    }

    private void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setTextArea(String str) {
        textArea.setText(str);
    }

    public static void main(String[] args) {
        new JTextAreaExample();
    }
}