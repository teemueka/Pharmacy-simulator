package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import simu.dao.SimulationDao;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * HistoryController is responsible for initializing barchart and event handling for tooltips
 */

public class HistoryController implements Initializable {
    @FXML
    public BarChart<String, Double> historyChart;

    SimulationDao simulationDao = new SimulationDao();

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */


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
                    String barData = simulationDao.getBarData(Integer.parseInt(d.getXValue()))
                            .stream()
                            .map(line -> line + "\n")
                            .collect(Collectors.joining());

                    Tooltip tooltip = new Tooltip(barData);
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
