package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import simu.dao.SimulationDao;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    @FXML
    public BarChart<String, Double> historyChart;
    @FXML
    private Button fetchButton;
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
            series.getData().add(new XYChart.Data<>(String.valueOf(id), s));
            id++;
        }

        historyChart.getData().add(series);

    }
}
