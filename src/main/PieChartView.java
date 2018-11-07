package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * @author Tor Gammelgard
 * @version 2015-11-23
 */
public class PieChartView extends JFrame {

    private Color[] colors = new Color[]{Color.BLUE, Color.RED, Color.CYAN, Color.YELLOW, Color.MAGENTA};

    private PieChartPanel pieChartPanel;
    private JList<Integer> numbersList;
    private JTextField numberTextField;
    private JButton addButton;
    private JButton resetButton;
    private DefaultListModel<Integer> data;

    /** Stores the angle values of the pie slices. <it>[slice1_startAngle, slice1_angle, ... ]</it> */
    private int[] angleArray;

    Font font = new Font("Sans-serif", Font.BOLD, 24);

    public PieChartView() {
        angleArray = new int[0];

        addButton = new JButton("Add");
        addButton.setFont(font);
        resetButton = new JButton("Reset");
        resetButton.setFont(font);
        numberTextField = new JTextField(10);
        numberTextField.setFont(font);

        JPanel inputPanel = new JPanel();
        inputPanel.add(numberTextField);
        inputPanel.add(addButton);
        inputPanel.add(resetButton);

        pieChartPanel = new PieChartPanel(400, 400);
        pieChartPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 4, 3, 4, true));

        data = new DefaultListModel<>();
        numbersList = new JList<>(data);
        numbersList.setFont(font);
        //DefaultListCellRenderer renderer = (DefaultListCellRenderer) numbersList.getCellRenderer(); // thx stackoverflow
        //renderer.setHorizontalAlignment(SwingConstants.RIGHT);
        numbersList.setCellRenderer(new CustomCellRenderer());

        add(new JScrollPane(numbersList), BorderLayout.WEST);
        add(pieChartPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        setTitle("Pie chart creator");
        setResizable(false);
        setIconImage(createAppIcon());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private BufferedImage createAppIcon(){
        BufferedImage bimg = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bimg.createGraphics();
        g2.setPaint(Color.BLUE);
        g2.fillArc(0, 0, 40, 40, 0, 225);
        g2.setPaint(Color.RED);
        g2.fillArc(0, 0, 40, 40, 225, 135);
        g2.dispose();
        return bimg;
    }
    public void setAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void setResetButtonListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }

    public int getNumber() throws NumberFormatException {
        return Integer.parseInt(numberTextField.getText());
    }

    public void setData(int[] data) {
        this.data.clear();
        for (Integer i : data)
            this.data.addElement(i);
    }

    public void clearNumberTextField() {
        numberTextField.setText("");
    }

    public void updatePieChart(int[] angleArray) {
        this.angleArray = angleArray;
        pieChartPanel.repaint();
    }

    private class PieChartPanel extends JPanel {

        PieChartPanel(int w, int h) {
            setMinimumSize(new Dimension(w, h));
            setPreferredSize(new Dimension(w, h));
            setMaximumSize(new Dimension(w, h));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            if (angleArray.length < 2)
                return;
            for (int i = 0; i < angleArray.length - 1; i += 2) {
                g2.setPaint(colors[i / 2 % colors.length]);
                g2.fillArc(0, 0, getWidth(), getHeight(), angleArray[i], angleArray[i + 1]);
            }
        }
    }

    private class CustomCellRenderer extends JLabel implements ListCellRenderer<Object> {
        private static final int WIDTH = 50;
        private static final int HEIGHT = 50;

        CustomCellRenderer(){
            setOpaque(true);
            setFont(new Font("Serif", Font.BOLD, 24));
            setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
        }

        private BufferedImage createIcon(int index) {
            BufferedImage bimg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
            // check for array index out of bounds
            if (2 * index + 1 > angleArray.length - 1)
                return bimg;
            Graphics2D g2 = bimg.createGraphics();
            g2.setPaint(colors[index % colors.length]);
            g2.fillArc(0, 0, WIDTH, HEIGHT, angleArray[2 * index], angleArray[2 * index + 1]);
            g2.dispose();
            return bimg;
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.toString());
            if (isSelected) {
                setBackground(Color.DARK_GRAY);
            } else {
                setBackground(Color.LIGHT_GRAY);
            }
            this.setIcon(new ImageIcon(createIcon(index)));
            return this;
        }
    }
}
