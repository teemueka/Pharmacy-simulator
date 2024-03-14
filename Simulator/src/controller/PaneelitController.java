package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Ellipse;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.stage.Stage;
import simu.framework.IMoottori;
import simu.framework.Kello;
import simu.model.Asiakas;
import simu.model.OmaMoottori;
import simu.view.IVisualisointi;
import simu.view.MainApp;

import java.io.IOException;


public class PaneelitController implements IKontrolleriForV, IKontrolleriForM {


	@FXML
	private Canvas visu;    // Käyttöliittymäkomponentti
	private IVisualisointi visualisointi = null; // Työjuhta

	@FXML
	private Label averageTimeLabel;

	@FXML
	private Label checkoutUsageLabel;

	@FXML
	private Label checkoutUtilisationLabel;

	@FXML
	private Label dissatisfiedCustomersLabel;

	@FXML
	private Label infoUsageLabel;

	@FXML
	private Label infoUtilisationLabel;

	@FXML
	private Label missedCustomersLabel;

	@FXML
	private Label prescriptionUsageLabel;

	@FXML
	private Label prescriptionUtilisationLabel;

	@FXML
	private Label satisfiedCustomersLabel;

	@FXML
	private Label servedCustomersLabel;

	@FXML
	private Label shelvesUsageLabel;

	@FXML
	private Label shelvesUtilisationLabel;

	@FXML
	private Label simulationTimeLabel;

	@FXML
	private Button startButton;
	@FXML
	private Button simuloiButton;

	@FXML
	private Spinner<Integer> a_staff;

	@FXML
	private Spinner<Integer> h_staff;

	@FXML
	private Spinner<Integer> r_staff;

	@FXML
	private Spinner<Integer> k_staff;

	@FXML
	private TextField timeText;

	@FXML
	private TextField delayText;
	@FXML
	private TextField customerntencityText;

	@FXML
	private TextField storeCpacity;

	@FXML
	private Line aulaJono;

	@FXML
	private ImageView aulaKuva;
	@FXML
	private Line hyllytJono;

	@FXML
	private ImageView hyllytKuva;

	@FXML
	private Line infoJono;

	@FXML
	private ImageView infoKuva;
	@FXML
	private Line kassaJono;

	@FXML
	private ImageView kassaKuva;

	@FXML
	private Ellipse naamaPallo;

	@FXML
	private ImageView reseptiKuva;

	@FXML
	private Line reseptiJono;
	@FXML
	private QuadCurve suuJokaLiikkuu;

	@FXML
	private Label tyytyvProsLuku;


	MainApp mainApp;
	IMoottori moottori;


	public void handleStart() {

		int a = (int) getA_staff();
		int h = (int) getH_staff();
		int r = (int) getR_staff();
		int k = (int) getK_staff();
		System.out.println("Start");


		moottori = new OmaMoottori(this, a, h, r, k, Integer.parseInt(customerntencityText.getText()), Integer.parseInt(storeCpacity.getText())); // luodaan uusi moottorisäie jokaista simulointia varten
		moottori.setSimulointiaika(Double.parseDouble(timeText.getText()));
		moottori.setViive(Long.parseLong(delayText.getText()));
		((Thread) moottori).start();

		startButton.setDisable(true);
		simuloiButton.setText("Stop");
		simuloiButton.setDisable(false);

	}

	int loop = 0;
	public void handleSimuloi() {

		if (loop == 0){
			simuloiButton.setText("Reset");
			moottori.terminate();

			loop = 1;
		}else{
			simu.framework.Kello.getInstance().reset();
			simu.model.Asiakas.reset();
			tyytyvProsLuku.setText("%");
			suuJokaLiikkuu.setControlY(28);
			naamaPallo.setFill(javafx.scene.paint.Color.rgb(52, 201,11 ));

			updateUI(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0);
			loop = 0;


			startButton.setDisable(false);

			simuloiButton.setDisable(true);

		}

	}


	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.a_staff.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));
		this.h_staff.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));
		this.r_staff.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));
		this.k_staff.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));

		timeText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*(\\d*)?")) {
					timeText.setText(oldValue);
				}
			}
		});
		delayText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*(\\d*)?")) {
					delayText.setText(oldValue);
				}
			}
		});

		customerntencityText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*(\\d*)?")) {
					customerntencityText.setText(oldValue);
				}
			}
		});
		storeCpacity.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*(\\d*)?")) {
					storeCpacity.setText(oldValue);
				}
			}
		});


	}

	public void simulationDone() {
		simuloiButton.setDisable(false);

	}

	@Override
	public void updateUI(int aspaTyontekijat, int hyllyTyontekijat, int reseptiTyontekijat, int kassaTyontekijat, int servedCustomers, int missedCustomers, int aspaUsage, int kauppaUsage, int reseptiUsage, int kassaUsage, int satisfiedCustomers, int dissatisfiedCustomers, double overallSatisfaction, double aspaUtilization, double kauppaUtilization, double reseptiUtilization, double kassaUtilization, double keskiarvo) {
		Platform.runLater(new Runnable() {
			public void run() {

				setSimulationTimeLabel(String.format(("%.1f"), Kello.getInstance().getAika()).replaceAll(",","."));
				setAverageTimeLabel(String.format(("%.1f"), keskiarvo).replaceAll(",", "."));
				setServedCustomersLabel(String.valueOf(servedCustomers));
				setMissedCustomersLabel(String.valueOf(missedCustomers));
				setSatisfiedCustomersLabel(String.valueOf(satisfiedCustomers));
				setDissatisfiedCustomersLabel(String.valueOf(dissatisfiedCustomers));
				setInfoUsageLabel(String.valueOf(aspaUsage));
				setInfoUtilisationLabel(String.valueOf(aspaUtilization));
				setPrescriptionUsageLabel(String.valueOf(kauppaUsage));
				setPrescriptionUtilisationLabel(String.valueOf(kauppaUtilization));
				setShelvesUsageLabel(String.valueOf(reseptiUsage));
				setShelvesUtilisationLabel(String.valueOf(reseptiUtilization));
				setCheckoutUsageLabel(String.valueOf(kassaUsage));
				setCheckoutUtilisationLabel(String.valueOf(kassaUtilization));
			}
		});
	}


	public int getA_staff() {
		return (int) a_staff.getValue();
	}


	public int getH_staff() {
		return (int) h_staff.getValue();
	}


	public int getR_staff() {
		return (int) r_staff.getValue();
	}


	public int getK_staff() {
		return (int) k_staff.getValue();
	}


	public void naytaLoppuaika(double aika) {

	}

	@Override
	public void naytaPalveltu(int servedCustomers) {

	}

	@Override
	public void naytaMenetetty(int missedCustomers) {

	}

	@Override
	public void naytaTyytyvaisyys(double satisfaction) {

	}

	@Override
	public void naytaAverage(double average) {

	}

	public void setSimulationTimeLabel(String simulationTimeLabel) {
		this.simulationTimeLabel.setText(simulationTimeLabel);
	}

	public void setAverageTimeLabel(String averageTimeLabel) {
		this.averageTimeLabel.setText(averageTimeLabel);
	}

	public void setServedCustomersLabel(String servedCustomersLabel) {
		this.servedCustomersLabel.setText(servedCustomersLabel);
	}

	public void setMissedCustomersLabel(String missedCustomersLabel) {
		this.missedCustomersLabel.setText(missedCustomersLabel);
	}

	public void setSatisfiedCustomersLabel(String satisfiedCustomersLabel) {
		this.satisfiedCustomersLabel.setText(satisfiedCustomersLabel);
	}

	public void setDissatisfiedCustomersLabel(String dissatisfiedCustomersLabel) {
		this.dissatisfiedCustomersLabel.setText(dissatisfiedCustomersLabel);
	}

	public void setInfoUsageLabel(String infoUsageLabel) {
		this.infoUsageLabel.setText(infoUsageLabel);
	}

	public void setInfoUtilisationLabel(String infoUtilisationLabel) {
		this.infoUtilisationLabel.setText(infoUtilisationLabel);
	}

	public void setPrescriptionUsageLabel(String prescriptionUsageLabel) {
		this.prescriptionUsageLabel.setText(prescriptionUsageLabel);
	}

	public void setPrescriptionUtilisationLabel(String prescriptionUtilisationLabel) {
		this.prescriptionUtilisationLabel.setText(prescriptionUtilisationLabel);
	}

	public void setShelvesUsageLabel(String shelvesUsageLabel) {
		this.shelvesUsageLabel.setText(shelvesUsageLabel);
	}

	public void setShelvesUtilisationLabel(String shelvesUtilisationLabel) {
		this.shelvesUtilisationLabel.setText(shelvesUtilisationLabel);
	}

	public void setCheckoutUsageLabel(String checkoutUsageLabel) {
		this.checkoutUsageLabel.setText(checkoutUsageLabel);
	}

	public void setCheckoutUtilisationLabel(String checkoutUtilisationLabel) {
		this.checkoutUtilisationLabel.setText(checkoutUtilisationLabel);
	}


	@Override
	public void kaynnistaSimulointi() {

	}

	@Override
	public void nopeuta() {

	}

	@Override
	public void hidasta() {

	}




	@Override
	public void updateTyytyvaisyys(double v) {
		Platform.runLater(new Runnable() {
			public void run() {
				tyytyvProsLuku.setText((int) v +"%");
			}
		});
	}

	@Override
	public void updateSuuJokaLiikkuu(double v) {
		Platform.runLater(new Runnable() {
			public void run() {
				suuJokaLiikkuu.setControlY(1.6 * v - 100);
				naamaPallo.setFill(javafx.scene.paint.Color.rgb(255 - (int) v, 31 + (int) v * 2, 31));
			}
		});
	}

	@Override
	public void updateAulaJonoPituus(double aulaJonoPit) {
		Platform.runLater(new Runnable() {
			public void run() {
				aulaJono.setStartX(aulaJono.getEndX() - (aulaJonoPit)*10);
				System.out.println("aulajono: " + aulaJonoPit);
			}
		});
	}

	@Override
	public void updateKassaJonoPituus(double kassaJonoPit) {
		Platform.runLater(new Runnable() {
			public void run() {
				kassaJono.setStartX(kassaJono.getEndX() - (kassaJonoPit)*10);
				System.out.println("kassajono: " + kassaJonoPit);
			}
		});
	}

	@Override
	public void updateReseptiJonoPituus(double reseptiJonoPit) {
		Platform.runLater(new Runnable() {
			public void run() {
				reseptiJono.setStartX(reseptiJono.getEndX() - (reseptiJonoPit)*10);
				System.out.println("reseptijono: " + reseptiJonoPit);
			}
		});
	}

	@Override
	public void updateHyllyJonoPituus(double hyllyJonoPit) {
		Platform.runLater(new Runnable() {
			public void run() {
				hyllytJono.setStartX(hyllytJono.getEndX() - (hyllyJonoPit)*10);
				System.out.println("hyllyjono: " + hyllyJonoPit);
			}
		});
	}

	@Override
	public void updateInfoJonoPituus(double infoJonoPit) {
		Platform.runLater(new Runnable() {
			public void run() {
				infoJono.setStartX(infoJono.getEndX() - (infoJonoPit)*10);
				System.out.println("infojono: " + infoJonoPit);
			}
		});
	}

	@FXML
	public void openHistory() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/History.fxml"));
		Parent newSceneRoot = loader.load();

		Scene newScene = new Scene(newSceneRoot);
		Stage newStage = new Stage();
		newStage.setTitle("History");
		newStage.setScene(newScene);
		newStage.showAndWait();
	}

}