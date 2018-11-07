import javax.swing.*;

/**
 * @author Tor Gammelgard
 * @version 2015-11-23
 */
public class App {

    public static void main(String[] args) {
        PieChartModel model = new PieChartModel();
        SwingUtilities.invokeLater(() -> {
            PieChartView view = new PieChartView();
            view.setVisible(true);
            PieChartController controller = new PieChartController(model, view);
        });

    }
}
