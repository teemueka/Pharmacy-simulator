package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import simu.dao.SimulationDao;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    @FXML
    public BarChart<String, Double> historyChart;
    @FXML
    private Button optimalButton;
    @FXML
    private Button averageButton;

    SimulationDao simulationDao = new SimulationDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Satisfaction %");

        List<Double> satisfaction = simulationDao.getSatisfaction();
        int id = 1;
        for (Double s : satisfaction) {
            XYChart.Data<String, Double> data = new XYChart.Data<>(String.valueOf(id), s);
            series.getData().add(data);
            id++;
        }

        historyChart.getData().add(series);

        //tooltips for bars
        for (XYChart.Series<String, Double> s : historyChart.getData()) {
            for (XYChart.Data<String, Double> d : s.getData()) {
                d.getNode().setOnMouseEntered(event -> {
                    Tooltip tooltip = new Tooltip("Satisfaction: " + d.getYValue().toString());
                    tooltip.setShowDelay(javafx.util.Duration.millis(100));
                    Tooltip.install(d.getNode(), tooltip);
                });
                d.getNode().setOnMouseExited(event -> {
                    Tooltip.uninstall(d.getNode(), null);
                });
            }
        }
    }
}
