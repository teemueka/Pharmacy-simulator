package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import simu.framework.IMoottori;
import simu.model.OmaMoottori;
import simu.view.IVisualisointi;
import simu.view.MainApp;
import simu.view.Visualisointi;

public class PaneelitController implements IKontrolleriForV, IKontrolleriForM {


    @FXML
	private Canvas visu;    // Käyttöliittymäkomponentti
	private IVisualisointi visualisointi = null; // Työjuhta


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


	MainApp mainApp;
	IMoottori moottori;


	public void handleStart() {

		int a = (int) getA_staff();
		int h = (int) getH_staff();
		int r = (int) getR_staff();
		int k = (int) getK_staff();
		System.out.println("Start");
		visualisointi = new Visualisointi(visu);
		visualisointi.tyhjennaNaytto();

		moottori = new OmaMoottori(this, a, h, r, k, Integer.parseInt(customerntencityText.getText()), Integer.parseInt(storeCpacity.getText())); // luodaan uusi moottorisäie jokaista simulointia varten
		moottori.setSimulointiaika(Double.parseDouble(timeText.getText()));
		moottori.setViive(Long.parseLong(delayText.getText()));
		((Thread) moottori).start();

		startButton.setDisable(true);
		simuloiButton.setDisable(false);

	}


	public void handleSimuloi() {
		moottori.terminate();
		visualisointi.tyhjennaNaytto();
		simu.framework.Kello.getInstance().reset();
		simu.model.Asiakas.reset();


		startButton.setDisable(false);
		simuloiButton.setDisable(true);
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

	public void simulationDone(){
		simuloiButton.setDisable(false);

	}



//	@Override
//	public double getAika() {
//		return Double.parseDouble(aika.getText());
//	}
//
//	@Override
//	public long getViive() {
//		return Long.parseLong(viive.getText());
//	}


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
	public void kaynnistaSimulointi() {

	}

	@Override
	public void nopeuta() {

	}

	@Override
	public void hidasta() {

	}

	public void visualisoiMenetettyAsiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				visualisointi.menetettyAsiakas();
			}
		});
	}

	@Override
	public void visualisoiUusiAsiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				visualisointi.uusiAsiakas();
			}
		});

	}
}