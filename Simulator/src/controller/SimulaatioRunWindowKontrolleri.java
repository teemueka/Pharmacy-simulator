package controller;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

public class SimulaatioRunWindowKontrolleri implements IKontrolleriForM {
    private RotateTransition rotate;
    @FXML
    private Sphere leftTopShape;

    @FXML
    private Label menetettyAmount;

    @FXML
    private Label palveltuAmount;

    @FXML
    private Label rahatAmount;

    @FXML
    private Sphere rightTopShape;

    @FXML
    private Label tyytyvProsentti;


    @Override
    public void naytaLoppuaika(double aika) {

    }

    @Override
    public void naytaPalveltu(int servedCustomers) {

    }

    @Override
    public void naytaMenetetty(int missedCustomers) {

    }

    @Override
    public void visualisoiMenetettyAsiakas() {

    }

    @Override
    public void visualisoiUusiAsiakas() {

    }

    @Override
    public void updateTyytyvaisyys(double v) {

    }

    @FXML
    public void rotateKuviot() {
        rotate = new RotateTransition();
        rotate.setNode(leftTopShape);
        rotate.setDuration(Duration.millis(2000));
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setByAngle(360);
        rotate.play();
    }

}
