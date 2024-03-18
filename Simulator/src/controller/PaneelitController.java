package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Ellipse;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import simu.framework.IMoottori;
import simu.framework.Kello;
import simu.model.OmaMoottori;
import simu.view.MainApp;
import java.io.IOException;

/**
 * PaneelitController-class is the controller for the Paneelit.fxml file. It is responsible for handling the user input and updating the UI.
 */
public class PaneelitController implements IKontrolleriForM {

	/**
	 * The following fields are the UI elements that are used to display the simulation data.
	 */

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
	private Line hyllytJono;

	@FXML
	private Line infoJono;

	@FXML
	private Line kassaJono;

	@FXML
	private Ellipse naamaPallo;

	@FXML
	private Line reseptiJono;

	@FXML
	private QuadCurve suuJokaLiikkuu;

	@FXML
	private Label tyytyvProsLuku;



	/**
	 * MainApp is the main class of the application. It is responsible for starting the application and setting the stage.
	 * Used as a reference to call the methods of the MainApp class.
	 */
	MainApp mainApp;

	/**
	 * moottori is the instance of the OmaMoottori class. It is used to start the simulation and to terminate it.
	 */
	IMoottori moottori;

	/**
	 * handleStart() is called when the startButton is clicked. It starts the simulation.
	 */
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

	/**
	 *  loop is used to change state of the button. If loop is 0, the button is set to "Stop" and the simulation is terminated. If loop is 1, the button is set to "Reset" and the simulation is reset.
	 */
	int loop = 0;

	/**
	 * handleSimuloi() is called when the simuloiButton is clicked. It stops the simulation if the loop is 0 and resets the simulation if the loop is 1.
	 */
	public void handleSimuloi() {

		if (loop == 0){
			simuloiButton.setText("Reset");
			System.out.println("Stop");
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

	/**
	 * setMainApp() is called to set the mainApp field.
	 * @param mainApp is the instance of the MainApp class.
	 * Here we set default values for the spinners and add listeners to the text fields to prevent the user from entering non-numeric values.
	 */
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


	/**
	 * simulationDone() is called when the simulation is done. It sets the loop to 0 and calls the handleSimuloi() method. Whi
	 */
	public void simulationDone() {
		loop = 1;
	}


	/**
	 * updateUI() is called to update the UI with the simulation data.
	 * @param aspaTyontekijat is the number of employees in the customer service.
	 * @param hyllyTyontekijat	is the number of employees in the shelves.
	 * @param reseptiTyontekijat	is the number of employees in the prescription.
	 * @param kassaTyontekijat	is the number of employees in the checkout.
	 * @param servedCustomers	is the number of customers served.
	 * @param missedCustomers	is the number of customers missed.
	 * @param aspaUsage	is the usage of the customer service.
	 * @param kauppaUsage	is the usage of the shelves.
	 * @param reseptiUsage	is the usage of the prescription.
	 * @param kassaUsage	is the usage of the checkout.
	 * @param satisfiedCustomers	is the number of satisfied customers.
	 * @param dissatisfiedCustomers	is the number of dissatisfied customers.
	 * @param overallSatisfaction	is the overall satisfaction of the customers.
	 * @param aspaUtilization	is the utilization of the customer service.
	 * @param kauppaUtilization	is the utilization of the shelves.
	 * @param reseptiUtilization	is the utilization of the prescription.
	 * @param kassaUtilization	is the utilization of the checkout.
	 * @param keskiarvo	is the average time of the customers.
	 */
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
				setInfoUtilisationLabel(String.valueOf(aspaUtilization)+"%");
				setPrescriptionUsageLabel(String.valueOf(kauppaUsage));
				setPrescriptionUtilisationLabel(String.valueOf(kauppaUtilization)+"%");
				setShelvesUsageLabel(String.valueOf(reseptiUsage));
				setShelvesUtilisationLabel(String.valueOf(reseptiUtilization)+"%");
				setCheckoutUsageLabel(String.valueOf(kassaUsage));
				setCheckoutUtilisationLabel(String.valueOf(kassaUtilization)+"%");
			}
		});
	}

	/**
	 * getA_staff() is called to get the number of employees in the customer service.
	 * @return the number of employees in the customer service.
	 */
	public int getA_staff() {
		return (int) a_staff.getValue();
	}

	/**
	 * getH_staff() is called to get the number of employees in the shelves.
	 * @return the number of employees in the shelves.
	 */
	public int getH_staff() {
		return (int) h_staff.getValue();
	}

	/**
	 * getR_staff() is called to get the number of employees in the prescription.
	 * @return the number of employees in the prescription.
	 */
	public int getR_staff() {
		return (int) r_staff.getValue();
	}

	/**
	 * getK_staff() is called to get the number of employees in the checkout.
	 * @return the number of employees in the checkout.
	 */
	public int getK_staff() {
		return (int) k_staff.getValue();
	}

	/**
	 * setSimulationTimeLabel() is called to set the simulationTimeLabel.
	 * @param simulationTimeLabel is the time of the simulation.
	 */
	public void setSimulationTimeLabel(String simulationTimeLabel) {
		this.simulationTimeLabel.setText(simulationTimeLabel);
	}

	/**
	 * setAverageTimeLabel() is called to set the averageTimeLabel.
	 * @param averageTimeLabel is the average time of the customers.
	 */
	public void setAverageTimeLabel(String averageTimeLabel) {
		this.averageTimeLabel.setText(averageTimeLabel);
	}

	/**
	 * setServedCustomersLabel() is called to set the servedCustomersLabel.
	 * @param servedCustomersLabel is the number of customers served.
	 */
	public void setServedCustomersLabel(String servedCustomersLabel) {
		this.servedCustomersLabel.setText(servedCustomersLabel);
	}

	/**
	 * setMissedCustomersLabel() is called to set the missedCustomersLabel.
	 * @param missedCustomersLabel is the number of customers missed.
	 */
	public void setMissedCustomersLabel(String missedCustomersLabel) {
		this.missedCustomersLabel.setText(missedCustomersLabel);
	}

	/**
	 * setSatisfiedCustomersLabel() is called to set the satisfiedCustomersLabel.
	 * @param satisfiedCustomersLabel is the number of satisfied customers.
	 */
	public void setSatisfiedCustomersLabel(String satisfiedCustomersLabel) {
		this.satisfiedCustomersLabel.setText(satisfiedCustomersLabel);
	}

	/**
	 * setDissatisfiedCustomersLabel() is called to set the dissatisfiedCustomersLabel.
	 * @param dissatisfiedCustomersLabel is the number of dissatisfied customers.
	 */
	public void setDissatisfiedCustomersLabel(String dissatisfiedCustomersLabel) {
		this.dissatisfiedCustomersLabel.setText(dissatisfiedCustomersLabel);
	}

	/**
	 * setInfoUsageLabel() is called to set the infoUsageLabel.
	 * @param infoUsageLabel is the usage of the customer service.
	 */
	public void setInfoUsageLabel(String infoUsageLabel) {
		this.infoUsageLabel.setText(infoUsageLabel);
	}

	/**
	 * setInfoUtilisationLabel() is called to set the infoUtilisationLabel.
	 * @param infoUtilisationLabel is the utilization of the customer service.
	 */
	public void setInfoUtilisationLabel(String infoUtilisationLabel) {
		this.infoUtilisationLabel.setText(infoUtilisationLabel);
	}

	/**
	 * setPrescriptionUsageLabel() is called to set the prescriptionUsageLabel.
	 * @param prescriptionUsageLabel is the usage of the shelves.
	 */

	public void setPrescriptionUsageLabel(String prescriptionUsageLabel) {
		this.prescriptionUsageLabel.setText(prescriptionUsageLabel);
	}

	/**
	 * setPrescriptionUtilisationLabel() is called to set the prescriptionUtilisationLabel.
	 * @param prescriptionUtilisationLabel is the utilization of the shelves.
	 */
	public void setPrescriptionUtilisationLabel(String prescriptionUtilisationLabel) {
		this.prescriptionUtilisationLabel.setText(prescriptionUtilisationLabel);
	}

	/**
	 * setShelvesUsageLabel() is called to set the shelvesUsageLabel.
	 * @param shelvesUsageLabel is the usage of the prescription.
	 */
	public void setShelvesUsageLabel(String shelvesUsageLabel) {
		this.shelvesUsageLabel.setText(shelvesUsageLabel);
	}

	/**
	 * setShelvesUtilisationLabel() is called to set the shelvesUtilisationLabel.
	 * @param shelvesUtilisationLabel is the utilization of the prescription.
	 */

	public void setShelvesUtilisationLabel(String shelvesUtilisationLabel) {
		this.shelvesUtilisationLabel.setText(shelvesUtilisationLabel);
	}

	/**
	 * setCheckoutUsageLabel() is called to set the checkoutUsageLabel.
	 * @param checkoutUsageLabel is the usage of the checkout.
	 */
	public void setCheckoutUsageLabel(String checkoutUsageLabel) {
		this.checkoutUsageLabel.setText(checkoutUsageLabel);
	}

	/**
	 * setCheckoutUtilisationLabel() is called to set the checkoutUtilisationLabel.
	 * @param checkoutUtilisationLabel is the utilization of the checkout.
	 */
	public void setCheckoutUtilisationLabel(String checkoutUtilisationLabel) {
		this.checkoutUtilisationLabel.setText(checkoutUtilisationLabel);
	}

	/**
	 * updateTyytyvaisyys() is called to update the satisfaction of the customers.
	 * @param v is the satisfaction of the customers.
	 */
	@Override
	public void updateTyytyvaisyys(double v) {
		Platform.runLater(new Runnable() {
			public void run() {
				tyytyvProsLuku.setText((int) v +"%");
			}
		});
	}

	/**
	 * updateSuuJokaLiikkuu() is called to update the mouth of the smiley face and the color of the smiley face.
	 * @param v is the satisfaction of the customers.
	 */
	@Override
	public void updateSuuJokaLiikkuu(double v) {
		Platform.runLater(new Runnable() {
			public void run() {
				suuJokaLiikkuu.setControlY(1.6 * v - 100);
				naamaPallo.setFill(javafx.scene.paint.Color.rgb(255 - (int) v, 31 + (int) v * 2, 31));
			}
		});
	}

	/**
	 * updateAulaJonoPituus() is called to update the length of the queue in the customer service.
	 * @param aulaJonoPit is the length of the queue in the customer service.
	 */
	@Override
	public void updateAulaJonoPituus(double aulaJonoPit) {
		Platform.runLater(new Runnable() {
			public void run() {
				aulaJono.setStartX(aulaJono.getEndX() - (aulaJonoPit)*10);
				System.out.println("aulajono: " + aulaJonoPit);
			}
		});
	}

	/**
	 * updateKassaJonoPituus() is called to update the length of the queue in the checkout.
	 * @param kassaJonoPit is the length of the queue in the checkout.
	 */
	@Override
	public void updateKassaJonoPituus(double kassaJonoPit) {
		Platform.runLater(new Runnable() {
			public void run() {
				kassaJono.setStartX(kassaJono.getEndX() - (kassaJonoPit)*10);
				System.out.println("kassajono: " + kassaJonoPit);
			}
		});
	}

	/**
	 * updateReseptiJonoPituus() is called to update the length of the queue in the prescription.
	 * @param reseptiJonoPit is the length of the queue in the prescription.
	 */
	@Override
	public void updateReseptiJonoPituus(double reseptiJonoPit) {
		Platform.runLater(new Runnable() {
			public void run() {
				reseptiJono.setStartX(reseptiJono.getEndX() - (reseptiJonoPit)*10);
				System.out.println("reseptijono: " + reseptiJonoPit);
			}
		});
	}

	/**
	 * updateHyllyJonoPituus() is called to update the length of the queue in the shelves.
	 * @param hyllyJonoPit is the length of the queue in the shelves.
	 */
	@Override
	public void updateHyllyJonoPituus(double hyllyJonoPit) {
		Platform.runLater(new Runnable() {
			public void run() {
				hyllytJono.setStartX(hyllytJono.getEndX() - (hyllyJonoPit)*10);
				System.out.println("hyllyjono: " + hyllyJonoPit);
			}
		});
	}

	/**
	 * updateInfoJonoPituus() is called to update the length of the queue in the customer service.
	 * @param infoJonoPit is the length of the queue in the customer service.
	 */
	@Override
	public void updateInfoJonoPituus(double infoJonoPit) {
		Platform.runLater(new Runnable() {
			public void run() {
				infoJono.setStartX(infoJono.getEndX() - (infoJonoPit)*10);
				System.out.println("infojono: " + infoJonoPit);
			}
		});
	}

	/**
	 * openHistory() is called to open the history window.
	 * @throws IOException if the file is not found.
	 */
	@FXML
	public void openHistory() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/History.fxml"));
		Parent newSceneRoot = loader.load();

		Scene newScene = new Scene(newSceneRoot);
		Stage newStage = new Stage();
		newStage.setTitle("History");
		newStage.setScene(newScene);
		newStage.show();
	}

}