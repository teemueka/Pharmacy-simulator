package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
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
import simu.framework.IMoottori;
import simu.model.Asiakas;
import simu.model.OmaMoottori;
import simu.view.IVisualisointi;
import simu.view.MainApp;


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
		simuloiButton.setDisable(false);

	}


	public void handleSimuloi() {
		moottori.terminate();

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

	public void simulationDone() {
		simuloiButton.setDisable(false);

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
}