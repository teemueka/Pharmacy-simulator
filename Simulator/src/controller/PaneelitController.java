package controller;



import javafx.application.Platform;
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
import simu.framework.IMoottori;
import simu.model.Asiakas;
import simu.model.OmaMoottori;
import simu.view.IVisualisointi;
import simu.view.MainApp;
import simu.view.Visualisointi;

public class PaneelitController implements IKontrolleriForV, IKontrolleriForM {
	private IMoottori moottori;

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
	private QuadCurve suuJokaLiikkuu;

	@FXML
	private Label tyytyvProsLuku;



	MainApp mainApp;


	public void handleStart() {
		System.out.println("Start");
		if (visualisointi == null) {
			visualisointi = new Visualisointi(visu);
			visualisointi.tyhjennaNaytto();

			handleSimuloi();
		}
		startButton.setDisable(true);
	}


	public void handleSimuloi() {
		int a = (int) getA_staff();
		int h = (int) getH_staff();
		int r = (int) getR_staff();
		int k = (int) getK_staff();


		moottori = new OmaMoottori(this, a, h, r, k); // luodaan uusi moottorisäie jokaista simulointia varten
		moottori.setSimulointiaika(100000);
		moottori.setViive(10);
		((Thread) moottori).start();

	}




	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.a_staff.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));
		this.h_staff.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));
		this.r_staff.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));
		this.k_staff.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));


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

	@Override
	public void updateTyytyvaisyys(double v) {
		Platform.runLater(new Runnable() {
			public void run() {
				tyytyvProsLuku.setText(String.valueOf((int)v));
			}
		});
	}
	public void updateSuuJokaLiikkuu(double v){
		Platform.runLater(new Runnable() {
			public void run() {
		suuJokaLiikkuu.setControlY(1.6 * v - 100);
	}
	});
	}
}