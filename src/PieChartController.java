import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Tor Gammelgard
 * @version 2015-11-23
 */
public class PieChartController {

    PieChartModel model;
    PieChartView view;

    public PieChartController(PieChartModel model, PieChartView view) {
        this.model = model;
        this.view = view;

        view.setAddButtonListener(new AddButtonListener());
        view.setResetButtonListener(new ResetButtonListener());
    }

    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // manipulate the model
            try {
                int number = view.getNumber();
                if (number <= 0) {
                    JOptionPane.showMessageDialog(null, "Enter a positive number.", "Input error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                model.addNumber(number);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Enter a number.", "Input error", JOptionPane.ERROR_MESSAGE);
            }

            // update the view
            view.setData(model.getData());
            view.updatePieChart(model.getAngleArray());
            view.clearNumberTextField();
        }
    }

    class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.reset();
            view.setData(model.getData());
            view.updatePieChart(model.getAngleArray());
        }
    }

}
