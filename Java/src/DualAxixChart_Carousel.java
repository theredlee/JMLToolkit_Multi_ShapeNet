import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class DualAxixChart_Carousel extends JPanel {
    //Set up animation parameters.
    static final int FPS_MIN = 0;
    static final int FPS_MAX = 10;
    static final int FPS_INIT = 5;    //initial frames per second
    int NUM_FRAMES = 2;
    ImageIcon[] images = new ImageIcon[NUM_FRAMES];
    JScrollPane[] scrollPanes = new JScrollPane[NUM_FRAMES];
    JScrollPane scrollPane = new JScrollPane();
    int delay;
    Timer timer;
    boolean frozen = false;

    //This label uses ImageIcon to show the doggy pictures.
    JPanel panel;
    DualAxisChart dualAxisChart;
    DualAxisChart_3 dualAxisChart_3;
    final int width = 550*2;
    final int height = 550;
    Dataset aDataset;

    public DualAxixChart_Carousel(Dataset aDataset) throws IOException {
        setDataset(aDataset);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(width/2+50, height+150));

        delay = 1000 / FPS_INIT;

        //Create the label.
        JLabel sliderLabel = new JLabel("Frames Per Second", JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //----------------------------------------------------------------------------
        // -------------------
        panel = new JPanel();
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(10,10,10,10)));

        //----------------------

        // Initialize two charts
        init_charts();
        //----------------------------------------------------------------------------

        //Put everything together.
        add(scrollPane);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    }

    private void setDataset(Dataset aDataset) {
        this.aDataset = aDataset;
    }

    private void init_charts() throws IOException {
        dualAxisChart = new DualAxisChart(aDataset.getGlobalTimeseries(), aDataset.getGlobalShapelet(), aDataset.getGlobalShapeletDimArr());
        dualAxisChart_3 = new DualAxisChart_3(aDataset.getGlobalTimeseries(), aDataset.getGlobalShapelet(), aDataset.getGlobalShapeletDimArr());

        JScrollPane aDualAxisScrollPane = dualAxisChart.getScrollPane();
        scrollPanes[0] = aDualAxisScrollPane;

        JScrollPane aDualAxisScrollPane_2 = dualAxisChart_3.getScrollPane();
        scrollPanes[1] = aDualAxisScrollPane_2;

        panel.setLayout(new GridLayout(1,2));
        panel.setPreferredSize(new Dimension(width, height));
        panel.add(scrollPanes[0]);
        panel.add(scrollPanes[1]);

        scrollPane.add(panel);
        scrollPane.setViewportView(panel);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    }

    public void windowOpened(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = DualAxixChart_Carousel.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public static void datasetSetting(Dataset aDataset) throws IOException {
        aDataset.loadShapelet();
        aDataset.loadTimeseries();
        aDataset.loadCoef();
        aDataset.loadIntercept();
        aDataset.loadFeatures();
        aDataset.multiplication_PN_TF();
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() throws IOException {
        //Create and set up the window.
        JFrame frame = new JFrame("DualAxixChart_Carousel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dataset aDataset = new Dataset(true);
        datasetSetting(aDataset);
        DualAxixChart_Carousel animator = new DualAxixChart_Carousel(aDataset);

        //Add content to the window.
        frame.add(animator, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public DualAxisChart getDualAxisChart() {
        return this.dualAxisChart;
    }

    public DualAxisChart_3 getDualAxisChart_3() {
        return this.dualAxisChart_3;
    }

    public static void main(String[] args) {
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);


        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
